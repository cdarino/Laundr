package org.example.gui.panels;

import org.example.gui.Mainframe;
import org.example.gui.utils.creators.buttonCreator;
import org.example.gui.utils.creators.iconCreator;
import org.example.gui.utils.creators.themeToggleButton;

import javax.swing.*;
import java.awt.*;

public class Register extends JPanel {
    private final Mainframe frame;
    private JLabel title;
    private JPanel formPanel;
    private buttonCreator registerBtn;
    private JButton toggleThemeBtn;
    private JLabel logoLabel;

    public Register(Mainframe frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());

        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setIcon(iconCreator.getIcon(
                frame.isDarkMode() ? "Icons/logoDarkMode.svg" : "Icons/logo.svg",
                150, 100
        ));
        formPanel.add(logoLabel, gbc);

        gbc.gridy++;
        title = new JLabel("Register");
        formPanel.add(title, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registerBtn = new buttonCreator("Register", "Heading3.font", () -> frame.showCard("LOGIN"));
        formPanel.add(registerBtn, gbc);

        gbc.gridy++;
        toggleThemeBtn = new themeToggleButton(frame::toggleTheme);
        formPanel.add(toggleThemeBtn, gbc);

        GridBagConstraints outer = new GridBagConstraints();
        outer.gridx = 0;
        outer.gridy = 0;
        outer.anchor = GridBagConstraints.NORTH;
        outer.weightx = 1.0;
        outer.weighty = 1.0;
        outer.insets = new Insets(40, 0, 0, 0);
        add(formPanel, outer);

        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();

        setBackground(UIManager.getColor("Panel.background"));
        if (formPanel != null) formPanel.setBackground(UIManager.getColor("Panel.background"));

        if (logoLabel != null) {
            logoLabel.setIcon(iconCreator.getIcon(
                    frame.isDarkMode() ? "Icons/logoDarkMode.svg" : "Icons/logo.svg",
                    150, 100
            ));
        }

        if (title != null) {
            title.setFont(UIManager.getFont("Title.font"));
            title.setForeground(UIManager.getColor("headerColor"));
        }

        if (registerBtn != null) registerBtn.updateUI();

        if (toggleThemeBtn != null) {
            toggleThemeBtn.setFont(UIManager.getFont("Button.font"));
            Color fg = UIManager.getColor("Button.foreground");
            if (fg != null) toggleThemeBtn.setForeground(fg);
        }
    }
}
