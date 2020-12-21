package be.ehb.progproject2.eatee.entity;

public class Post {
    private int id;
    private int employeeId;
    private String title;
    private String description;
    private int likes;

    public Post(int id, int employeeId, String title, String description, int likes) {
        this.id = id;
        this.employeeId = employeeId;
        this.title = title;
        this.description = description;
        this.likes = likes;
    }

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

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", likes=" + likes +
                '}';
    }
}
