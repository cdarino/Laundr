package org.example.gui.laundromats;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import org.example.gui.utils.creators.iconCreator;
import org.example.gui.utils.creators.roundedPanel;
import org.example.gui.utils.creators.roundedBorder;

public class LaundromatCard extends roundedPanel {

    private static final int CARD_RADIUS = 20;
    private static final int IMAGE_HEIGHT = 260;

    private final JPanel bottomPanel;
    private final JPanel leftPanel;
    private final JPanel rightPanel;
    private final JLabel nameLabel;
    private final JLabel distanceLabel;
    private final JLabel deliveryLabel;
    private final TopRoundedImagePanel imagePanel;

    public LaundromatCard(LaundromatData data, Consumer<LaundromatData> onSelect) {
        super(CARD_RADIUS);
        setLayout(new BorderLayout());

        nameLabel = new JLabel(data.name);
        distanceLabel = new JLabel(" " + data.distance);
        deliveryLabel = new JLabel(" " + data.deliveryPeriod);
        bottomPanel = new JPanel(new GridLayout(1, 2));
        leftPanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel(new GridBagLayout());
        imagePanel = new TopRoundedImagePanel(data.imagePath, 10, 3, getLightModeBlue());

        setupLayout(data, onSelect);
        applyThemeStyling();

        UIManager.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("lookAndFeel".equals(evt.getPropertyName())) {
                    SwingUtilities.invokeLater(() -> applyThemeStyling());
                }
            }
        });
    }

    private void setupLayout(LaundromatData data, Consumer<LaundromatData> onSelect) {
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));
        setPreferredSize(new Dimension(0, 320));

        imagePanel.setPreferredSize(new Dimension(0, IMAGE_HEIGHT));
        imagePanel.setOpaque(false);
        add(imagePanel, BorderLayout.NORTH);

        // === Bottom section ===
        bottomPanel.setOpaque(true);
        bottomPanel.setPreferredSize(new Dimension(0, 60));
        bottomPanel.setBorder(new MatteBorder(0, 0, 0, 0, getLightModeBlue()));

        // Left panel (name)
        leftPanel.setOpaque(false);
        leftPanel.setBorder(new MatteBorder(0, 3, 3, 2, getLightModeBlue()));

        nameLabel.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 15.5f));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nameLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel nameHolder = new JPanel(new BorderLayout());
        nameHolder.setOpaque(false);
        nameHolder.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        nameHolder.add(nameLabel, BorderLayout.CENTER);
        leftPanel.add(nameHolder, BorderLayout.CENTER);

        // Right panel (distance + delivery)
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new MatteBorder(0, 2, 3, 3, getLightModeBlue()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(1, 0, 1, 0);
        gbc.anchor = GridBagConstraints.WEST; // ✅ restore left alignment
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel distIcon = new JLabel(iconCreator.getIcon("Icons/distancefromUser.svg", 16, 16));
        JLabel periodIcon = new JLabel(iconCreator.getIcon("Icons/deliveryperiod.svg", 16, 16));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row1.setOpaque(false);
        row1.add(distIcon);
        row1.add(distanceLabel);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row2.setOpaque(false);
        row2.add(periodIcon);
        row2.add(deliveryLabel);

        rightPanel.add(row1, gbc);
        gbc.gridy++;
        rightPanel.add(row2, gbc);

        bottomPanel.add(leftPanel);
        bottomPanel.add(rightPanel);
        add(bottomPanel, BorderLayout.CENTER);

        // === Hover effects ===
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onSelect.accept(data);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(UIManager.getColor("Sidebar.hoverBackground"));
                bottomPanel.setBackground(UIManager.getColor("Sidebar.hoverBackground"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                applyThemeStyling();
            }
        });
    }

    private void applyThemeStyling() {
        boolean dark = isDarkTheme();

        Color borderColor = dark ? Color.WHITE : getLightModeBlue();
        Color textColor = dark ? Color.WHITE : Color.BLACK;
        Color cardBg = dark ? getDarkScrollbarColor() : Color.WHITE;
        Color bottomBg = dark ? new Color(0x273755) : Color.WHITE;

        setBackground(cardBg);
        bottomPanel.setBackground(bottomBg);
        nameLabel.setForeground(textColor);
        distanceLabel.setForeground(textColor);
        deliveryLabel.setForeground(textColor);

        setBorder(new roundedBorder(CARD_RADIUS, borderColor, 2));
        leftPanel.setBorder(new MatteBorder(0, 3, 3, 2, borderColor));
        rightPanel.setBorder(new MatteBorder(0, 2, 3, 3, borderColor));

        imagePanel.setBorderColor(borderColor);
        imagePanel.repaint();

        revalidate();
        repaint();
    }

    private static Color getLightModeBlue() {
        Color c = UIManager.getColor("Component.accentColor");
        if (c == null) c = UIManager.getColor("Actions.Blue");
        if (c == null) c = new Color(0x2196F3);
        return c;
    }

    private static Color getDarkScrollbarColor() {
        Color c = UIManager.getColor("ScrollBar.thumbDarkShadow");
        if (c == null) c = new Color(0x4A90E2);
        return c;
    }

    private boolean isDarkTheme() {
        Color bg = UIManager.getColor("Panel.background");
        if (bg == null) return false;
        double luminance = (0.299 * bg.getRed()) + (0.587 * bg.getGreen()) + (0.114 * bg.getBlue());
        return luminance < 128;
    }

    private static class TopRoundedImagePanel extends JPanel {
        private final String imagePath;
        private final int radius;
        private final int borderThickness;
        private Color borderColor;
        private Image baseImage;
        private Image lastRendered;

        TopRoundedImagePanel(String imagePath, int radius, int borderThickness, Color borderColor) {
            this.imagePath = imagePath;
            this.radius = radius;
            this.borderThickness = borderThickness;
            this.borderColor = borderColor;
            setOpaque(false);

            Icon raw = iconCreator.getIcon(imagePath, 360, IMAGE_HEIGHT);
            if (raw instanceof ImageIcon)
                baseImage = ((ImageIcon) raw).getImage();
            else if (raw instanceof com.formdev.flatlaf.extras.FlatSVGIcon) {
                BufferedImage img = new BufferedImage(360, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
                Graphics g = img.getGraphics();
                raw.paintIcon(null, g, 0, 0);
                g.dispose();
                baseImage = img;
            }

            addComponentListener(new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(java.awt.event.ComponentEvent e) {
                    rescaleAndCrop();
                    repaint();
                }
            });
        }

        void setBorderColor(Color newColor) {
            this.borderColor = newColor;
        }

        private void rescaleAndCrop() {
            if (baseImage == null) {
                lastRendered = null;
                return;
            }

            int w = getWidth(), h = getHeight();
            if (w <= 0 || h <= 0) return;

            double panelRatio = (double) w / h;
            double imageRatio = (double) baseImage.getWidth(null) / baseImage.getHeight(null);
            int scaledW, scaledH;

            if (imageRatio > panelRatio) {
                scaledH = h;
                scaledW = (int) Math.round(scaledH * imageRatio);
            } else {
                scaledW = w;
                scaledH = (int) Math.round(scaledW / imageRatio);
            }

            Image scaled = baseImage.getScaledInstance(scaledW, scaledH, Image.SCALE_SMOOTH);
            BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = out.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int x = (scaledW - w) / 2, y = (scaledH - h) / 2;
            Shape clip = createTopRoundedClip(w, h, radius);
            g2.setClip(clip);
            g2.drawImage(scaled, -x, -y, null);
            g2.setClip(null);

            if (borderThickness > 0) {
                g2.setStroke(new BasicStroke(borderThickness));
                g2.setColor(borderColor);
                Shape borderShape = createTopRoundedClip(w - borderThickness, h - borderThickness, radius);
                AffineTransform at = AffineTransform.getTranslateInstance(borderThickness / 2.0, borderThickness / 2.0);
                g2.draw(at.createTransformedShape(borderShape));
            }
            g2.dispose();
            lastRendered = out;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (lastRendered == null) rescaleAndCrop();
            if (lastRendered != null) g.drawImage(lastRendered, 0, 0, null);
        }

        private Shape createTopRoundedClip(int width, int height, int r) {
            int radius = Math.max(0, Math.min(r, Math.min(width / 2, height / 2)));
            Path2D path = new Path2D.Double();
            path.moveTo(0, radius);
            path.quadTo(0, 0, radius, 0);
            path.lineTo(width - radius, 0);
            path.quadTo(width, 0, width, radius);
            path.lineTo(width, height);
            path.lineTo(0, height);
            path.closePath();
            return path;
        }
    }
}
