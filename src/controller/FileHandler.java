package controller;

import model.Expense;
import model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // ----------- USERS -----------
    // saves user data in users.txt in form of string seperated by commas
    public static void saveUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
            bw.write(user.getUsername() + "," + user.getPassword());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // converts the string from user.txt back to user object and store it to users array list
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("No users file found. Starting fresh.");
        }
        return users;
    }

    // ----------- EXPENSES -----------
    public static void saveExpenses(User user) {
        String filename = user.getUsername() + "_expenses.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Expense e : user.getExpenses()) {
                bw.write(e.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadExpenses(User user) {
        String filename = user.getUsername() + "_expenses.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            user.getExpenses().clear();
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    String category = parts[2];
                    LocalDate date = LocalDate.parse(parts[3]);
    
                    user.addExpense(new Expense(name, amount, category, date));
                }
            }
        } catch (IOException e) {
            System.out.println("No expenses found for user " + user.getUsername());
        }
    }
    

    // ----------- BUDGET -----------
    public static void saveBudget(User user) {
        String filename = user.getUsername() + "_budget.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(String.valueOf(user.getBudget().getMonthlyLimit()));
            bw.newLine();
            for (String cat : user.getBudget().getAllCategoryLimits().keySet()) {
                bw.write(cat + "," + user.getBudget().getCategoryLimit(cat));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadBudget(User user) {
        String filename = user.getUsername() + "_budget.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            if (line != null) {
                user.getBudget().setMonthlyLimit(Double.parseDouble(line));
            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String cat = parts[0];
                    double limit = Double.parseDouble(parts[1]);
                    user.getBudget().setCategoryLimit(cat, limit);
                }
            }
        } catch (IOException e) {
            System.out.println("No budget found for user " + user.getUsername());
        }
    }
}
