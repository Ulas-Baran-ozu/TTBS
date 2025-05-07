package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;

public class DBConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/TTBS";
    public static final String USER = "root";
    public static final String PASSWORD = "happy34Moonlight";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //CODE TO CONNECT TO DATABASE (WİLL BE USED WHEN INITIALİSİNG
    /*public static void main(String[] args) {
        //Connecting to database
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Connection to the database established!\n");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }*/
}
