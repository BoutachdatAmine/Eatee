package be.ehb.progproject2.eatee.entity.request;

public class PostBody {
    private String title;
    private String description;

    public PostBody(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PostBody{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void checkFields() throws Exception {
        if(title == null || description == null)
            throw new Exception("The following fields are required: title, description");
    }
}
