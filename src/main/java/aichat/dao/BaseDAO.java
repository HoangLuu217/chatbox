//package aichat.dao;
//
//import aichat.database.DatabaseConnection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.PreparedStatement;
//import java.sql.Statement;
//import java.sql.Connection;
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class BaseDAO<T> {
//    protected final DatabaseConnection db;
//    protected final String tableName;
//    
//    public BaseDAO(String tableName) {
//        this.db = DatabaseConnection.getInstance();
//        this.tableName = tableName;
//    }
//    
//    // Convert ResultSet to model object
//    protected abstract T mapResultSetToModel(ResultSet rs) throws SQLException;
//    
//    // Get column names for insert/update operations
//    protected abstract String[] getColumnNames();
//    
//    // Get values for insert/update operations
//    protected abstract Object[] getColumnValues(T model);
//    
//    // Get primary key column name
//    protected abstract String getPrimaryKeyColumn();
//    
//    // Get primary key value
//    protected abstract Object getPrimaryKeyValue(T model);
//    
//    // Find by ID
//    public T findById(Object id) throws SQLException {
//        String query = "SELECT * FROM " + tableName + " WHERE " + getPrimaryKeyColumn() + " = ? AND IsActive = 1";
//        
//        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
//            pstmt.setObject(1, id);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    return mapResultSetToModel(rs);
//                }
//            }
//        }
//        return null;
//    }
//    
//    // Find all active records
//    public List<T> findAll() throws SQLException {
//        List<T> results = new ArrayList<>();
//        String query = "SELECT * FROM " + tableName + " WHERE IsActive = 1";
//        
//        try (Statement stmt = db.getConnection().createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                results.add(mapResultSetToModel(rs));
//            }
//        }
//        return results;
//    }
//    
//    // Insert new record
//    public T insert(T model) throws SQLException {
//        StringBuilder columns = new StringBuilder();
//        StringBuilder values = new StringBuilder();
//        String[] columnNames = getColumnNames();
//        Object[] columnValues = getColumnValues(model);
//        
//        for (int i = 0; i < columnNames.length; i++) {
//            if (i > 0) {
//                columns.append(", ");
//                values.append(", ");
//            }
//            columns.append(columnNames[i]);
//            values.append("?");
//        }
//        
//        String query = "INSERT INTO " + tableName + " (" + columns.toString() + 
//                      ", IsActive, CreatedAt) VALUES (" + values.toString() + 
//                      ", ?, ?)";
//        
//        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            int paramIndex = 1;
//            for (Object value : columnValues) {
//                pstmt.setObject(paramIndex++, value);
//            }
//            pstmt.setBoolean(paramIndex++, model.isActive());
//            pstmt.setObject(paramIndex++, model.getCreatedAt());
//            
//            int affectedRows = pstmt.executeUpdate();
//            if (affectedRows > 0) {
//                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        setGeneratedId(model, generatedKeys.getObject(1));
//                    }
//                }
//            }
//            return model;
//        }
//    }
//    
//    // Update existing record
//    public boolean update(T model) throws SQLException {
//        StringBuilder setClause = new StringBuilder();
//        String[] columnNames = getColumnNames();
//        Object[] columnValues = getColumnValues(model);
//        
//        for (int i = 0; i < columnNames.length; i++) {
//            if (i > 0) {
//                setClause.append(", ");
//            }
//            setClause.append(columnNames[i]).append(" = ?");
//        }
//        
//        String query = "UPDATE " + tableName + " SET " + setClause.toString() + 
//                      " WHERE " + getPrimaryKeyColumn() + " = ?";
//        
//        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
//            int paramIndex = 1;
//            for (Object value : columnValues) {
//                pstmt.setObject(paramIndex++, value);
//            }
//            pstmt.setObject(paramIndex, getPrimaryKeyValue(model));
//            
//            return pstmt.executeUpdate() > 0;
//        }
//    }
//    
//    // Soft delete (set IsActive = false)
//    public boolean delete(Object id) throws SQLException {
//        String query = "UPDATE " + tableName + " SET IsActive = 0 WHERE " + 
//                      getPrimaryKeyColumn() + " = ?";
//        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
//            pstmt.setObject(1, id);
//            return pstmt.executeUpdate() > 0;
//        }
//    }
//    
//    // Hard delete (remove from database)
//    public boolean hardDelete(Object id) throws SQLException {
//        String query = "DELETE FROM " + tableName + " WHERE " + getPrimaryKeyColumn() + " = ?";
//        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
//            pstmt.setObject(1, id);
//            return pstmt.executeUpdate() > 0;
//        }
//    }
//    
//    // Set generated ID after insert
//    protected abstract void setGeneratedId(T model, Object id);
//} 