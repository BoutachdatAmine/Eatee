package be.ehb.progproj2.eatee.entity;

import java.util.List;

public class CustomSandwich {
    private int id;
    private int customerId;
    private List<Integer> ingredients;

    public int getId() {
        return id;
    }
    public int getCustomerId() {
        return customerId;
    }
    public List<Integer> getIngredients() {
        return ingredients;
    }
}
