package aichat.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseExample {
    public static void main(String[] args) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        
        try {
            // Test the connection
            if (db.testConnection()) {
                System.out.println("Connection test successful!");
                
                // Example: Query all categories
                String query = "SELECT * FROM Categories";
                try (ResultSet rs = db.executeQuery(query)) {
                    while (rs.next()) {
                        int categoryId = rs.getInt("CategoryID");
                        String categoryName = rs.getString("CategoryName");
                        System.out.printf("Category ID: %d, Name: %s%n", categoryId, categoryName);
                    }
                }
                
                // Example: Insert a new category using PreparedStatement
                String insertQuery = "INSERT INTO Categories (CategoryName, IsActive) VALUES (?, ?)";
                try (var pstmt = db.prepareStatement(insertQuery)) {
                    pstmt.setString(1, "New Category");
                    pstmt.setBoolean(2, true);
                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("Rows affected: " + rowsAffected);
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } finally {
            // Always close the connection when done
            db.closeConnection();
        }
    }
} 