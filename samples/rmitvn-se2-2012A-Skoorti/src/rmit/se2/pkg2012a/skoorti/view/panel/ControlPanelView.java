/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Le Anh Duy
 */
public class ControlPanelView extends JPanel {

    private JPanel gameStatusPanel, statusBarPanel, settingsPanel;
    private JTabbedPane jTabbedPane, purchaseTabbedPane;

    public ControlPanelView(JPanel gameStatusPanel, JTabbedPane purchaseTabbedPane, JPanel statusBarPanel, JPanel settingsPanel) {
        this.gameStatusPanel = gameStatusPanel;
        this.purchaseTabbedPane = purchaseTabbedPane;
        this.statusBarPanel = statusBarPanel;
        this.settingsPanel = settingsPanel;
        init();
    }

    private void init() {
        jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("Status", gameStatusPanel);
        jTabbedPane.addTab("Purchase", purchaseTabbedPane);
        jTabbedPane.addTab("Settings", settingsPanel);

        setLayout(new BorderLayout());
        add(jTabbedPane);
        add(statusBarPanel, BorderLayout.SOUTH);
    }

    public JPanel getGameStatusPanel() {
        return gameStatusPanel;
    }

    public JTabbedPane getPurchasePanel() {
        return purchaseTabbedPane;
    }

    public JPanel getStatusBarPanel() {
        return statusBarPanel;
    }
}
