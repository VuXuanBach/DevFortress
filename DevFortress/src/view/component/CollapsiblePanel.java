package view.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Developer;
import model.Project;
import model.ProjectRequirement;
import model.exception.InvalidException;
import model.facade.DevModel;
import model.skill.Skill;
import net.miginfocom.swing.MigLayout;
import view.lnf.DevStyle;
import view.lnf.SegoeFont;
import view.mediator.Colleague;
import view.mediator.Mediator;
import view.util.ViewUtilities;
import org.jdesktop.swingx.JXPanel;

public class CollapsiblePanel extends MouseAdapter implements Colleague {

    // Constants
    private static final Font SKILL_POINT = SegoeFont.
            createSegoeUIFont("normal",
            15.0f);
    // Model
    private DevModel model;
    private Mediator mediator;
    private int type;
    // Components
    private ActionPanel[] aps;
    private JPanel[] panels;
    private ArrayList<Project> availProjects;
    private ArrayList<Developer> availDevs;

    public CollapsiblePanel(DevModel model, Mediator mediator, int type) {
        this.model = model;
        this.mediator = mediator;
        this.type = type;
        this.availProjects = model.getAvailableProjects();
        this.availDevs = model.getAvailableDevelopers();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ActionPanel ap = (ActionPanel) e.getSource();
        if (ap.target.contains(e.getPoint())) {
            ap.toggleSelection();
            togglePanelVisibility(ap);
        }
    }

    private void togglePanelVisibility(ActionPanel ap) {
        int index = getPanelIndex(ap);
        if (panels[index].isShowing()) {
            panels[index].setVisible(false);
        } else {
            panels[index].setVisible(true);
        }
        ap.getParent().validate();
    }

    private int getPanelIndex(ActionPanel ap) {
        for (int j = 0; j < aps.length; j++) {
            if (ap == aps[j]) {
                return j;
            }
        }
        return -1;
    }

    private void assembleActionProjectPanels() {
        aps = new ActionPanel[availProjects.size()];
        for (int j = 0; j < aps.length; j++) {
             //ImageIcon icon = new ImageIcon(availProjects.get(j).getImage().getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            aps[j] = new ActionPanel(null, availProjects.get(j).getName(),
                    availProjects.get(j).getTotalPoint() + "FP/"
                    + availProjects.get(j).getWeekLength() + "W",
                    "$" + ViewUtilities.formatMoney(availProjects.get(j).getPayment()), this);
        }
    }

    private void assembleProjectPanels() {
        JPanel[] pnLists = new JPanel[aps.length];
        for (int i = 0; i < aps.length; i++) {
            pnLists[i] = makeProjectPanel(i);
        }
        panels = pnLists;
    }

    private JPanel makeProjectPanel(final int index) {
        // Creates panel
        MigLayout layout = new MigLayout(
                "wrap 4, ins 10", // layout constraints
                "[grow, fill, left][grow, fill, right]20[grow, fill, left][grow, fill, right]", // column constraints
                "");// row constraints
        JPanel panel = new JPanel(layout);

        // Prepares components
        JLabel label;

        // Gets required information
        ArrayList<ProjectRequirement> requirements = availProjects.get(index).
                getRequirements();

        for (ProjectRequirement pr : requirements) {
            // Adds requirement's name
            label = new JLabel(pr.getRequiredSkill().getName());
            label.setFont(DevStyle.SKILL_NAME);
            panel.add(label, "w 160!");

            // Adds requirement's func-point
            label = new JLabel(pr.getPoint() + " FP");
            label.setFont(DevStyle.SKILL_POINT);
            label.setHorizontalAlignment(JLabel.TRAILING);
            panel.add(label, "growx");
        }

        // Adds accept button
        JTKButton btnAccept = new JTKButton(
                "<html><center>Sign the<br/>Contract!</center></html>");
        btnAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                acceptProject(index);
            }
        });
        panel.add(btnAccept, "east, h 45:45:45, gapright 10");

        // Finalizes panel
        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 1,
                1, Color.lightGray));

        return panel;
    }

    private void acceptProject(int index) {
        try {
            model.acceptProject(availProjects.get(index).getName());
        } catch (InvalidException ex) {
            
        }
        send(ViewUtilities.MEDIATOR_ACCEPT_PROJECT);
    }

    private void assembleActionDevPanels() {
        aps = new ActionPanel[availDevs.size()];
        for (int j = 0; j < aps.length; j++) {
            ImageIcon icon = new ImageIcon(availDevs.get(j).getAvatar().getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            aps[j] = new ActionPanel(icon, availDevs.get(j).getName(),
                    availDevs.get(j).getMainSkill().getName() + " developer     lvl."
                    + availDevs.get(j).getMainSkill().getLevel(),
                    "$" + ViewUtilities.formatMoney(availDevs.get(j).getHireSalary()), this);
        }
    }

    private void assembleDevPanels() {
        JPanel[] pnLists = new JPanel[aps.length];
        for (int i = 0; i < aps.length; i++) {
            pnLists[i] = makeDevPanel(i);
        }
        panels = pnLists;
    }

    private JPanel makeDevPanel(final int index) {
        // Creates panel
        MigLayout layout = new MigLayout(
                "wrap 6, ins 10", // layout constraints
                "[grow, fill, left][grow, fill, right][grow, fill, left][grow, fill, right][grow, fill, left][grow, fill, right]", // column constraints
                "");// row constraints
        JPanel panel = new JPanel(layout);

        // Prepares components
        JLabel label;

        // Gets required information
        ArrayList<Skill> skills = availDevs.get(index).getSkills();

        for (Skill skill : skills) {
            // Adds skill's name
            label = new JLabel(skill.getName());
            label.setFont(DevStyle.SKILL_NAME);
            panel.add(label, "w 140!");

            // Adds skill's current level
            label = new JLabel("Lv." + skill.getLevel());
            label.setFont(DevStyle.SKILL_POINT);
            panel.add(label, "growx");
        }

        JTKButton btnHire = new JTKButton("Hire me!");
        btnHire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doHireDev(index);
            }
        });
        panel.add(btnHire, "east, h 45:45:45, gapright 10");

        // Finalizes panel
        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 1,
                1, Color.lightGray));

        return panel;
    }

    private void doHireDev(int index) {
        try {
            model.hireDeveloper(availDevs.get(index).getName());
            send(ViewUtilities.MEDIATOR_HIRE_DEV);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void addComponents(Component c1, Component c2, Container c,
            GridBagConstraints gbc) {
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        c.add(c1, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        c.add(c2, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
    }

    public JPanel getComponent() {
        if (type == 0) {
            assembleActionProjectPanels();
            assembleProjectPanels();
        } else if (type == 1) {
            assembleActionDevPanels();
            assembleDevPanels();
        }
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 3, 0, 3);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        for (int j = 0; j < aps.length; j++) {
            panel.add(aps[j], gbc);
            panel.add(panels[j], gbc);
            panels[j].setVisible(false);
        }
        JLabel padding = new JLabel();
        gbc.weighty = 1.0;
        panel.add(padding, gbc);
        return panel;
    }

    @Override
    public void send(String message) {
        mediator.send(message, this);
    }

    @Override
    public void receive(String message) {
    }
}

class ActionPanel extends JPanel {

    String text, time, money;
    Font font;
    private boolean selected;
    BufferedImage open, closed;
    Rectangle target;
    final int OFFSET = 30,
            PAD = 5;
    ImageIcon ii;

    public ActionPanel(ImageIcon ii, String text, String time, String money, MouseListener ml) {
        this.ii = ii;
        this.text = text;
        this.time = time;
        this.money = money;
        addMouseListener(ml);
        font = new Font("sans-serif", Font.PLAIN, 12);
        selected = false;
        setBackground(new Color(200, 200, 220));
        setPreferredSize(new Dimension(200, 20));
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
        setPreferredSize(new Dimension(200, 20));
        createImages();
        setRequestFocusEnabled(true);
    }

    public void toggleSelection() {
        selected = !selected;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        if (selected) {
            g2.drawImage(open, PAD, 0, this);
        } else {
            g2.drawImage(closed, PAD, 0, this);
        }

        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics(text, frc);
        float height = lm.getAscent() + lm.getDescent();
        float x = OFFSET;
        float y = (h + height) / 2 - lm.getDescent();

        if (ii != null) {
            g2.drawImage(ii.getImage(), (int) x, (int) y - 16, 32, 32, null);
            g2.drawString(text, x + 32, y);
        } else {
            g2.drawString(text, x, y);
        }

        lm = font.getLineMetrics(time, frc);
        height = lm.getAscent() + lm.getDescent();
        x = 300;
        y = (h + height) / 2 - lm.getDescent();
        g2.drawString(time, x, y);

        x = 600;
        g2.drawString(money, x, y);
    }

    private void createImages() {
        int w = 20;
        int h = getPreferredSize().height;
        target = new Rectangle(2, 0, 20, 18);
        open = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = open.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, w, h);
        int[] x = {2, w / 2, 18};
        int[] y = {4, 15, 4};
        Polygon p = new Polygon(x, y, 3);
        g2.setPaint(Color.green.brighter());
        g2.fill(p);
        g2.setPaint(Color.blue.brighter());
        g2.draw(p);
        g2.dispose();
        closed = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        g2 = closed.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, w, h);
        x = new int[]{3, 13, 3};
        y = new int[]{4, h / 2, 16};
        p = new Polygon(x, y, 3);
        g2.setPaint(Color.red);
        g2.fill(p);
        g2.setPaint(Color.blue.brighter());
        g2.draw(p);
        g2.dispose();
    }
}
