package artgallery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginFrame() {
        setTitle("Art Gallery Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        // === UI Components ===
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Create Account");

        add(loginButton);
        add(registerButton);

        // === Button Actions ===
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        registerButton.addActionListener(e -> new RegisterFrame());

        setVisible(true);
    }

    // === Handle login ===
    private void loginUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill in all fields!");
            return;
        }

        try {
            // Check credentials in database
            String role = UserDAO.getUserRole(username, password);

            if (role == null) {
                JOptionPane.showMessageDialog(this, "❌ Invalid username or password!");
                return;
            }

            JOptionPane.showMessageDialog(this, "✅ Login successful as " + role + "!");
            dispose(); // close login window

            if (role.equalsIgnoreCase("admin")) {
                new AdminFrame();
            } else {
                new VisitorFrame();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }

    // === Start the app ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
