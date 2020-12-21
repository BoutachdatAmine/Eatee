package be.ehb.progproject2.eatee.db.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PromotionDAOTest {

    //Declare fields
    private PromotionDAO promotionDAO;
    private int productId0;
    private int productId1;

    @BeforeEach
    public void setUp() {

        //Initialize
        productId0 = 1000;
        productId1 = 5;

        //DAO
        promotionDAO = new PromotionDAO();

    }


    @Test
    void testGetPromotion0() {   //Get non-existing promotion with incorrect Product ID

        int expected = -1;
        int actual = promotionDAO.getPromotionIdFromProduct(productId0);

        assertEquals(expected, actual, "Promotion doesn't exist for this product");
    }

    @Test
    void testGetPromotion1() {   //Get existing promotion with correct Product ID

        int expected = 5;
        int actual = promotionDAO.getPromotionIdFromProduct(productId1);

        assertEquals(expected, actual, "Promotion exists");
    }

    @Test
    void testCheckPromotionActive() {   //Check for a non-active promotion

        int expected = 0;
        int actual = (int) promotionDAO.checkIfPromotion(productId1);

        assertEquals(expected, actual, "Promotion does not exist at this time");
    }
}
