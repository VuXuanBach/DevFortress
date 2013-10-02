/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.dialog.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Developer;
import model.Project;
import model.ProjectRequirement;
import model.facade.DevModel;
import net.miginfocom.swing.MigLayout;
import view.lnf.PanelCreator;
import view.mediator.Colleague;
import view.mediator.Mediator;
import view.util.ViewUtilities;

/**
 *
 * @author BACH
 */
public class AssignDevelopersDialog extends JDialog implements PanelCreator, Colleague {

    private DevModel model;
    private Project currentProject;
    private Mediator mediator;
    private JPanel pnHead, pnDev, pnEnd, pnAvai, pnChose;
    private JButton btnHeadSave, btnEndSave;
    private Map<Developer, String> devChoses;
    private List<Developer> devAvais, requestRemoveDev;
    private JLabel lbdevAssign;

    public AssignDevelopersDialog(DevModel model, Project currentProject, Mediator mediator) {
        this.model = model;
        this.currentProject = currentProject;
        this.mediator = mediator;

        init();
        initLayout();
        initListener();
        loadHead();
        loadDev();
        loadEnd();
    }

    @Override
    public void init() {
        pnHead = new JPanel();
        pnDev = new JPanel();
        pnEnd = new JPanel();
        pnAvai = new JPanel();
        pnChose = new JPanel();

        btnHeadSave = new JButton("Save");
        btnEndSave = new JButton("Save");
        lbdevAssign = new JLabel(currentProject.getProjectDeveloper().size()
                + " developer(s) assigned");
    }

    @Override
    public void initLayout() {
        setLayout(new BorderLayout());
        pnHead.setLayout(new MigLayout("", "[]push[]", ""));
        pnDev.setLayout(new MigLayout());
        pnEnd.setLayout(new MigLayout("", "[]push[]", ""));

        add(pnHead, BorderLayout.NORTH);
        add(pnDev);
        add(pnEnd, BorderLayout.SOUTH);

        setVisible(true);
        setPreferredSize(new Dimension(700, 500));
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void initListener() {
        btnHeadSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                save();
            }
        });

        btnEndSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                save();
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

    private void save() {
        for (Developer d : devChoses.keySet()) {
            currentProject.assignDeveloper(d, currentProject.searchProjectRequirement(devChoses.get(d)));
        }
        for (Developer d : requestRemoveDev) {
            currentProject.removeDeveloper(d.getName());
        }

        send(ViewUtilities.MEDIATOR_ASSIGN_DEV);
        lbdevAssign.setText(currentProject.getProjectDeveloper().size()
                + " developer(s) assigned");
    }

    private void loadHead() {
        JPanel pnTop = new JPanel();
        pnTop.setLayout(new BorderLayout());
        pnTop.add(new JLabel(currentProject.getImage()), BorderLayout.WEST);
        pnTop.add(new JLabel(currentProject.getName()));

        pnHead.add(pnTop, "span");
        pnHead.add(new JLabel("Add Developers"), "gap unrel");
        pnHead.add(btnHeadSave);
    }

    private void loadDev() {
        devAvais = new ArrayList<>();
        devAvais = model.getDevelopers().getFreeDevelopers();
        devChoses = new HashMap<>();
        requestRemoveDev = new ArrayList<>();

        addAvai();

        JScrollPane j1 = new JScrollPane(pnAvai);
        j1.setPreferredSize(new Dimension(700, 220));
        JScrollPane j2 = new JScrollPane(pnChose);
        j2.setPreferredSize(new Dimension(700, 220));

        pnDev.add(j1, "span");
        pnDev.add(j2);
    }

    private void loadEnd() {
        pnEnd.add(lbdevAssign);
        pnEnd.add(btnEndSave);
    }

    private void addAvai() {
        pnAvai.removeAll();
        pnAvai.setLayout(new MigLayout("", "20[]50[]50[]50[]"));

        for (final Developer d : devAvais) {
            JPanel pnD = new JPanel();
            pnD.setLayout(new BorderLayout());
            ImageIcon icon = new ImageIcon(d.getAvatar().getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            pnD.add(new JLabel(icon), BorderLayout.WEST);
            pnD.add(new JLabel(d.getName()));

            final JComboBox com = new JComboBox(convertReqToNames().toArray());
            JButton btnAdd = new JButton("Add");

            pnAvai.add(pnD);
            pnAvai.add(new JLabel(d.getMainSkill().getName()));
            pnAvai.add(com);
            pnAvai.add(btnAdd, "span");

            btnAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    devChoses.put(d, com.getSelectedItem().toString());
                    devAvais.remove(d);
                    requestRemoveDev.remove(d);
                    addAvai();
                    addChose();
                }
            });
        }
        pnAvai.repaint();
        pnAvai.revalidate();
    }

    private void addChose() {
        pnChose.removeAll();
        pnChose.setLayout(new MigLayout("", "20[]50[]50[]50[]"));

        for (final Developer d : devChoses.keySet()) {
            JPanel pnD = new JPanel();
            pnD.setLayout(new BorderLayout());
            ImageIcon icon = new ImageIcon(d.getAvatar().getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            pnD.add(new JLabel(icon), BorderLayout.WEST);
            pnD.add(new JLabel(d.getName()));

            JButton btnAdd = new JButton("Remove");

            pnChose.add(pnD);
            pnChose.add(new JLabel(d.getMainSkill().getName()));
            pnChose.add(new JLabel(devChoses.get(d)));
            pnChose.add(btnAdd, "span");

            btnAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
//                    currentProject.assignDeveloper(d, currentProject.searchProjectRequirement(com.getSelectedItem().toString()));
                    devAvais.add(d);
                    requestRemoveDev.add(d);
                    devChoses.remove(d);
                    addAvai();
                    addChose();
                }
            });
        }
        pnChose.repaint();
        pnChose.revalidate();
    }

    private ArrayList<String> convertReqToNames() {
        ArrayList names = new ArrayList<>();
        for (ProjectRequirement pr : currentProject.getUnfinishedRequirement()) {
            names.add(pr.getRequiredSkill().getName());
        }
        return names;
    }
}