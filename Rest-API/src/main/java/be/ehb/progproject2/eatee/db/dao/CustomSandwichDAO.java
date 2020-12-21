package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.CustomSandwich;
import be.ehb.progproject2.eatee.entity.request.SandwichBody;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomSandwichDAO extends BaseDAO {

    public int createCustom(int customerId, SandwichBody body) throws Exception {
        body.checkFields();

        try (Connection c = getConnection()) {
            // Create sandwich
            int sandwichId = -1;
            try(PreparedStatement ps1 = c.prepareStatement("INSERT INTO CustomSandwich VALUES(NULL, ?);", Statement.RETURN_GENERATED_KEYS)) {
                ps1.setInt(1, customerId);
                ps1.executeUpdate();

                try(ResultSet keys = ps1.getGeneratedKeys()) {
                    if (keys.next())
                        sandwichId = keys.getInt(1);
                }
            }

            // Link to ingredients
            try(PreparedStatement ps2 = c.prepareStatement("INSERT INTO Custom_Ingredient VALUES(?, ?);")) {
                for (int ingredientId : body.getIngredients()) {
                    ps2.setInt(1, ingredientId);
                    ps2.setInt(2, sandwichId);
                    ps2.executeUpdate();
                }
            }

            return sandwichId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public List<CustomSandwich> retrieveAll() {
        List<Integer> sandwichIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            // Get all sandwich IDs
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM CustomSandwich;")) {
                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        sandwichIds.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<CustomSandwich> sandwiches = new ArrayList<>();
        for(int sandwichId : sandwichIds)
            sandwiches.add(retrieve(sandwichId));

        return sandwiches;
    }

    public CustomSandwich retrieve(int sandwichId) {
        List<Integer> ingredientIds = new IngredientDAO().getIngredientIdsFromCustom(sandwichId);

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM CustomSandwich WHERE Id=?;")) {
                ps.setInt(1, sandwichId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return new CustomSandwich(
                                rs.getInt("Id"),
                                rs.getInt("CustomerId"),
                                ingredientIds
                        );

                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Integer> retrieveSandwichIdsFromCustomer(int customerId) {
        List<Integer> sandwiches = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM CustomSandwich WHERE CustomerId=?;")) {
                ps.setInt(1, customerId);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        sandwiches.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sandwiches;
    }

    public boolean updateCustom(int sandwichId, SandwichBody body) {
        try(Connection c = getConnection()) {
            try(PreparedStatement ps1 = c.prepareStatement("DELETE FROM Custom_Ingredient WHERE CustomSandwichId=?;")) {
                ps1.setInt(1, sandwichId);
                ps1.executeUpdate();

                try(PreparedStatement ps2 = c.prepareStatement("INSERT INTO Custom_Ingredient VALUES(?,?);")) {
                    for (int ingredientId : body.getIngredients()) {
                        ps2.setInt(1, ingredientId);
                        ps2.setInt(2, sandwichId);
                        ps2.executeUpdate();
                    }
                }
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean deleteCustom(int id) {
        try(Connection c = getConnection()) {
            try(PreparedStatement ps1 = c.prepareStatement("DELETE FROM CustomSandwich WHERE Id=?;")) {
                ps1.setInt(1, id);
                ps1.executeUpdate();
            }

            try(PreparedStatement ps2 = c.prepareStatement("DELETE FROM Custom_Ingredient WHERE CustomSandwichId=?;")){
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
