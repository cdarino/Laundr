package org.example.gui.laundromats;

import org.example.gui.utils.creators.iconCreator;
import org.example.gui.utils.creators.roundedPanel;

import javax.swing.*;
import java.awt.*;

/**
 * One user review card.
 */
public class ReviewCard extends roundedPanel {
    public ReviewCard(String name, String text) {
        super(20);
        setLayout(new BorderLayout(6, 6));
        setBackground(UIManager.getColor("Sidebarbtn.background"));

        JLabel icon = new JLabel(iconCreator.getIcon("Icons/userIconBlue.svg", 24, 24));
        add(icon, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel nameLbl = new JLabel(name);
        JTextArea txt = new JTextArea(text);
        txt.setWrapStyleWord(true);
        txt.setLineWrap(true);
        txt.setEditable(false);
        txt.setOpaque(false);

        textPanel.add(nameLbl);
        textPanel.add(txt);

        add(textPanel, BorderLayout.CENTER);
    }
}
