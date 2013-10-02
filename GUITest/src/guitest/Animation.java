/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guitest;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author BACH
 */
public class Animation extends JPanel {
    
    JLabel label = new JLabel("asdkljsahj sdfsda");
    
    public Animation() {
        setVisible(true);
        setPreferredSize(new Dimension(100, 100));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(label.getX(), label.getY(), label.getWidth(), label.getHeight());        
    }
}
