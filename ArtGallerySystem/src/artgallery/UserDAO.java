package artgallery;

import java.sql.*;
import javax.swing.JOptionPane;

public class UserDAO {

    // === Register new user ===
    public static boolean registerUser(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "✅ Account created successfully!");
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "⚠️ Username already exists!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Registration failed: " + e.getMessage());
        }

        return false;
    }
    
    public static String getUserRole(String username, String password) {
        String role = null;
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return role;
    }

    // === Login check ===
    public static boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // True if credentials match

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
