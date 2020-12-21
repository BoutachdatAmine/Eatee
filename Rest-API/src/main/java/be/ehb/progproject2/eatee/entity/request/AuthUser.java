package be.ehb.progproject2.eatee.entity.request;

public class AuthUser {
    private String email;
    private String password;
    private String twoFactorKey;

    public AuthUser(String email, String password) {
        this.email = email;
        this.password = password;
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

    public void checkFields() throws Exception {
        if(email == null || password == null)
            throw new Exception("The following fields are required: email, password");
    }
}
