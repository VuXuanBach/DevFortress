
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rmit.se2.pkg2012a.skoorti.context.ZooContext;
import rmit.se2.pkg2012a.skoorti.view.panel.ControlPanelView;
import rmit.se2.pkg2012a.skoorti.view.GameCanvas;
import rmit.se2.pkg2012a.skoorti.view.panel.*;

/**
 *
 * @author zozo
 */
public class GameLoad extends JFrame {

    public static boolean loading;

    public GameLoad(GameEngine gameEngine, GameCanvas gameCanvas) throws HeadlessException {
        loadGame(gameEngine, gameCanvas);
    }

    private void loadGame(GameEngine gameEngine, GameCanvas gameCanvas) {
        ZooContext.setAppContext(new ClassPathXmlApplicationContext("rmit/se2/pkg2012a/skoorti/AngryParkSpring.xml"));

        StatusPanel statusPanel = new StatusPanel(gameEngine);
        PurchaseTabbedPanel purchaseTabbedPane = new PurchaseTabbedPanel(gameEngine);
        LogPanel logPanel = new LogPanel(gameEngine);
        SettingsPanel settingsPanel = new SettingsPanel(gameEngine);
        ControlPanelView controlJPanel =
                new ControlPanelView(statusPanel, purchaseTabbedPane, logPanel, settingsPanel);
        add(controlJPanel, BorderLayout.EAST);
        gameEngine.startGame();

        add(gameCanvas);
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);

        if (GameStart.loadingScreen != null) {
            GameStart.loadingScreen.setVisible(false);
        }
        loading = false;
    }
}