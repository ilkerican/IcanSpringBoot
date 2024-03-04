package com.ican.initial.demo.AppMain;

import javax.annotation.PostConstruct;

import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.stereotype.Service;

import com.ican.initial.demo.DatabaseLayer.DatabaseManager;

import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Map;

@Service
public class InitService {
    private List<Map<String, Object>> metadata;

    @PostConstruct
    private void loadDataDuringInitialization() {

        Properties properties = new Properties();

        DatabaseManager.DatabaseType dbType = null;
        String url = null;
        String username = null;
        String password = null;

        try (InputStream input = InitService.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);

            // Get the value of a custom parameter like databaseType
            String databaseType = properties.getProperty("databaseType");
            dbType = DatabaseManager.getDatabaseType(databaseType);

            url = properties.getProperty("spring.datasource.url");
            username = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");

            System.out.println("Database Type: " + databaseType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String sql = "SELECT TABLENAME, TD.Description AS TABLEDESCRIPTION, CD.ColumnName, CD.Description AS ";
        sql += "COLUMNDESCRIPTION, CD.ColumnType, CD.ColumnLength, CD.Nullable FROM TableDefinition TD, ColumnDefinition CD ";
        sql += "WHERE CD.TableId = TD.Id";

        DatabaseManager databaseManager = new DatabaseManager(dbType, url, username, password);
        metadata = databaseManager.executeQuery(sql, null);
    }

    public List<Map<String, Object>> getMetaData() {
        return metadata;
    }
}
