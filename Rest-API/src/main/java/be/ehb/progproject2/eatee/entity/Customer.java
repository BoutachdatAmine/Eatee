package be.ehb.progproject2.eatee.entity;

import java.util.List;

public class Customer extends User {
    private int customerId;
    private List<Integer> discounts;
    private List<Integer> customSandwiches;
    private List<Integer> orders;

    public Customer(int userId, String firstname, String lastname,
                    String password, String email, String twoFactorKey,
                    int customerId, List<Integer> discounts,
                    List<Integer> customSandwiches, List<Integer> orders) {
        super(userId, firstname, lastname, password, email, twoFactorKey);
        this.customerId = customerId;
        this.discounts = discounts;
        this.customSandwiches = customSandwiches;
        this.orders = orders;
    }

    public Customer(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }
    public List<Integer> getDiscounts() {
        return discounts;
    }
    public List<Integer> getCustomSandwiches() {
        return customSandwiches;
    }
    public List<Integer> getOrders() {
        return orders;
    }
}
