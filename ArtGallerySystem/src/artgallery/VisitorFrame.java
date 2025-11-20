package artgallery;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VisitorFrame extends JFrame {
    private JTable artworkTable;
    private DefaultTableModel tableModel;
    private JButton likeButton, dislikeButton, refreshButton;

    public VisitorFrame() {
        setTitle("Visitor Page");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JLabel welcome = new JLabel("Available Artworks", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcome, BorderLayout.NORTH);

        // columns: include Likes and Dislikes
        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Artist", "Description", "Likes", "Dislikes"}, 0) {
            // make cells not editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        artworkTable = new JTable(tableModel);
        artworkTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        artworkTable.setAutoCreateRowSorter(true); // allow sorting
        add(new JScrollPane(artworkTable), BorderLayout.CENTER);

        // Buttons panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        likeButton = new JButton("👍 Like");
        dislikeButton = new JButton("👎 Dislike");
        refreshButton = new JButton("Refresh");

        btnPanel.add(likeButton);
        btnPanel.add(dislikeButton);
        btnPanel.add(refreshButton);
        
        JButton logoutButton = new JButton("Logout");
        btnPanel.add(logoutButton);

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });


        add(btnPanel, BorderLayout.SOUTH);

        // Action listeners
        likeButton.addActionListener(e -> handleLike());
        dislikeButton.addActionListener(e -> handleDislike());
        refreshButton.addActionListener(e -> loadArtworks());

        // double-click to view details (optional)
        artworkTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // double-click
                    int r = artworkTable.getSelectedRow();
                    if (r != -1) {
                        int modelRow = artworkTable.convertRowIndexToModel(r);
                        int id = (int) tableModel.getValueAt(modelRow, 0);
                        String title = (String) tableModel.getValueAt(modelRow, 1);
                        String artist = (String) tableModel.getValueAt(modelRow, 2);
                        String desc = (String) tableModel.getValueAt(modelRow, 3);
                        int likes = (int) tableModel.getValueAt(modelRow, 4);
                        int dislikes = (int) tableModel.getValueAt(modelRow, 5);
                        JOptionPane.showMessageDialog(VisitorFrame.this,
                            title + "\nby " + artist + "\n\n" + desc + "\n\nLikes: " + likes + "  Dislikes: " + dislikes,
                            "Artwork Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        loadArtworks();
        setVisible(true);
    }

    private void handleLike() {
        int selectedRow = artworkTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an artwork to like.");
            return;
        }
        int modelRow = artworkTable.convertRowIndexToModel(selectedRow);
        int id = (int) tableModel.getValueAt(modelRow, 0);
        ArtWorksDAO.likeArtwork(id);
        loadArtworks();
    }

    private void handleDislike() {
        int selectedRow = artworkTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an artwork to dislike.");
            return;
        }
        int modelRow = artworkTable.convertRowIndexToModel(selectedRow);
        int id = (int) tableModel.getValueAt(modelRow, 0);
        ArtWorksDAO.dislikeArtwork(id);
        loadArtworks();
    }

    private void loadArtworks() {
        tableModel.setRowCount(0);
        List<ArtWorks> list = ArtWorksDAO.getAllArtWorks();
        for (ArtWorks a : list) {
            tableModel.addRow(new Object[]{
                a.getId(),
                a.getTitle(),
                a.getArtist(),
                a.getDescription(),
                a.getLikes(),
                a.getDislikes()
            });
        }
        tableModel.fireTableDataChanged();
    }
}
