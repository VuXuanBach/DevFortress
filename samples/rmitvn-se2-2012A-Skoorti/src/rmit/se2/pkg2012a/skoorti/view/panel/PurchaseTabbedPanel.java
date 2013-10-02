/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view.panel;

import rmit.se2.pkg2012a.skoorti.view.util.ImageUtil;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;
import rmit.se2.pkg2012a.skoorti.model.research.Research;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.person.Staff;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.springframework.context.ApplicationContext;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.GameEngine;
import rmit.se2.pkg2012a.skoorti.context.ZooContext;
import rmit.se2.pkg2012a.skoorti.model.*;
import rmit.se2.pkg2012a.skoorti.view.GameCanvas;

/**
 *
 * @author Le Anh Duy
 */
public class PurchaseTabbedPanel extends JTabbedPane implements ChangeListener {

    private JPanel buildingPanel, staffPanel;
    private ResearchListView researchPanel;
    // building panel
    private JButton[] buildingButtons, staffButtons;
    private BufferedImage[] buildingImages, staffImages;
    private String[] buildingTypes, staffTypes;
    private String[] animalTypes;
    private GameEngine gameEngine;

    public PurchaseTabbedPanel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        init();
        initController();
        this.addChangeListener(this);
    }

    private void init() {
        initBuildingImages();
        initBuildingPanel();
        initStaffImages();
        initStaffPanel();
        List<Research> researches = GameFactory.getInstance().getGameStorage().getResearches();
        researchPanel = new ResearchListView(researches);
        addTab("Building", buildingPanel);
        addTab("Staff", staffPanel);
        addTab("Researches", researchPanel);
    }

    private void initBuildingImages() {
        buildingImages = new BufferedImage[GameConstants.BUILDING_CHOICE_NUM];
        try {
            // cage
            loadImage(0, "building", "cageImg.png", 32 * 2, 32 * 2);
            //restaurant
            loadImage(1, "building", "restaurant1.png", 32 * 3, (int) (32 * 2.5));
            // museum
            loadImage(2, "building", "museum1.png", (int) (32 * 5), (int) (32 * 5 / 1.5));
            // mall
            loadImage(3, "building", "mall1.png", 32 * 4, 32 * 4);
            // cage
            loadImage(4, "building", "gym.png", (int) (32 * 2.5), (int) (32 * 1.5));
            // museum
            loadImage(5, "building", "toilet.png", 64, 64);
        } catch (IOException ex) {
        }
    }

    private void initStaffImages() {
        staffImages = new BufferedImage[GameConstants.STAFF_CHOICE_NUM];
        try {
            // clown
            loadImage(0, "staff", "clownImg.png", 32 * 2, 32 * 2);
            // zookeeper
            loadImage(1, "staff", "keeperImg.png", 32 * 2, 32 * 2);
        } catch (IOException ex) {
        }
    }

    private void loadImage(int i, String type, String fileName, int width, int height) throws IOException {
        if (type.equalsIgnoreCase("building")) {
            buildingImages[i] = ImageUtil.getImageFromFile(fileName, width, height);
        }
        if (type.equalsIgnoreCase("staff")) {
            staffImages[i] = ImageUtil.getImageFromFile(fileName, width, height);
        }
    }

    private void initBuildingPanel() {
        buildingTypes = new String[]{
            "cage", "restaurant", "museum", "mall", "gym", "toilet"};

        buildingPanel = new JPanel(new GridLayout(6, 1));
        buildingButtons = new JButton[GameConstants.BUILDING_CHOICE_NUM];

        for (int i = 0; i < buildingButtons.length; i++) {
            buildingButtons[i] = new JButton(new ImageIcon(buildingImages[i]));
            buildingButtons[i].setOpaque(false);
            buildingButtons[i].setContentAreaFilled(false);
            buildingPanel.add(buildingButtons[i]);
        }
    }

    private void initStaffPanel() {
        staffTypes = new String[]{
            "clown","zooKeeper"};

        staffPanel = new JPanel(new GridLayout(GameConstants.STAFF_CHOICE_NUM, 1));
        staffButtons = new JButton[GameConstants.STAFF_CHOICE_NUM];

        for (int i = 0; i < staffButtons.length; i++) {
            staffButtons[i] = new JButton(new ImageIcon(staffImages[i]));
            staffButtons[i].setOpaque(false);
            staffButtons[i].setContentAreaFilled(false);
            staffPanel.add(staffButtons[i]);
        }
    }

    private void initController() {
        for (int i = 0; i < GameConstants.BUILDING_CHOICE_NUM; i++) {
            buildingButtons[i].addMouseListener(new BuildingClick(i));
        }
        for (int i = 0; i < GameConstants.STAFF_CHOICE_NUM; i++) {
            staffButtons[i].addMouseListener(new StaffClick(i));
        }
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        switch (this.getSelectedIndex()) {
            case 2:
                List<Research> researches = GameFactory.getInstance().getGameStorage().getResearches();
                researchPanel.setResearchList(researches);
                researchPanel.revalidate();
                researchPanel.repaint();
                this.revalidate();
                this.repaint();
                break;
            default:
                break;
        }
    }

    private class BuildingClick extends MouseAdapter {

        private int i;

        public BuildingClick(int i) {
            this.i = i;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            ApplicationContext ctx = ZooContext.getAppContext();
            Building b = ctx.getBean(buildingTypes[i], Building.class);
            //Building b = GameFactory.getInstance().createBuilding(buildingTypes[i]);
            if (!gameEngine.enoughMoney(b.getCost())) {
                Mediator.publish("game:notice", "You dont have enough money to build this" + buildingTypes[i]);
                return;
            }
            // TODO: we should charge the users whenever they want to build new building
            Mediator.publish("game:cost", -b.getCost());
            GameCanvas.currentSelectedBuilding = b;
        }
    }
    
    private class StaffClick extends MouseAdapter {

        private int i;

        public StaffClick(int i) {
            this.i = i;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            Staff s = GameFactory.getInstance().createStaff(staffTypes[i]);
            if (!gameEngine.enoughMoney(s.getCost())) {
                Mediator.publish("game:notice", "You dont have enough money to hire this" + staffTypes[i]);
                return;
            }
            Mediator.publish("game:cost", -s.getCost());  
            Mediator.publish("person:new", s);
        }
    }


}
