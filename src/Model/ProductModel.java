package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.sql.DataSource;

public class ProductModel {

    DataSource ds = DatabaseSource.getMySQLDataSource();

    public ObservableList< Product> getDistributors() {
        try (Connection con = ds.getConnection()) {
            ObservableList< Product> ol = FXCollections.observableArrayList();
            String query = "SELECT * FROM product";
            PreparedStatement pStmt = con.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                ol.add(new Product(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8)));
            }
            return ol;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void add(Product p) {
        try (Connection con = ds.getConnection()) {
            String query = "INSERT INTO product VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, p.getProductID());
            pStmt.setString(2, p.getProductName());
            pStmt.setString(3, p.getProductDescription());
            pStmt.setString(4, p.getProductBrand());
            pStmt.setInt(5, p.getProductStrength());
            pStmt.setString(6, p.getProductType());
            pStmt.setInt(7, p.getProductStock());
            pStmt.setInt(8, p.getProductMinStock());
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int isExisting(String code) {
        try (Connection con = ds.getConnection()) {
            String query = "SELECT count(*) FROM product WHERE productID = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, code);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void update(Product p) {
        try (Connection con = ds.getConnection()) {
            String query = "UPDATE product SET " + "productName='" + p.getProductName() + "'," + "productDescription='" + p.getProductDescription() + "'," + "productBrand='" + p.getProductBrand() + "'," + "productStrength=" + p.getProductStrength() + "," + "productType='" + p.getProductType() + "'," + "productStock=" + p.getProductStock() + "," + "productMinStock=" + p.getProductMinStock() + " WHERE productID='" + p.getProductID() + "'";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(String productID) {
        try (Connection con = ds.getConnection()) {
            String query = "DELETE FROM product WHERE productID='" + productID + "'";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
