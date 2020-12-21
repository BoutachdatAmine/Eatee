package be.ehb.progproject2.eatee.entity.request;

public class PasswordBody {
    private String password;

    public PasswordBody(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void checkField() throws Exception {
        if(password == null || password.isEmpty())
            throw new Exception("The following field is required: password");
    }
}
