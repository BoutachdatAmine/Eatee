package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.DiscountDAO;
import be.ehb.progproject2.eatee.entity.Discount;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    @GetMapping("/{discountId}")
    public Discount retrieve(@PathVariable int discountId) throws SQLException {
        return new DiscountDAO().retrieve(discountId);
    }

    @DeleteMapping("/{discountId}")
    public boolean delete(@PathVariable int discountId) throws SQLException {
        return new DiscountDAO().delete(discountId);
    }

}
