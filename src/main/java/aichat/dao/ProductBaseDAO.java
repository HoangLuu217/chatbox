package aichat.dao;

import aichat.models.Brand;
import aichat.models.Category;
import aichat.models.ProductBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ProductBaseDAO extends BaseDAO<ProductBase> {
    private final CategoryDAO categoryDAO;
    private final BrandDAO brandDAO;
    
    public ProductBaseDAO() {
        super("ProductBase");
        this.categoryDAO = new CategoryDAO();
        this.brandDAO = new BrandDAO();
    }
    
    @Override
    public List<ProductBase> findAll() throws SQLException {
        List<ProductBase> results = new ArrayList<>();
        String query = "SELECT p.*, c.CategoryName, c.ParentCategoryID, b.BrandName " +
                      "FROM " + tableName + " p " +
                      "LEFT JOIN Categories c ON p.CategoryID = c.CategoryID " +
                      "LEFT JOIN Brands b ON p.BrandID = b.BrandID " +
                      "WHERE p.IsActive = 1";
        
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ProductBase product = new ProductBase();
                product.setProductBaseId(rs.getInt("ProductBaseID"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategoryId(rs.getInt("CategoryID"));
                product.setBrandId(rs.getInt("BrandID"));
                product.setDescription(rs.getString("Description"));
                product.setSpecifications(rs.getString("Specifications"));
                product.setActive(rs.getBoolean("IsActive"));
                product.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
                
                // Set category
                Category category = new Category();
                category.setCategoryId(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));
                category.setParentCategoryId(rs.getObject("ParentCategoryID", Integer.class));
                product.setCategory(category);
                
                // Set brand
                Brand brand = new Brand();
                brand.setBrandId(rs.getInt("BrandID"));
                brand.setBrandName(rs.getString("BrandName"));
                product.setBrand(brand);
                
                results.add(product);
            }
        }
        return results;
    }
    
    @Override
    protected ProductBase mapResultSetToModel(ResultSet rs) throws SQLException {
        ProductBase product = new ProductBase();
        product.setProductBaseId(rs.getInt("ProductBaseID"));
        product.setProductName(rs.getString("ProductName"));
        product.setCategoryId(rs.getInt("CategoryID"));
        product.setBrandId(rs.getInt("BrandID"));
        product.setDescription(rs.getString("Description"));
        product.setSpecifications(rs.getString("Specifications"));
        product.setActive(rs.getBoolean("IsActive"));
        product.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        return product;
    }
    
    @Override
    protected String[] getColumnNames() {
        return new String[]{
            "ProductName",
            "CategoryID",
            "BrandID",
            "Description",
            "Specifications"
        };
    }
    
    @Override
    protected Object[] getColumnValues(ProductBase model) {
        return new Object[]{
            model.getProductName(),
            model.getCategoryId(),
            model.getBrandId(),
            model.getDescription(),
            model.getSpecifications()
        };
    }
    
    @Override
    protected String getPrimaryKeyColumn() {
        return "ProductBaseID";
    }
    
    @Override
    protected Object getPrimaryKeyValue(ProductBase model) {
        return model.getProductBaseId();
    }
    
    @Override
    protected void setGeneratedId(ProductBase model, Object id) {
        model.setProductBaseId(((Number) id).intValue());
    }
    
    // Additional methods specific to ProductBase
    public List<ProductBase> findByCategoryId(int categoryId) throws SQLException {
        List<ProductBase> results = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + " WHERE CategoryID = ? AND IsActive = 1";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToModel(rs));
                }
            }
        }
        return results;
    }
    
    public List<ProductBase> findByBrandId(int brandId) throws SQLException {
        List<ProductBase> results = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + " WHERE BrandID = ? AND IsActive = 1";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, brandId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToModel(rs));
                }
            }
        }
        return results;
    }
    
    public List<ProductBase> searchByName(String searchTerm) throws SQLException {
        List<ProductBase> results = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + 
                      " WHERE ProductName LIKE ? AND IsActive = 1";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchTerm + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToModel(rs));
                }
            }
        }
        return results;
    }
    
    public Category getCategory(int categoryId) throws SQLException {
        return categoryDAO.findById(categoryId);
    }
    
    public Brand getBrand(int brandId) throws SQLException {
        return brandDAO.findById(brandId);
    }
} 