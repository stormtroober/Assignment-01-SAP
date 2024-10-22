// DatabaseConfiguration.java
package sap.ass01.layered.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfiguration {

    private static final String PROPERTIES_FILE = "/database.properties";
    private static final String DB_TYPE_KEY = "db.type";
    private static DatabaseType databaseType = DatabaseType.DISK;  // Default to DISK

    static {
        loadConfiguration();
    }

    private static void loadConfiguration() {
        try (InputStream input = DatabaseConfiguration.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                String dbType = prop.getProperty(DB_TYPE_KEY);
                databaseType = DatabaseType.valueOf(dbType.toUpperCase());
            }
        } catch (IOException | IllegalArgumentException e) {
            // Default to DISK if any exception occurs
            databaseType = DatabaseType.DISK;
        }
    }

    // Setter for the database type (for testing purposes)
    public static void setDatabaseType(DatabaseType dbType) {
        databaseType = dbType;
    }

    // Getter for the database type
    public static DatabaseType getDatabaseType() {
        return databaseType;
    }
}