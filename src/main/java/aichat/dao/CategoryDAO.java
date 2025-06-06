package aichat.dao;

import aichat.database.DatabaseConnection;
import aichat.models.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private final DatabaseConnection db;
    private final String tableName = "Categories";
    
    public CategoryDAO() {
        this.db = DatabaseConnection.getInstance();
    }
    
    public List<Category> findAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                categories.add(mapResultSetToModel(rs));
            }
        }
        return categories;
    }
    
    public Category findById(int id) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE CategoryID = ?";
        
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
    
    public void insert(Category category) throws SQLException {
        String query = "INSERT INTO " + tableName + " (CategoryName, ParentCategoryID) VALUES (?, ?)";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setObject(2, category.getParentCategoryId());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    category.setCategoryId(rs.getInt(1));
                }
            }
        }
    }
    
    public void update(Category category) throws SQLException {
        String query = "UPDATE " + tableName + " SET CategoryName = ?, ParentCategoryID = ? WHERE CategoryID = ?";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, category.getCategoryName());
            pstmt.setObject(2, category.getParentCategoryId());
            pstmt.setInt(3, category.getCategoryId());
            pstmt.executeUpdate();
        }
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE CategoryID = ?";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    public List<Category> findByParentId(Integer parentId) throws SQLException {
        List<Category> results = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + 
                      " WHERE ParentCategoryID " + (parentId == null ? "IS NULL" : "= ?");
        
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            if (parentId != null) {
                pstmt.setInt(1, parentId);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToModel(rs));
                }
            }
        }
        return results;
    }
    
    public List<Category> findRootCategories() throws SQLException {
        return findByParentId(null);
    }
    
    private Category mapResultSetToModel(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getInt("CategoryID"));
        category.setCategoryName(rs.getString("CategoryName"));
        category.setParentCategoryId(rs.getObject("ParentCategoryID", Integer.class));
        return category;
    }
} 