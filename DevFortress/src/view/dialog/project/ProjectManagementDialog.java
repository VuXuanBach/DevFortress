/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.dialog.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Developer;
import model.Project;
import model.ProjectRequirement;
import model.Utilities;
import model.facade.DevModel;
import net.miginfocom.swing.MigLayout;
import view.component.BarChart;
import view.component.CollapsiblePanel;
import view.component.JTKButton;
import view.lnf.DevStyle;
import view.lnf.PanelCreator;
import view.mediator.Colleague;
import view.mediator.Mediator;
import view.util.ViewUtilities;

/**
 *
 * @author DELL XPS
 */
public class ProjectManagementDialog extends JDialog implements PanelCreator,
        Colleague {

    private DevModel model;
    private Mediator mediator;
    private JScrollPane listDev;
    private JList projects;
    private String[] projectNames;
    private Developer[] developers;
    private JComboBox[] areas;
    private JPanel pnProjList, pnDetail, pnDev;
    private JTKButton btnAddProject, btnAssignDev, btnSave, btnCancel;
    private CollapsiblePanel collap;
    private Project currProject;

    public ProjectManagementDialog(DevModel model, Mediator mediator,
            CollapsiblePanel collap) {
        this.model = model;
        this.mediator = mediator;
        this.collap = collap;

        init();
        initLayout();
        initListener();
    }

    @Override
    public void init() {
        // Initializes the frame
        setTitle("Projects Management");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // Initializes components
        pnDetail = new JPanel();
        pnDev = new JPanel();

        projects = new JList(loadProjectList());
        projects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projects.setLayoutOrientation(JList.VERTICAL);
        projects.setVisibleRowCount(4);
        projects.setCellRenderer(new ProjectCellRenderer());

        ListSelectionModel listSelectionModel = projects.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                new SharedListSelectionHandler());

        btnAddProject = new JTKButton("Accept Projects");

        btnAssignDev = new JTKButton("Assign Developers");
        btnAssignDev.setFont(DevStyle.SMALL_BUTTON_FONT);

        btnSave = new JTKButton("Save");
        btnSave.setFont(DevStyle.SMALL_BUTTON_FONT);

        btnCancel = new JTKButton("Cancel");
        btnCancel.setFont(DevStyle.SMALL_BUTTON_FONT);
    }

    @Override
    public void initLayout() {
        setLayout(new BorderLayout());
        pnDetail.setLayout(new MigLayout("fill"));

        pnProjList = makeProjectsList();

        add(pnProjList, BorderLayout.WEST);
        add(pnDetail);

        setPreferredSize(new Dimension(950, 600));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void initListener() {
        btnAddProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loadAddingProject();
            }
        });

        btnAssignDev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doAssignDev();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        });
    }

    @Override
    public void send(String message) {
        mediator.send(message, this);
    }

    @Override
    public void receive(String message) {
        if (message.equals(ViewUtilities.MEDIATOR_ACCEPT_PROJECT)) {
            projects.setListData(loadProjectList());
            loadAddingProject();
        }
        if (message.equals(ViewUtilities.MEDIATOR_ASSIGN_DEV)) {
            loadDeveloperDetails(currProject);
        }
    }

    private ListProjectEntry[] loadProjectList() {
        ListProjectEntry[] entries = new ListProjectEntry[model.getProjects().
                getProjectList().size()];
        projectNames = new String[model.getProjects().getProjectList().size()];
        int i = 0;
        for (Project p : model.getProjects().getProjectList()) {
            entries[i] = new ListProjectEntry(p.getImage(), alignment(p.
                    getName(),
                    Utilities.format2DecimalDouble(p.getPercentFunction())));
            projectNames[i] = p.getName();
            i++;
        }
        return entries;
    }

    private JPanel makeProjectsList() {
        // Creates panel
        MigLayout layout = new MigLayout("fill, insets 0 10 0 10");
        JPanel panel = new JPanel(layout);

        // Adds title
        JLabel label = new JLabel("Projects");
        label.setFont(DevStyle.FORM_TITLE);
        panel.add(label, "north, gapx 8 5, gapy 0 5");

        // Adds projects list
        JScrollPane scrollPane = new JScrollPane(projects);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        panel.add(scrollPane, "grow");

        // Adds accept projects button
        panel.add(btnAddProject, "south, gapx 10 10, gapy 5 10");

        // Finalizes
        panel.setPreferredSize(new Dimension(235, 600));

        return panel;
    }

    private String alignment(String name, String percent) {//
        if (name.length() + percent.length() >= 25) {
            name = name.substring(0, 20) + "...";
        }
        StringBuilder buff = new StringBuilder();
        buff.append("<html><table>");
        buff.append(String.format(
                "<tr><td width='105'>%s</td><td align='right'>%s</td></tr>",
                name, percent + "%"));
        buff.append("</table></html>");
        return buff.toString();
    }

    private int getCurrentTurn() {
        return model.getWeek();
    }

    private void loadProjectDetails() {
        // Clears the pnDetail
        pnDetail.removeAll();

        JPanel top = new JPanel();
        // Creates title
        JLabel title = new JLabel(currProject.getName(), currProject.getImage(),
                SwingConstants.LEADING);
        title.setFont(DevStyle.FORM_TITLE);
        top.add(title);
        
        JButton btnDetail = new JButton("Details");
        top.add(btnDetail);
        btnDetail.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                DetailProjectProgressDialog dppd = new DetailProjectProgressDialog(model, mediator, currProject);
            }
        });
        pnDetail.add(top, "north");

        title = new JLabel("Progress so far");
        title.setFont(DevStyle.FORM_HEADER);
        title.setForeground(DevStyle.MARINE);
        pnDetail.add(title, "north, gapx 5 5, pad 0 0 10 0");

        // Creates process graph
        BarChart barChart = new BarChart("",
                currProject.getPercentFunction(), currProject.
                getPercentTime(getCurrentTurn()));
        pnDetail.add(barChart, "center, dock north");        

        loadDeveloperDetails(currProject);
        pnDetail.add(pnDev, "span, grow");

        pnDetail.repaint();
        pnDetail.revalidate();
    }

    private void loadDeveloperDetails(final Project p) {
        // Clears the pnDev
        pnDev.removeAll();

        MigLayout layout = new MigLayout("", // layout constraints
                "[]push[]", // column constraints
                ""); // row constraints
        pnDev.setLayout(layout);

        JLabel title = new JLabel("Developers");
        title.setFont(DevStyle.FORM_HEADER);
        title.setForeground(DevStyle.MARINE);
        pnDev.add(title);

//        pnDev.add(btnSave);
//        pnDev.add(btnCancel);
        pnDev.add(btnAssignDev, "wrap, gapx 0 7");

        JScrollPane scrollPane = new JScrollPane(makeProjDevDetails(p));
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
                Color.LIGHT_GRAY));
        pnDev.add(scrollPane, "span, grow");

        pnDev.repaint();
        pnDev.revalidate();
    }

    private void doAssignDev() {
        AssignDevelopersDialog add = new AssignDevelopersDialog(model,
                currProject, mediator);
        mediator.addColleague(add);
    }

    private JPanel makeProjDevDetails(final Project p) {
        MigLayout layout = new MigLayout("",
                "[]50[][]push[]",
                "");
        JPanel panel = new JPanel(layout);
        System.out.println(p == null);
        System.out.println(p.getProjectDeveloper() == null);
        int projDevNum = p.getProjectDeveloper().size();
        developers = new Developer[projDevNum];
        areas = new JComboBox[projDevNum];

        int i = 0;
        for (final Developer d : p.getProjectDeveloper()) {
            developers[i] = d;
            ImageIcon icon = new ImageIcon(d.getAvatar().getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            JLabel lblDev = new JLabel(d.getName(), icon, SwingConstants.LEADING);
            panel.add(lblDev);

            ArrayList<String> names = convertReqToNames();
            areas[i] = new JComboBox(names.toArray());
            for (int j = 0; j < names.size(); j++) {
                if (names.get(j).equals(d.getProjectRequirement().
                        getRequiredSkill().getName())) {
                    areas[i].setSelectedIndex(j);
                }
            }
            final int count = i;
            areas[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    model.changeDevRequirement(d, areas[count].getSelectedItem().toString());
                }
            });

            panel.add(areas[i]);

            JLabel lblFuncPerTurn = new JLabel("+" + d.totalPoint(currProject, d.getProjectRequirement()) + "/turn");
            panel.add(lblFuncPerTurn);

            JButton btnRemove = new JTKButton("Remove");
            btnRemove.setFont(DevStyle.SMALL_BUTTON_FONT);

            btnRemove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    currProject.removeDeveloper(d.getName());
                    loadDeveloperDetails(currProject);
                }
            });
            btnRemove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    p.getProjectDeveloper().remove(d);
                }
            });
            panel.add(btnRemove);
            i++;
        }
        return panel;
    }

    private void loadAddingProject() {
        pnDetail.removeAll();

        JLabel title = new JLabel("Projects Available");
        title.setFont(DevStyle.FORM_TITLE);
        pnDetail.add(title, "north");

        JScrollPane pnScroll = new JScrollPane(collap.getComponent());
        pnScroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0,
                0, Color.LIGHT_GRAY));
        pnScroll.getVerticalScrollBar().setUnitIncrement(14);
        pnDetail.add(pnScroll, "span, grow");

        pnDetail.repaint();
        pnDetail.revalidate();
    }

    private ArrayList<String> convertReqToNames() {
        ArrayList names = new ArrayList<>();
        for (ProjectRequirement pr : currProject.getRequirements()) {
            names.add(pr.getRequiredSkill().getName());
        }
        return names;
    }

    class SharedListSelectionHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            if (!lsm.isSelectionEmpty() && !e.getValueIsAdjusting()) {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                // Since this is a single-selected List, 
                // minIndex will be the selected index.
                if (lsm.isSelectedIndex(minIndex)) {
                    for (Project p : model.getProjects().getProjectList()) {
                        if (projectNames[minIndex].equals(p.getName())) {
                            currProject = p;
                            loadProjectDetails();
                            return;
                        }
                    }
                }
            }
        }
    }
}

class ListProjectEntry {

    private String title;
    private ImageIcon imageIcon;

    public ListProjectEntry(ImageIcon image, String title) {
        this.title = title;
        this.imageIcon = image;
    }

    public String getTitle() {
        return title;
    }

    public ImageIcon getImage() {
        return imageIcon;
    }

    // Override standard toString method to give a useful result
    @Override
    public String toString() {
        return title;
    }
}

class ProjectCellRenderer extends JLabel implements ListCellRenderer {

    public ProjectCellRenderer() {
        setOpaque(true);
        setIconTextGap(10);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        ListProjectEntry entry = (ListProjectEntry) value;
        setText(entry.getTitle());
        setFont(DevStyle.SMALL_BUTTON_FONT);

        // Resize image
        Image image;
        if (entry.getImage() != null) {
            image = entry.getImage().getImage();
            image = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        } else {
            image = (Image) new BufferedImage(32, 32,
                    BufferedImage.TYPE_INT_ARGB);
        }
        setIcon(new ImageIcon(image));
        if (isSelected) {
            setBackground(DevStyle.HIGHLIGHT_COLOR);
            setForeground(DevStyle.FORE_COLOR);
        } else {
            setBackground(DevStyle.BACK_COLOR);
            setForeground(DevStyle.FORE_COLOR);
        }
        return this;
    }
}
