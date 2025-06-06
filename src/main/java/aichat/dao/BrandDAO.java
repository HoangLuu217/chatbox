package aichat.dao;

import aichat.database.DatabaseConnection;
import aichat.models.Brand;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO {
    private final DatabaseConnection db;
    private final String tableName = "Brands";
    
    public BrandDAO() {
        this.db = DatabaseConnection.getInstance();
    }
    
    public List<Brand> findAll() throws SQLException {
        List<Brand> brands = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                brands.add(mapResultSetToModel(rs));
            }
        }
        return brands;
    }
    
    public Brand findById(int id) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE BrandID = ?";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToModel(rs);
                }
            }
        }
        return null;
    }
    
    public void insert(Brand brand) throws SQLException {
        String query = "INSERT INTO " + tableName + " (BrandName) VALUES (?)";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, brand.getBrandName());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    brand.setBrandId(rs.getInt(1));
                }
            }
        }
    }
    
    public void update(Brand brand) throws SQLException {
        String query = "UPDATE " + tableName + " SET BrandName = ? WHERE BrandID = ?";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, brand.getBrandName());
            pstmt.setInt(2, brand.getBrandId());
            pstmt.executeUpdate();
        }
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE BrandID = ?";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    private Brand mapResultSetToModel(ResultSet rs) throws SQLException {
        Brand brand = new Brand();
        brand.setBrandId(rs.getInt("BrandID"));
        brand.setBrandName(rs.getString("BrandName"));
        return brand;
    }
} 