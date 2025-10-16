package org.example.gui.laundromats;

import org.example.gui.utils.creators.iconCreator;
import org.example.gui.utils.creators.roundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

/**
 * Right-side details view of a laundromat.
 */
public class LaundromatDetailsPanel extends JPanel {

    private JPanel headerPanel;
    private JTextArea descriptionArea;
    private JPanel reviewsPanel;
    private JPanel servicesPanel;

    public LaundromatDetailsPanel() {
        setLayout(new BorderLayout(12, 12));
        setBackground(UIManager.getColor("Panel.background"));

        // Padding around the entire details panel
        setBorder(new EmptyBorder(20, 20, 20, 20));

        headerPanel = new roundedPanel(20);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(UIManager.getColor("Sidebarbtn.background"));
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // inner padding
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        centerPanel.setOpaque(false);

        // Left: description
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setBorder(BorderFactory.createTitledBorder("What We Offer:"));
        centerPanel.add(descScroll);

        // Right: reviews
        reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        reviewsPanel.setOpaque(false);
        JScrollPane revScroll = new JScrollPane(reviewsPanel);
        revScroll.setBorder(BorderFactory.createTitledBorder("Reviews:"));
        centerPanel.add(revScroll);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(12, 0));
        bottomPanel.setOpaque(false);

        // Services
        servicesPanel = new JPanel();
        servicesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 12));
        servicesPanel.setOpaque(false);
        bottomPanel.add(servicesPanel, BorderLayout.CENTER);

        // Request pickup button
        JButton pickupBtn = new JButton("Request Pickup");
        pickupBtn.setPreferredSize(new Dimension(160, 40));
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 0)); // spacing above button row
        bottomPanel.add(pickupBtn, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setLaundromat(LaundromatData data) {
        headerPanel.removeAll();

        JLabel name = new JLabel(iconCreator.getIcon("Icons/laundromatLogo.svg", 24, 24));
        name.setText(" " + data.name);
        name.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 16f));

        JLabel addr = new JLabel(iconCreator.getIcon("Icons/address.svg", 16, 16));
        addr.setText(" " + data.address);

        JLabel dist = new JLabel(iconCreator.getIcon("Icons/distancefromUser.svg", 16, 16));
        dist.setText(" " + data.distance);

        JLabel period = new JLabel(iconCreator.getIcon("Icons/deliveryperiod.svg", 16, 16));
        period.setText(" " + data.deliveryPeriod);

        JLabel stars = new JLabel("★".repeat(data.stars));
        stars.setForeground(Color.ORANGE);

        headerPanel.add(name);
        headerPanel.add(addr);
        headerPanel.add(dist);
        headerPanel.add(period);
        headerPanel.add(stars);

        descriptionArea.setText(data.description);

        // Mock reviews
        reviewsPanel.removeAll();
        Arrays.asList(
            new ReviewCard("John Doe", "Great service! Will definitely come back."),
            new ReviewCard("Jane Doe", "Fast delivery and very clean laundry."),
            new ReviewCard("John Q. Public", "Reliable and friendly staff.")
        ).forEach(reviewsPanel::add);

        // Mock services
        servicesPanel.removeAll();
        servicesPanel.add(new ServiceCard("Wash and Fold", "Icons/washandFold.svg"));
        servicesPanel.add(new ServiceCard("Dry Clean", "Icons/dryClean.svg"));
        servicesPanel.add(new ServiceCard("Iron", "Icons/iron.svg"));

        revalidate();
        repaint();
    }
}
