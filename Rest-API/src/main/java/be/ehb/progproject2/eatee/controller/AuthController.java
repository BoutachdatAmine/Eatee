package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.UserDAO;
import be.ehb.progproject2.eatee.entity.request.AuthUser;
import be.ehb.progproject2.eatee.entity.request.PasswordBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/employee")
    public int employee(@RequestBody AuthUser body) throws Exception {
        return new UserDAO().authenticateEmployee(body);
    }

    @PostMapping("/customer")
    public int customer(@RequestBody AuthUser body) throws Exception {
        return new UserDAO().authenticateCustomer(body);
    }

    // Activate Two factor authentication
    @PostMapping("/twofactor/{userId}")
    public String activate(@PathVariable int userId) {
        return new UserDAO().enableTwoFactor(userId);
    }

    @PostMapping("/customer/{customerId}/password")
    public boolean checkPassword(@PathVariable int customerId, @RequestBody PasswordBody body) throws Exception {
        return new UserDAO().checkPassword(customerId, body);
    }

}
