package be.ehb.progproj2.eatee.entity;

public class Discount {
    private int discountId;
    private int customerId;
    private double value;
    private boolean used;

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
