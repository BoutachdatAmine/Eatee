package be.ehb.progproj2.eatee.entity;

import java.sql.Date;
import java.util.List;

public class Menu {
    private int menuId;
    private Date validFrom;
    private Date validTo;
    private List<Integer> products;

    public int getMenuId() {
        return menuId;
    }
    public Date getValidFrom() {
        return validFrom;
    }
    public Date getValidTo() {
        return validTo;
    }
    public List<Integer> getProducts() {
        return products;
    }
}
