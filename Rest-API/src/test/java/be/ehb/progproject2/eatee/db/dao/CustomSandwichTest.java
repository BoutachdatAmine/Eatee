package be.ehb.progproject2.eatee.db.dao;


import be.ehb.progproject2.eatee.entity.request.SandwichBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomSandwichTest {

    //Declare fields
    private CustomSandwichDAO customSandwichDAO;
    private SandwichBody sandwichBody;
    private List<Integer> ingredientsList;
    private int customerId;

    @BeforeEach
    public void setUp() {

        //Initialize
        customerId = 9;
        ingredientsList = new ArrayList<>();
        ingredientsList.add(5);
        ingredientsList.add(6);

        sandwichBody = new SandwichBody(ingredientsList);

        //DAO
        customSandwichDAO = new CustomSandwichDAO();

    }


    @Test
    public void testCreate() throws Exception{    //Create a custom sandwich

        int expected = customSandwichDAO.createCustom(customerId, sandwichBody);
        boolean correct = false;

        if(expected > 0){
            correct = true;
        }

        assertTrue(correct, "Custom sandwich created");
    }


}
