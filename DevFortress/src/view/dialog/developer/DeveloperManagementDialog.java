/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package view.dialog.developer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Developer;
import model.Project;
import model.SkillFactory;
import model.Utilities;
import model.exception.InvalidException;
import model.exception.MoneyException;
import model.facade.DevModel;
import model.skill.Skill;
import net.miginfocom.swing.MigLayout;
import view.component.CollapsiblePanel;
import view.component.ComponentBorder;
import view.component.JTKButton;
import view.lnf.DevStyle;
import view.lnf.PanelCreator;
import view.mediator.ApplicationMediator;
import view.mediator.Colleague;
import view.util.ViewUtilities;

/**
 *
 * @author DELL XPS
 */
public class DeveloperManagementDialog extends JDialog implements PanelCreator,
        Colleague {

    // Constants
    private static final ImageIcon MALE_SIGN = ViewUtilities.makeIcon(
            "male_sign.png");
    private static final ImageIcon FEMALE_SIGN = ViewUtilities.makeIcon(
            "female_sign.png");
    private DevModel model;
    private CollapsiblePanel pnCollap;
    private ApplicationMediator mediator;
    private JPanel pnDevList, pnDevDetail, pnDevSkill, pnProj, pnButton;
    private JList devs;
    private String[] devNames;
    private Developer currDev;
    private JTKButton btnHireDev, btnSave, btnReset;

    public DeveloperManagementDialog(DevModel model, CollapsiblePanel pnCollap,
            ApplicationMediator mediator) {
        this.model = model;
        this.pnCollap = pnCollap;
        this.mediator = mediator;

        init();
        initLayout();
        initListener();
    }

    @Override
    public void init() {
        // Initializes the frame
        setTitle("Developers Managemet");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // Initializes components
        pnDevDetail = new JPanel();
        pnDevSkill = new JPanel();
        pnProj = new JPanel();
        pnButton = new JPanel();

        devs = new JList(loadDevList());
        devs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        devs.setLayoutOrientation(JList.VERTICAL);
        devs.setVisibleRowCount(4);
        devs.setCellRenderer(new DevCellRenderer());

        ListSelectionModel listSelectionModel = devs.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                new SharedListSelectionHandler());

        btnHireDev = new JTKButton("Hire Developers");

        btnSave = new JTKButton("Save");
        btnSave.setFont(DevStyle.SMALL_BUTTON_FONT);

        btnReset = new JTKButton("Reset");
        btnReset.setFont(DevStyle.SMALL_BUTTON_FONT);

        loadButtons();
    }

    @Override
    public void initLayout() {
        setLayout(new BorderLayout());

        pnDevSkill.setLayout(new MigLayout());
        pnProj.setLayout(new MigLayout());
        pnButton.setLayout(new MigLayout());

        pnDevList = makeDevelopersList();

        add(pnDevList, BorderLayout.WEST);
        add(pnDevDetail);

        setPreferredSize(new Dimension(950, 600));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void initListener() {
        btnHireDev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loadHiringDev();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                loadDevDetails();
            }
        });
    }

    @Override
    public void send(String message) {
        mediator.send(message, this);
    }

    @Override
    public void receive(String message) {
        if (message.equals(ViewUtilities.MEDIATOR_HIRE_DEV)) {
            devs.setListData(loadDevList());
            loadHiringDev();
        }
    }

    private ListDevEntry[] loadDevList() {
        ListDevEntry[] entries = new ListDevEntry[model.getDevelopers().
                getDeveloperList().size()];
        devNames = new String[model.getDevelopers().getDeveloperList().size()];
        int i = 0;
        for (Developer d : model.getDevelopers().getDeveloperList()) {
            entries[i] = new ListDevEntry(d.getAvatar(), d.getName());
            devNames[i] = d.getName();
            i++;
        }
        return entries;
    }

    private JPanel makeDevelopersList() {
        // Creates panel
        MigLayout layout = new MigLayout(
                "fill, insets 5 10 5 10",
                "",
                "");
        JPanel panel = new JPanel(layout);

        // Adds title
        JLabel title = new JLabel("Developers");
        title.setFont(DevStyle.FORM_TITLE);
        panel.add(title, "north, gapx 8 5");

        // Adds developers list
        JScrollPane scrollPane = new JScrollPane(devs);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        panel.add(scrollPane, "grow");

        // Adds hire developers button
        panel.add(btnHireDev, "south, gapx 10 10, gapy 0 10");

        // Finalizes
        panel.setPreferredSize(new Dimension(235, 600));

        return panel;
    }

    private void loadDevDetails() {
        // Clears panel
        pnDevDetail.removeAll();

        MigLayout devDetailLayout = new MigLayout(
                "fill, ins 10",
                "",
                "");
        pnDevDetail.setLayout(devDetailLayout);

        // Creates developer basic info panel
        JPanel pnTop = makeDevBasicInfo();
        pnDevDetail.add(pnTop, "north, gapx 0 10");

        JPanel pnDevSkill = makeDevSkills();
        JScrollPane scrollPane = new JScrollPane(pnDevSkill);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.lightGray));
        pnDevDetail.add(scrollPane, "span, growx");

        loadProject();
        pnDevDetail.add(pnProj, "span");

        pnDevDetail.add(pnButton, "south");

        pnDevDetail.repaint();
        pnDevDetail.revalidate();
    }

    private JPanel makeDevBasicInfo() {
        // Creates panel
        MigLayout layout = new MigLayout(
                "fill, insets 0 5 5 5",
                "",
                "");
        JPanel panel = new JPanel(layout);

        JLabel label;

        // Adds avatar of current viewing developer
        ImageIcon icon = new ImageIcon(currDev.getAvatar().getImage().
                getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        label = new JLabel(icon);
        panel.add(label, "west");

        // Adds developer's name
        label = ViewUtilities.makeLabel(currDev.getName() + " ",
                currDev.getGender() + "_sign.png",
                JLabel.CENTER, JLabel.LEFT);
        label.setFont(DevStyle.FORM_TITLE);
        label.setHorizontalAlignment(JLabel.LEFT);
        panel.add(label, "span, growx");

        // Adds developer's profession
        label = new JLabel(currDev.getMainSkill().getName() + " developer "
                + "Lv. " + currDev.getMainSkill().getLevel());
        label.setFont(DevStyle.SMALL_BUTTON_FONT);
        panel.add(label, "span, growx, top");

        // Finalizes
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1,
                0, Color.lightGray));

        return panel;
    }

    private JPanel makeDevSkills() {
        // Creates panel
        MigLayout layout = new MigLayout(
                "wrap 8",
                "[][][][]50[][][][]",
                "");
        JPanel panel = new JPanel(layout);

        // Adds current developer's skills
        for (String s : Utilities.getAllSkill()) {
            addSkillsItemPanel(panel, s);
        }

        // Finalizes

        return panel;
    }

    private void addSkillsItemPanel(JPanel panel, final String s) {
        // Initializes required objects        
        Skill skill = currDev.searchSkill(s);
        if (skill == null) {
            skill = SkillFactory.createSkill(s);
        }

        int level = skill.getLevel();

        // Adds skill's name
        JLabel label = new JLabel(s);
        label.setFont(DevStyle.SKILL_NAME);
        panel.add(label, "w 150!");

        // Adds skill's level
        label = new JLabel("Lv." + level);
        label.setFont(DevStyle.SKILL_POINT);
        panel.add(label, "w 35!");

        // Adds up skill cost
        label = new JLabel(" $ " + skill.getUpLevelCost());
        label.setFont(DevStyle.SKILL_POINT);
        label.setBorder(BorderFactory.createMatteBorder(1, 1, 1,
                1, Color.lightGray));
        panel.add(label, "w 70!, growy");

        // Adds up skill button
        JButton button = ViewUtilities.makeSimpleImgButton("up.png", s,
                "Increasing skill " + s + " to the next level cost $ " + skill.
                getUpLevelCost(),
                "Up Level!");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doUpSkill(s);
            }
        });
        panel.add(button);
    }

    private void doUpSkill(String skillName) {
        try {
            model.upSkill(currDev, skillName);
        } catch (MoneyException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.WARNING_MESSAGE);
        } catch (InvalidException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.WARNING_MESSAGE);
        }
        loadDevDetails();
        send(ViewUtilities.MEDIATOR_UP_SKILL_LEVEL);
    }

//    private JPanel make
    private void loadProject() {
        pnProj.removeAll();

        pnProj.add(new JLabel("Project Assign"), "span");
        Project p = currDev.getAssignedProject();
        if (p == null) {
            pnProj.add(new JLabel("No project assigned"));
        } else {
            JPanel pnTop = new JPanel();
            pnTop.setLayout(new BorderLayout());
            pnTop.add(new JLabel(p.getImage()), BorderLayout.WEST);
            pnTop.add(new JLabel(p.getName()));

            pnProj.add(pnTop);
        }

        pnProj.repaint();
        pnProj.revalidate();
    }

    private void loadButtons() {
        pnButton.add(btnSave);
        pnButton.add(btnReset);
    }

    private void loadHiringDev() {
        pnDevDetail.removeAll();

        JPanel pnTop = new JPanel();
        pnTop.add(new JLabel("Developers Available"));

        JScrollPane pnScroll = new JScrollPane(pnCollap.getComponent());
        pnScroll.setPreferredSize(new Dimension(900, 600));
        pnScroll.setBorder(null);

        pnDevDetail.add(pnTop, BorderLayout.NORTH);
        pnDevDetail.add(pnScroll);

        pnDevDetail.repaint();
        pnDevDetail.revalidate();
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
                    for (Developer d : model.getDevelopers().getDeveloperList()) {
                        if (devNames[minIndex].equals(d.getName())) {
                            currDev = d;
                            loadDevDetails();
                            return;
                        }
                    }
                }
            }
        }
    }
}

class ListDevEntry {

    private String title;
    private ImageIcon imageIcon;

    public ListDevEntry(ImageIcon image, String title) {
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

class DevCellRenderer extends JLabel implements ListCellRenderer {

    public DevCellRenderer() {
        setOpaque(true);
        setIconTextGap(10);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        ListDevEntry entry = (ListDevEntry) value;
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
