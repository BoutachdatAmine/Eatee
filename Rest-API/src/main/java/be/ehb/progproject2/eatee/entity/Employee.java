package be.ehb.progproject2.eatee.entity;

import java.util.List;

public class Employee extends User {
    private int employeeId;
    private int role;
    private boolean activated;
    private List<Integer> posts;

    public Employee(int userId, String firstname, String lastname, String password, String email, String twoFactorKey, int employeeId, int role, boolean activated, List<Integer> posts) {
        super(userId, firstname, lastname, password, email, twoFactorKey);
        this.employeeId = employeeId;
        this.role = role;
        this.activated = activated;
        this.posts = posts;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public int getRole() {
        return role;
    }
    public boolean isActivated() {
        return activated;
    }
    public List<Integer> getPosts() {
        return posts;
    }
}
