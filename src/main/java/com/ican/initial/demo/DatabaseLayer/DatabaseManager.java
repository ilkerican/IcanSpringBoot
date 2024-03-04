package com.ican.initial.demo.DatabaseLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//https://stackoverflow.com/questions/15241960/com-microsoft-sqlserver-jdbc-sqlserverdriver-not-found-error

public class DatabaseManager {

    public static String PRIMARYKEYFIELDNAME = "ID";
    private String URL;
    private String USERNAME;
    private String PASSWORD;
    private DatabaseType DBTYPE;

    public static enum DatabaseType {
        MSSQL,
        ORACLE,
        OTHER
    }

    public static DatabaseType getDatabaseType(String dbTypeasString) {

        switch (dbTypeasString) {
            case "MSSQL":
                return DatabaseType.MSSQL;
            case "ORACLE":
                return DatabaseType.ORACLE;

            default:
                return DatabaseType.OTHER;
        }

    }

    private Connection connection;

    public DatabaseManager(Connection con) {
        connection = con;
    }

    public DatabaseManager(DatabaseType dbType, String url, String userName, String password) {
        DBTYPE = dbType;
        URL = url;
        USERNAME = userName;
        PASSWORD = password;
    }

    private Connection getConnection() throws SQLException {

        if (connection == null) {
            if (DBTYPE == DatabaseType.MSSQL) {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    return connection;
                } catch (ClassNotFoundException e) {
                    throw new SQLException("MS SQL Server JDBC Driver not found", e);
                }
            } else {
                // TODO : Implement for Oracle
                return null;
            }
        } else {
            return connection;
        }

    }

    public void save(String tableName, Map<String, Object> values) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                // Check if there is a primary key in the values

                if (values.containsKey(PRIMARYKEYFIELDNAME)) {
                    // Check if the record already exists
                    if (recordExists(tableName, (int) values.get(PRIMARYKEYFIELDNAME))) {
                        updateRecord(tableName, values);
                    } else {
                        insertRecord(tableName, values);
                    }
                } else {
                    // System.out.println("Primary key not found in values.");
                    insertRecord(tableName, values);
                }

            } else {
                System.out.println("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean recordExists(String tableName, int primaryKeyValue) throws SQLException {
        try (Connection connection = getConnection()) {
            assert connection != null;
            try (PreparedStatement statement = connection
                    .prepareStatement("SELECT 1 FROM " + tableName + " WHERE " + PRIMARYKEYFIELDNAME + " = ?")) {
                statement.setInt(1, primaryKeyValue);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        }
    }

    private void updateRecord(String tableName, Map<String, Object> values) throws SQLException {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                StringBuilder updateQuery = new StringBuilder("UPDATE " + tableName + " SET ");

                // Append columns to the update query
                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    if (!entry.getKey().equals(PRIMARYKEYFIELDNAME)) {
                        updateQuery.append(entry.getKey()).append(" = ?, ");
                    }
                }

                // Remove the trailing comma and space
                updateQuery.delete(updateQuery.length() - 2, updateQuery.length());

                // Append the WHERE clause
                updateQuery.append(" WHERE " + PRIMARYKEYFIELDNAME + " = ?");

                try (PreparedStatement statement = connection.prepareStatement(updateQuery.toString())) {
                    // Set parameter values for columns
                    int i = 1;
                    for (Map.Entry<String, Object> entry : values.entrySet()) {
                        if (!entry.getKey().equals(PRIMARYKEYFIELDNAME)) {
                            setParameter(statement, i++, entry.getKey(), entry.getValue());
                        }
                    }

                    // Set parameter value for the primary key
                    setParameter(statement, i, PRIMARYKEYFIELDNAME, values.get(PRIMARYKEYFIELDNAME));

                    // Execute the update query
                    statement.executeUpdate();
                }
            } else {

                System.out.println("Failed to establish a database connection.");
            }
        }
    }

    private void insertRecord(String tableName, Map<String, Object> values) throws SQLException {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                // Prepare the insert query
                StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");

                // Append columns to the insert query
                for (String column : values.keySet()) {
                    insertQuery.append(column).append(", ");
                }

                // Remove the trailing comma and space
                insertQuery.delete(insertQuery.length() - 2, insertQuery.length());

                // Append values placeholders and closing parenthesis
                insertQuery.append(") VALUES (");
                for (int i = 0; i < values.size(); i++) {
                    insertQuery.append("?, ");
                }

                // Remove the trailing comma and space
                insertQuery.delete(insertQuery.length() - 2, insertQuery.length());

                // Append closing parenthesis
                insertQuery.append(")");

                try (PreparedStatement statement = connection.prepareStatement(insertQuery.toString())) {
                    // Set parameter values for values
                    int i = 1;
                    for (Map.Entry<String, Object> entry : values.entrySet()) {
                        setParameter(statement, i++, entry.getKey(), entry.getValue());
                    }

                    // Execute the insert query
                    statement.executeUpdate();
                }
            } else {
                System.out.println("Failed to establish a database connection.");
            }
        }
    }

    public void deleteRecord(String tableName, int primaryKeyValue) {
        try (Connection connection = getConnection()) {
            assert connection != null;
            try (PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM " + tableName + " WHERE " + PRIMARYKEYFIELDNAME + " = ?")) {

                setParameter(statement, 1, PRIMARYKEYFIELDNAME, primaryKeyValue);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> executeQuery(String sqlQuery, Map<String, Object> parameters) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                    // Set parameters in the prepared statement

                    if (parameters != null) {
                        if (!parameters.isEmpty()) {
                            int i = 1;
                            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                                setParameter(statement, i++, entry.getKey(), entry.getValue());
                            }
                        }
                    }
                    // Execute the query and return the result set
                    // return statement.executeQuery();
                    ResultSet resultSet = statement.executeQuery();

                    List<Map<String, Object>> resultList = new ArrayList<>();

                    if (resultSet != null) {
                        // Get column names from the result set
                        int columnCount = resultSet.getMetaData().getColumnCount();
                        List<String> columnNames = new ArrayList<>();
                        for (int i = 1; i <= columnCount; i++) {
                            columnNames.add(resultSet.getMetaData().getColumnName(i));
                        }

                        // Iterate through the result set and build rows
                        while (resultSet.next()) {
                            Map<String, Object> row = new HashMap<>();
                            for (String columnName : columnNames) {
                                row.put(columnName, resultSet.getObject(columnName));
                            }
                            resultList.add(row);
                        }
                    }

                    return resultList;
                }
            } else {
                System.out.println("Failed to establish a database connection.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void setParameter(PreparedStatement statement, int index, String columnName, Object value)
            throws SQLException {
        // Set parameter values based on the data type of the column
        if (value == null) {
            statement.setNull(index, java.sql.Types.NULL);
        } else if (value instanceof Integer) {
            statement.setInt(index, (int) value);
        } else if (value instanceof Long) {
            statement.setLong(index, (long) value);
        } else if (value instanceof Double) {
            statement.setDouble(index, (double) value);
        } else if (value instanceof Float) {
            statement.setFloat(index, (float) value);
        } else if (value instanceof String) {
            statement.setString(index, (String) value);
        } else if (value instanceof java.sql.Date) {
            statement.setDate(index, (java.sql.Date) value);
        } else if (value instanceof java.sql.Timestamp) {
            statement.setTimestamp(index, (java.sql.Timestamp) value);
        } else if (value instanceof Boolean) {
            statement.setBoolean(index, (boolean) value);
        } else {
            // Handle additional data types as needed
            System.out.println("Unsupported data type for column: " + columnName);
        }
    }

}
