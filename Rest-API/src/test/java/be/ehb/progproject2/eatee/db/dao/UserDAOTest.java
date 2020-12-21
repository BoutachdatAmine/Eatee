package be.ehb.progproject2.eatee.db.dao;


import be.ehb.progproject2.eatee.entity.request.AuthUser;
import be.ehb.progproject2.eatee.entity.request.PasswordBody;
import be.ehb.progproject2.eatee.entity.request.UserBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UserDAOTest {

    //Declare fields
    private UserDAO userDAO;
    private AuthUser authUser;
    private UserBody userBody;
    private int customerId;
    private PasswordBody passwordBody0;
    private PasswordBody passwordBody1;


    @BeforeEach
    public void setUp() {

        //Initialize
        customerId = 9;
        passwordBody0 = new PasswordBody("abc123");
        passwordBody1 = new PasswordBody("hello");

        //DAO
        userDAO = new UserDAO();


        //Already existing credentials
        authUser = new AuthUser(
                "amine.boutachdat@student.ehb.be",
                "hello"
        );

        //New Registered customer with already existing email
        userBody = new UserBody(
                "Amine",
                "Boutachdat",
                "amine.boutachdat@student.ehb.be",
                "abc123",
                true
        );
    }


    @Test
    public void testCreate() throws Exception{    //Create a user with already existing email

        int expected = -1;
        int actual = userDAO.create(userBody);

        assertEquals(expected, actual, "Email already exists");
    }

    @Test
    public void testAuthenticateCustomer() throws Exception{     //Authentication requiring 6 digit code

        int expected = -5;
        int actual = userDAO.authenticateCustomer(authUser);

        assertEquals(expected, actual, "Credentials require a 6 digit code");
    }

    @Test
    public void testGetUserId(){   //Get User ID 18 using his customerId

        int expected = 18;
        int actual = userDAO.getUserIdFromCustomer(customerId);

        assertEquals(expected, actual, "Correct ID");
    }

    @Test
    public void TestCheckPassword0() throws Exception{   //Check an incorrect password

        boolean actual = userDAO.checkPassword(customerId, passwordBody0);

        assertFalse(actual, "Incorrect password");
    }

    @Test
    public void TestCheckPassword1() throws Exception{   //Check a correct password
        boolean actual = userDAO.checkPassword(customerId, passwordBody1);

        assertTrue(actual, "Correct password");
    }


}
