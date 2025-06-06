package aichat.dao;

import aichat.database.DatabaseConnection;
import aichat.models.AITraining;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AITrainingDAO {
    private final DatabaseConnection db;
    private final String tableName = "AITraining";
    
    public AITrainingDAO() {
        this.db = DatabaseConnection.getInstance();
    }
    
    public List<String> getAllProductNames() throws SQLException {
        return getFieldList("ProductName");
    }

    public List<String> getAllBrandNames() throws SQLException {
        return getFieldList("BrandName");
    }

    public List<String> getAllCategoryNames() throws SQLException {
        return getFieldList("CategoryName");
    }

    public List<String> getAllColors() throws SQLException {
        return getFieldList("Color");
    }

    public List<String> getAllSpecifications() throws SQLException {
        return getFieldList("Specifications");
    }

    public List<String> getAllDescriptions() throws SQLException {
        return getFieldList("Description");
    }

    public List<String> getAllRamValues() throws SQLException {
        return getFieldList("CAST(RAM AS NVARCHAR)", "RAM");
    }

    public List<String> getAllRomValues() throws SQLException {
        return getFieldList("CAST(ROM AS NVARCHAR)", "ROM");
    }

    public List<String> getAllPrices() throws SQLException {
        return getFieldList("CAST(Price AS NVARCHAR)", "Price");
    }

    // Helper chung
    private List<String> getFieldList(String field) throws SQLException {
        return getFieldList(field, field);
    }

    private List<String> getFieldList(String field, String alias) throws SQLException {
        String sql = "SELECT DISTINCT " + field + " AS " + alias +
                     " FROM AITraining WHERE IsActive = 1 AND " + alias + " IS NOT NULL";
        List<String> results = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                results.add(rs.getString(alias).trim());
            }
        }
        return results;
    }
} 