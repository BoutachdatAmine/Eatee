package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Discount;
import be.ehb.progproject2.eatee.entity.request.DiscountBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO extends BaseDAO {

    public Discount retrieve(int discountId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Discount WHERE Id=?;")) {
                ps.setInt(1, discountId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return new Discount(
                                rs.getInt("Id"),
                                rs.getInt("CustomerId"),
                                rs.getDouble("Value"),
                                rs.getBoolean("Used")
                        );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Integer> getDiscountIdsFromCustomer(int customerId) {
        List<Integer> discounts = new ArrayList<>();
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Discount WHERE CustomerId=?;")) {
                ps.setInt(1, customerId);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        discounts.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return discounts;
    }

    public boolean delete(int discountId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM Discount WHERE Id=?:")) {
                ps.setInt(1, discountId);
                return ps.executeUpdate() > 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean create(int customerId, DiscountBody body) throws Exception {
        body.checkField();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Discount VALUES(NULL, ?, ?, ?);")) {
                ps.setInt(1, customerId);
                ps.setDouble(2, body.getValue());
                ps.setBoolean(3, false);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
