package be.ehb.progproject2.eatee.entity.response;

public class AuthCustomer {
    private int customerId;
    private boolean keyRequired;

    public AuthCustomer(int customerId, boolean keyRequired) {
        this.customerId = customerId;
        this.keyRequired = keyRequired;
    }

    public int getCustomerId() {
        return customerId;
    }

    public boolean isKeyRequired() {
        return keyRequired;
    }
}
