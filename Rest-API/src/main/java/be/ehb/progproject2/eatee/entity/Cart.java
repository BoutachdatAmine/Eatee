package be.ehb.progproject2.eatee.entity;

import java.util.List;
import java.util.Map;

public class Cart {
    private int customerId;
//    private List<Integer> products;
//    private List<Integer> sandwiches;
    private Map<Integer, Integer> products;
    private Map<Integer, Integer> sandwiches;
    private double total;

    public Cart(int customerId, Map<Integer, Integer> products, Map<Integer, Integer> sandwiches, double total) {
        this.customerId = customerId;
        this.products = products;
        this.sandwiches = sandwiches;
        this.total = total;
    }

    public int getCustomerId() {
        return customerId;
    }
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
