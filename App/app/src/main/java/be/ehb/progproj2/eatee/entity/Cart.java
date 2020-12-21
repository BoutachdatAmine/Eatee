package be.ehb.progproj2.eatee.entity;

import java.util.Map;

public class Cart {
    private int customerId;
    private double total;

    public int getCustomerId() {
        return customerId;
    }

    private Map<Integer, Integer> products;
    private Map<Integer, Integer> sandwiches;

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public Map<Integer, Integer> getSandwiches() {
        return sandwiches;
    }

    public double getTotal() {
        return total;
    }
}
