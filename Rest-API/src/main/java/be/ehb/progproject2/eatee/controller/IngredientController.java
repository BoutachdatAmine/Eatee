package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.IngredientDAO;
import be.ehb.progproject2.eatee.entity.Ingredient;
import be.ehb.progproject2.eatee.entity.request.IngredientBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ingredients")
public class IngredientController {

    // Create
    @PostMapping("/")
    public boolean create(@RequestBody IngredientBody body) throws Exception {
        IngredientDAO ingredientDAO = new IngredientDAO();
        return ingredientDAO.create(body);
    }

    // Retrieve
    @GetMapping("/{ingredientId}")
    public Ingredient retrieve(@PathVariable int ingredientId) throws SQLException {
        IngredientDAO ingredientDAO = new IngredientDAO();
        return ingredientDAO.retrieve(ingredientId);
    }

    // Retrieve all
    @GetMapping("/")
    public List<Ingredient> retrieveAll(@RequestParam(required = false) String sandwichIngredient) throws SQLException {
        return new IngredientDAO().retrieveAll(sandwichIngredient != null);
    }

    // Update
    @PatchMapping("/{ingredientId}")
    public boolean update(@PathVariable int ingredientId, @RequestBody IngredientBody body) throws SQLException {
        IngredientDAO ingredientDAO = new IngredientDAO();
        return ingredientDAO.update(ingredientId, body);
    }

    // Delete
    @DeleteMapping("/{ingredientId}")
    public boolean delete(@PathVariable int ingredientId) throws SQLException {
        IngredientDAO ingredientDAO = new IngredientDAO();
        return ingredientDAO.delete(ingredientId);
    }
}
