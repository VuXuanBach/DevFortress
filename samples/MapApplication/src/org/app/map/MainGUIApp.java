package org.app.map;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.app.map.zooitem.ZooTile;
import org.app.map.zooitem.animal.Animal;
import org.app.visitor.CasualVisitor;
import org.app.visitor.Visitor;
import org.map.GUIModel.ItemListCellRenderer;

/**
 *
 * @author dell
 */
public class MainGUIApp implements ListSelectionListener, Observer {

    private class ClickListener implements ActionListener {

        MainGUIApp mainGUI;

        public ClickListener(MainGUIApp mainGUI) {
            this.mainGUI = mainGUI;
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnFeed) {
                mainGUI.getAnimalOnClick().feed();
                mainGUI.money -= 100;
                mainGUI.value.setText("$" + money);
            } else if (e.getSource() == btnUpgrade) {
                mainGUI.getAnimalOnClick().upgrade();
                mainGUI.money -= (mainGUI.getAnimalOnClick()).upgradeCost();
                mainGUI.value.setText("$" + money);
            }
        }
    }
    private ZooMap map;
    private JList itemList;
    private JPanel rightPanel;
    private JPanel textPanel;
    private JLabel moneyLabel;
    private int money;
    private JLabel value;
    private JScrollPane itemScrollPane;
    private JScrollPane canvasScrollPane;
    private JSplitPane splitPane;
    private JButton btnFeed;
    private JButton btnUpgrade;
    private JPanel btnPanel;
    private Animal animalOnClick;

    public MainGUIApp() {

        /**
         * This piece of code is a workaround to fix the setMaximumSize bug in
         * Java now we can use setMaximumSize as it has no bug some flickering
         * is encountered but not too bad
         */
        JFrame frame = new JFrame("Zoo Tycoon") {

            @Override
            public void paint(Graphics g) {
                Dimension d = getSize();
                Dimension m = getMaximumSize();
                boolean resize = d.width > m.width || d.height > m.height;
                d.width = Math.min(m.width, d.width);
                d.height = Math.min(m.height, d.height);
                if (resize) {
                    Point p = getLocation();
                    setVisible(false);
                    setSize(d);
                    setLocation(p);
                    setVisible(true);
                }
                super.paint(g);
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(2000, 2000));
        frame.setMaximumSize(new Dimension(ZooMap.MAP_WIDTH * ZooTile.TILE_SIZE, ZooMap.MAP_HEIGHT * ZooTile.TILE_SIZE));
        //frame.setResizable(false);
        initItemList();

        map = new ZooMap();
        money = 10000;
        map.addObserver(this);
        canvasScrollPane = new JScrollPane(map);
        rightPanel = new JPanel(new BorderLayout());
        textPanel = new JPanel(new FlowLayout());
        value = new JLabel();
        value.setHorizontalAlignment(SwingConstants.CENTER);
        value.setText("$" + money);
        moneyLabel = new JLabel();
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moneyLabel.setText("Money: ");
        textPanel.add(moneyLabel);
        textPanel.add(value);
        rightPanel.add(textPanel, BorderLayout.NORTH);

        rightPanel.add(itemScrollPane, BorderLayout.CENTER);


        btnFeed = new JButton("Feed");
        btnUpgrade = new JButton("Upgrade");
        btnFeed.setEnabled(false);
        btnUpgrade.setEnabled(false);
        btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnFeed);
        btnPanel.add(btnUpgrade);
        rightPanel.add(btnPanel, BorderLayout.SOUTH);
        animalOnClick = null;

        ActionListener cl = new ClickListener(this);
        btnFeed.addActionListener(cl);
        btnUpgrade.addActionListener(cl);

        canvasScrollPane.setSize(new Dimension(200, 200));
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvasScrollPane, rightPanel);
        splitPane.setOneTouchExpandable(true);
        //when split pane change size the canvas get all the extra spaces
        splitPane.setResizeWeight(1);
        frame.add(splitPane);
        frame.setVisible(true);

        //frame.pack();
        //map.gameLoop();
    }

    /**
     * initialize a JList contains the building image and a short description on
     * each cell using ItemListCellRenderer the list is then added to a
     * JScrollPane
     */
    private void initItemList() {

        // construct the menuList as a JList
        itemList = new JList();
        itemList.setCellRenderer(new ItemListCellRenderer());

        // get our images
        URL url = getClass().getClassLoader().getResource("sprites/house.png");
        URL url1 = getClass().getClassLoader().getResource("sprites/house1.png");
        URL url2 = getClass().getClassLoader().getResource("sprites/ZooCageLandIcon.png");
        URL url3 = getClass().getClassLoader().getResource("sprites/ZooCageWaterIcon.png");
        URL url13 = getClass().getClassLoader().getResource("sprites/ZooCageAirIcon.png");
        URL url4 = getClass().getClassLoader().getResource("sprites/AnimalFoot01_1.png");
        URL url5 = getClass().getClassLoader().getResource("sprites/AnimalFoot01_2.png");
        URL url6 = getClass().getClassLoader().getResource("sprites/AnimalFoot01_3.png");
        URL url7 = getClass().getClassLoader().getResource("sprites/AnimalSwim01_1.png");
        URL url8 = getClass().getClassLoader().getResource("sprites/AnimalSwim01_2.png");
        URL url9 = getClass().getClassLoader().getResource("sprites/AnimalSwim01_3.png");
        URL url10 = getClass().getClassLoader().getResource("sprites/AnimalFly01_1.png");
        URL url11 = getClass().getClassLoader().getResource("sprites/AnimalFly01_2.png");
        URL url12 = getClass().getClassLoader().getResource("sprites/AnimalFly01_3.png");
        Image image = null;
        Image image1 = null;
        Image landCageImage = null, waterCageImage = null, airCageImage = null;
        Image walkAnimal11Image = null, walkAnimal12Image = null, walkAnimal13Image = null;
        Image swimAnimal11Image = null, swimAnimal12Image = null, swimAnimal13Image = null;
        Image flyAnimal11Image = null, flyAnimal12Image = null, flyAnimal13Image = null;
        try {
            image = ImageIO.read(url);
            image1 = ImageIO.read(url1);
            landCageImage = ImageIO.read(url2);
            waterCageImage = ImageIO.read(url3);
            airCageImage = ImageIO.read(url13);
            walkAnimal11Image = ImageIO.read(url4);
            walkAnimal12Image = ImageIO.read(url5);
            walkAnimal13Image = ImageIO.read(url6);
            swimAnimal11Image = ImageIO.read(url7);
            swimAnimal12Image = ImageIO.read(url8);
            swimAnimal13Image = ImageIO.read(url9);
            flyAnimal11Image = ImageIO.read(url10);
            flyAnimal12Image = ImageIO.read(url11);
            flyAnimal13Image = ImageIO.read(url12);
        } catch (IOException ex) {
            Logger.getLogger(MainGUIApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        Icon houseImage = new ImageIcon(image);
        Icon house1Image = new ImageIcon(image1);
        //Icon netstatImage = new ImageIcon(getClass().getClassLoader().getResource("sprite/gate.png"));
        Icon landCageIcon = new ImageIcon(landCageImage);
        Icon waterCageIcon = new ImageIcon(waterCageImage);
        Icon airCageIcon = new ImageIcon(airCageImage);
        Icon walkAnimal11Icon = new ImageIcon(walkAnimal11Image);
        Icon walkAnimal12Icon = new ImageIcon(walkAnimal12Image);
        Icon walkAnimal13Icon = new ImageIcon(walkAnimal13Image);
        Icon swimAnimal11Icon = new ImageIcon(swimAnimal11Image);
        Icon swimAnimal12Icon = new ImageIcon(swimAnimal12Image);
        Icon swimAnimal13Icon = new ImageIcon(swimAnimal13Image);
        Icon flyAnimal11Icon = new ImageIcon(flyAnimal11Image);
        Icon flyAnimal12Icon = new ImageIcon(flyAnimal12Image);
        Icon flyAnimal13Icon = new ImageIcon(flyAnimal13Image);

        // add the images to jlabels with text
        JLabel houseLabel = new JLabel("Basic House", houseImage, JLabel.LEFT);
        JLabel advancedHouseLabel = new JLabel("Advanced House", house1Image, JLabel.LEFT);
        //JLabel netstatLabel = new JLabel("Netstat", netstatImage, JLabel.LEFT);

        // create the corresponding panels
        JPanel housePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel advancedHousePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel zooCageLandLabel = new JLabel("Land Cage", landCageIcon, JLabel.LEFT);
        JLabel zooCageWaterLabel = new JLabel("Water Cage", waterCageIcon, JLabel.LEFT);
        JLabel zooCageAirLabel = new JLabel("Air Cage", airCageIcon, JLabel.LEFT);
        JLabel walkAnimal11Label = new JLabel("Walkable1_L1", walkAnimal11Icon, JLabel.LEFT);
        JLabel walkAnimal12Label = new JLabel("Walkable1_L2", walkAnimal12Icon, JLabel.LEFT);
        JLabel walkAnimal13Label = new JLabel("Walkable1_L3", walkAnimal13Icon, JLabel.LEFT);
        JLabel swimAnimal11Label = new JLabel("Swimable1_L1", swimAnimal11Icon, JLabel.LEFT);
        JLabel swimAnimal12Label = new JLabel("Swimable1_L2", swimAnimal12Icon, JLabel.LEFT);
        JLabel swimAnimal13Label = new JLabel("Swimable1_L3", swimAnimal13Icon, JLabel.LEFT);
        JLabel flyAnimal11Label = new JLabel("Flyable1_L1", flyAnimal11Icon, JLabel.LEFT);
        JLabel flyAnimal12Label = new JLabel("Flyable1_L2", flyAnimal12Icon, JLabel.LEFT);
        JLabel flyAnimal13Label = new JLabel("Flyable1_L3", flyAnimal13Icon, JLabel.LEFT);
        //JPanel netstatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // add the labels onto the panels
        housePanel.add(houseLabel);
        advancedHousePanel.add(advancedHouseLabel);
        //netstatPanel.add(netstatLabel);
        JPanel landCagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        landCagePanel.add(zooCageLandLabel);
        JPanel waterCagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        waterCagePanel.add(zooCageWaterLabel);
        JPanel airCagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        airCagePanel.add(zooCageAirLabel);
        JPanel walkAnimal11Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        walkAnimal11Panel.add(walkAnimal11Label);
        JPanel walkAnimal12Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        walkAnimal12Panel.add(walkAnimal12Label);
        JPanel walkAnimal13Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        walkAnimal13Panel.add(walkAnimal13Label);
        JPanel swimAnimal11Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        swimAnimal11Panel.add(swimAnimal11Label);
        JPanel swimAnimal12Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        swimAnimal12Panel.add(swimAnimal12Label);
        JPanel swimAnimal13Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        swimAnimal13Panel.add(swimAnimal13Label);
        JPanel flyAnimal11Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flyAnimal11Panel.add(flyAnimal11Label);
        JPanel flyAnimal12Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flyAnimal12Panel.add(flyAnimal12Label);
        JPanel flyAnimal13Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flyAnimal13Panel.add(flyAnimal13Label);

        // create a panel array
        Object[] panels = {housePanel, advancedHousePanel, landCagePanel, waterCagePanel, airCagePanel, walkAnimal11Panel, walkAnimal12Panel, walkAnimal13Panel, swimAnimal11Panel, swimAnimal12Panel, swimAnimal13Panel, flyAnimal11Panel, flyAnimal12Panel, flyAnimal13Panel};

        // tell the jlist to use the panel array for its data
        itemList.setListData(panels);

        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setLayoutOrientation(JList.VERTICAL);
        itemList.setFixedCellHeight(46);

        // put our JList in a JScrollPane
        itemScrollPane = new JScrollPane(itemList);
        itemScrollPane.setMinimumSize(new Dimension(150, 50));

        itemList.addListSelectionListener(this);
    }

    public static void main(String[] args) {
        new MainGUIApp();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList source = (JList) e.getSource();
        map.setSelectedItem(source.getSelectedIndex());
    }

    @Override
    public void update(ZooBuildItem item) {
        if (item != null) {
            //System.out.println("vo");
            int cost = item.getCost();
            if (money - cost < 0) {
                JOptionPane.showMessageDialog(null, "GAME OVER");
                map.stopGame();
            } else {
                money -= cost;
                value.setText("$" + money);
            }
        }
    }

    @Override
    public void update(Animal a, boolean status) {
        if (this != null) {
            this.setBtnFeedStatus(status);
            this.setBtnUpgradeStatus(status);
            animalOnClick = a;
        }
    }

    public Animal getAnimalOnClick() {
        return animalOnClick;
    }

    @Override
    public void update(Visitor visitor) {
        ArrayList visitors = map.getTotalVisitors();
        for (int i = 0; i < visitors.size(); i++) {
            if (visitors.get(i) instanceof CasualVisitor) {
                money += 10;
            } else {
                money += 20;
            }

        }

        value.setText("$" + money);

    }

    public void setBtnFeedStatus(boolean status) {
        this.btnFeed.setEnabled(status);
    }

    public void setBtnUpgradeStatus(boolean status) {
        this.btnUpgrade.setEnabled(status);
    }
}
