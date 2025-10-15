package org.example.gui.utils.dashboard.recommendations;

import org.example.gui.utils.creators.roundedBorder;
import org.example.gui.utils.creators.roundedPanel;
import org.example.gui.utils.fonts.fontManager;

import javax.swing.*;
import java.awt.*;

public class laundromatsNearUser extends roundedPanel {
    private JLabel laundromats;
    private JTextArea list;

    public laundromatsNearUser() {
        setLayout(new BorderLayout());
        setBackground(UIManager.getColor("Menu.background"));
        applyRoundedBorder(16, UIManager.getColor("listBorder"), 2);

        laundromats = new JLabel("Laundromats Near You");
        laundromats.setHorizontalAlignment(SwingConstants.CENTER);
        fontManager.applyHeading(laundromats, 4);

// Add padding: top, left, bottom, right
        laundromats.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        add(laundromats, BorderLayout.NORTH);

        list = new JTextArea("\n" +
                "• Real Laundromat \n\n" +
                "• Actual Laundromat \n\n" +
                "• Existing Laundromat \n\n" +
                "• Lorem Laundromat \n\n" +
                "• Ipsum Laundromat \n\n");

        list.setEditable(false);
        list.setLineWrap(true);
        list.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    private void applyRoundedBorder(int arc, Color color, int thickness) {
        setBorder(new roundedBorder(arc, color, thickness));
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBackground(UIManager.getColor("Menu.background"));
        applyRoundedBorder(16, UIManager.getColor("listBorder"), 2);
    }
}
