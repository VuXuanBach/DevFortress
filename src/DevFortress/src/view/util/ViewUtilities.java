/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.util;

import view.lnf.DevStyle;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Luan Nguyen Thanh
 */
public class ViewUtilities {
    /**
     * Returns an ImageIcon based on the name of that icon.
     *
     * @param iconName The name of the icon. Cannot be null or empty. Must be
     * exactly like the name of the PNG file stored in client\view\img.
     * @return The ImageIcon based on the name.
     */
    public static ImageIcon makeIcon(String iconName) {
        URL imgURL = DevStyle.class.getResource("images/" + iconName + ".png");
        return new ImageIcon(imgURL);
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
    
    
}
