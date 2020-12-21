package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Menu;
import be.ehb.progproject2.eatee.entity.request.MenuBody;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO extends BaseDAO {

    private List<Integer> getProductIdsFromMenu(int menuId) {
        List<Integer> products = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT ProductId FROM Menu_Product WHERE MenuId=?;")) {
                ps.setInt(1, menuId);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        products.add(rs.getInt("ProductId"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return products;
    }

    public Menu retrieve(int menuId) {
        List<Integer> products = getProductIdsFromMenu(menuId);

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Menu WHERE Id=?;")) {
                ps.setInt(1, menuId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return new Menu(
                                rs.getInt("Id"),
                                rs.getDate("ValidFrom"),
                                rs.getDate("ValidTo"),
                                products
                        );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Menu> retrieveAll() {
        List<Integer> menuIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Menu;")) {

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        menuIds.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Menu> menus = new ArrayList<>();
        for(int menuId : menuIds)
            menus.add(retrieve(menuId));

        return menus;
    }

    public int create(MenuBody body) throws Exception {
        body.checkFields();

        try (Connection c = getConnection()) {
            // Create menu
            try(PreparedStatement ps1 = c.prepareStatement("INSERT INTO Menu VALUES(NULL, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
                ps1.setDate(1, body.getValidFrom());
                ps1.setDate(2, body.getValidTo());
                ps1.executeUpdate();

                // ID of menu
                int menuId = -1;
                try(ResultSet keys = ps1.getGeneratedKeys()) {
                    if (keys.next())
                        menuId = keys.getInt(1);
                }

                if (body.getProducts() != null && body.getProducts().size() > 0) {
                    // Create link of products in this menu
                    try(PreparedStatement ps2 = c.prepareStatement("INSERT INTO Menu_Product VALUES(?, ?);")) {
                        for (int productId : body.getProducts()) {
                            ps2.setInt(1, menuId);
                            ps2.setInt(2, productId);
                            ps2.executeUpdate();
                        }
                    }
                }

                return menuId;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public boolean update(int menuId, MenuBody body) {
        Menu menu = retrieve(menuId);

        try (Connection c = getConnection()) {
            // Update order
            try(PreparedStatement ps = c.prepareStatement("UPDATE Menu SET ValidFrom=?, ValidTo=? WHERE Id=?;")) {
                ps.setInt(3, menuId);

                if (body.getValidFrom() == null) ps.setDate(1, menu.getValidFrom());
                else ps.setDate(1, body.getValidFrom());

                if (body.getValidTo() == null) ps.setDate(2, menu.getValidTo());
                else ps.setDate(2, body.getValidTo());
                ps.executeUpdate();
            }

            // Update products in order
            if(body.getProducts() != null && body.getProducts().size() > 0) {
                // Delete all current products in order
                try(PreparedStatement ps2 = c.prepareStatement("DELETE FROM Menu_Product WHERE MenuId=?;")) {
                    ps2.setInt(1, menuId);
                    ps2.executeUpdate();
                }

                // Add new products
                try(PreparedStatement ps3 = c.prepareStatement("INSERT INTO Menu_Product VALUES(?, ?);")) {
                    for (int productId : body.getProducts()) {
                        ps3.setInt(1, menuId);
                        ps3.setInt(2, productId);
                        ps3.executeUpdate();
                    }
                }
            }
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean delete(int menuId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM Menu WHERE Id=?;")) {
                ps.setInt(1, menuId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean addProduct(int menuId, int productId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Menu_Product VALUES(?, ?);")) {
                ps.setInt(1, menuId);
                ps.setInt(2, productId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean removeProduct(int menuId, int productId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM Menu_Product WHERE MenuId=? AND ProductId=?;")) {
                ps.setInt(1, menuId);
                ps.setInt(2, productId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Menu> getCurrentMenus() {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Menu WHERE CURDATE() BETWEEN ValidFrom AND ValidTo;")) {
                List<Menu> menus = new ArrayList<>();

                try(ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        try (PreparedStatement ps1 = c.prepareStatement("SELECT ProductId FROM Menu_Product WHERE MenuId=?;")) {
                            ps1.setInt(1, rs.getInt("Id"));

                            try(ResultSet rs1 = ps1.executeQuery()) {
                                List<Integer> productIds = new ArrayList<>();
                                while (rs1.next())
                                    productIds.add(rs1.getInt("ProductId"));

                                menus.add(new Menu(
                                        rs.getInt("Id"),
                                        rs.getDate("ValidFrom"),
                                        rs.getDate("ValidTo"),
                                        productIds
                                ));
                            }
                        }
                    }
                }

                return menus;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
