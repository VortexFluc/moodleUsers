package com.moodle.info.users.services.database;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;

public class ParseExcel {
    public static void readAndInsert(String path) {
        try {
            parse(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void parse(String file) throws IOException, SQLException {
        String delimiter = ",";

        Connection connection = connectToDB();  // Connection to PostgreSQL DB
        createTable(connection);  // Create a table, which contains data from .xlsx and id's of city and region

        System.out.println("Adding data from: " + file);
        XSSFWorkbook excelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet excelSheet = excelBook.getSheetAt(0);  // ALWAYS FIRST COLUMN (index = 0)
        Iterator rowIterator = excelSheet.rowIterator();
        while (rowIterator.hasNext()) {  // Select a row
            XSSFRow row = (XSSFRow) rowIterator.next();
            Iterator cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {  // Select a cell
                XSSFCell cell = (XSSFCell) cellIterator.next();
                String curCell = cell.getStringCellValue();

                int delimIndex = curCell.indexOf(delimiter);  // Find position of delimiter
                String region = curCell.substring(0, delimIndex).trim();
                String city = curCell.substring(delimIndex+1).trim();
                // System.out.println("Регион: " + region + ", Город: " + city + "."); <--- ОТЛАДОЧНОЕ ГОВНО!!!

                int cityId = insertAndSelectId(connection,"city", city);
                int regId = insertAndSelectId(connection,"regions", region);
                // System.out.println("ID города: " + cityId + "\nID региона: " + regId + "\nТекущая ячейка: " + curCell); <--- ОТЛАДОЧНОЕ ГОВНО!!!
                addRow(connection, curCell, regId, cityId);
            }
        }
        connection.close();
        System.out.println("---".repeat(30));
        System.out.println("DONE!");
    }

    /*
    This function creates a table region_and_city, which contains data from .xlsx file cells and id's city's and region's
    in database.
    */
    public static void createTable(Connection connection) {
        try {
            String createTable = "CREATE TABLE IF NOT EXISTS region_and_city (id SERIAL PRIMARY KEY, region_city VARCHAR(200) UNIQUE, region_id INTEGER, city_id INTEGER)";
            PreparedStatement prepCreate = connection.prepareStatement(createTable);
            prepCreate.executeUpdate();
            System.out.println("Table region_and_city created if not exist!");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    /*
    Make connection to database!
    */
    public static Connection connectToDB() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        //System.out.println("Enter the name of database (ENSURE THAT TABLES city AND regions EXISTS!!!): ");
        String db_name = "db_users";

        //System.out.println("Enter the name of database user: ");
        String db_user = "postgres";

        //System.out.println("Enter password: ");
        String db_password = "12345";

        String url = "jdbc:postgresql://localhost:5432/" + db_name;
        Properties props = new Properties();
        props.setProperty("user", db_user);
        props.setProperty("password", db_password);
        props.setProperty("ssl", "false");
        return DriverManager.getConnection(url, props);
    }

    /*
    Insert parsed values. Return value is id of inserted value in database.
    */
    public static int insertAndSelectId(Connection connection, String table, String value) throws SQLException {
        int id; // id of inserted value in db
        try {
            String insert;
            String getId;
            if (table.equals("city")) {
                insert = "INSERT INTO city (name) VALUES (?) ON CONFLICT DO NOTHING";
                getId = "SELECT id FROM city WHERE name = ?";
            } else {
                insert = "INSERT INTO regions (name) VALUES (?) ON CONFLICT DO NOTHING";
                getId = "SELECT id FROM regions WHERE name = ?";
            }
            // Insert values
            PreparedStatement prepStat = connection.prepareStatement(insert);
            prepStat.setString(1, value);
            prepStat.executeUpdate();

            // Get the ID of inserted value
            PreparedStatement prepGetId;
            prepGetId = connection.prepareStatement(getId);
            prepGetId.setString(1, value);
            ResultSet answer = prepGetId.executeQuery();
            answer.next();
            id = answer.getInt("id");
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            id = -1;
        }
        return id;
    }

    /*
    Inserting into database region_and_city values.
    */
    public static void addRow(Connection connection, String regionCity, int idRegion, int idCity) {
        try {
            String addQuery = "INSERT INTO region_and_city (region_city, region_id, city_id) VALUES (?, ?, ?) ON CONFLICT DO NOTHING";
            PreparedStatement prepAdd = connection.prepareStatement(addQuery);
            prepAdd.setString(1, regionCity);
            prepAdd.setInt(2, idRegion);
            prepAdd.setInt(3, idCity);
            prepAdd.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }
}
