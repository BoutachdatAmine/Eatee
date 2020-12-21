package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.IngredientAmount;
import be.ehb.progproject2.eatee.entity.request.ProductBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDAOTest {

    //Declare fields
    private ProductDAO productDAO;
    private ProductBody productBody0;
    private ProductBody productBody1;
    private IngredientAmount ingredientAmount;
    private int categoryId0;
    private int categoryId1;
    private int allergyId;
    private List<Integer> allergiesList;
    private List<IngredientAmount> ingredientAmountList;



    @BeforeEach
    public void setUp() {

        //Initialize
        categoryId0 = 1000;
        categoryId1 = 1;
        allergyId = 2;

        ingredientAmount = new IngredientAmount(9, 1);

        allergiesList = new ArrayList<>();
        allergiesList.add(allergyId);


        ingredientAmountList = new ArrayList<>();
        ingredientAmountList.add(ingredientAmount);


        //Incorrect product with non-existing Category ID
        productBody0 = new ProductBody(
                "Perrier Citrus",
                2.75,
                categoryId0,
                allergiesList,
                ingredientAmountList
                );

        //Correct product
        productBody1 = new ProductBody(
                "Perrier Citrus",
                2.75,
                categoryId1,
                allergiesList,
                ingredientAmountList
        );

        //DAO
        productDAO = new ProductDAO();

    }


    @Test
    public void testCreate0() throws Exception{  //Create a product with non-existing Category ID

        boolean actual = productDAO.create(productBody0);

        assertFalse(actual, "Incorrect product data");

    }

    @Test
    public void testCreate1() throws Exception{  //Create a complete product with  allergies

        boolean actual = productDAO.create(productBody1);

        assertTrue(actual, "Product created");
    }
}
