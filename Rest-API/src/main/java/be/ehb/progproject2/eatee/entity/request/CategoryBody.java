package be.ehb.progproject2.eatee.entity.request;

public class CategoryBody {
    private String name;

    public String getName() {
        return name;
    }

    public void checkField() throws Exception {
        if(name == null || name.isEmpty())
            throw new Exception("The following field is required: name");
    }
}
