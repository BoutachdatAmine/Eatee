package be.ehb.progproject2.eatee.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Promotion {
    private int id;
    private int productId;
    private double value;
    private Date validFrom;
    private Date validTo;

    public Promotion(int id, int productId, double value, Date validFrom, Date validTo) {
        this.id = id;
        this.productId = productId;
        this.value = value;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public int getId() {
        return id;
    }
    public int getProductId() {
        return productId;
    }
    public double getValue() {
        return value;
    }
    public Date getValidFrom() {
        return validFrom;
    }
    public Date getValidTo() {
        return validTo;
    }
}
