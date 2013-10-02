/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.bar;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import model.facade.DevModel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXTaskPane;
import view.component.Colors;
import view.lnf.PanelCreator;
import view.mediator.ApplicationMediator;
import view.mediator.Colleague;
import view.util.ViewUtilities;

/**
 * A panel that shows current resources to the player.
 *
 * @author Luan Nguyen Thanh
 */
public class ResourceBar extends JXTaskPane implements PanelCreator, Colleague {

    private DevModel model;
    private ApplicationMediator mediator;
    /**
     * A label to show current time scale
     */
    private JLabel lblTime;
    private JTextField txtMoney, txtBeer, txtFood;

    public ResourceBar(DevModel model, ApplicationMediator mediator) {
        this.model = model;
        this.mediator = mediator;

        init();
        initListener();
        initLayout();
        loadInfo();
        changeUIdefaults();
    }

    @Override
    public void init() {
        // Init the components
        lblTime = new JLabel();
        txtMoney = new JTextField(7);
        txtMoney.setHorizontalAlignment(JTextField.TRAILING);
        txtBeer = new JTextField(5);
        txtBeer.setHorizontalAlignment(JTextField.TRAILING);
        txtFood = new JTextField(5);
        txtFood.setHorizontalAlignment(JTextField.TRAILING);
    }

    @Override
    public void initLayout() {
        setLayout(new MigLayout("", "[]push[][]25[][]25[][]", ""));

        add(lblTime);

        add(new JLabel("Money"), "gap unrel");
        add(txtMoney);

        add(new JLabel("Beer"));
        add(txtBeer);

        add(new JLabel("Food"));
        add(txtFood);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void send(String message) {
        mediator.send(message, this);
    }

    @Override
    public void receive(String message) {
        if (message.equals(ViewUtilities.MEDIATOR_BUY_ITEM)
                || message.equals(ViewUtilities.MEDIATOR_UP_SKILL_LEVEL)
                || message.equals(ViewUtilities.MEDIATOR_HIRE_DEV)
                || message.equals(ViewUtilities.MEDIATOR_ACCEPT_PROJECT)
                || message.equals(ViewUtilities.MEDIATOR_NEXT_TURN)) {
            loadInfo();
        }
    }

    private void loadInfo() {
        int year = model.getWeek() / 48;
        int month = 1 + (model.getWeek() - year * 48) / 4;
        int week = 1 + model.getWeek() - (year * 48) - ((month - 1) * 4);
        setLblTime(" Year " + model.getWeek() / 48 + " Month " + month + " Week " + week);
        setTxtMoney(ViewUtilities.formatMoney(model.getBalance()) + "");
        setTxtBeer(model.getBeer() + "");
        setTxtFood(model.getFoodStorage() + "");

        repaint();
        revalidate();
    }

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public JLabel getLblTime() {
        return lblTime;
    }

    public void setLblTime(String lblTime) {
        this.lblTime.setText(lblTime);
    }

    public JTextField getTxtMoney() {
        return txtMoney;
    }

    public void setTxtMoney(String txtMoney) {
        this.txtMoney.setText(txtMoney);
    }

    public JTextField getTxtFood() {
        return txtFood;
    }

    public void setTxtFood(String txtCoffee) {
        this.txtFood.setText(txtCoffee);
    }

    public JTextField getTxtBeer() {
        return txtBeer;
    }

    public void setTxtBeer(String txtBeer) {
        this.txtBeer.setText(txtBeer);
    }
    //</editor-fold>

    private void changeUIdefaults() {
        UIManager.put("TaskPaneContainer.useGradient", Boolean.FALSE);
        UIManager.put("TaskPaneContainer.background", Colors.LightGray.color(0.5f));

        UIManager.put("TaskPane.font", new FontUIResource(new Font("Verdana", Font.BOLD, 16)));
        UIManager.put("TaskPane.titleBackgroundGradientStart", Colors.White.color());
        UIManager.put("TaskPane.titleBackgroundGradientEnd", Colors.LightBlue.color());
    }
}
