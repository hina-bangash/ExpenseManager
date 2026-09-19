package gui;

import model.User;
import controller.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AnalyticsScreen extends JFrame {
    private User user;
    private ExpenseOperations manager;

    public AnalyticsScreen(User user) {
        this.user = user;
        this.manager = new ExpenseManager(user);

        setTitle("Analytics - " + user.getUsername());
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(250, 250, 250)); // light gray background

        // Bar Chart Panel
        ChartPanel categoryChartPanel = createCategoryChart();
        add(categoryChartPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private ChartPanel createCategoryChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Fill dataset
        for (Map.Entry<String, Double> entry : user.getBudget().getAllCategoryLimits().entrySet()) {
            String category = entry.getKey();
            double spent = manager.getCategoryTotal(category);
            dataset.addValue(spent, "Spent", category);
        }

        // Create chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Category Spending",
                "Category",
                "Amount Spent",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        // Customize chart
        barChart.setBackgroundPaint(new Color(250, 250, 250)); // match frame background
        barChart.getTitle().setPaint(Color.DARK_GRAY);          // dark title
        barChart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 16));

        // Custom renderer for bars
        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(243, 244, 246)); // light gray behind bars
        plot.setRangeGridlinePaint(Color.GRAY);

        plot.setRenderer(new BarRenderer() {
            @Override
            public Paint getItemPaint(int row, int column) {
                String cat = (String) dataset.getColumnKey(column);
                double spent = manager.getCategoryTotal(cat);
                double limit = user.getBudget().getCategoryLimit(cat);
                return spent > limit ? new Color(0x7a0000) : new Color(0x007a3f); // red/green red for over limit green for in limit
            }
        });

        return new ChartPanel(barChart);
    }
}
