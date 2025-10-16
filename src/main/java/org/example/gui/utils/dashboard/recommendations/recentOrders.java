package org.example.gui.utils.dashboard.recommendations;

import org.example.gui.utils.creators.roundedPanel;
import org.example.gui.utils.creators.roundedBorder;
import org.example.gui.utils.fonts.fontManager;

import javax.swing.*;
import java.awt.*;

public class recentOrders extends roundedPanel {
    private roundedPanel headingPanel;
    private JTable table;

    public recentOrders() {
        super(16); // Main panel rounded corners
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackgroundColorKey("background");
        setBorder(new roundedBorder(16, UIManager.getColor("listBorder"), 2));
        setOpaque(false);

        // === Heading Panel ===
        headingPanel = new roundedPanel(16);
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBackgroundColorKey("Menu.background");
        headingPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel headingLabel = new JLabel("Recent Orders");
        headingLabel.setHorizontalAlignment(SwingConstants.LEFT);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 6, 10));
        fontManager.applyHeading(headingLabel, 4);

        headingPanel.add(headingLabel, BorderLayout.CENTER);
        add(headingPanel);
        add(Box.createVerticalStrut(10));

        // === Table Data ===
        String[] columns = {"Order ID", "Laundromat", "Status"};
        Object[][] data = {
                {"001", "Real Laundromat", "Done"},
                {"002", "Actual Laundromat", "Done"},
                {"003", "Existing Laundromat", "Done"},
                {"004", "Lorem Laundromat", "In Progress"},
                {"005", "Ipsum Laundromat", "In Progress"},
                {"006", "A Laundromat", "In Progress"},
                {"007", "B Laundromat", "In Progress"}
        };

        table = new JTable(data, columns);
        table.setFillsViewportHeight(true);
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setOpaque(true);
        table.setBackground(UIManager.getColor("Panel.background"));
        table.setForeground(UIManager.getColor("foreground"));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(UIManager.getColor("Panel.background"));
        tableWrapper.add(scrollPane, BorderLayout.CENTER);

        add(tableWrapper);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBackground(UIManager.getColor("Menu.background"));
        setBorder(new roundedBorder(16, UIManager.getColor("listBorder"), 2));

        if (headingPanel != null) headingPanel.setBackgroundColorKey("Menu.background");

    }

    public JTable getTable() {
        return table;
    }
}
