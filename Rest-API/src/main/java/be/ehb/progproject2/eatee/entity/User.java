package be.ehb.progproject2.eatee.entity;

public class User {
    private int userId;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String twoFactorKey;

    public User(int userId, String firstname, String lastname, String password, String email, String twoFactorKey) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.twoFactorKey = twoFactorKey;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwoFactorKey() {
        return twoFactorKey;
    }

    public void setTwoFactorKey(String twoFactorKey) {
        this.twoFactorKey = twoFactorKey;
    }
}
