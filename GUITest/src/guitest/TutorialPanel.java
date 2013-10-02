/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package guitest;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class TutorialPanel extends JDialog implements KeyListener {

    JLabel label = new JLabel("asdsadasasdasd asdasdas asdasd");
    String s = "Furson ishowlem, a shand Davah, ans, afts me, at mor graelvah twor brain he sere for the imem of thr";
    int count;

    public TutorialPanel() throws InterruptedException {
//        init();
        init2();
    }

    public void init() throws InterruptedException {
        // Initializes the frame
        setTitle("Developers Managemet");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(800, 600);
        label = new JLabel("");
        add(label);
        addKeyListener(this);
        update();
    }

    private void init2() {
        setTitle("Developers Managemet");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(800, 600);
        add(label);
    }

    private void update() throws InterruptedException {
        while (count < s.length()) {
            String s2 = s.substring(0, count);
            label.setText(s2);
            repaint();
            count++;
            Thread.sleep(100);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == 32) {
            count = s.length() - 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
     @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(label.getX(), label.getY(), label.getWidth(), label.getHeight());        
    }
}
