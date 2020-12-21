package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.PromotionDAO;
import be.ehb.progproject2.eatee.entity.Promotion;
import be.ehb.progproject2.eatee.entity.request.PromotionBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    // Create
    @PostMapping("/")
    public boolean create(@RequestBody PromotionBody body) throws Exception {
        return new PromotionDAO().create(body);
    }

    // Retrieve
    @GetMapping("/")
    public List<Promotion> retrieveAll() throws SQLException {
        return new PromotionDAO().retrieveAll();
    }

    // Update
    @PatchMapping("/{promotionId}")
    public boolean update(@PathVariable int promotionId, @RequestBody PromotionBody body) throws SQLException {
        return new PromotionDAO().update(promotionId, body);
    }

    // Delete
    @DeleteMapping("/{promotionId}")
    public boolean delete(@PathVariable int promotionId) throws SQLException {
        return new PromotionDAO().delete(promotionId);
    }
}
