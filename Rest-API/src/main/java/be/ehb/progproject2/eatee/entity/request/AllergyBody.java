package be.ehb.progproject2.eatee.entity.request;

public class AllergyBody {
    private String name;

    public String getName() {
        return name;
    }

    public void checkFields() throws Exception {
        if(name == null)
            throw new Exception("The following field is required: name");
    }
}
