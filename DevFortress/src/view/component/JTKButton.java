/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */

package view.component;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import view.lnf.DevStyle;

/**
 *
 * @author Luan Nguyen Thanh <tklarryonline@gmail.com>
 */
public class JTKButton extends JButton implements MouseListener {

    public JTKButton(String text, Icon icon) {
        super(text, icon);
        init();
    }

    public JTKButton(Action a) {
        super(a);
        init();
    }

    public JTKButton(String text) {
        super(text);
        init();
    }

    public JTKButton(Icon icon) {
        super(icon);
        init();
    }

    public JTKButton() {
        init();
    }

    private void init() {
        setForeground(new Color(33, 33, 33));
        setFont(DevStyle.BUTTON_FONT);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setForeground(Color.white);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setForeground(new Color(33, 33, 33));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setForeground(new Color(33, 33, 33));
    }
}
