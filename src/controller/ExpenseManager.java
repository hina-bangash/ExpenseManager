package controller;

import model.Expense;
import model.User;

public class ExpenseManager implements ExpenseOperations {
    private User user;

    public ExpenseManager(User user) {
        this.user = user;
    }

    @Override
    public void addExpense(Expense expense) {
        user.getExpenses().add(expense);
    }

    @Override
    public double getTotalExpenses() {
        return user.getExpenses().stream()
                   .mapToDouble(Expense::getAmount)
                   .sum();
    }

    @Override
    public double getCategoryTotal(String category) {
        return user.getExpenses().stream()
                   .filter(e -> e.getCategory().equals(category))
                   .mapToDouble(Expense::getAmount)
                   .sum();
    }
}
