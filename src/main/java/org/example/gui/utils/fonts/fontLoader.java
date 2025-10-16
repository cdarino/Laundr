package org.example.gui.utils.fonts;

import java.awt.*;
import java.io.InputStream;

public class fontLoader {
    public static void loadFonts() {
        loadFont("/Fonts/Lato-Regular.ttf");
        loadFont("/Fonts/Lato-Bold.ttf");
        loadFont("/Fonts/Fredoka-Bold.ttf");
    }

    private static void loadFont(String path) {
        try (InputStream is = fontLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                return;
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
