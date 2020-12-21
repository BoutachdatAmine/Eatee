package be.ehb.progproj2.eatee.entity;

public class AuthUser {

    private String email;
    private String password;
    private String twoFactorKey;

    //Auth user
    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Complete Auth user
    public AuthUser(String email, String password, String twoFactorKey) {
        this.email = email;
        this.password = password;
        this.twoFactorKey = twoFactorKey;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTwoFactorKey() {
        return twoFactorKey;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", twoFactorKey='" + twoFactorKey + '\'' +
                '}';
    }
}
