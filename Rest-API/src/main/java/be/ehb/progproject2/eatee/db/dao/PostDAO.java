package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Post;
import be.ehb.progproject2.eatee.entity.request.PostBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAO extends BaseDAO {
    public List<Integer> getPostIdsFromEmployee(int employeeId) {
        List<Integer> postIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Post WHERE EmployeeId=?;")) {
                ps.setInt(1, employeeId);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        postIds.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return postIds;
    }

    public boolean delete(int postId) {
        // Delete likes from post
        LikeDAO likeDAO = new LikeDAO();
        for(int likeId : likeDAO.getLikeIdsFromPost(postId))
            likeDAO.delete(likeId);

        try (Connection c = getConnection()) {
            // Delete post
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM Post WHERE Id=?;")) {
                ps.setInt(1, postId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean create(int employeeId, PostBody body) throws Exception {
        body.checkFields();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO Post VALUES(NULL, ?, ?, ?);")) {
                ps.setInt(1, employeeId);
                ps.setString(2, body.getTitle());
                ps.setString(3, body.getDescription());
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public List<Post> retrieveAll() {
        List<Integer> postIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Post;")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                    postIds.add(rs.getInt("Id"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Post> posts = new ArrayList<>();
        for(int postId : postIds)
            posts.add(retrieve(postId));

        return posts;
    }

    public Post retrieve(int postId) {
        int likes = getLikesFromPost(postId);

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Post WHERE Id=?;")) {
                ps.setInt(1, postId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return new Post(
                                rs.getInt("Id"),
                                rs.getInt("EmployeeId"),
                                rs.getString("Title"),
                                rs.getString("Description"),
                                likes
                        );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    private int getLikesFromPost(int postId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT COUNT(PostId) FROM `Like` WHERE PostId=?;")) {
                ps.setInt(1, postId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return rs.getInt(1);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public boolean update(int postId, PostBody body) {
        Post post = retrieve(postId);

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("UPDATE Post SET Title=?, Description=? WHERE Id=?")) {
                ps.setInt(3, postId);

                if (body.getTitle() == null) ps.setString(1, post.getTitle());
                else ps.setString(1, body.getTitle());

                if (body.getDescription() == null) ps.setString(2, post.getDescription());
                else ps.setString(2, body.getDescription());
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean addLike(int postId, int customerId) {
        try (Connection c = getConnection()) {
            // Check if already liked
            try(PreparedStatement ps1 = c.prepareStatement("SELECT * FROM `Like` WHERE PostId=? AND CustomerId=?;")) {
                ps1.setInt(1, postId);
                ps1.setInt(2, customerId);

                try(ResultSet rs1 = ps1.executeQuery()) {
                    if (rs1.next())
                        return false; // Already liked
                }
            }

            // Like post
            try(PreparedStatement ps = c.prepareStatement("INSERT INTO `Like` VALUES(NULL, ?, ?);")) {
                ps.setInt(1, postId);
                ps.setInt(2, customerId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean removeLike(int postId, int customerId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM `Like` WHERE PostId=? AND CustomerId=?;")) {
                ps.setInt(1, postId);
                ps.setInt(2, customerId);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }

    public List<Integer> getLikedPosts(int customerId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT PostId FROM `Like` WHERE CustomerId=?;")) {
                ps.setInt(1, customerId);

                try(ResultSet rs = ps.executeQuery()) {
                    List<Integer> postIds = new ArrayList<>();
                    while (rs.next())
                        postIds.add(rs.getInt("PostId"));

                    return postIds;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
