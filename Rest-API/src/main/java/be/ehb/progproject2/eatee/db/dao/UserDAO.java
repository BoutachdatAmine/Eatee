package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.User;
import be.ehb.progproject2.eatee.entity.request.AuthUser;
import be.ehb.progproject2.eatee.entity.request.PasswordBody;
import be.ehb.progproject2.eatee.entity.request.UserBody;
import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;

public class UserDAO extends BaseDAO {
    public User retrieve(int userId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM `User` WHERE Id=?;")) {
                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getInt("Id"),
                                rs.getString("FirstName"),
                                rs.getString("LastName"),
                                rs.getString("Password"),
                                rs.getString("Email"),
                                rs.getString("TwoFactorKey")
                        );
                    }
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean update(int userId, UserBody body) {
        boolean success = false;
        User user = retrieve(userId);

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("UPDATE User SET FirstName=?, LastName=?, Email=?, Password=?, TwoFactorKey=? WHERE Id=?;")) {
                ps.setInt(6, userId);

                if (body.getFirstName() != null) ps.setString(1, body.getFirstName());
                else ps.setString(1, user.getFirstname());

                if (body.getLastName() != null) ps.setString(2, body.getLastName());
                else ps.setString(2, user.getLastname());

                if (body.getEmail() != null) ps.setString(3, body.getEmail());
                else ps.setString(3, user.getEmail());

                if (body.getPassword() != null) {
                    // Hash password using BCrypt
                    String pw_hash = BCrypt.hashpw(body.getPassword(), BCrypt.gensalt());
                    ps.setString(4, pw_hash);
                } else ps.setString(4, user.getPassword());

                // Two factor
                if (body.getTwoFactorKey() == null) {
                    // Dont edit, leave as it is
                    ps.setString(5, user.getTwoFactorKey());
                } else {
                    // Turn off (or generate new key if true, see below)
                    ps.setString(5, null);
                }

                success = ps.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(body.getTwoFactorKey() != null && body.getTwoFactorKey()) {
            enableTwoFactor(userId);
        }
        return success;
    }

    public int authenticateEmployee(AuthUser body) throws Exception {
        body.checkFields();

        try (Connection c = getConnection()) {
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `User` WHERE Email=?;")) {
                ps.setString(1, body.getEmail());

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Check if employee
                        try(PreparedStatement ps1 = c.prepareStatement("SELECT * FROM Employee WHERE UserId=?;")) {
                            ps1.setInt(1, rs.getInt("Id"));

                            try(ResultSet rs1 = ps1.executeQuery()) {
                                // No employee found with UserId
                                if (!rs1.next()) {
                                    return -3;
                                }

                                // Account not activated
                                if (!rs1.getBoolean("Activated")) {
                                    return -7;
                                }


                                // Employee found with UserId
                                if (BCrypt.checkpw(body.getPassword(), rs.getString("Password"))) {
                                    // Entered password equals hash

                                    // Check TwoFactor
                                    if (rs.getString("TwoFactorKey") == null) {
                                        // TwoFactorKey disabled, return EmployeeId
                                        return rs1.getInt("Id");
                                    } else {
                                        // TwoFactorKey enabled
                                        if (body.getTwoFactorKey() == null) {
                                            // TwoFactorKey not in request
                                            return -5;
                                        } else {
                                            // TwoFactorKey in request
                                            String twoFactorKey = rs.getString("TwoFactorKey");  // Two factor key, 32 chars
                                            String enteredCode = body.getTwoFactorKey();                    // 6 digit code

                                            TimeProvider timeProvider = new SystemTimeProvider();
                                            CodeGenerator codeGenerator = new DefaultCodeGenerator();
                                            CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

                                            // secret = the shared secret for the user
                                            // code = the code submitted by the user
                                            boolean successful = verifier.isValidCode(twoFactorKey, enteredCode);

                                            if (successful) {
                                                // Correct code, return CustomerId
                                                return rs1.getInt("Id");
                                            } else {
                                                // Wrong code
                                                return -6;
                                            }
                                        }
                                    }

                                } else {
                                    // Wrong password
                                    return -4;
                                }
                            }
                        }
                    } else {
                        // Email not found in DB
                        return -2;
                    }
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Database connection failed
        return -1;
    }

    public int authenticateCustomer(AuthUser body) throws Exception {
        // Fields filled in
        body.checkFields();

        try (Connection c = getConnection()) {
            // Check if email exists
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM `User` WHERE Email=?;")) {
                ps.setString(1, body.getEmail());


                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Check if Customer
                        try(PreparedStatement ps1 = c.prepareStatement("SELECT * FROM Customer WHERE UserId=?;")) {
                            ps1.setInt(1, rs.getInt("Id"));

                            try(ResultSet rs1 = ps1.executeQuery()) {
                                // No customer found with UserId
                                if (!rs1.next()) {
                                    return -3;
                                }


                                // Customer found with UserId
                                if (BCrypt.checkpw(body.getPassword(), rs.getString("Password"))) {
                                    // Entered password equals hash

                                    // Check TwoFactor
                                    if (rs.getString("TwoFactorKey") == null) {
                                        // TwoFactorKey disabled, return CustomerId
                                        return rs1.getInt("Id");

                                    } else {
                                        // TwoFactorKey enabled
                                        if (body.getTwoFactorKey() == null) {
                                            // TwoFactorKey not in request
                                            return -5;
                                        } else {
                                            // TwoFactorKey in request
                                            String twoFactorKey = rs.getString("TwoFactorKey");  // Two factor key, 32 chars
                                            String enteredCode = body.getTwoFactorKey();                    // 6 digit code

                                            TimeProvider timeProvider = new SystemTimeProvider();
                                            CodeGenerator codeGenerator = new DefaultCodeGenerator();
                                            CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

                                            // secret = the shared secret for the user
                                            // code = the code submitted by the user
                                            boolean successful = verifier.isValidCode(twoFactorKey, enteredCode);

                                            if (successful) {
                                                // Correct code, return CustomerId
                                                return rs1.getInt("Id");
                                            } else {
                                                // Wrong code
                                                return -6;
                                            }
                                        }
                                    }

                                } else {
                                    // Wrong password
                                    return -4;
                                }
                            }
                        }

                    } else {
                        // Email not found in DB
                        return -2;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Database connection error
        return -1;
    }

    public int create(UserBody body) throws Exception {
        body.checkFields();

        int userId = -1;

        try (Connection c = getConnection()) {
            // Check if email exists
            try (PreparedStatement ps1 = c.prepareStatement("SELECT Email FROM `User` WHERE Email=?;")) {
                ps1.setString(1, body.getEmail());

                try (ResultSet rs1 = ps1.executeQuery()) {
                    // Email exists in DB
                    if (rs1.next())
                        return -1;
                }
            }

            // Create user
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO `User` VALUES(NULL, ?, ?, ?, ?, NULL);", Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, body.getFirstName());
                ps.setString(2, body.getLastName());
                ps.setString(3, body.getEmail());

                // Hash password using BCrypt
                String pw_hash = BCrypt.hashpw(body.getPassword(), BCrypt.gensalt());
                ps.setString(4, pw_hash);
                ps.executeUpdate();

                try(ResultSet key = ps.getGeneratedKeys()) {
                    if (key.next())
                        userId = key.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Enable TwoFactor authentication
        if(userId > 0 && body.getTwoFactorKey() != null && body.getTwoFactorKey())
            enableTwoFactor(userId);

        return userId;
    }

    public boolean delete(int userId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM `User` WHERE Id=?;")) {
                ps.setInt(1, userId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public int getUserIdFromCustomer(int customerId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT UserId FROM Customer WHERE Id=?;")) {
                ps.setInt(1, customerId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return rs.getInt("UserId");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int getUserIdFromEmployee(int employeeId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT UserId FROM Employee WHERE Id=?;")) {
                ps.setInt(1, employeeId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return rs.getInt("UserId");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public String enableTwoFactor(int userId) {
        // Generate a secret that is 32 chars long
        SecretGenerator secretGenerator = new DefaultSecretGenerator();
        String secret = secretGenerator.generate();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("UPDATE `User` SET TwoFactorKey=? WHERE Id=?;")) {
                ps.setInt(2, userId);
                ps.setString(1, secret);  // TWO FACTOR KEY
                ps.executeUpdate();

                return secret;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean checkPassword(int customerId, PasswordBody body) throws Exception {
        body.checkField();
        int userId = getUserIdFromCustomer(customerId);

        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT Password FROM `User` WHERE Id=?;");
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    // Found User with id
                    return BCrypt.checkpw(body.getPassword(), rs.getString("Password"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
