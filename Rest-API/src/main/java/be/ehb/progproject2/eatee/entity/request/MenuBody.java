package be.ehb.progproject2.eatee.entity.request;

import java.sql.Date;
import java.util.List;

public class MenuBody {
    private Date validFrom;
    private Date validTo;
    private List<Integer> products;

    public Date getValidFrom() {
        return validFrom;
    }
    public Date getValidTo() {
        return validTo;
    }
    public List<Integer> getProducts() {
        return products;
    }

    public void checkFields() throws Exception {
        if(validFrom == null || validTo == null)
            throw new Exception("The following fields are required: validFrom, validTo");

        if(validTo.before(validFrom))
            throw new Exception("The field 'validTo' cannot be before 'validFrom'");
    }
}
