package be.ehb.progproject2.eatee.entity.response;

public class AuthEmployee {
    private int employeeId;
    private boolean keyRequired;

    public AuthEmployee(int employeeId, boolean keyRequired) {
        this.employeeId = employeeId;
        this.keyRequired = keyRequired;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public boolean isKeyRequired() {
        return keyRequired;
    }
}
