package be.ehb.progproject2.eatee.controller;

import be.ehb.progproject2.eatee.db.dao.PostDAO;
import be.ehb.progproject2.eatee.entity.Post;
import be.ehb.progproject2.eatee.entity.request.PostBody;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    // Read all
    @GetMapping("/")
    public List<Post> retrieveAll() throws SQLException {
        return new PostDAO().retrieveAll();
    }

    // Read one
    @GetMapping("/{postId}")
    public Post retrieve(@PathVariable int postId) throws SQLException {
        return new PostDAO().retrieve(postId);
    }

    // Update
    @PatchMapping("/{postId}")
    public boolean update(@PathVariable int postId, @RequestBody PostBody body) throws SQLException {
        return new PostDAO().update(postId, body);
    }

    // Delete
    @DeleteMapping("/{postId}")
    public boolean delete(@PathVariable int postId) throws SQLException {
        return new PostDAO().delete(postId);
    }

    // Add like
    @PostMapping("/{postId}/like/{customerId}")
    public boolean like(@PathVariable int postId, @PathVariable int customerId) throws SQLException {
        return new PostDAO().addLike(postId, customerId);
    }

    // Remove like
    @PostMapping("/{postId}/removelike/{customerId}")
    public boolean removeLike(@PathVariable int postId, @PathVariable int customerId) throws SQLException {
        return new PostDAO().removeLike(postId, customerId);
    }

    @GetMapping("/liked/{customerId}")
    public List<Integer> likedPosts(@PathVariable int customerId) throws SQLException {
        return new PostDAO().getLikedPosts(customerId);
    }
}
