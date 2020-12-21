package be.ehb.progproject2.eatee.entity;

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

    public Order(int orderId, int customerId, OrderStatus status, Timestamp orderedAt, Timestamp orderedFor, List<Integer> productIds, List<Integer> sandwichIds, double total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.orderedAt = orderedAt;
        this.orderedFor = orderedFor;
        this.productIds = productIds;
        this.sandwichIds = sandwichIds;
        this.total = total;
    }


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
