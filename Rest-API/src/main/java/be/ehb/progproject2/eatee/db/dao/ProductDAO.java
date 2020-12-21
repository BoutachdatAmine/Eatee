package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.IngredientAmount;
import be.ehb.progproject2.eatee.entity.Product;
import be.ehb.progproject2.eatee.entity.request.ProductBody;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductDAO extends BaseDAO {

    public boolean create(ProductBody body) throws Exception {
        body.checkFields();

        try (Connection c = getConnection()) {
            // Create product
            int productId = -1;
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Product VALUES(NULL, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, body.getCategoryId());
                ps.setString(2, body.getName());
                ps.setDouble(3, body.getPrice());
                ps.executeUpdate();

                // Get generated key
                try(ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next())
                        productId = keys.getInt(1);
                }
            }

            // Create link to ingredients
            if(body.getIngredients().size() > 0) {
                try(PreparedStatement psIngredient = c.prepareStatement("INSERT INTO Product_Ingredient VALUES(?, ?, ?);")) {
                    for (IngredientAmount ingredient : body.getIngredients()) {
                        psIngredient.setInt(1, productId);
                        psIngredient.setInt(2, ingredient.getId());
                        psIngredient.setInt(3, ingredient.getAmount());
                        psIngredient.executeUpdate();
                    }
                }
            }

            // Create link to allergies
            if(body.getAllergies().size() > 0) {
                try(PreparedStatement psAllergy = c.prepareStatement("INSERT INTO Product_Allergy VALUES(?, ?);")) {
                    for (int allergyId : body.getAllergies()) {
                        psAllergy.setInt(1, productId);
                        psAllergy.setInt(2, allergyId);
                        psAllergy.executeUpdate();
                    }
                }
            }
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Product> retrieveAll() {
        List<Integer> productIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Product;")) {

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        productIds.add(rs.getInt("Id"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Product> products = new ArrayList<>();
        for(int productId : productIds)
            products.add(retrieveOne(productId));

        return products;
    }

    public Product retrieveOne(int productId) {
        List<IngredientAmount> ingredients = new IngredientDAO().getIngredientAmountsFromProduct(productId);
        List<Integer> allergyIds = new AllergyDAO().getAllergyIdsFromProduct(productId);

        int quantity = amountInStock(productId);
        double promotionActive = new PromotionDAO().checkIfPromotion(productId);

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Product WHERE Id=?;")) {
                ps.setInt(1, productId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Calculate price (changes if promotion is active)
                        double price = rs.getDouble("Price");
                        if (promotionActive > 0) price -= promotionActive;
                        if (price < 0) price = 0;

                        return new Product(
                                rs.getInt("Id"),
                                price,
                                rs.getString("Name"),
                                quantity,
                                rs.getInt("CategoryId"),
                                ingredients,
                                allergyIds,
                                quantity > 0,
                                promotionActive
                        );
                    }
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private int amountInStock(int productId) {
        List<Integer> amounts = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps1 = c.prepareStatement("SELECT IngredientId,Amount FROM Product_Ingredient WHERE ProductId=?;")) {
                ps1.setInt(1, productId);

                try (ResultSet rs1 = ps1.executeQuery()) {
                    while (rs1.next()) {
                        try (PreparedStatement ps2 = c.prepareStatement("SELECT Quantity FROM Ingredient WHERE Id=?;")) {
                            ps2.setInt(1, rs1.getInt("IngredientId"));

                            try (ResultSet rs2 = ps2.executeQuery()) {
                                while (rs2.next()) {
                                    if (rs1.getDouble("Amount") < 1 && rs1.getDouble("Amount") > 0) {
                                        int t = (int) (rs2.getInt("Quantity") * rs1.getDouble("Amount"));
                                        amounts.add(t);
                                    } else {
                                        int t = (int) (rs2.getInt("Quantity") / rs1.getDouble("Amount"));
                                        amounts.add(t);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(amounts.stream().min(Comparator.naturalOrder()).isPresent())
            return amounts.stream().min(Comparator.naturalOrder()).get();
        else return 0;
    }

    public List<Integer> getProductIdsFromOrder(int orderId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT ProductId FROM Order_Product WHERE OrderId=?;")) {
                ps.setInt(1, orderId);

                try(ResultSet rs = ps.executeQuery()) {
                    List<Integer> products = new ArrayList<>();
                    while (rs.next())
                        products.add(rs.getInt("ProductId"));

                    return products;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Integer> getSandwichIdsFromOrder(int orderId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT CustomSandwichId From Order_Sandwich WHERE OrderId=?;")) {
                ps.setInt(1, orderId);

                try(ResultSet rs = ps.executeQuery()) {
                    List<Integer> sandwiches = new ArrayList<>();
                    while (rs.next())
                        sandwiches.add(rs.getInt("CustomSandwichId"));

                    return sandwiches;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean update(int productId, ProductBody body) {
        // Product currently in DB
        Product product = retrieveOne(productId);

        try (Connection c = getConnection()) {
            // Update product query
            try (PreparedStatement ps2 = c.prepareStatement("UPDATE Product SET CategoryId=?, `Name`=?, Price=? WHERE Id=?;")) {
                ps2.setInt(1, body.getCategoryId());

                if (body.getAllergies() != null && body.getAllergies().size() > 0) {
                    // Delete old allergies
                    try (PreparedStatement ps3 = c.prepareStatement("DELETE FROM Product_Allergy WHERE ProductId=?;")) {
                        ps3.setInt(1, productId);
                        ps3.executeUpdate();
                    }

                    // Insert new allergies
                    try (PreparedStatement ps4 = c.prepareStatement("INSERT INTO Product_Allergy VALUES(?, ?);")) {
                        for (int allergyId : body.getAllergies()) {
                            ps4.setInt(1, productId);
                            ps4.setInt(2, allergyId);
                            ps4.executeUpdate();
                        }
                    }
                }

                if (body.getIngredients() != null && body.getIngredients().size() > 0) {
                    // Delete old ingredients
                    try(PreparedStatement psDeleteIngredients = c.prepareStatement("DELETE FROM Product_Ingredient WHERE ProductId=?;")) {
                        psDeleteIngredients.setInt(1, productId);
                        psDeleteIngredients.executeUpdate();
                    }

                    // Insert new ingredients
                    try(PreparedStatement ps6 = c.prepareStatement("INSERT INTO Product_Ingredient VALUES(?, ?, ?);")) {
                        for (IngredientAmount ingredient : body.getIngredients()) {
                            ps6.setInt(1, productId);
                            ps6.setInt(2, ingredient.getId());
                            ps6.setInt(3, ingredient.getAmount());
                            ps6.executeUpdate();
                        }
                    }
                }

                // Update product
                if (body.getCategoryId() > -1) ps2.setInt(1, body.getCategoryId());
                else ps2.setInt(1, product.getCategoryId());

                if (body.getName() != null) ps2.setString(2, body.getName());
                else ps2.setString(2, product.getName());

                if (body.getPrice() > -1) ps2.setDouble(3, body.getPrice());
                else ps2.setDouble(3, product.getPrice());

                ps2.setInt(4, productId);
                return ps2.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean delete(int productId) {
        // Delete promotion
        PromotionDAO promotionDAO = new PromotionDAO();
        promotionDAO.delete(promotionDAO.getPromotionIdFromProduct(productId));

        try (Connection c = getConnection()) {
            // Delete links to this product
            try(PreparedStatement ps2 = c.prepareStatement("DELETE FROM Product_Allergy WHERE ProductId=?;")) {
                ps2.setInt(1, productId);
                ps2.executeUpdate();
            }

            try(PreparedStatement ps3 = c.prepareStatement("DELETE FROM Product_Ingredient WHERE ProductId=?;")) {
                ps3.setInt(1, productId);
                ps3.executeUpdate();
            }

            try(PreparedStatement ps4 = c.prepareStatement("DELETE FROM Order_Product WHERE ProductId=?;")) {
                ps4.setInt(1, productId);
                ps4.executeUpdate();
            }

            try(PreparedStatement ps6 = c.prepareStatement("DELETE FROM Menu_Product WHERE ProductId=?;")) {
                ps6.setInt(1, productId);
                ps6.executeUpdate();
            }

            // Delete product
            try(PreparedStatement ps1 = c.prepareStatement("DELETE FROM Product WHERE Id=?;")) {
                ps1.setInt(1, productId);
                return ps1.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public double getPriceFromProduct(int productId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Price FROM Product WHERE Id=?;")) {
                ps.setInt(1, productId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return rs.getDouble("Price");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public boolean addProductToCart(int customerId, int productId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Cart_Product VALUES(NULL, ?, ?);")) {
                ps.setInt(1, customerId);
                ps.setInt(2, productId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean removeProductFromCart(int customerId, int cartId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM Cart_Product WHERE CustomerId=? AND Id=?;")) {
                ps.setInt(1, customerId);
                ps.setInt(2, cartId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean addSandwichToCart(int customerId, int sandwichId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Cart_Sandwich VALUES(NULL, ?, ?);")) {
                ps.setInt(1, customerId);
                ps.setInt(2, sandwichId);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean removeSandwichFromCart(int customerId, int cartId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM Cart_Sandwich WHERE CustomerId=? AND Id=?;")) {
                ps.setInt(1, customerId);
                ps.setInt(2, cartId);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
