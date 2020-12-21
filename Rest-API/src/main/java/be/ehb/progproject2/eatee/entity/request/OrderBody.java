package be.ehb.progproject2.eatee.entity.request;

import be.ehb.progproject2.eatee.entity.OrderStatus;

import java.sql.Timestamp;
import java.util.List;

public class OrderBody {
    private int customerId;
    private OrderStatus status;
    private Timestamp orderedAt;
    private Timestamp orderedFor;

    public OrderBody(int customerId, OrderStatus status, Timestamp orderedFor) {
        this.customerId = customerId;
        this.status = status;
        this.orderedAt = new Timestamp(System.currentTimeMillis());
        this.orderedFor = orderedFor;
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

    public void checkFields() throws Exception {
        if(customerId <= 0)
            throw new Exception("The following fields are required: customerId, status, orderedFor");

        if(status == null) {
            String msg = "The field status is required and can have one of the following values: ";
            for(OrderStatus status : OrderStatus.values())
                msg += status.toString()
                        + (status.toString().equals(OrderStatus.values()[OrderStatus.values().length-1].toString()) ? "":", ");
            throw new Exception(msg);
        }

        if(orderedFor == null)
            throw new Exception("The field orderedFor is required in the following format: YYYY-MM-DD");
    }
}
