/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import view.lnf.DevStyle;

/**
 * A JButton that has text only.
 *
 * @author Luan Nguyen Thanh <tklarryonline@gmail.com>
 */
public class JTextButton extends JButton implements MouseListener {

    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final int DEFAULT_ALPHA = 128;
    private static final int HOVER_ALPHA = 255;
    private static final int PRESSED_ALPHA = 100;
    private Color foreColor;

    public JTextButton(String text) {
        super(text);
        setFont(DevStyle.BUTTON_FONT);
        this.foreColor = DEFAULT_COLOR;
        
        init();
    }

    public JTextButton(String text, Font font) {
        super(text);
        setFont(font);
        this.foreColor = DEFAULT_COLOR;

        init();
    }

    private void init() {
        // Makes the button's text as-is
        setBorder(null);
        setContentAreaFilled(false);

        setForeground(new Color(0, 0, 0, 160));
        addMouseListener(this);

        setPressedIcon(new ImageIcon(new BufferedImage(1,
                1, BufferedImage.TYPE_INT_ARGB_PRE)));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setVerticalTextPosition(CENTER);
        setHorizontalTextPosition(CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setForeground(new Color(0, 0, 0, PRESSED_ALPHA));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setForeground(new Color(0, 0, 0, DEFAULT_ALPHA));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setForeground(new Color(0, 0, 0, HOVER_ALPHA));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setForeground(new Color(0, 0, 0, DEFAULT_ALPHA));
    }
}