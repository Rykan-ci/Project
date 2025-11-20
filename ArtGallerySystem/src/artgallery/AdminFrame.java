package artgallery;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {
    private JTextField titleField, artistField;
    private JTextArea descArea;
    private JButton addButton, deleteButton, refreshButton;
    private JTable artworkTable;
    private DefaultTableModel tableModel;

    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Top Form ===
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Artwork"));

        formPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Artist:"));
        artistField = new JTextField();
        formPanel.add(artistField);

        formPanel.add(new JLabel("Description:"));
        descArea = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(descArea));

        addButton = new JButton("Add Artwork");
        formPanel.add(addButton);

        deleteButton = new JButton("Delete Selected");
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.NORTH);

        // === Table for artworks ===
        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Artist", "Description"}, 0);
        artworkTable = new JTable(tableModel);
        add(new JScrollPane(artworkTable), BorderLayout.CENTER);

        // === Refresh Button ===
        refreshButton = new JButton("Refresh");
        add(refreshButton, BorderLayout.SOUTH);

        // === Button actions ===
        addButton.addActionListener(e -> addArtwork());
        deleteButton.addActionListener(e -> deleteSelectedArtwork());
        refreshButton.addActionListener(e -> loadArtWorks());
    
        // === Logout Button ===
        JButton logoutButton = new JButton("Logout");
        add(logoutButton, BorderLayout.WEST);

        // Logout Action
        logoutButton.addActionListener(e -> {
            dispose(); // Close this window
            new LoginFrame(); // Go back to login
        });


        loadArtWorks(); // initial load
        setVisible(true);
    }

    // === Add new artwork to DB ===
    private void addArtwork() {
        String title = titleField.getText();
        String artist = artistField.getText();
        String desc = descArea.getText();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title is required.");
            return;
        }

        ArtWorks art = new ArtWorks(0, title, artist, desc, 0, 0);
        ArtWorksDAO.insertArtwork(art);
        loadArtWorks();

        titleField.setText("");
        artistField.setText("");
        descArea.setText("");
    }

    // === Delete selected artwork from table and DB ===
    private void deleteSelectedArtwork() {
        int selectedRow = artworkTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0); // get ID
        ArtWorksDAO.deleteArtwork(id);
        loadArtWorks(); // refresh after deletion
    }

    // === Load artworks from database ===
    private void loadArtWorks() {
        tableModel.setRowCount(0); // clear current rows
        List<ArtWorks> artworks = ArtWorksDAO.getAllArtWorks();

        for (ArtWorks art : artworks) {
            tableModel.addRow(new Object[]{
                art.getId(),
                art.getTitle(),
                art.getArtist(),
                art.getDescription()
            });
        }
    }
}
