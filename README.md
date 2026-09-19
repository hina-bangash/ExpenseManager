# Expense Manager

A Java Swing-based desktop application designed to help users manage their personal expenses, set budgets, and analyze their spending.

## Features

- User registration and login
- Add and manage expenses
- Categorize expenses
- Monthly budget planning
- Category-based budget limits
- Expense history
- Spending analytics
- Graphical expense visualization using JFreeChart
- File-based data persistence

## Technologies Used

- Java
- Java Swing
- JFreeChart
- Object-Oriented Programming (OOP)
- MVC Architecture
- File Handling
- Java Collections

## Project Structure

```text
ExpenseManager/
│
├── src/
│   ├── controller/
│   │   ├── ExpenseManager.java
│   │   ├── ExpenseOperations.java
│   │   └── FileHandler.java
│   │
│   ├── gui/
│   │   ├── AddExpenseScreen.java
│   │   ├── AnalyticsScreen.java
│   │   ├── BudgetPlannerScreen.java
│   │   ├── Dashboard.java
│   │   ├── LoginScreen.java
│   │   └── ViewExpenseScreen.java
│   │
│   └── model/
│       ├── Budget.java
│       ├── Expense.java
│       └── User.java
│
└── lib/
    └── jfreechart-1.5.3.jar
```

## Architecture

The project follows the Model-View-Controller (MVC) architecture.

- **Model:** Handles users, expenses, and budgets.
- **View/GUI:** Provides the graphical user interface using Java Swing.
- **Controller:** Handles application logic and file operations.

## Data Storage

The application uses text files for local data persistence.

User-specific expense and budget files are generated at runtime.

## Learning Outcomes

Through this project, I practiced:

- Object-oriented programming in Java
- Designing GUI applications using Java Swing
- Applying MVC architecture
- File handling and data persistence
- Working with Java collections
- Creating data visualizations using JFreeChart
- Structuring a multi-class Java application

## Disclaimer

This project was developed as a student project for educational purposes. It is not intended to provide financial advice or serve as a production financial management system.
