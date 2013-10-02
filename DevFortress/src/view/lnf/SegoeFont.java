/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.lnf;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

/**
 *
 * @author Luan Nguyen Thanh <tklarryonline@gmail.com>
 */
public class SegoeFont {

    private static final Font SERIF_FONT = new Font("serif", Font.PLAIN, 13);
    private static final float DEFAULT_SIZE = 14.0f;
    // Font styles' names
    public static final String LIGHT = "light",
            LIGHT_ITALIC = "lightitalic",
            SEMI_LIGHT = "semilight",
            SEMI_LIGHT_ITALIC = "semilightitalic",
            NORMAL = "normal",
            ITALIC = "italic",
            SEMI_BOLD = "semibold",
            SEMI_BOLD_ITALIC = "semibolditalic",
            BOLD = "bold",
            BOLD_ITALIC = "bolditalic";

    public static Font createSegoeUIFont(String style) {
        Font font;
        try {
            InputStream fontStream = SegoeFont.class.getResourceAsStream(
                    "fonts/segoeui_" + style + ".ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            GraphicsEnvironment ge = GraphicsEnvironment.
                    getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (Exception ex) {
            System.out.println("Cannot find the file");
            font = SERIF_FONT;
        }

        return font.deriveFont(DEFAULT_SIZE);
    }

    public static Font createSegoeUIFont(String style, float size) {
        return createSegoeUIFont(style).deriveFont(size);
    }
}
