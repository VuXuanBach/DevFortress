/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.lnf;

import de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.text.ParseException;
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

    // Modern style colors
    public static final Color BLUE = new Color(0x1b, 0xa1, 0xe2),
            LIGHT_BLUE = new Color(0xd8, 0xf3, 0xff),
            RED = new Color(0xe5, 0x14, 0x00),
            GREEN = new Color(0x33, 0x99, 0x33),
            MARINE = new Color(0x03, 0x48, 0x88),
            LIGHTER_MARINE = new Color(0x29, 0x7A, 0xCC),
            TEAL_ORANGE = new Color(0xd5, 0x4d, 0x34);
    // Basic colors
    public static final Color HIGHLIGHT_COLOR = LIGHT_BLUE;
    public static final Color FORE_COLOR = Color.black;
    public static final Color BACK_COLOR = Color.white;    
    // Font styles applied for some common cases
    public static final Font BUTTON_FONT = SegoeFont.createSegoeUIFont(
            SegoeFont.SEMI_LIGHT,
            15f);
    public static final Font SMALL_BUTTON_FONT = SegoeFont.createSegoeUIFont(
            SegoeFont.NORMAL, 
            13.4f);
    public static final Font FORM_TITLE = SegoeFont.createSegoeUIFont(
            SegoeFont.SEMI_LIGHT,
            25f);
    public static final Font FORM_HEADER = SegoeFont.createSegoeUIFont(
            SegoeFont.SEMI_BOLD,
            16f);
    public static final Font SKILL_NAME = SegoeFont.createSegoeUIFont(
            SegoeFont.SEMI_BOLD,
            15.0f);
    public static final Font SKILL_POINT = SegoeFont.createSegoeUIFont(
            SegoeFont.NORMAL,
            14.0f);

    /**
     * Sets look and feel for the game. True for using Microsoft Modern Style
     * False for using System Look and Feel
     *
     * @param useModern
     */
    public static void setLookAndFeel(boolean useModern) {
        if (useModern) {
            try {
                // Customizes the default Java L&F
                UIManager.put("MenuItem.selectionForeground", Color.WHITE);

                // Customizes the Synthetica L&F
                UIManager.put("Synthetica.window.arcW", 0);
                UIManager.put("Synthetica.window.arcH", 0);
                UIManager.put("Synthetica.rootPane.titlePane.iconifyButton.gap",
                        10);
                UIManager.put("Synthetica.rootPane.titlePane.opaque",
                        false);
                UIManager.put("Synthetica.rootPane.titlePane.toggleButton.gap",
                        10);
                UIManager.put("Synthetica.rootPane.titlePane.title.visible",
                        false);
                UIManager.put(
                        "Synthetica.rootPane.titlePane.iconifyButton.insets",
                        new Insets(10, 0, 15, 0));
                UIManager.put(
                        "Synthetica.rootPane.titlePane.toggleButton.insets",
                        new Insets(10, 0, 15, 0));
                UIManager.
                        put("Synthetica.rootPane.titlePane.closeButton.insets",
                        new Insets(10, 0, 15, 5));
                UIManager.put("Synthetica.rootPane.titlePane.menuBar.insets",
                        new Insets(10, 3, 3, 3));

                // Sets look and feel
                UIManager.setLookAndFeel(new SyntheticaWhiteVisionLookAndFeel());

                // Sets license for Synthetica pack
                String[] syntheticaLicense = {
                    "Licensee=Nguyen Thanh Luan",
                    "LicenseRegistrationNumber=NCNL120529",
                    "Product=Synthetica",
                    "LicenseType=Non Commercial",
                    "ExpireDate=--.--.----",
                    "MaxVersion=2.999.999"
                };
                UIManager.put("Synthetica.license.info", syntheticaLicense);
                UIManager.put("Synthetica.license.key",
                        "3328F49F-31D2DE58-63F25088-5D69B566-1039A8FD");

                // Sets license for Synthetica Addons pack
                String[] syntheticaAddonsLicense = {
                    "Licensee=Nguyen Thanh Luan",
                    "LicenseRegistrationNumber=NCNL120529",
                    "Product=SyntheticaAddons",
                    "LicenseType=Non Commercial",
                    "ExpireDate=--.--.----",
                    "MaxVersion=1.999.999"};
                UIManager.put("SyntheticaAddons.license.info",
                        syntheticaAddonsLicense);
                UIManager.put("SyntheticaAddons.license.key",
                        "8BF822DF-DCC143A8-BB6B46BF-DE468F33-35AF01FE");

            } catch (ParseException | UnsupportedLookAndFeelException ex) {
                // Sets system looks and feel. If there's exception, leave the L&F
                // be Java Default (Metal L&F).
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch (UnsupportedLookAndFeelException |
                        ClassNotFoundException |
                        InstantiationException |
                        IllegalAccessException e) {
                }
            }
        } else {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException |
                    InstantiationException |
                    IllegalAccessException |
                    UnsupportedLookAndFeelException ex) {
                Logger.getLogger(DevStyle.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
    }
}
