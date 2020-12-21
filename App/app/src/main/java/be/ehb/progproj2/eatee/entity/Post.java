package be.ehb.progproj2.eatee.entity;

public class Post {
    private int id;
    private int employeeId;
    private String title;
    private String description;
    private int likes;

    public int getId() {
        return id;
    }
    public int getEmployeeId() {
        return employeeId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getLikes() {
        return likes;
    }
}
