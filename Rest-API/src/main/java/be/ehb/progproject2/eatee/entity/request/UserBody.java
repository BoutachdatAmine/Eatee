package be.ehb.progproject2.eatee.entity.request;

public class UserBody {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean twoFactorKey;

    public UserBody(String firstName, String lastName, String email, String password, Boolean twoFactorKey) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.twoFactorKey = twoFactorKey;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getTwoFactorKey() {
        return twoFactorKey;
    }

    public void checkFields() throws Exception {
        if(firstName == null         || firstName.isEmpty() ||
                lastName == null     || lastName.isEmpty()  ||
                email == null        || email.isEmpty()     ||
                password == null     || password.isEmpty())
            throw new Exception("The following fields are required: firstName, lastName, email, password");
    }
}
