/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package View;

import util.DevConfig;
import view.lnf.DevStyle;
import view.lnf.PanelCreator;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.game.gamecanvas.GameCanvas;

/**
 *
 * @author Luan Nguyen Thanh
 */
public class DevView extends JFrame implements PanelCreator, KeyListener {

    public DevView() {
        init();
        initListener();
    }

    @Override
    public void init() {
        setTitle(DevConfig.GAME_NAME + " version " + DevConfig.GAME_VERSION);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        initLayout();

//        pack();
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        displayGame();
    }

    @Override
    public void initLayout() {
    }

    @Override
    public void initListener() {
        addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keycode = ke.getKeyCode();
        if (keycode == 192) { // Tilde key
            showCheat();
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    private void showCheat() {
        String op = JOptionPane.showInputDialog(null, "Command", null, 1);
        command(op);
    }

    private void command(String cmd) {
        if (cmd != null) {
            //OK
        } else {
            //Cancel
        }
    }

    private void displayGame() {
        add(new GameCanvas(getWidth(), getHeight()));
    }

    public static void main(String[] args) {
        DevStyle.setLookAndFeel();

        DevView dv = new DevView();        
    }
}
