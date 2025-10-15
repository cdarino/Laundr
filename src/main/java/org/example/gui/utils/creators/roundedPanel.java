package org.example.gui.utils.creators;

import javax.swing.*;
import java.awt.*;

public class roundedPanel extends JPanel {
    private int arc;
    private String backgroundColorKey;
    private boolean useCustomBackground;

    public roundedPanel() {
        this.arc = UIManager.getInt("Button.arc");
        if (arc <= 0) arc = 16;
        setOpaque(false);
        this.useCustomBackground = false;
    }

    public roundedPanel(int arc) {
        this.arc = arc;
        setOpaque(false);
        this.useCustomBackground = false;
    }

    public void setArc(int arc) {
        this.arc = arc;
        repaint();
    }

    public void setBackgroundColorKey(String colorKey) {
        this.backgroundColorKey = colorKey;
        this.useCustomBackground = true;
        updateBackground();
    }

    private void updateBackground() {
        if (useCustomBackground && backgroundColorKey != null) {
            Color bgColor = UIManager.getColor(backgroundColorKey);
            if (bgColor != null) {
                setBackground(bgColor);
                repaint();
            }
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        updateBackground();

        this.arc = UIManager.getInt("Button.arc");
        if (arc <= 0) arc = 16;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color fill = getBackground();
        g2.setColor(fill != null ? fill : new Color(0, 0, 0, 0));

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }
}