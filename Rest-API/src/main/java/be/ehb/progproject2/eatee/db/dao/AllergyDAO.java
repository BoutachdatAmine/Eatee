package be.ehb.progproject2.eatee.db.dao;

import be.ehb.progproject2.eatee.entity.Allergy;
import be.ehb.progproject2.eatee.entity.request.AllergyBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllergyDAO extends BaseDAO {

    public List<Integer> getAllergyIdsFromProduct(int productId) {
        List<Integer> allergyIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT AllergyId FROM Product_Allergy WHERE ProductId=?;")) {
                ps.setInt(1, productId);

                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next())
                        allergyIds.add(rs.getInt("AllergyId"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allergyIds;
    }

    public boolean create(AllergyBody body) throws Exception {
        body.checkFields();

        try (Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO Allergy VALUES(NULL, ?);");
            ps.setString(1, body.getName());
            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Allergy retrieve(int allergyId) {
        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT * FROM Allergy WHERE Id=?;")) {
                ps.setInt(1, allergyId);

                try(ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return new Allergy(
                                rs.getInt("Id"),
                                rs.getString("Name")
                        );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public List<Allergy> retrieveAll() {
        List<Integer> allergyIds = new ArrayList<>();

        try (Connection c = getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT Id FROM Allergy;")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        allergyIds.add(rs.getInt("Id"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Allergy> allergies = new ArrayList<>();
        for(int allergyId : allergyIds)
            allergies.add(retrieve(allergyId));
        return allergies;
    }

    public boolean update(int allergyId, AllergyBody body) {
        Allergy allergy = retrieve(allergyId);

        try (Connection c = getConnection()) {
            try (PreparedStatement ps = c.prepareStatement("UPDATE Allergy SET `Name`=? WHERE Id=?;")) {
                ps.setInt(2, allergyId);

                if (body.getName() == null) ps.setString(1, allergy.getName());
                else ps.setString(1, body.getName());
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean delete(int allergyId) {
        // Delete allergy
        try (Connection c = getConnection()) {
            try (PreparedStatement ps1 = c.prepareStatement("DELETE FROM Product_Allergy WHERE AllergyId=?;")) {
                ps1.setInt(1, allergyId);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps3 = c.prepareStatement("DELETE FROM Allergy WHERE Id=?;")) {
                ps3.setInt(1, allergyId);
                return ps3.executeUpdate() > 0;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
