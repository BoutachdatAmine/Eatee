package be.ehb.progproj2.eatee.entity;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private OrderStatus status;
    private Timestamp orderedAt;
    private Timestamp orderedFor;
    private List<Integer> productIds;
    private List<Integer> sandwichIds;
    private double total;

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Timestamp getOrderedAt() {
        return orderedAt;
    }

    public Timestamp getOrderedFor() {
        return orderedFor;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }

    public List<Integer> getSandwichIds() {
        return sandwichIds;
    }

    public double getTotal() {
        return total;
    }
}
