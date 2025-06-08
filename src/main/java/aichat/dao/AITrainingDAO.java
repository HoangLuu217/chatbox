package aichat.dao;

import aichat.database.DatabaseConnection;
import aichat.models.AITraining;
import java.math.BigDecimal;
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

    public List<Integer> getAllRomValues() throws SQLException {
        List<Integer> romList = new ArrayList<>();
        String sql = "SELECT DISTINCT ROM FROM AITraining WHERE ROM IS NOT NULL";

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                romList.add(rs.getInt("ROM"));
            }
        }

        return romList;
    }

    public List<BigDecimal> getAllPrices() throws SQLException {
        List<BigDecimal> priceList = new ArrayList<>();
        String sql = "SELECT DISTINCT Price FROM AITraining WHERE Price IS NOT NULL";

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                priceList.add(rs.getBigDecimal("Price"));
            }
        }

        return priceList;
    }

    public List<AITraining> getAllTrainings() throws SQLException {
        List<AITraining> trainings = new ArrayList<>();
        String sql = "SELECT TrainingID, ProductBaseID, VariantID, ProductName, BrandName, CategoryName, Color, RAM, ROM, Price, Specifications, Description, IsActive "
                + "FROM AITraining WHERE IsActive = 1";

        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int trainingId = rs.getInt("TrainingID");
                int productBaseId = rs.getInt("ProductBaseID");
                int variantId = rs.getInt("VariantID");
                String productName = rs.getString("ProductName");
                String brandName = rs.getString("BrandName");
                String categoryName = rs.getString("CategoryName");
                String color = rs.getString("Color");
                Integer rom = rs.getObject("ROM") != null ? rs.getInt("ROM") : null;
                BigDecimal price = rs.getBigDecimal("Price");
                String specifications = rs.getString("Specifications");
                String description = rs.getString("Description");
                boolean isActive = rs.getBoolean("IsActive");

                AITraining training = new AITraining(
                        trainingId, productBaseId, variantId, productName, brandName,
                        categoryName, color, rom, price, specifications, description, isActive
                );

                trainings.add(training);
            }
        }

        return trainings;
    }

    // Helper chung
    private List<String> getFieldList(String field) throws SQLException {
        return getFieldList(field, field);
    }

    private List<String> getFieldList(String field, String alias) throws SQLException {
        String sql = "SELECT DISTINCT " + field + " AS " + alias
                + " FROM AITraining WHERE IsActive = 1 AND " + alias + " IS NOT NULL";
        List<String> results = new ArrayList<>();

        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                results.add(rs.getString(alias).trim());
            }
        }
        return results;
    }
}
