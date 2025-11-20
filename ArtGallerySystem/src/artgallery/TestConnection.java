package artgallery;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("✅ Connection test successful!");
        } else {
            System.out.println("❌ Connection test failed!");
        }
    }
}