/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.menu;

import View.DevView;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import model.facade.DevModel;
import net.miginfocom.swing.MigLayout;
import view.component.JTextButton;
import view.lnf.PanelCreator;
import view.lnf.SegoeFont;
import view.mediator.ApplicationMediator;
import view.mediator.Colleague;
import view.util.ViewUtilities;

/**
 * Menu panel for game.
 *
 * @author Luan Nguyen Thanh <tklarryonline@gmail.com>
 */
public class StartMenuPanel extends JPanel implements PanelCreator,
        ActionListener, Colleague {

    private DevModel model;
    private ApplicationMediator mediator;
    private static final Font BUTTON_FONT = SegoeFont.createSegoeUIFont(
            "normal", 35.0f);
    // Swing Components
    private JTextButton btnNewGame, btnContinue, btnLoad, btnOption, btnExitGame;
//    private Image backgroundImg;

    public StartMenuPanel(DevModel model, ApplicationMediator mediator) {
        this.model = model;
        this.mediator = mediator;
        init();
        initListener();
    }

    @Override
    public void init() {
        // Creates components
        btnNewGame = new JTextButton("Start new game", BUTTON_FONT);
        btnContinue = new JTextButton("Continue", BUTTON_FONT);
//        btnLoad = new JTextButton("Load", BUTTON_FONT);
        btnOption = new JTextButton("Settings", BUTTON_FONT);
        btnExitGame = new JTextButton("Goodbye", BUTTON_FONT);

        initLayout();
    }

    @Override
    public void initLayout() {
        // Initializes panel
        MigLayout layout = new MigLayout("wrap 1", // layout constraints
                "52[200px]", // column constraints
                "300[][]");// row constraints
        setLayout(layout);

        add(btnNewGame, "alignx leading");
        add(btnContinue, "alignx leading");
//        add(btnLoad, "alignx leading");
//        add(btnOption, "alignx leading");
        add(btnExitGame, "alignx leading");
    }

    @Override
    public void initListener() {
        btnNewGame.addActionListener(this);
        btnContinue.addActionListener(this);
//        btnLoad.addActionListener(this);
//        btnOption.addActionListener(this);
        btnExitGame.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Image bgImg = ViewUtilities.makeIcon("BG - GameStart.png").
                getImage();
        Dimension size = this.getSize();

        g.drawImage(bgImg, 0, 0, size.width, size.height, null);

        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String cmd = ae.getActionCommand();

        if (cmd.equals(btnNewGame.getActionCommand())) {
            doStartClick();
        } else if (cmd.equals(btnContinue.getActionCommand())) {
            doContinueClick();
//        } else if (cmd.equals(btnLoad.getActionCommand())) {
//            doLoadClick();
        } else if (cmd.equals(btnOption.getActionCommand())) {
            doSettingsClick();
        } else if (cmd.equals(btnExitGame.getActionCommand())) {
            doExitClick();
        }
    }

    private void doStartClick() {
        DevView dv = new DevView(model, mediator);
        send(ViewUtilities.MEDIATOR_START_GAME);
    }

    private void doContinueClick() {
        model.load();
        doStartClick();
    }

    private void doLoadClick() {
    }

    private void doSettingsClick() {
    }

    private void doExitClick() {
        System.exit(0);
    }

    @Override
    public void send(String message) {
        mediator.send(message, this);
    }

    @Override
    public void receive(String message) {
    }
}
