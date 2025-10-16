package org.example.gui.utils.creators;

import javax.swing.*;
import java.awt.*;

public class buttonCreator extends roundedPanel {
    private final JLabel label;
    private final String fontKey;
    private boolean selected = false;

    public buttonCreator(String text, String fontKey, Runnable action) {
        this.fontKey = fontKey;

        setLayout(new GridBagLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setOpaque(false);

        setPreferredSize(new Dimension(150, 40));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        label = new JLabel(text, SwingConstants.CENTER);
        add(label);

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.run();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!selected) {
                    Color hover = UIManager.getColor("Button.hoverBackground");
                    if (hover != null) setBackground(hover);
                    repaint();
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!selected) {
                    Color bg = UIManager.getColor("Button.background");
                    if (bg != null) setBackground(bg);
                    repaint();
                }
            }
        });

        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();

        if (label != null) {
            Font f = UIManager.getFont(fontKey);
            if (f != null) label.setFont(f);
            Color fg = UIManager.getColor("Button.foreground");
            if (fg != null) label.setForeground(fg);
        }

        if (!selected) {
            Color bg = UIManager.getColor("Button.background");
            if (bg != null) setBackground(bg);
        } else {
            Color pressed = UIManager.getColor("Button.pressedBackground");
            if (pressed != null) setBackground(pressed);
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        updateUI();
    }

    public boolean isSelected() {
        return selected;
    }
}
