package gui;

import model.User;
import controller.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// extends/inherits JFrame so basically loginScreen IS A screen

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;
    private List<User> users;

    public LoginScreen() {
        setTitle("Expense Manager - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Theme ---
        Color bgColor = new Color(245, 245, 245); // light gray
        Color btnColor = new Color(0x00273d); // blue
        Color btnTextColor = Color.WHITE;
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font btnFont = new Font("SansSerif", Font.PLAIN, 13);
        getContentPane().setBackground(bgColor);  // the content of the frame
        setLayout(new GridLayout(4, 2, 10, 10));
        // Components are added in order, row by row, left to right.

        // Load existing users
        users = FileHandler.loadUsers(); // from user.txt into users list

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        add(userLabel);
        usernameField = new JTextField();
        add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        add(passLabel);
        passwordField = new JPasswordField();
        add(passwordField);

        // Buttons
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");
        JButton[] buttons = {loginButton, signupButton};
        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setBackground(btnColor);
            btn.setForeground(btnTextColor);
        }
        add(loginButton);
        add(signupButton);

        // Action listeners
        loginButton.addActionListener(e -> login());
        signupButton.addActionListener(e -> signup());

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                // Load existing user data
                FileHandler.loadExpenses(u);
                FileHandler.loadBudget(u);

                // Save updated expenses
                FileHandler.saveExpenses(u);

                JOptionPane.showMessageDialog(this, "Login Successful!");
                new Dashboard(u);  // dashboard screen shows for this specific user
                this.dispose();  // closes and destroys the current JFrame saves memory
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Invalid username or password.");
    }

    private void signup() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists.");
                return;
            }
        }

        User newUser = new User(username, password);
        users.add(newUser);
        FileHandler.saveUser(newUser);

        JOptionPane.showMessageDialog(this, "Sign Up Successful! You can now log in.");
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
