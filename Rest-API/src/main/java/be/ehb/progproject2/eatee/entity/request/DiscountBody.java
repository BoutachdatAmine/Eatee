package be.ehb.progproject2.eatee.entity.request;

public class DiscountBody {
    private double value;

    public double getValue() {
        return value;
    }

    public void checkField() throws Exception {
        if(value == 0)
            throw new Exception("The following field is required: value");

        if(value < 0)
            throw new Exception("The field value cannot be under 0!");
    }
}
