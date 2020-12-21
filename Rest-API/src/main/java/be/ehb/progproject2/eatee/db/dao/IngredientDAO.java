package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Ingredient;
import be.ehb.progproject2.eatee.entity.IngredientAmount;
import be.ehb.progproject2.eatee.entity.request.IngredientBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO extends BaseDAO {

    public List<IngredientAmount> getIngredientAmountsFromProduct(int productId) {
        List<IngredientAmount> ingredients = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Product_Ingredient WHERE ProductId=?;")) {
                ps.setInt(1, productId);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        ingredients.add(new IngredientAmount(rs.getInt("IngredientId"), rs.getInt("Amount")));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ingredients;
    }

    public List<Integer> getIngredientIdsFromCustom(int customId) {
        List<Integer> ingredientIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT IngredientId FROM Custom_Ingredient WHERE CustomSandwichId=?;")) {
                ps.setInt(1, customId);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        ingredientIds.add(rs.getInt("IngredientId"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ingredientIds;
    }

    public Ingredient retrieve(int ingredientId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Ingredient WHERE Id=?;")) {
                ps.setInt(1, ingredientId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return new Ingredient(
                                rs.getInt("Id"),
                                rs.getString("Name"),
                                rs.getInt("Quantity"),
                                rs.getInt("CategoryId"),
                                rs.getBoolean("SandwichIngredient")
                        );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Ingredient> retrieveAll(boolean sandwichIngredients) {
        List<Integer> ingredientIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            PreparedStatement ps;
            if(sandwichIngredients) ps = c.prepareStatement("SELECT Id FROM Ingredient WHERE SandwichIngredient=1;");
            else ps = c.prepareStatement("SELECT Id FROM Ingredient;");

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    ingredientIds.add(rs.getInt("Id"));
            } finally {
                ps.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Ingredient> ingredients = new ArrayList<>();
        for(int ingredientId : ingredientIds)
            ingredients.add(retrieve(ingredientId));
        return ingredients;
    }

    public boolean create(IngredientBody body) throws Exception {
        body.checkFields();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Ingredient VALUES(NULL, ?, ?, ?, ?);")) {
                ps.setString(1, body.getName());
                ps.setInt(2, body.getQuantity());
                ps.setInt(3, body.getCategoryId());
                ps.setBoolean(4, body.isSandwichIngredient() == null ? false : body.isSandwichIngredient());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean update(int ingredientId, IngredientBody body){
        Ingredient ingredient = retrieve(ingredientId);

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("UPDATE Ingredient SET `Name`=?, Quantity=?, CategoryId=?, SandwichIngredient=? WHERE Id=?;")) {
                ps.setInt(5, ingredientId);

                if (body.getName() == null) ps.setString(1, ingredient.getName());
                else ps.setString(1, body.getName());

                if (body.getQuantity() == null) ps.setInt(2, ingredient.getQuantityStock());
                else ps.setInt(2, body.getQuantity());

                if (body.getCategoryId() <= 0) ps.setInt(3, ingredient.getCategoryId());
                else ps.setInt(3, body.getCategoryId());

                if (body.isSandwichIngredient() == null) ps.setBoolean(4, ingredient.isSandwichIngredient());
                else ps.setBoolean(4, body.isSandwichIngredient());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean delete(int ingredientId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps2 = c.prepareStatement("DELETE FROM Custom_Ingredient WHERE IngredientId=?;")) {
                ps2.setInt(1, ingredientId);
                ps2.executeUpdate();
            }

            try(PreparedStatement ps3 = c.prepareStatement("DELETE FROM Product_Ingredient WHERE IngredientId=?;")) {
                ps3.setInt(1, ingredientId);
                ps3.executeUpdate();
            }

            try(PreparedStatement ps1 = c.prepareStatement("DELETE FROM Ingredient WHERE Id=?;")) {
                ps1.setInt(1, ingredientId);
                return ps1.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
