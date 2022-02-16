package com.moodle.info.users.services.database;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class PopulateDB {
    public static void actulizeDB() throws SQLException {
        Connection mysqlConnection = ConnectManager.connectToDB("mysql");
        Connection postgresConnection = ConnectManager.connectToDB("postgres");
        DataManager.actulize(mysqlConnection, postgresConnection);
    }
}
