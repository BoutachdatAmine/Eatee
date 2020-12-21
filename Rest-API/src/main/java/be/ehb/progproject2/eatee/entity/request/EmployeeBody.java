package be.ehb.progproject2.eatee.entity.request;

public class EmployeeBody extends UserBody {
    private int role;
    private Boolean activated;

    public EmployeeBody(String firstName, String lastName, String email, String password, Boolean twoFactorKey) {
        super(firstName, lastName, email, password, twoFactorKey);
    }

    public int getRole() {
        return role;
    }
    public Boolean getActivated() {
        return activated;
    }

    public void checkFields() throws Exception {
        super.checkFields();

        if(activated == null || role <= 0)
            throw new Exception("The following fields are required: role, activated");
    }
}
