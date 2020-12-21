package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Employee;
import be.ehb.progproject2.eatee.entity.User;
import be.ehb.progproject2.eatee.entity.request.EmployeeBody;
import be.ehb.progproject2.eatee.entity.request.UserBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends BaseDAO {

    public List<Employee> retrieveAll() {
        List<Integer> employeeIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Employee;")) {
                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        employeeIds.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Employee> employees = new ArrayList<>();
        for(int employeeId : employeeIds)
            employees.add(retrieve(employeeId));

        return employees;
    }

    public Employee retrieve(int employeeId) {
        Employee employee = null;
        List<Integer> posts = new PostDAO().getPostIdsFromEmployee(employeeId);

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Employee WHERE Id=?;")) {
                ps.setInt(1, employeeId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        employee = new Employee(
                                rs.getInt("UserId"),
                                null,
                                null,
                                null,
                                null,
                                null,
                                rs.getInt("Id"),
                                rs.getInt("Role"),
                                rs.getBoolean("Activated"),
                                posts
                        );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        User user = new UserDAO().retrieve(employee.getUserId());
        employee.setFirstname(user.getFirstname());
        employee.setLastname(user.getLastname());
        employee.setPassword(user.getPassword());
        employee.setEmail(user.getEmail());
        employee.setTwoFactorKey(user.getTwoFactorKey());

        return employee;
    }

    public boolean update(int employeeId, EmployeeBody body) {
        Employee employee = retrieve(employeeId);

        try (Connection c = getConnection()) {
            // Update Employee
            try(PreparedStatement ps3 = c.prepareStatement("UPDATE Employee SET `Role`=?, Activated=? WHERE Id=?;")) {
                ps3.setInt(3, employeeId);

                if (body.getRole() > 0) ps3.setInt(1, body.getRole());
                else ps3.setInt(1, employee.getRole());

                if (body.getActivated() != null) ps3.setBoolean(2, body.getActivated());
                else ps3.setBoolean(2, employee.isActivated());

                ps3.executeUpdate();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Update user
        UserDAO userDAO = new UserDAO();
        return userDAO.update(employee.getUserId(), body);
    }

    public boolean create(EmployeeBody body) throws Exception {
        body.checkFields();

        // Create User
        int userId = new UserDAO().create(body);
        if(userId < 0) return false;

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Employee VALUES(NULL, ?, ?, ?);")) {
                ps.setInt(1, userId);
                ps.setInt(2, body.getRole());
                ps.setBoolean(3, body.getActivated());
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean delete(int employeeId) {
        // Delete posts
        PostDAO postDAO = new PostDAO();
        for(int postId : postDAO.getPostIdsFromEmployee(employeeId))
            postDAO.delete(postId);

        UserDAO userDAO = new UserDAO();
        int userId = userDAO.getUserIdFromEmployee(employeeId);

        try (Connection c = getConnection()) {
            // Delete employee
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM Employee WHERE Id=?;")) {
                ps.setInt(1, employeeId);
                ps.executeUpdate();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Delete user
        return userDAO.delete(userId);
    }
}
