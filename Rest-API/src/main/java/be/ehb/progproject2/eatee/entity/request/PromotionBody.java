package be.ehb.progproject2.eatee.entity.request;

import java.sql.Date;

public class PromotionBody {
    private int product;
    private double value;
    private Date validFrom;
    private Date validTo;

    public int getProduct() {
        return product;
    }
    public double getValue() {
        return value;
    }
    public Date getValidFrom() {
        return validFrom;
    }
    public Date getValidTo() {
        return validTo;
    }

    public void checkFields() throws Exception {
        if(product <= 0 || value <= 0 || validFrom == null || validTo == null)
            throw new Exception("The following fields are required: product, value, validFrom, validTo");

        if(validFrom.after(validTo))
            throw new Exception("The field validFrom cannot be after the field validTo");

        if(validTo.before(validFrom))
            throw new Exception("The field validTo cannot be before the field validFrom");
    }
}
