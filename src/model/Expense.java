package model;

import java.time.LocalDate;

public class Expense {
    private String name;
    private double amount;
    private String category;
    private LocalDate date;

    public Expense(String name, double amount, String category, LocalDate date) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getters and setters
    public String getName() { return name; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }

    // saving data to file
    @Override
    public String toString() {
        return name + "," + amount + "," + category + "," + date;
    }
}
