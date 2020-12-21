package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Post;
import be.ehb.progproject2.eatee.entity.request.PostBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PostDAOTest {

    //Declare fields
    PostDAO postDAO;
    int employeeId0;
    int employeeId1;
    int postId;
    PostBody postBody;
    Post postExpected;
    Post postActual;

    @BeforeEach
    public void setUp() {

        //Initialize
        employeeId0 = 10000;
        employeeId1 = 1;
        postId = 2;


        //DAO
        postDAO = new PostDAO();


        //New post
        postBody = new PostBody(
                "New post",
                "This is a new post!"
        );

        postExpected = new Post(
                postId,
                employeeId1,
                "Fanta",
                "Fanta",
                1
        );
    }


    @Test
    public void testCreate() throws Exception{  //Create a post with a non-existing Employee ID

        boolean actual = postDAO.create(employeeId0, postBody);

        assertFalse(actual, "Incorrect parameters");
    }

    @Test
    public void testRetrieve(){     //Retrieving an existing post
        postActual = postDAO.retrieve(postId);
        assertEquals(postExpected.toString(), postActual.toString());
    }
    
}
