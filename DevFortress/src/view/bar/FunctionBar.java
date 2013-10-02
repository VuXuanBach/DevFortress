/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.bar;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import model.exception.GameOverException;
import model.facade.DevModel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXTaskPane;
import view.component.CollapsiblePanel;
import view.component.Colors;
import view.dialog.developer.DeveloperManagementDialog;
import view.dialog.project.ProjectManagementDialog;
import view.dialog.shop.ShopDialog;
import view.lnf.PanelCreator;
import view.mediator.ApplicationMediator;
import view.mediator.Colleague;
import view.util.ViewUtilities;

/**
 *
 * @author DELL XPS
 */
public class FunctionBar extends JXTaskPane implements PanelCreator, Colleague {

    private DevModel model;
    private ApplicationMediator mediator;
    private JButton btnProject, btnDeveloper, btnShop, btnNext;

    public FunctionBar(DevModel model, ApplicationMediator mediator) {
        this.model = model;
        this.mediator = mediator;
        init();
        initLayout();
        initListener();
        changeUIdefaults();
    }

    @Override
    public void init() {
        btnProject = new JButton("Project");
        btnDeveloper = new JButton("Developer");
        btnShop = new JButton("Shop");
        btnNext = new JButton("Next");
    }

    @Override
    public void initLayout() {
        setLayout(new MigLayout("", "[][][]push[]", ""));
        add(btnProject);
        add(btnDeveloper);
        add(btnShop);
        add(btnNext, "gap unrel");
    }

    @Override
    public void initListener() {
        btnProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doProjectClick();
            }
        });

        btnDeveloper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doDevClick();
            }
        });

        btnShop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doShopClick();
            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doNextClick();
            }
        });
    }

    @Override
    public void send(String message) {
        mediator.send(message, this);
    }

    @Override
    public void receive(String message) {
    }

    private void doProjectClick() {
        CollapsiblePanel pnCollap = new CollapsiblePanel(model, mediator, 0);
        ProjectManagementDialog pmd = new ProjectManagementDialog(model, mediator,
                pnCollap);
        mediator.addColleague(pmd);
        mediator.addColleague(pnCollap);
    }

    private void doDevClick() {
        CollapsiblePanel pnCollap = new CollapsiblePanel(model, mediator, 1);
        DeveloperManagementDialog dmd = new DeveloperManagementDialog(model, pnCollap, mediator);
        mediator.addColleague(dmd);
    }

    private void doShopClick() {
        ShopDialog sd = new ShopDialog(model, mediator);
        mediator.addColleague(sd);
    }

    private void doNextClick() {
        try {
            model.nextTurn();
            send(ViewUtilities.MEDIATOR_NEXT_TURN);
        } catch (GameOverException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void changeUIdefaults() {
        UIManager.put("TaskPaneContainer.useGradient", Boolean.FALSE);
        UIManager.put("TaskPaneContainer.background", Colors.LightGray.color(
                0.5f));

        UIManager.put("TaskPane.font", new FontUIResource(new Font("Verdana",
                Font.BOLD, 16)));
        UIManager.put("TaskPane.titleBackgroundGradientStart", Colors.White.
                color());
        UIManager.put("TaskPane.titleBackgroundGradientEnd", Colors.LightBlue.
                color());
    }
}
