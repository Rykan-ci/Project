package artgallery;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/art_gallery",
                "root",
                "bjp37kiiwj2aoq2ous" // change this to your MySQL password
            );
            System.out.println("✅ Connected to MySQL!");
        } catch (Exception e) {
            System.out.println("❌ Connection failed!");
            e.printStackTrace();
        }
        return conn;
    }
}