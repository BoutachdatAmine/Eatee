package be.ehb.progproj2.eatee.entity;

import java.util.List;

public class Customer {
    private int customerId;
    private List<Integer> discounts;
    private Cart cart;
    private List<Integer> customSandwiches;
    private List<Integer> orders;
    private List<Integer> allergies;
    private int userId;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private boolean TFA;
    private String twoFactorKey;

    public Customer(String firstName, String lastName, String password, String email, boolean TFA) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.password = password;
        this.email = email;
        this.TFA = TFA;
    }

    public Customer() {
    }

    public int getCustomerId() {
        return customerId;
    }
    public List<Integer> getDiscounts() {
        return discounts;
    }
    public Cart getCart() {
        return cart;
    }
    public List<Integer> getCustomSandwiches() {
        return customSandwiches;
    }
    public List<Integer> getOrders() {
        return orders;
    }
    public List<Integer> getAllergies() {
        return allergies;
    }
    public int getUserId() {
        return userId;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public boolean isTFA() {
        return TFA;
    }
    public String getTwoFactorKey() {
        return twoFactorKey;
    }
}
