package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.ProductDAO;
import be.ehb.progproject2.eatee.entity.Product;
import be.ehb.progproject2.eatee.entity.request.ProductBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    // Create
    @PostMapping("/")
    public boolean createProduct(@RequestBody ProductBody body) throws Exception {
        return new ProductDAO().create(body);
    }

    // Retrieve all
    @GetMapping("/")
    public List<Product> retrieveAll() throws SQLException {
        return new ProductDAO().retrieveAll();
    }

    // Retrieve one
    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable int productId) throws SQLException {
        return new ProductDAO().retrieveOne(productId);
    }

    // Update
    @PatchMapping("/{productId}")
    public boolean updateProduct(@PathVariable int productId, @RequestBody ProductBody body) throws SQLException {
        return new ProductDAO().update(productId, body);
    }

    // Delete
    @DeleteMapping("/{productId}")
    public boolean deleteProduct(@PathVariable int productId) throws SQLException {
        return new ProductDAO().delete(productId);
    }
}

