/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.lnf;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 *
 * @author Luan Nguyen Thanh
 */
public class DevStyle {

    /**
     * Set the look and feel for the game.
     * Currently using the System Look and Feel (Windows)
     */
    public static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DevStyle.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
}
