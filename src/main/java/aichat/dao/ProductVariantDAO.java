//package aichat.dao;
//
//import aichat.database.DatabaseConnection;
//import aichat.models.ProductVariant;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class ProductVariantDAO {
//    private final DatabaseConnection db;
//    private final String tableName = "ProductVariants";
//    private final ProductBaseDAO productBaseDAO;
//    
//    public ProductVariantDAO() {
//        this.db = DatabaseConnection.getInstance();
//        this.productBaseDAO = new ProductBaseDAO();
//    }
//    
//    public ProductVariant findById(int id) throws SQLException {
//        String query = "SELECT * FROM " + tableName + " WHERE VariantID = ? AND IsActive = 1";
//        
//        try (Connection conn = db.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setInt(1, id);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapResultSetToModel(rs);
//                }
//            }
//        }
//        return null;
//    }
//    
//    public List<ProductVariant> findAll() throws SQLException {
//        List<ProductVariant> results = new ArrayList<>();
//        String query = "SELECT * FROM " + tableName + " WHERE IsActive = 1";
//        
//        try (Connection conn = db.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                results.add(mapResultSetToModel(rs));
//            }
//        }
//        return results;
//    }
//    
//    public void insert(ProductVariant variant) throws SQLException {
//        String query = "INSERT INTO " + tableName + 
//                      " (ProductBaseID, Color, RAM, ROM, SKU, Price, StockQuantity, ImageURLs, IsActive) " +
//                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        
//        try (Connection conn = db.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            pstmt.setInt(1, variant.getProductBaseId());
//            pstmt.setString(2, variant.getColor());
//            pstmt.setObject(3, variant.getRam());
//            pstmt.setObject(4, variant.getRom());
//            pstmt.setString(5, variant.getSku());
//            pstmt.setBigDecimal(6, variant.getPrice());
//            pstmt.setInt(7, variant.getStockQuantity());
//            pstmt.setString(8, variant.getImageUrls() != null ? String.join(",", variant.getImageUrls()) : null);
//            pstmt.setBoolean(9, variant.isActive());
//            
//            int affectedRows = pstmt.executeUpdate();
//            if (affectedRows > 0) {
//                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        variant.setVariantId(generatedKeys.getInt(1));
//                    }
//                }
//            }
//        }
//    }
//    
//    public boolean update(ProductVariant variant) throws SQLException {
//        String query = "UPDATE " + tableName + 
//                      " SET ProductBaseID = ?, Color = ?, RAM = ?, ROM = ?, " +
//                      "SKU = ?, Price = ?, StockQuantity = ?, ImageURLs = ?, IsActive = ? " +
//                      "WHERE VariantID = ?";
//        
//        try (Connection conn = db.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setInt(1, variant.getProductBaseId());
//            pstmt.setString(2, variant.getColor());
//            pstmt.setObject(3, variant.getRam());
//            pstmt.setObject(4, variant.getRom());
//            pstmt.setString(5, variant.getSku());
//            pstmt.setBigDecimal(6, variant.getPrice());
//            pstmt.setInt(7, variant.getStockQuantity());
//            pstmt.setString(8, variant.getImageUrls() != null ? String.join(",", variant.getImageUrls()) : null);
//            pstmt.setBoolean(9, variant.isActive());
//            pstmt.setInt(10, variant.getVariantId());
//            
//            return pstmt.executeUpdate() > 0;
//        }
//    }
//    
//    public boolean delete(int id) throws SQLException {
//        String query = "UPDATE " + tableName + " SET IsActive = 0 WHERE VariantID = ?";
//        try (Connection conn = db.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setInt(1, id);
//            return pstmt.executeUpdate() > 0;
//        }
//    }
//    
//    private ProductVariant mapResultSetToModel(ResultSet rs) throws SQLException {
//        ProductVariant variant = new ProductVariant();
//        variant.setVariantId(rs.getInt("VariantID"));
//        variant.setProductBaseId(rs.getInt("ProductBaseID"));
//        variant.setColor(rs.getString("Color"));
//        variant.setRam(rs.getObject("RAM", Integer.class));
//        variant.setRom(rs.getObject("ROM", Integer.class));
//        variant.setSku(rs.getString("SKU"));
//        variant.setPrice(rs.getBigDecimal("Price"));
//        variant.setStockQuantity(rs.getInt("StockQuantity"));
//        
//        // Parse image URLs from comma-separated string
//        String imageUrlsStr = rs.getString("ImageURLs");
//        if (imageUrlsStr != null && !imageUrlsStr.isEmpty()) {
//            variant.setImageUrls(Arrays.asList(imageUrlsStr.split(",")));
//        }
//        
//        variant.setActive(rs.getBoolean("IsActive"));
//        
//        // Load related product
//        try {
//            variant.setProductBase(productBaseDAO.findById(variant.getProductBaseId()));
//        } catch (SQLException e) {
//            System.err.println("Error loading related product: " + e.getMessage());
//        }
//        
//        return variant;
//    }
//    
//    // Additional methods specific to ProductVariant
//    public List<ProductVariant> findByProductBaseId(int productBaseId) throws SQLException {
//        List<ProductVariant> results = new ArrayList<>();
//        String query = "SELECT * FROM " + tableName + " WHERE ProductBaseID = ? AND IsActive = 1";
//        try (Connection conn = db.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setInt(1, productBaseId);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    results.add(mapResultSetToModel(rs));
//                }
//            }
//        }
//        return results;
//    }
//    
//    public ProductVariant findBySku(String sku) throws SQLException {
//        String query = "SELECT * FROM " + tableName + " WHERE SKU = ? AND IsActive = 1";
//        try (Connection conn = db.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setString(1, sku);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapResultSetToModel(rs);
//                }
//            }
//        }
//        return null;
//    }
//    
//    public List<ProductVariant> findByColor(String color) throws SQLException {
//        List<ProductVariant> results = new ArrayList<>();
//        String query = "SELECT * FROM " + tableName + " WHERE Color = ? AND IsActive = 1";
//        try (Connection conn = db.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setString(1, color);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    results.add(mapResultSetToModel(rs));
//                }
//            }
//        }
//        return results;
//    }
//    
//    public List<ProductVariant> findByPriceRange(double minPrice, double maxPrice) throws SQLException {
//        List<ProductVariant> results = new ArrayList<>();
//        String query = "SELECT * FROM " + tableName + 
//                      " WHERE Price BETWEEN ? AND ? AND IsActive = 1";
//        try (Connection conn = db.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setDouble(1, minPrice);
//            pstmt.setDouble(2, maxPrice);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    results.add(mapResultSetToModel(rs));
//                }
//            }
//        }
//        return results;
//    }
//    
//    public boolean updateStockQuantity(int variantId, int newQuantity) throws SQLException {
//        String query = "UPDATE " + tableName + 
//                      " SET StockQuantity = ? WHERE VariantID = ? AND IsActive = 1";
//        try (Connection conn = db.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setInt(1, newQuantity);
//            pstmt.setInt(2, variantId);
//            return pstmt.executeUpdate() > 0;
//        }
//    }
//} 