package be.ehb.progproj2.eatee.entity;

public class Register {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String twoFactorKey;

    public Register(String firstName, String lastName, String password, String email, String twoFactorKey) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.twoFactorKey = twoFactorKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getTwoFactorKey() {
        return twoFactorKey;
    }

    @Override
    public String toString() {
        return "Register{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", twoFactorKey='" + twoFactorKey + '\'' +
                '}';
    }
}
