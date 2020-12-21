package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Promotion;
import be.ehb.progproject2.eatee.entity.request.PromotionBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO extends BaseDAO {
    public Promotion retrieve(int promotionId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Promotion WHERE Id=?;")) {
                ps.setInt(1, promotionId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return new Promotion(
                            rs.getInt("Id"),
                            rs.getInt("ProductId"),
                            rs.getDouble("Value"),
                            rs.getDate("ValidFrom"),
                            rs.getDate("ValidTo")
                        );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int getPromotionIdFromProduct(int productId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Promotion WHERE ProductId=?;")) {
                ps.setInt(1, productId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return rs.getInt("Id");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }

    public boolean delete(int promotionId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM Promotion WHERE Id=?;")) {
                ps.setInt(1, promotionId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean create(PromotionBody body) throws Exception {
        body.checkFields();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Promotion VALUES(NULL, ?, ?, ?, ?);")) {
                ps.setInt(1, body.getProduct());
                ps.setDouble(2, body.getValue());
                ps.setDate(3, body.getValidFrom());
                ps.setDate(4, body.getValidTo());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean update(int promotionId, PromotionBody body) {
        Promotion promotion = retrieve(promotionId);

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("UPDATE Promotion SET ProductId=?, `Value`=?, ValidFrom=?, ValidTo=? WHERE Id=?;")) {
                ps.setInt(5, promotionId);

                if (body.getProduct() <= 0) ps.setInt(1, promotion.getProductId());
                else ps.setInt(1, body.getProduct());

                if (body.getValue() <= 0) ps.setDouble(2, promotion.getValue());
                else ps.setDouble(2, body.getValue());

                if (body.getValidFrom() == null) ps.setDate(3, promotion.getValidFrom());
                else ps.setDate(3, body.getValidFrom());

                if (body.getValidTo() == null) ps.setDate(4, promotion.getValidTo());
                else ps.setDate(4, body.getValidTo());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public double checkIfPromotion(int productId) {
        // Return value if active, else return 0
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Promotion WHERE ProductId=? AND CURDATE() BETWEEN ValidFrom AND ValidTo;")) {
                ps.setInt(1, productId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getDouble("Value");
                    else return 0;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }

    public List<Promotion> retrieveAll() {
        List<Integer> promotionIds = new ArrayList<>();
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Promotion;")) {

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        promotionIds.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Promotion> promotions = new ArrayList<>();
        for(int promotionId : promotionIds)
            promotions.add(retrieve(promotionId));
        return promotions;
    }
}
