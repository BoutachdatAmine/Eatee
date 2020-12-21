package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.CategoryDAO;
import be.ehb.progproject2.eatee.entity.Category;
import be.ehb.progproject2.eatee.entity.request.CategoryBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryController {

    // Create
    @PostMapping("/")
    public boolean create(@RequestBody CategoryBody body) throws Exception {
        return new CategoryDAO().create(body);
    }

    // Retrieve one
    @GetMapping("/{categoryId}")
    public Category retrieve(@PathVariable int categoryId) throws SQLException {
        return new CategoryDAO().retrieve(categoryId);
    }

    // Retrieve all
    @GetMapping("/")
    public List<Category> retrieve() throws SQLException {
        return new CategoryDAO().retrieveAll();
    }

    // Update
    @PatchMapping("/{categoryId}")
    public boolean update(@PathVariable int categoryId, @RequestBody CategoryBody body) throws Exception {
        return new CategoryDAO().update(categoryId, body);
    }

    // Delete
    @DeleteMapping("/{categoryId}")
    public boolean delete(@PathVariable int categoryId) throws SQLException {
        return new CategoryDAO().delete(categoryId);
    }
}
