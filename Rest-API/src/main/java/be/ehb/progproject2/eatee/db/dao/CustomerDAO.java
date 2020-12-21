package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Cart;
import be.ehb.progproject2.eatee.entity.Customer;
import be.ehb.progproject2.eatee.entity.User;
import be.ehb.progproject2.eatee.entity.request.UserBody;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CustomerDAO extends BaseDAO {
    // Returns CustomerId
    public int create(UserBody body) throws Exception {
        body.checkFields();

        // Create user
        int userId = new UserDAO().create(body);
        if(userId < 0) return userId;

        // Create customer
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Customer VALUES(NULL, ?);", Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.executeUpdate();

                try(ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next())
                        return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    public Customer retrieve(int customerId) throws SQLException {
        List<Integer> discounts = new DiscountDAO().getDiscountIdsFromCustomer(customerId);
        List<Integer> sandwiches = new CustomSandwichDAO().retrieveSandwichIdsFromCustomer(customerId);
        List<Integer> orders = new OrderDAO().getOrderIdsFromCustomer(customerId);
        Customer customer = null;

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Customer WHERE Id=?;")) {
                ps.setInt(1, customerId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        customer = new Customer(
                                rs.getInt("UserId"),
                                null,
                                null,
                                null,
                                null,
                                null,
                                rs.getInt("Id"),
                                discounts,
                                sandwiches,
                                orders
                        );

                }
            }
        }

        // User details
        User user = new UserDAO().retrieve(customer.getUserId());
        customer.setFirstname(user.getFirstname());
        customer.setLastname(user.getLastname());
        customer.setEmail(user.getEmail());
        customer.setPassword(user.getPassword());
        customer.setTwoFactorKey(user.getTwoFactorKey());
        return customer;
    }

    public List<Customer> retrieveAll() throws SQLException {
        List<Integer> customerIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Customer;")) {

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        customerIds.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Customer> customers = new ArrayList<>();
        for(int customerId : customerIds)
            customers.add(retrieve(customerId));

        return customers;
    }

    public boolean update(int customerId, UserBody body) throws SQLException {
        Customer customer = retrieve(customerId);
        return new UserDAO().update(customer.getUserId(), body);
    }

    public boolean delete(int customerId) throws SQLException {
        // Delete sandwiches
        CustomSandwichDAO sandwichDAO = new CustomSandwichDAO();
        for(int sandwichId : sandwichDAO.retrieveSandwichIdsFromCustomer(customerId))
            sandwichDAO.deleteCustom(sandwichId);

        // Delete discounts
        DiscountDAO discountDAO = new DiscountDAO();
        for(int discountId : discountDAO.getDiscountIdsFromCustomer(customerId))
            discountDAO.delete(discountId);

        // Delete orders
        OrderDAO orderDAO = new OrderDAO();
        for(int orderId : orderDAO.getOrderIdsFromCustomer(customerId))
            orderDAO.delete(orderId);

        // Get user id before deleting Customer
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.getUserIdFromCustomer(customerId);

        try (Connection c = getConnection()) {
            // Delete likes
            try(PreparedStatement ps2 = c.prepareStatement("DELETE FROM `Like` WHERE CustomerId=?;")) {
                ps2.setInt(1, customerId);
                ps2.executeUpdate();
            }

            // Delete customer
            try(PreparedStatement ps3 = c.prepareStatement("DELETE FROM Customer WHERE Id=?;")) {
                ps3.setInt(1, customerId);
                ps3.executeUpdate();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Delete user
        return userDAO.delete(userId);
    }

    public Cart retrieveCart(int customerId) throws SQLException {
        double total = 0;
        Map<Integer, Integer> products = new TreeMap<>();
        Map<Integer, Integer> sandwiches = new TreeMap<>();

        try (Connection c = getConnection()) {
            // Products in cart
            try(PreparedStatement ps = c.prepareStatement("SELECT Id,ProductId FROM Cart_Product WHERE CustomerId=?;")) {
                ps.setInt(1, customerId);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        products.put(rs.getInt("Id"), rs.getInt("ProductId"));
                }
            }

            // Sandwiches in cart
            try(PreparedStatement ps1 = c.prepareStatement("SELECT Id,CustomSandwichId FROM Cart_Sandwich WHERE CustomerId=?;")) {
                ps1.setInt(1, customerId);

                try(ResultSet rs1 = ps1.executeQuery()) {
                    while (rs1.next())
                        sandwiches.put(rs1.getInt("Id"), rs1.getInt("CustomSandwichId"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ProductDAO productDAO = new ProductDAO();
        for(Map.Entry<Integer, Integer> product : products.entrySet())
            total += productDAO.retrieveOne(product.getValue()).getPrice();

        for(Map.Entry<Integer, Integer> sandwich : sandwiches.entrySet())
            total += 4;

        // Cart
        return new Cart(
                customerId,
                products,
                sandwiches,
                total
        );
    }
}
