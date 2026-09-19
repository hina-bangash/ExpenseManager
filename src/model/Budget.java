package model;

import java.util.HashMap;
import java.util.Map;

public class Budget {
    private double monthlyLimit;
    private Map<String, Double> categoryLimits;


    public Budget(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
        this.categoryLimits = new HashMap<>();
         

        // every user have some default categories and can add more of their choice
        categoryLimits.put("Food", 0.0);
        categoryLimits.put("Transport", 0.0);
        categoryLimits.put("Shopping", 0.0);
        categoryLimits.put("Bills", 0.0);
    }
        
    

    public double getMonthlyLimit() { return monthlyLimit; }
    public void setMonthlyLimit(double monthlyLimit) { this.monthlyLimit = monthlyLimit; }

    public void setCategoryLimit(String category, double limit) {
        categoryLimits.put(category, limit);
    }


    // return 0.0 instead of crashing if a category does not exist
    public double getCategoryLimit(String category) {
        return categoryLimits.getOrDefault(category, 0.0);
    }

    // returns all category limits used in file handler
    public Map<String, Double> getAllCategoryLimits() { return categoryLimits; }
}
