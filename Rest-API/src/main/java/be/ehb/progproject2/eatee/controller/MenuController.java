package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.MenuDAO;
import be.ehb.progproject2.eatee.entity.Menu;
import be.ehb.progproject2.eatee.entity.request.MenuBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    // Create
    @PostMapping("/")
    public int create(@RequestBody MenuBody body) throws Exception {
        return new MenuDAO().create(body);
    }

    // Retrieve all
    @GetMapping("/")
    public List<Menu> retrieveAll() throws SQLException {
        return new MenuDAO().retrieveAll();
    }

    // Retrieve one
    @GetMapping("/{menuId}")
    public Menu retrieve(@PathVariable int menuId) throws SQLException {
        return new MenuDAO().retrieve(menuId);
    }

    // Retrieve current menus
    @GetMapping("/current")
    public List<Menu> retrieveCurrent() throws SQLException {
        return new MenuDAO().getCurrentMenus();
    }

    // Update
    @PatchMapping("/{menuId}")
    public boolean update(@PathVariable int menuId, @RequestBody MenuBody body) throws SQLException {
        return new MenuDAO().update(menuId, body);
    }

    // Delete
    @DeleteMapping("/{menuId}")
    public boolean delete(@PathVariable int menuId) throws SQLException {
        return new MenuDAO().delete(menuId);
    }

    // Add product to menu
    @PostMapping("/{menuId}/add/{productId}")
    public boolean addProduct(@PathVariable int menuId, @PathVariable int productId) throws SQLException {
        return new MenuDAO().addProduct(menuId, productId);
    }

    // Delete product from menu
    @PostMapping("/{menuId}/remove/{productId}")
    public boolean removeProduct(@PathVariable int menuId, @PathVariable int productId) throws SQLException {
        return new MenuDAO().removeProduct(menuId, productId);
    }
}
