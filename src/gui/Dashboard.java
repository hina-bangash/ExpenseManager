package gui;

import model.User;
import controller.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Dashboard extends JFrame {
    private User user;
    private ExpenseOperations manager;

    private JLabel totalLabel;
    private JLabel monthlyBudgetLabel;
    private JLabel remainingLabel;
    private JPanel categoryPanel;
    private JButton addExpenseButton;
    private JButton analyticsButton;

    public Dashboard(User user) {
        this.user = user;
        this.manager = new ExpenseManager(user);

        setTitle("Dashboard - " + user.getUsername());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        //north, south, center, east , west
        // north(up)  topPanel 
        // south(bottom)  bottomPanel
        //center   categoryPanel

        // --- Theme Colors ---
        Color bgColor = new Color(245, 245, 245); // light gray background
        Color btnColor = new Color(0x00273d); // dark blue buttons
        Color btnTextColor = Color.WHITE;
        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font btnFont = new Font("SansSerif", Font.BOLD, 13);
        getContentPane().setBackground(bgColor);

        // --- Top Panel for totals ---
        JPanel topPanel = new JPanel(new GridLayout(3, 1)); // cells of equla size
        // 3 rows 1 column so everything is placed vertically 
        topPanel.setBackground(bgColor);

        totalLabel = new JLabel();
        totalLabel.setFont(labelFont);
        totalLabel.setForeground(new Color(50, 50, 50));

        monthlyBudgetLabel = new JLabel();
        monthlyBudgetLabel.setFont(labelFont);
        monthlyBudgetLabel.setForeground(new Color(50, 50, 50));

        remainingLabel = new JLabel();
        remainingLabel.setFont(labelFont);

        topPanel.add(totalLabel);
        topPanel.add(monthlyBudgetLabel);
        topPanel.add(remainingLabel);
        add(topPanel, BorderLayout.NORTH);  // adds the panel to the top of the frame

        // --- Center Panel for categories ---
        categoryPanel = new JPanel(new GridLayout(0, 1));
        // 0 rows b ecause rows increase dynamically as you add more components/categories
        // thats how gridLayout works
        categoryPanel.setBackground(bgColor);
        add(new JScrollPane(categoryPanel), BorderLayout.CENTER);

        // --- Bottom Panel for buttons ---
        JPanel bottomPanel = new JPanel();  // no layout specified 
        // flowLayout by default
        bottomPanel.setBackground(bgColor);

        addExpenseButton = new JButton("Add Expense");
        analyticsButton = new JButton("View Analytics");
        JButton budgetButton = new JButton("Set Budget");
        JButton viewExpensesButton = new JButton("View Expenses");

        JButton[] buttons = {addExpenseButton, analyticsButton, budgetButton, viewExpensesButton};
        for (JButton btn : buttons) {
            btn.setFont(btnFont);
            btn.setBackground(btnColor);
            btn.setForeground(btnTextColor);
            bottomPanel.add(btn);
        }

        add(bottomPanel, BorderLayout.SOUTH);  // adds the buttons panel to the bottom of the screen

        // --- Action listeners ---
        addExpenseButton.addActionListener(e -> new AddExpenseScreen(user, this));
        analyticsButton.addActionListener(e -> new AnalyticsScreen(user));
        budgetButton.addActionListener(e -> new BudgetPlannerScreen(user, this));
        viewExpensesButton.addActionListener(e -> new ViewExpenseScreen(user, this));

        refresh();
        setVisible(true);
    }

    // --- Refresh dashboard ---
    public void refresh() {

        double totalExpenses = manager.getTotalExpenses();   // uses ExpenseManager
        double monthlyBudget = user.getBudget().getMonthlyLimit();  // uses ExpenseManager
        double remaining = monthlyBudget - totalExpenses;

        totalLabel.setText("Total Expenses: " + totalExpenses);
        monthlyBudgetLabel.setText("Monthly Budget: " + monthlyBudget);
        remainingLabel.setText("Remaining: " + remaining);
        remainingLabel.setForeground(remaining < 0 ? new Color(0x7a0000) : new Color(0x007a3f));  // red or green

        // Update categories and check for overspending
        categoryPanel.removeAll();
        for (Map.Entry<String, Double> entry : user.getBudget().getAllCategoryLimits().entrySet()) {
            String cat = entry.getKey();
            double limit = entry.getValue();
            double spent = manager.getCategoryTotal(cat);

            JLabel label = new JLabel(cat + ": " + spent + "/" + limit);
            label.setFont(new Font("SansSerif", Font.PLAIN, 13));
            label.setForeground(spent > limit ? new Color(0x7a0000) : new Color(0x007a3f));  // red or green
            //ternary operator shortcut for if else
            categoryPanel.add(label);
        }

        categoryPanel.revalidate();
        categoryPanel.repaint();
    }
}
