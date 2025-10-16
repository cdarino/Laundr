package org.example.gui.utils.creators;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Returns an Icon for either SVG files (via FlatSVGIcon) or raster images (png/jpg).
 * Falls back to UIManager icon on error.
 */
public class iconCreator {
    public static Icon getIcon(String path, int w, int h) {
        try {
            // Normalize path for resource lookup (allow "Icons/..." or simple filename)
            String normalized = path.startsWith("/") ? path : "/" + path;
            // Treat svg differently
            if (path.toLowerCase().endsWith(".svg")) {
                // FlatSVGIcon supports scaling directly
                return new FlatSVGIcon(path, w, h);
            } else {
                // Try load raster image from resources
                URL res = iconCreator.class.getResource(normalized);
                if (res == null) {
                    // try without leading slash just in case
                    res = iconCreator.class.getResource(path);
                }
                if (res != null) {
                    Image img = ImageIO.read(res);
                    if (img != null) {
                        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                        return new ImageIcon(scaled);
                    }
                }
                // As a last attempt: create an ImageIcon relative using path directly
                return new ImageIcon(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return UIManager.getIcon("OptionPane.informationIcon");
        }
    }
}
