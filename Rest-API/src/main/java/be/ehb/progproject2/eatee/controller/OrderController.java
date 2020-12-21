package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.OrderDAO;
import be.ehb.progproject2.eatee.entity.Order;
import be.ehb.progproject2.eatee.entity.request.OrderBody;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {

    // Create
    @PostMapping("/")
    public int create(@RequestBody OrderBody body) throws Exception {
        return new OrderDAO().create(body);
    }

    // Retrieve all
    @GetMapping("/")
    public List<Order> allOrders(@RequestParam(required = false) Date orderedFor) throws SQLException {
        return new OrderDAO().retrieveAll(orderedFor);
    }

    // Retrieve one
    @GetMapping("/{orderId}")
    public Order read(@PathVariable int orderId) throws SQLException {
        return new OrderDAO().retrieveOne(orderId);
    }

    // Update
    @PatchMapping("/{orderId}")
    public boolean update(@PathVariable int orderId, @RequestBody OrderBody body) throws SQLException {
        return new OrderDAO().update(orderId, body);
    }

    // Delete
    @DeleteMapping("/{orderId}")
    public boolean delete(@PathVariable int orderId) throws SQLException {
        return new OrderDAO().delete(orderId);
    }
}
