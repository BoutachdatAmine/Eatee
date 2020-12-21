package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Allergy;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


class AllergyDAOTest {

    private AllergyDAO allergyDAO = new AllergyDAO();


    @Test
    public void testRetrieveOne1() {
        // Allergy with ID = 1 exists
        Allergy allergy = allergyDAO.retrieve(1);
        assertNotNull(allergy);
        assertEquals(allergy.getId(), 1);
        assertEquals(allergy.getName(), "Gluten");
    }

    @Test
    public void testRetrieveOne2() {
        // Allergy cannot exist
        Allergy allergy = allergyDAO.retrieve(-1);
        assertNull(allergy);
    }

}