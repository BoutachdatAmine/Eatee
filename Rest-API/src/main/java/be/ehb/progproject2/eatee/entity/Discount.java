package be.ehb.progproject2.eatee.entity;

public class Discount {
    private int discountId;
    private int customerId;
    private double value;
    private boolean used;

    public Discount(int discountId, int customerId, double value, boolean used) {
        this.discountId = discountId;
        this.customerId = customerId;
        this.value = value;
        this.used = used;
    }

    public int getDiscountId() {
        return discountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getValue() {
        return value;
    }

    public boolean isUsed() {
        return used;
    }
}
