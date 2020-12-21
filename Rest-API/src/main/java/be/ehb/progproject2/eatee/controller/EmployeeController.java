package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.EmployeeDAO;
import be.ehb.progproject2.eatee.db.dao.PostDAO;
import be.ehb.progproject2.eatee.entity.Employee;
import be.ehb.progproject2.eatee.entity.request.EmployeeBody;
import be.ehb.progproject2.eatee.entity.request.PostBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeController {

    // Create
    @PostMapping("/")
    public boolean create(@RequestBody EmployeeBody body) throws Exception {
        return new EmployeeDAO().create(body);
    }

    // Retrieve all
    @GetMapping("/")
    public List<Employee> retrieveAll() throws SQLException {
        return new EmployeeDAO().retrieveAll();
    }

    // Retrieve one
    @GetMapping("/{employeeId}")
    public Employee retrieveOne(@PathVariable int employeeId) throws SQLException {
        return new EmployeeDAO().retrieve(employeeId);
    }

    // Update
    @PatchMapping("/{employeeId}")
    public boolean update(@PathVariable int employeeId, @RequestBody EmployeeBody body) throws SQLException {
        return new EmployeeDAO().update(employeeId, body);
    }

    // Delete Employee
    @DeleteMapping("/{employeeId}")
    public boolean delete(@PathVariable int employeeId) throws SQLException {
        return new EmployeeDAO().delete(employeeId);
    }

    // Create post
    @PostMapping("/{employeeId}/post")
    public boolean create(@PathVariable int employeeId, @RequestBody PostBody body) throws Exception {
        return new PostDAO().create(employeeId, body);
    }
}
