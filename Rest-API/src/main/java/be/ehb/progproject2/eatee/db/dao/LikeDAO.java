package be.ehb.progproject2.eatee.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikeDAO extends BaseDAO {
    public List<Integer> getLikeIdsFromPost(int postId) {
        List<Integer> likeIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM `Like` WHERE PostId=?;")) {
                ps.setInt(1, postId);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        likeIds.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return likeIds;
    }

    public boolean delete(int likeId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("DELETE FROM `Like` WHERE Id=?;")) {
                ps.setInt(1, likeId);
                return ps.executeUpdate() > 1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
