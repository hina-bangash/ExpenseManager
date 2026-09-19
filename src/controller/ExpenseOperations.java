package controller;

import model.Expense;

public interface ExpenseOperations {
    void addExpense(Expense expense);
    double getTotalExpenses();
    double getCategoryTotal(String category);
}
