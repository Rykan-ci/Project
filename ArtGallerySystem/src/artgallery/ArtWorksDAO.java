package artgallery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ArtWorksDAO {

    // === Insert artwork ===
    public static void insertArtwork(ArtWorks art) {
        String sql = "INSERT INTO artworks (title, artist, description, likes, dislikes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, art.getTitle());
            stmt.setString(2, art.getArtist());
            stmt.setString(3, art.getDescription());
            stmt.setInt(4, art.getLikes());
            stmt.setInt(5, art.getDislikes());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Artwork inserted successfully!");
            } else {
                System.out.println("⚠️ No artwork inserted.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Insert failed: " + e.getMessage());
        }
    }

    public static void likeArtwork(int id) {
        String sql = "UPDATE artworks SET likes = likes + 1 WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dislikeArtwork(int id) {
        String sql = "UPDATE artworks SET dislikes = dislikes + 1 WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === Delete artwork by ID ===
    public static void deleteArtwork(int id) {
        String sql = "DELETE FROM artworks WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // === Like an artwork ===
    public static void likeArtWork(int id) {
        String sql = "UPDATE artworks SET likes = likes + 1 WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("👍 Artwork liked (id=" + id + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === Dislike an artwork ===
    public static void dislikeArtWork(int id) {
        String sql = "UPDATE artworks SET dislikes = dislikes + 1 WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("👎 Artwork disliked (id=" + id + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // === Get all artworks from database ===
    public static List<ArtWorks> getAllArtWorks() {
        List<ArtWorks> list = new ArrayList<>();
        String sql = "SELECT * FROM artworks";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ArtWorks art = new ArtWorks(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("artist"),
                    rs.getString("description"),
                    rs.getInt("likes"),
                    rs.getInt("dislikes")
                );
                list.add(art);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
