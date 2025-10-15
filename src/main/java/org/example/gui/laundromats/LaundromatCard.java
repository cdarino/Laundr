package org.example.gui.laundromats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import org.example.gui.utils.creators.iconCreator;
import org.example.gui.utils.creators.roundedPanel;

public class LaundromatCard extends roundedPanel {

    public LaundromatCard(LaundromatData data, Consumer<LaundromatData> onSelect) {
        super(20); // restored and slightly increased roundness
        setLayout(new BorderLayout());
        setBackground(UIManager.getColor("Sidebarbtn.background"));

        // Card width ~70% of laundromat list
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));
        setPreferredSize(new Dimension(0, 320));

        // === Top: Cover image (fills entire panel) ===
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setPreferredSize(new Dimension(0, 260));

        // Load image resource (using iconCreator for raster + svg)
        Icon rawIcon = iconCreator.getIcon(data.imagePath, 360, 260);

        // We'll keep original Image if available for rescaling on resize
        final Image[] baseImage = new Image[1];
        if (rawIcon instanceof ImageIcon) {
            baseImage[0] = ((ImageIcon) rawIcon).getImage();
        } else if (rawIcon instanceof com.formdev.flatlaf.extras.FlatSVGIcon) {
            // create a snapshot image from SVG at requested size as fallback
            BufferedImage img = new BufferedImage(360, 260, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            rawIcon.paintIcon(null, g, 0, 0);
            g.dispose();
            baseImage[0] = img;
        }

// Image label that will be updated on resize
JLabel imgLabel = new JLabel();
imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
imgLabel.setVerticalAlignment(SwingConstants.CENTER);
imagePanel.add(imgLabel, BorderLayout.CENTER);

if (baseImage[0] != null) {
    Image scaled = baseImage[0].getScaledInstance(-1, 260, Image.SCALE_SMOOTH);
    imgLabel.setIcon(new ImageIcon(scaled));
}

imagePanel.addComponentListener(new java.awt.event.ComponentAdapter() {
    @Override
    public void componentResized(java.awt.event.ComponentEvent e) {
        if (baseImage[0] == null) return;

        int targetW = imagePanel.getWidth();
        int targetH = imagePanel.getHeight();
        if (targetW <= 0 || targetH <= 0) return;

        // --- Compute scale to completely fill the panel (cropping overflow) ---
        double panelRatio = (double) targetW / targetH;
        double imageRatio = (double) baseImage[0].getWidth(null) / baseImage[0].getHeight(null);

        int scaledW, scaledH;
        if (imageRatio > panelRatio) {
            // image is wider than panel → fit by height, crop sides
            scaledH = targetH;
            scaledW = (int) Math.round(scaledH * imageRatio);
        } else {
            // image is taller/narrower → fit by width, crop top/bottom
            scaledW = targetW;
            scaledH = (int) Math.round(scaledW / imageRatio);
        }

        Image scaled = baseImage[0].getScaledInstance(scaledW, scaledH, Image.SCALE_SMOOTH);

        // --- Create cropped snapshot that exactly matches panel size ---
        BufferedImage cropped = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);
        Graphics g = cropped.getGraphics();
        int x = (scaledW - targetW) / 2;
        int y = (scaledH - targetH) / 2;
        g.drawImage(scaled, -x, -y, null);
        g.dispose();

        SwingUtilities.invokeLater(() -> imgLabel.setIcon(new ImageIcon(cropped)));
    }
});


imagePanel.setBorder(new MatteBorder(0, 0, 1, 0,
        safeColor("Label.foreground", UIManager.getColor("Menu.borderColor"))));
add(imagePanel, BorderLayout.NORTH);

// === Bottom section (compact info) ===
JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
bottomPanel.setOpaque(false);
bottomPanel.setPreferredSize(new Dimension(0, 60));

bottomPanel.setBorder(new MatteBorder(1, 0, 0, 0,
        safeColor("Label.foreground", UIManager.getColor("Menu.borderColor"))));

// Left: Laundromat name (no change)
JPanel leftPanel = new JPanel(new BorderLayout());
leftPanel.setOpaque(false);
leftPanel.setBorder(new MatteBorder(0, 0, 0, 1,
        safeColor("Label.foreground", UIManager.getColor("Menu.borderColor"))));

JLabel nameLabel = new JLabel(data.name);
nameLabel.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 15.5f));
nameLabel.setForeground(UIManager.getColor("Label.foreground"));
nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
nameLabel.setVerticalAlignment(SwingConstants.CENTER);

JPanel nameHolder = new JPanel(new BorderLayout());
nameHolder.setOpaque(false);
nameHolder.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
nameHolder.add(nameLabel, BorderLayout.CENTER);
leftPanel.add(nameHolder, BorderLayout.CENTER);

// Right: Distance + Delivery with icons, vertically centered
JPanel rightPanel = new JPanel(new GridBagLayout());
rightPanel.setOpaque(false);
rightPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

GridBagConstraints gbc = new GridBagConstraints();
gbc.gridx = 0;
gbc.gridy = 0;
gbc.insets = new Insets(4, 0, 4, 0);
gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.weightx = 1.0;

// Row 1: distance icon + label
JLabel distIcon = new JLabel(iconCreator.getIcon("Icons/distancefromUser.svg", 16, 16));
JLabel distanceLabel = new JLabel(" " + data.distance);
distanceLabel.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 12.5f));
distanceLabel.setForeground(UIManager.getColor("Label.foreground"));

JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
row1.setOpaque(false);
row1.add(distIcon);
row1.add(distanceLabel);

gbc.anchor = GridBagConstraints.CENTER;
rightPanel.add(row1, gbc);

// Row 2: period icon + label
gbc.gridy++;
JLabel periodIcon = new JLabel(iconCreator.getIcon("Icons/deliveryperiod.svg", 16, 16));
JLabel deliveryLabel = new JLabel(" " + data.deliveryPeriod);
deliveryLabel.setFont(UIManager.getFont("Label.font").deriveFont(Font.PLAIN, 12.5f));
deliveryLabel.setForeground(UIManager.getColor("Label.foreground"));

JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
row2.setOpaque(false);
row2.add(periodIcon);
row2.add(deliveryLabel);

rightPanel.add(row2, gbc);

// Add panels to bottom
bottomPanel.add(leftPanel);
bottomPanel.add(rightPanel);

add(bottomPanel, BorderLayout.CENTER);

        // === Hover & click effects ===
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onSelect.accept(data);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(UIManager.getColor("Sidebar.hoverBackground"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(UIManager.getColor("Sidebarbtn.background"));
            }
        });
    }

    private Color safeColor(String key, Color fallback) {
        Color c = UIManager.getColor(key);
        return (c != null) ? c : (fallback != null ? fallback : Color.GRAY);
    }
}
