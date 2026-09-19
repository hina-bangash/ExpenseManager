package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Expense> expenses;
    private Budget budget;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
        this.budget = new Budget(0); // default monthly limit = 0
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public List<Expense> getExpenses() { return expenses; }
    public void addExpense(Expense e) { expenses.add(e); }
    public void removeExpense(Expense e) { expenses.remove(e); }

    public Budget getBudget() { return budget; }
    public void setBudget(Budget budget) { this.budget = budget; }
}
