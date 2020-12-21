package be.ehb.progproj2.eatee.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Payment {
    private String cardNumber;
    private Timestamp paymentDate;

    public String getCardNumber() {
        return cardNumber;
    }
    public Timestamp getPaymentDate() {
        return paymentDate;
    }
}
