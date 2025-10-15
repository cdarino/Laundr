package org.example.gui.utils.creators;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class themeToggleButton extends JButton {

    private final Runnable toggleAction;

    public themeToggleButton(Runnable toggleAction) {
        super();
        this.toggleAction = toggleAction;

        setOpaque(true);
        setBorderPainted(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setPreferredSize(new Dimension(40, 40));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        updateAppearance();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleAction.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateAppearance();
            }
        });

        //makes sure that the toggle button updates globally
        UIManager.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("lookAndFeel".equals(evt.getPropertyName())) {
                    SwingUtilities.invokeLater(() -> updateAppearance());
                }
            }
        });
    }

    public void updateAppearance() {
        if (FlatLaf.isLafDark()) {
            setIcon(new FlatSVGIcon("icons/sun.svg", 21, 21));
            setBackground(new Color(0x1D2D4C));
        } else {
            setIcon(new FlatSVGIcon("icons/moon.svg", 21, 21));
            setBackground(new Color(0x31A4E5));
        }
        repaint();
    }
}
