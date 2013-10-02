/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import view.lnf.DevStyle;

/**
 *
 * @author Luan Nguyen Thanh
 */
public class ViewUtilities {

    public static final int BLOCK_SIZE = 32;
    public static final int NUM_BLOCK_WIDTH = 50;
    public static final int NUM_BLOCK_HEIGHT = 50;
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 700;
    public static final String MEDIATOR_ACCEPT_PROJECT = "ACCEPT";
    public static final String MEDIATOR_ASSIGN_DEV = "ASSIGN";
    public static final String MEDIATOR_BUY_ITEM = "BUY";
    public static final String MEDIATOR_UP_SKILL_LEVEL = "UP SKILL LEVEL";
    public static final String MEDIATOR_HIRE_DEV = "HIRE DEVELOPER";
    public static final String MEDIATOR_START_GAME = "START NEW GAME";
    public static final String MEDIATOR_NEXT_TURN = "NEXT TURN";    
    
    /**
     * Returns an ImageIcon based on the name of that icon.
     *
     * @param iconSubPath The name of the icon. Cannot be null or empty. Must be
     * exactly like the name of the PNG file stored in view/lnf/images.
     * @return The ImageIcon based on the name.
     */
    public static ImageIcon makeIcon(String iconSubPath) {
        URL imgURL = DevStyle.class.getResource("images/" + iconSubPath);
        return new ImageIcon(imgURL);
    }

    /**
     * Returns an BufferedImage based on the name of that image.
     *
     * @param imgPath The name of the image. Cannot be null or empty. Must be
     * exactly like the name of the PNG file stored in view/lnf/images.
     * @return The BufferedImage based on the name.
     */
    public static BufferedImage makeBufferedImage(String imgPath) throws IOException {
        return ImageIO.read(DevStyle.class.getResource("images/" + imgPath));
    }

    /**
     * Returns a JLabel with the specified text, icon and vertical, horizontal
     * text position.
     *
     * @param text The specified text of the JLabel. Can be null or empty.
     * @param icon The icon of the JLabel.
     * @param vert Must be a JLabel constant of vertical position.
     * @param horiz Must be a JLabel constant of horizontal position.
     * @see Util.makeIcon()
     * @return The created JLabel.
     */
    public static JLabel makeLabel(String text, String icon,
            int vert, int horiz) {
        JLabel label = new JLabel(text, makeIcon(icon), SwingConstants.CENTER);
        label.setVerticalTextPosition(vert);
        label.setHorizontalTextPosition(horiz);
        return label;
    }
    
    /**
     * Creates and returns a simple image only button (no hovered and pressed
     * effects). If the image does not exist, this will return a standard button
     * with text specified by altTxt.
     *
     * @param iconName The name of the icon file.
     * @param command The ActionCommand of this button.
     * @param tooltip The tool tip text for this button.
     * @param altTxt The alternative text in case the icon does not exist.
     * @return The created button.
     */
    public static JButton makeSimpleImgButton(String iconName, String command,
            String tooltip, String altTxt) {
        ImageIcon icon = makeIcon(iconName);
        JButton button = new JButton();

        if (icon != null) {
            button.setIcon(icon);
            button.setActionCommand(command);
            button.setToolTipText(tooltip);
            button.setBorder(null);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
        } else {
            button.setText(altTxt);
            button.setActionCommand(command);
        }

        return button;
    }
    
    public static String formatMoney(double money){
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(money);
    }
}