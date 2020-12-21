package be.ehb.progproject2.eatee.db.dao;


import be.ehb.progproject2.eatee.entity.request.IngredientBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class IngredientDAOTest {

    //Declare fields
    private IngredientDAO ingredientDAO;
    private IngredientBody ingredientBody0;
    private IngredientBody ingredientBody1;



    @BeforeEach
    public void setUp() {

        //Initialize

        ingredientBody0 = new IngredientBody(
                "Cucumber",
                23,
                1209,
                true
        );

        ingredientBody1 = new IngredientBody(
                "Cucumber",
                23,
                4,
                true
        );


        //DAO
        ingredientDAO = new IngredientDAO();

    }


    @Test
    public void testCreate0() throws Exception{  //Create an ingredient incorrectly

        boolean actual = ingredientDAO.create(ingredientBody0);

        assertFalse(actual, "Ingredient cannot be created");
    }

    @Test
    public void testCreate1() throws Exception{  //Create an ingredient correctly

        boolean actual = ingredientDAO.create(ingredientBody1);

        assertTrue(actual, "Ingredient is created successfully");
    }

}
