package org.example.gui.laundromats;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class LaundromatListPanel extends JPanel {

    private final JPanel listContainer;

    public LaundromatListPanel(Consumer<LaundromatData> onSelect) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(320, 0));
        setBackground(UIManager.getColor("Panel.background"));

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);
        // Add padding around items so they don't stick to the scrollbar/edges
        listContainer.setBorder(new EmptyBorder(0, 0, 0, 12));

        JScrollPane scrollPane = new JScrollPane(listContainer,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // Demo/sample items (5 total)
        List<LaundromatData> demo = Arrays.asList(
                new LaundromatData("WashEat Laundry", "209 Daang Maharlika Hwy, Poblacion District, Davao City",
                        "1.2 km", "2–4 days", "Pictures/laundromat1.png"),
                new LaundromatData("La Vahh Laundromat", "95 Emilio Jacinto St., Poblacion District, Davao City",
                        "850 m", "1–2 days", "laundromat2.png"),
                new LaundromatData("Allklean Laundromat", "95 Emilio Jacinto St., Poblacion District, Davao City",
                        "2.5 km", "3–5 days", "Pictures/laundromat3.png"),
                new LaundromatData("Bubble Blink's Laundry Shop", "87 Artiaga St., Poblacion District, Davao City",
                        "3.8 km", "2–3 days", "laundromat1.png"),
                new LaundromatData("D'Laundry Station", "Bonifacio St., Poblacion District, Davao City",
                        "5.0 km", "3–6 days", "Pictures/laundromat2.png")
        );

        for (LaundromatData data : demo) {
            LaundromatCard card = new LaundromatCard(data, onSelect);
            listContainer.add(card);
            listContainer.add(Box.createVerticalStrut(12));
        }
    }
}
