/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.component;

import view.lnf.PanelCreator;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 * A panel that shows current resources to the player.
 *
 * @author Luan Nguyen Thanh
 */
public class ResourceBar extends JPanel implements PanelCreator {

    /**
     * A label to show current time scale
     */
    private JLabel lblTime;
    private JTextField txtMoney, txtBeer, txtCoffee, txtPizza, txtRedbull;

    public ResourceBar(String curDate, String curMoney, String curBeer, String curCoffee, String curPizza, String curRedbull) {
        init();

        // Set value
        setLblTime(curDate);
        setTxtMoney(curMoney);
        setTxtBeer(curBeer);
        setTxtCoffee(curCoffee);
        setTxtPizza(curPizza);
        setTxtRedbull(curRedbull);

        initListener();
    }

    @Override
    public void init() {
        // Init the components
        lblTime = new JLabel();
        txtMoney = new JTextField(5);
        txtMoney.setHorizontalAlignment(JTextField.TRAILING);
        txtBeer = new JTextField(5);
        txtBeer.setHorizontalAlignment(JTextField.TRAILING);
        txtCoffee = new JTextField(5);
        txtCoffee.setHorizontalAlignment(JTextField.TRAILING);
        txtPizza = new JTextField(5);
        txtPizza.setHorizontalAlignment(JTextField.TRAILING);
        txtRedbull = new JTextField(5);
        txtRedbull.setHorizontalAlignment(JTextField.TRAILING);

        initLayout();
    }

    @Override
    public void initLayout() {
        setLayout(new MigLayout("", "[]push[][][][][]", ""));

        add(lblTime);

        add(new JLabel("Money"), "gap unrel");
        add(txtMoney);

        add(new JLabel("Beer"));
        add(txtBeer);

        add(new JLabel("Coffee"));
        add(txtCoffee);

        add(new JLabel("Pizza"));
        add(txtPizza);

        add(new JLabel("Redbull"));
        add(txtRedbull);
    }

    @Override
    public void initListener() {
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

    public JTextField getTxtCoffee() {
        return txtCoffee;
    }

    public void setTxtCoffee(String txtCoffee) {
        this.txtCoffee.setText(txtCoffee);
    }

    public JTextField getTxtPizza() {
        return txtPizza;
    }

    public void setTxtPizza(String txtPizza) {
        this.txtPizza.setText(txtPizza);
    }

    public JTextField getTxtRedbull() {
        return txtRedbull;
    }

    public void setTxtRedbull(String txtRedbull) {
        this.txtRedbull.setText(txtRedbull);
    }

    public JTextField getTxtBeer() {
        return txtBeer;
    }

    public void setTxtBeer(String txtBeer) {
        this.txtBeer.setText(txtBeer);
    }
    //</editor-fold>

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setLayout(new BorderLayout());

        ResourceBar bar = new ResourceBar("Y1 M1 W1", "" + 900000,
                "" + 9, "" + 9, "" + 7, "" + 16);
        frame.add(bar, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}
