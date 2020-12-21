package be.ehb.progproj2.eatee.entity;

import java.time.LocalDate;

public class Promotion {
    private double value;
    private LocalDate validFrom;
    private LocalDate validTo;

    public double getValue() {
        return value;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public LocalDate getValidTo() {
        return validTo;
    }
}
