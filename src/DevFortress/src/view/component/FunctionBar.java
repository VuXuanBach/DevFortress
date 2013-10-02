/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.component;

import view.lnf.PanelCreator;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author DELL XPS
 */
public class FunctionBar extends JPanel implements PanelCreator {

    private JButton btnProject, btnDeveloper, btnShop, btnNext;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setLayout(new BorderLayout());

        FunctionBar dv = new FunctionBar();
        frame.add(dv, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

    }

    public FunctionBar() {
        init();
        initLayout();
        initListener();        
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
            }
        });
    }
}
