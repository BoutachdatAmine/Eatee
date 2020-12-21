package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.entity.request.SandwichBody;
import be.ehb.progproject2.eatee.db.dao.CustomSandwichDAO;
import be.ehb.progproject2.eatee.entity.CustomSandwich;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/custom")
public class SandwichController {

    // Get all
    @GetMapping("/")
    public List<CustomSandwich> getAllCustom() throws SQLException {
        return new CustomSandwichDAO().retrieveAll();
    }

    // Get one
    @GetMapping("/{customId}")
    public CustomSandwich getCustomSandwich(@PathVariable int customId) throws SQLException {
        return new CustomSandwichDAO().retrieve(customId);
    }

    // Update one
    @PatchMapping("/{customId}")
    @ResponseBody
    public boolean updateCustom(@PathVariable int customId, @RequestBody SandwichBody body) throws SQLException {
        return new CustomSandwichDAO().updateCustom(customId, body);
    }

    // Delete one
    @DeleteMapping("/{customId}")
    public boolean deleteCustom(@PathVariable int customId) throws SQLException {
        return new CustomSandwichDAO().deleteCustom(customId);
    }
}
