package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.AllergyDAO;
import be.ehb.progproject2.eatee.entity.Allergy;
import be.ehb.progproject2.eatee.entity.request.AllergyBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/allergies")
public class AllergyController {

    // Create
    @PostMapping("/")
    public boolean create(@RequestBody AllergyBody body) throws Exception {
        return new AllergyDAO().create(body);
    }

    // Retrieve one
    @GetMapping("/{allergyId}")
    public Allergy retrieve(@PathVariable int allergyId) throws SQLException {
        return new AllergyDAO().retrieve(allergyId);
    }

    // Retrieve all
    @GetMapping("/")
    public List<Allergy> retrieveAll() throws SQLException {
        return new AllergyDAO().retrieveAll();
    }

    // Update
    @PatchMapping("/{allergyId}")
    public boolean update(@PathVariable int allergyId, @RequestBody AllergyBody body) throws SQLException {
        return new AllergyDAO().update(allergyId, body);
    }

    // Delete
    @DeleteMapping("/{allergyId}")
    public boolean delete(@PathVariable int allergyId) throws SQLException {
        return new AllergyDAO().delete(allergyId);
    }
}
