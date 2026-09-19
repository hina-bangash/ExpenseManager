package gui;

import model.User;
import model.Expense;
import controller.FileHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ViewExpenseScreen extends JFrame {
    private User user;
    private Dashboard dashboard;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton deleteButton;

    public ViewExpenseScreen(User user, Dashboard dashboard) {
        this.user = user;
        this.dashboard = dashboard;

        setTitle("View Expenses");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Name", "Amount", "Category", "Date"}, 0);
        table = new JTable(tableModel);
        loadExpenses();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Delete Button
        deleteButton = new JButton("Delete Selected Expense");
        deleteButton.setBackground(new Color(0x00273d));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        deleteButton.addActionListener(e -> deleteSelectedExpense());
        add(deleteButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadExpenses() {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Expense e : user.getExpenses()) {
            tableModel.addRow(new Object[]{e.getName(), e.getAmount(), e.getCategory(), e.getDate().format(formatter)});
        }
    }

    private void deleteSelectedExpense() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Expense e = user.getExpenses().get(selectedRow);
            user.getExpenses().remove(e);
            FileHandler.saveExpenses(user);
            loadExpenses();
            dashboard.refresh();
            JOptionPane.showMessageDialog(this, "Expense deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an expense to delete.");
        }
    }
}
