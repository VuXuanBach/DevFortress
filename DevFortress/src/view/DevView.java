/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import model.facade.DevModel;
import net.miginfocom.swing.MigLayout;
import util.DevConfig;
import view.animation.GameCanvas;
import view.bar.FunctionBar;
import view.bar.ResourceBar;
import view.component.Colors;
import view.lnf.DevStyle;
import view.lnf.PanelCreator;
import view.mediator.ApplicationMediator;
import view.menu.StartMenuPanel;

/**
 *
 * @author Luan Nguyen Thanh
 */
public class DevView extends JFrame implements PanelCreator, KeyListener {

    private DevModel model;
    private ApplicationMediator mediator;
    // Swing components    
    private ResourceBar resourceBar;
    private FunctionBar functionBar;
    private GameCanvas gameCanvas;

    public DevView(DevModel model, ApplicationMediator mediator) {
        this.model = model;
        this.mediator = mediator;
        init();
        initListener();
        
        Thread t = new Thread(gameCanvas);
        t.start();
    }

    @Override
    public void init() {
        DevStyle.setLookAndFeel(true);
        model.startGame();
        
        resourceBar = new ResourceBar(model, mediator);
        functionBar = new FunctionBar(model, mediator);
        mediator.addColleague(resourceBar);
        mediator.addColleague(functionBar);

        gameCanvas = new GameCanvas();
        gameCanvas.setIgnoreRepaint(true);
        
        setTitle(DevConfig.GAME_NAME + " version " + DevConfig.GAME_VERSION);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        //initLayout();
        displayGame();
//        pack();
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setMinimumSize(new Dimension(1024, 700));
        setLocationRelativeTo(null);
        setIgnoreRepaint(true);
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
//        JXTaskPaneContainer container = new JXTaskPaneContainer();
//        container.add(resourceBar);

        JScrollPane scroll = new JScrollPane(gameCanvas);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        scroll.getHorizontalScrollBar().setUnitIncrement(14);

        setLayout(new MigLayout("fill"));

        add(resourceBar, "north");
        add(scroll, "grow");
        add(functionBar, "south");

        changeUIdefaults();
    }

    private void changeUIdefaults() {
        resourceBar.getContentPane().setBackground(Colors.LightGray.color());
        functionBar.getContentPane().setBackground(Colors.LightGray.color());
        UIManager.put("TaskPane.titleBackgroundGradientStart", Colors.White.color());
        UIManager.put("TaskPane.titleBackgroundGradientEnd", Color.GREEN);

    }
}
