package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.*;
import be.ehb.progproject2.eatee.entity.*;
import be.ehb.progproject2.eatee.entity.request.DiscountBody;
import be.ehb.progproject2.eatee.entity.request.SandwichBody;
import be.ehb.progproject2.eatee.entity.request.UserBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public class CustomerController {

    // Create
    @PostMapping("/")
    public Customer create(@RequestBody UserBody body) throws Exception {
        CustomerDAO customerDAO = new CustomerDAO();
        int customerId = new CustomerDAO().create(body);

        if(customerId < 0) return new Customer(
                customerId
        );
        else return customerDAO.retrieve(customerId);
    }

    // Read all
    @GetMapping("/")
    public List<Customer> readAll() throws SQLException {
        return new CustomerDAO().retrieveAll();
    }

    // Read one
    @GetMapping("/{customerId}")
    public User read(@PathVariable int customerId) throws SQLException {
        return new CustomerDAO().retrieve(customerId);
    }

    // Update
    @PatchMapping("/{customerId}")
    public boolean update(@PathVariable int customerId, @RequestBody UserBody body) throws SQLException {
        return new CustomerDAO().update(customerId, body);
    }

    // Delete
    @DeleteMapping("/{customerId}")
    public boolean delete(@PathVariable int customerId) throws SQLException {
        return new CustomerDAO().delete(customerId);
    }

    // Read custom sandwiches
    @GetMapping("/{customerId}/custom")
    public List<CustomSandwich> readCustom(@PathVariable int customerId) throws SQLException {
        List<CustomSandwich> sandwiches = new ArrayList<>();
        CustomSandwichDAO dao = new CustomSandwichDAO();
        for(int sandwichId : dao.retrieveSandwichIdsFromCustomer(customerId))
            sandwiches.add(dao.retrieve(sandwichId));

        return sandwiches;
    }

    // Read discounts
    @GetMapping("/{customerId}/discounts")
    public List<Discount> retrieveDiscounts(@PathVariable int customerId) throws SQLException {
        List<Discount> discounts = new ArrayList<>();
        DiscountDAO discountDAO = new DiscountDAO();
        for(int discountId : discountDAO.getDiscountIdsFromCustomer(customerId))
            discounts.add(discountDAO.retrieve(discountId));

        return discounts;
    }

    // Create discount
    @PostMapping("/{customerId}/discount")
    public boolean createDiscount(@PathVariable int customerId, @RequestBody DiscountBody body) throws Exception {
        return new DiscountDAO().create(customerId, body);
    }

    // Create custom sandwich
    @PostMapping("/{customerId}/custom")
    public int createCustom(@PathVariable int customerId, @RequestBody SandwichBody body) throws Exception {
        return new CustomSandwichDAO().createCustom(customerId, body);
    }

    @GetMapping("/{customerId}/orders")
    public List<Order> retrieveOrders(@PathVariable int customerId) throws SQLException {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = new ArrayList<>();
        for(int orderId : orderDAO.getOrderIdsFromCustomer(customerId))
            orders.add(orderDAO.retrieveOne(orderId));

        return orders;
    }

    // Show cart
    @GetMapping("/{customerId}/cart")
    public Cart retrieveCart(@PathVariable int customerId) throws SQLException {
        return new CustomerDAO().retrieveCart(customerId);
    }

    // Add product to cart
    @PostMapping("/{customerId}/cart/p/{productId}")
    public boolean addProductToCart(@PathVariable int customerId, @PathVariable int productId) throws SQLException {
        return new ProductDAO().addProductToCart(customerId, productId);
    }

    // Remove product from cart
    @DeleteMapping("/{customerId}/cart/p/{cartId}")
    public boolean removeProductFromCart(@PathVariable int customerId, @PathVariable int cartId) throws SQLException {
        return new ProductDAO().removeProductFromCart(customerId, cartId);
    }

    // Add sandwich to cart
    @PostMapping("/{customerId}/cart/s/{sandwichId}")
    public boolean addSandwichToCart(@PathVariable int customerId, @PathVariable int sandwichId) throws SQLException {
        return new ProductDAO().addSandwichToCart(customerId, sandwichId);
    }

    // Remove sandwich from cart
    @DeleteMapping("/{customerId}/cart/s/{cartId}")
    public boolean removeSandwichFromCart(@PathVariable int customerId, @PathVariable int cartId) throws SQLException {
        return new ProductDAO().removeSandwichFromCart(customerId, cartId);
    }
}
