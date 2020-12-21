package be.ehb.progproject2.eatee.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Payment {
    private String cardNumber;
    private Timestamp paymentDate;

    public Payment(String cardNumber, Timestamp paymentDate) {
        this.cardNumber = cardNumber;
        this.paymentDate = paymentDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }
}
