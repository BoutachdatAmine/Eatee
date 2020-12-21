package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Order;
import be.ehb.progproject2.eatee.entity.OrderStatus;
import be.ehb.progproject2.eatee.entity.request.OrderBody;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends BaseDAO {

    public List<Integer> getOrderIdsFromCustomer(int customerId) {
        List<Integer> orders = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM `Order` WHERE CustomerId=?;")) {
                ps.setInt(1, customerId);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        orders.add(rs.getInt("Id"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return orders;
    }

    public boolean delete(int orderId) {
        
        try (Connection c = getConnection()) {
            // Delete products of order
            try(PreparedStatement ps1 = c.prepareStatement("DELETE FROM Order_Product WHERE OrderId=?;")) {
                ps1.setInt(1, orderId);
                ps1.executeUpdate();
            }

            // Delete order
            try(PreparedStatement ps2 = c.prepareStatement("DELETE FROM `Order` WHERE Id=?;")) {
                ps2.setInt(1, orderId);
                return ps2.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Order retrieveOne(int orderId) {
        ProductDAO productDAO = new ProductDAO();
        List<Integer> productIds = productDAO.getProductIdsFromOrder(orderId);
        List<Integer> sandwichIds = productDAO.getSandwichIdsFromOrder(orderId);

        double total = 0;
        for(int productId : productIds)
            total += productDAO.getPriceFromProduct(productId);

        // Custom sandwiches always cost 4 euros
        for(int sandwichId : sandwichIds)
            total += 4;

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM `Order` WHERE Id=?;")) {
                ps.setInt(1, orderId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return new Order(
                                rs.getInt("Id"),
                                rs.getInt("CustomerId"),
                                OrderStatus.valueOf(rs.getString("Status")),
                                rs.getTimestamp("OrderedAt"),
                                rs.getTimestamp("OrderedFor"),
                                productIds,
                                sandwichIds,
                                total
                        );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Order> retrieveAll(Date orderedFor) {
        List<Integer> orderIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            PreparedStatement ps = null;
            if(orderedFor == null) ps = c.prepareStatement("SELECT Id FROM `Order`;");
            else  {
                ps = c.prepareStatement("SELECT Id FROM `Order` WHERE OrderedFor BETWEEN ? AND ?;");
                ps.setTimestamp(1, Timestamp.valueOf(orderedFor.toString() + " 00:00:00"));
                ps.setTimestamp(2, Timestamp.valueOf(orderedFor.toString() + " 23:59:59"));
            }

            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next())
                    orderIds.add(rs.getInt("Id"));
            } finally {
                ps.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Order> orders = new ArrayList<>();
        for(int orderId : orderIds)
            orders.add(retrieveOne(orderId));

        return orders;
    }

    public int create(OrderBody body) throws Exception {
        body.checkFields();

        try (Connection c = getConnection()) {
            // Create order
            int orderId = -1;
            try(PreparedStatement ps1 = c.prepareStatement("INSERT INTO `Order` VALUES(NULL, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
                ps1.setInt(1, body.getCustomerId());
                ps1.setString(2, body.getStatus().toString());
                ps1.setTimestamp(3, body.getOrderedAt());
                ps1.setTimestamp(4, body.getOrderedFor());
                ps1.executeUpdate();

                try(ResultSet keys = ps1.getGeneratedKeys()) {
                    if (keys.next())
                        orderId = keys.getInt(1);
                }
            }

            // Get products from cart + delete
            List<Integer> products = new ArrayList<>();
            try(PreparedStatement ps3 = c.prepareStatement("SELECT ProductId FROM Cart_Product WHERE CustomerId=?;")) {
                ps3.setInt(1, body.getCustomerId());

                try (ResultSet rs3 = ps3.executeQuery()) {
                    while (rs3.next())
                        products.add(rs3.getInt("ProductId"));
                }
            }

            try(PreparedStatement ps4 = c.prepareStatement("DELETE FROM Cart_Product WHERE CustomerId=?;")) {
                ps4.setInt(1, body.getCustomerId());
                ps4.executeUpdate();
            }


            // Get sandwiches from cart + delete
            List<Integer> sandwiches = new ArrayList<>();

            try(PreparedStatement ps5 = c.prepareStatement("SELECT CustomSandwichId FROM Cart_Sandwich WHERE CustomerId=?;")) {
                ps5.setInt(1, body.getCustomerId());

                try (ResultSet rs5 = ps5.executeQuery()) {
                    while (rs5.next())
                        sandwiches.add(rs5.getInt("CustomSandwichId"));
                }
            }

            try(PreparedStatement ps6 = c.prepareStatement("DELETE FROM Cart_Sandwich WHERE CustomerId=?;")) {
                ps6.setObject(1, body.getCustomerId());
                ps6.executeUpdate();
            }

            // Put products in order
            try(PreparedStatement ps2 = c.prepareStatement("INSERT INTO Order_Product VALUES(?, ?);")) {
                for (int productId : products) {
                    ps2.setInt(1, orderId);
                    ps2.setInt(2, productId);
                    ps2.executeUpdate();
                }
            }

            // Put sandwiches in order
            try(PreparedStatement ps7 = c.prepareStatement("INSERT INTO Order_Sandwich VALUES(?, ?);")) {
                for (int sandwichId : sandwiches) {
                    ps7.setInt(1, orderId);
                    ps7.setInt(2, sandwichId);
                    ps7.executeUpdate();
                }
            }
            return orderId;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;

    }

    public boolean update(int orderId, OrderBody body) {
        Order order = retrieveOne(orderId);

        try (Connection c = getConnection()) {
            // Update Order
            try(PreparedStatement ps = c.prepareStatement("UPDATE `Order` SET Status=?,OrderedFor=? WHERE Id=?;")) {
                ps.setInt(3, orderId);

                if (body.getStatus() == null) ps.setString(1, order.getStatus().toString());
                else ps.setString(1, body.getStatus().toString());

                if (body.getOrderedFor() == null) ps.setTimestamp(2, order.getOrderedFor());
                else ps.setTimestamp(2, body.getOrderedFor());
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
