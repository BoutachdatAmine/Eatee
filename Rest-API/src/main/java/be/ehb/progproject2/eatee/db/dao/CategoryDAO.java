package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Category;
import be.ehb.progproject2.eatee.entity.request.CategoryBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends BaseDAO {
    // Create
    public boolean create(CategoryBody body) throws Exception {
        body.checkField();

        try (Connection c = getConnection()) {
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO Category VALUES(NULL, ?);")) {
                ps.setString(1, body.getName());
                return ps.executeUpdate() > 0;
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    // Retrieve one
    public Category retrieve(int categoryId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Category WHERE Id=?;")) {
                ps.setInt(1, categoryId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return new Category(
                                rs.getInt("Id"),
                                rs.getString("Name")
                        );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    // Retrieve all
    public List<Category> retrieveAll() {
        List<Integer> categoryIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Category;")) {
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next())
                        categoryIds.add(rs.getInt("Id"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Category> categories = new ArrayList<>();
        for(int categoryId : categoryIds)
            categories.add(retrieve(categoryId));

        return categories;
    }

    // Update
    public boolean update(int categoryId, CategoryBody body) throws Exception {
        body.checkField();

        try (Connection c = getConnection()) {
            try (PreparedStatement ps = c.prepareStatement("UPDATE Category SET `Name`=? WHERE Id=?;")) {
                ps.setString(1, body.getName());
                ps.setInt(2, categoryId);
                return ps.executeUpdate() > 0;
            }

        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    // Delete
    public boolean delete(int categoryId) throws SQLException {
        List<Integer> ingredientIds = new ArrayList<>();
        List<Integer> productIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            // Ingredients to be deleted
            try(PreparedStatement ps2 = c.prepareStatement("SELECT Id FROM Ingredient WHERE CategoryId=?;")) {
                ps2.setInt(1, categoryId);
                try(ResultSet rs2 = ps2.executeQuery()) {
                    while (rs2.next())
                        ingredientIds.add(rs2.getInt("Id"));
                }
            }

            // Products to be deleted
            try(PreparedStatement ps3 = c.prepareStatement("SELECT Id FROM Product WHERE CategoryId=?;")) {
                ps3.setInt(1, categoryId);
                try(ResultSet rs3 = ps3.executeQuery()) {
                    while (rs3.next())
                        productIds.add(rs3.getInt("Id"));
                }
            }

        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        IngredientDAO ingredientDAO = new IngredientDAO();
        for(int ingredientId : ingredientIds)
            ingredientDAO.delete(ingredientId);

        ProductDAO productDAO = new ProductDAO();
        for(int productId : productIds)
            productDAO.delete(productId);

        try (Connection c = getConnection()) {
            // Delete category
            try (PreparedStatement ps1 = c.prepareStatement("DELETE FROM Category WHERE Id=?;")) {
                ps1.setInt(1, categoryId);
                return ps1.executeUpdate() > 0;
            }
        } catch(SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
