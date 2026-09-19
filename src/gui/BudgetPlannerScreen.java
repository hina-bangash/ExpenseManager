package gui;

import model.User;
import controller.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BudgetPlannerScreen extends JFrame {
    private User user;
    private Dashboard dashboard;

    private JPanel categoryPanel;
    private JButton saveButton;
    private JLabel monthlyBudgetLabel;

    public BudgetPlannerScreen(User user, Dashboard dashboard) {
        this.user = user;
        this.dashboard = dashboard;

        setTitle("Budget Planner");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Theme colors and fonts ---
        Color bgColor = new Color(245, 245, 245);
        Color btnSaveColor = new Color(0x00273d);
        Color btnAddColor = new Color(0x00273d);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);

        getContentPane().setBackground(bgColor);

        // --- Top panel for monthly budget ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(bgColor);
        monthlyBudgetLabel = new JLabel("Monthly Budget: 0");
        monthlyBudgetLabel.setFont(labelFont);
        monthlyBudgetLabel.setForeground(new Color(50, 50, 50));
        topPanel.add(monthlyBudgetLabel);
        add(topPanel, BorderLayout.NORTH);

        // --- Center panel for category limits ---
        categoryPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        categoryPanel.setBackground(bgColor);

        refreshCategoryFields(labelFont, fieldFont, bgColor);

        JScrollPane scroll = new JScrollPane(categoryPanel);
        scroll.getViewport().setBackground(bgColor);
        add(scroll, BorderLayout.CENTER);

        // --- Bottom panel for buttons ---
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(bgColor);

        saveButton = new JButton("Save Budget");
        saveButton.setFont(fieldFont);
        saveButton.setBackground(btnSaveColor);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> saveBudget());
        bottomPanel.add(saveButton);

        // Make Enter key trigger Save button
        getRootPane().setDefaultButton(saveButton);


        JTextField newCatField = new JTextField(10);
        newCatField.setFont(fieldFont);
        JButton addCatButton = new JButton("Add Category");
        addCatButton.setFont(fieldFont);
        addCatButton.setBackground(btnAddColor);
        addCatButton.setForeground(Color.WHITE);

        bottomPanel.add(new JLabel("New Category:"));
        bottomPanel.add(newCatField);
        bottomPanel.add(addCatButton);

        // Make Enter key in newCatField trigger Add Category
        newCatField.addActionListener(e -> addCatButton.doClick());


        add(bottomPanel, BorderLayout.SOUTH);

        // --- Add new category action ---
        addCatButton.addActionListener(e -> {
            String newCat = newCatField.getText().trim();
            if (newCat.isEmpty()) return;
            if (user.getBudget().getAllCategoryLimits().containsKey(newCat)) {
                JOptionPane.showMessageDialog(this, "Category already exists!");
                return;
            }
            user.getBudget().setCategoryLimit(newCat, 0);
            FileHandler.saveBudget(user); // saves new category automatically dont have to manually click save budget everytine
            refreshCategoryFields(labelFont, fieldFont, bgColor);
            newCatField.setText("");
        });

        // Initial monthly budget update
        updateMonthlyBudgetLabel();

        setVisible(true);
    }

    // Refresh category fields
    private void refreshCategoryFields(Font labelFont, Font fieldFont, Color bgColor) {
        categoryPanel.removeAll();

        for (Map.Entry<String, Double> entry : user.getBudget().getAllCategoryLimits().entrySet()) {
            JLabel lbl = new JLabel(entry.getKey() + ":");
            lbl.setFont(labelFont);
            lbl.setForeground(new Color(50, 50, 50));
            categoryPanel.add(lbl);

            JTextField tf = new JTextField(String.valueOf(entry.getValue()));
            tf.setName(entry.getKey());
            tf.setFont(fieldFont);
            tf.setBackground(Color.WHITE);
            categoryPanel.add(tf);
        }

        categoryPanel.revalidate();
        categoryPanel.repaint();

        updateMonthlyBudgetLabel();
    }

    // Save budget and update monthly
    private void saveBudget() {
        try {
            double totalMonthly = 0;

            for (Component comp : categoryPanel.getComponents()) {
                if (comp instanceof JTextField textField) {
                    String cat = textField.getName();
                    double limit = Double.parseDouble(textField.getText().trim());
                    if (limit < 0) limit = 0;
                    user.getBudget().setCategoryLimit(cat, limit);
                    totalMonthly += limit;
                }
            }

            user.getBudget().setMonthlyLimit(totalMonthly);
            FileHandler.saveBudget(user);
            dashboard.refresh();
            updateMonthlyBudgetLabel();

            JOptionPane.showMessageDialog(this, "Budget saved successfully! Monthly Budget: " + totalMonthly);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid non-negative numbers!");
        }
    }

    private void updateMonthlyBudgetLabel() {
        double total = user.getBudget().getAllCategoryLimits().values().stream().mapToDouble(Double::doubleValue).sum();
        monthlyBudgetLabel.setText("Monthly Budget: " + total);
    }
}
