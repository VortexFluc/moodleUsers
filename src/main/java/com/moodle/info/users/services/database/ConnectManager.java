package com.moodle.info.users.services.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectManager {
    public static Connection connectToDB(String database) throws java.sql.SQLException, RuntimeException {
        String url;
        Properties props = new Properties();
        if (database == "postgres") {
            url = "jdbc:postgresql://localhost/db_users";
            props.setProperty("user", "postgres");
            props.setProperty("password", "12345");
            props.setProperty("ssl", "false");
        } else if (database == "mysql") {
            url = "jdbc:mariadb://localhost/moodle_migrate";
            props.setProperty("user", "root");
            props.setProperty("password", "12345");
        } else {
            throw new RuntimeException("Invalid database!");
        }
        return DriverManager.getConnection(url, props);
    }
}
