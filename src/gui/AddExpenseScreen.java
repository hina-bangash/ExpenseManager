package gui;

import model.User;
import model.Expense;
import controller.ExpenseManager;
import controller.ExpenseOperations;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddExpenseScreen extends JFrame {
    private User user;
    private Dashboard dashboard;
    private ExpenseOperations manager;

    private JTextField nameField;
    private JTextField amountField;
    private JComboBox<String> categoryBox;
    private JButton addButton;

    public AddExpenseScreen(User user, Dashboard dashboard) {
        this.user = user;
        this.dashboard = dashboard;
        this.manager = new ExpenseManager(user);
    
        setTitle("Add Expense");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));
    
        // Name
        add(new JLabel("Expense Name:"));
        nameField = new JTextField();
        add(nameField);
    
        // Amount
        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);
    
        // Category
        add(new JLabel("Category:"));
        categoryBox = new JComboBox<>();
        for (String cat : user.getBudget().getAllCategoryLimits().keySet()) {
            categoryBox.addItem(cat);
        }
        add(categoryBox);
    
        // Empty labels for spacing
        add(new JLabel());
        add(new JLabel());
    
        // Add button
        addButton = new JButton("Add Expense");
        addButton.setBackground(new Color(0x00273d));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(addButton);

    
        addButton.addActionListener(e -> addExpense());
    
        // --- Make Enter key trigger Add button ---
        getRootPane().setDefaultButton(addButton);    
        setVisible(true);
    }
    

    private void addExpense() {
        String name = nameField.getText().trim();
        String amountText = amountField.getText().trim();
        String category = (String) categoryBox.getSelectedItem();

        if (name.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount!");
            return;
        }

        Expense expense = new Expense(name, amount, category, LocalDate.now());
        manager.addExpense(expense);

        // Save to file
        controller.FileHandler.saveExpenses(user);

        JOptionPane.showMessageDialog(this, "Expense added!");

        // Refresh dashboard
        dashboard.refresh();

        dispose();
    }
}
