package be.ehb.progproject2.eatee.entity;

import java.sql.Date;
import java.util.List;

public class Menu {
    private int menuId;
    private Date validFrom;
    private Date validTo;
    private List<Integer> products;

    public Menu(int menuId, Date validFrom, Date validTo, List<Integer> products) {
        this.menuId = menuId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.products = products;
    }

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
