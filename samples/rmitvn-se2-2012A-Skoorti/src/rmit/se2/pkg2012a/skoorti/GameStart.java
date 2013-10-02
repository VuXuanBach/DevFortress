/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti;

import rmit.se2.pkg2012a.skoorti.view.panel.BackgroundPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import rmit.se2.pkg2012a.skoorti.model.GameMap;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.person.Person;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;
import rmit.se2.pkg2012a.skoorti.model.storage.Storage;
import rmit.se2.pkg2012a.skoorti.view.GameCanvas;
import rmit.se2.pkg2012a.skoorti.view.GameMapView;

/**
 *
 * @author Le Anh Duy
 */
public class GameStart extends JFrame {

    private int selectedMap;
    private int selectedLevel;
    private static GameStart menu;
    private JToggleButton btnLevel;
    private JButton btnStart;
    private JButton btnLoad;
    private JComboBox cbMap;
    static JWindow loadingScreen;

    public GameStart() throws HeadlessException {
        initComponents();
    }

    private void initComponents() {
        BackgroundPanel ss = new BackgroundPanel(new BorderLayout(), "menuLogo.png", 1024, 150);
        JPanel commandPanel = new JPanel(new GridLayout(1, 4));
        cbMap = new javax.swing.JComboBox();
        btnLevel = new javax.swing.JToggleButton();
        btnLoad = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbMap.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Los Angeles", "Singapore", "Sahara"}));
        cbMap.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMapActionPerformed(evt);
            }
        });

        btnLevel.setText("Easy");
        btnLevel.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLevelActionPerformed(evt);
            }
        });

        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        btnStart.setText("Start game");
        btnStart.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        commandPanel.add(cbMap);
        commandPanel.add(btnLevel);
        commandPanel.add(btnStart);
        commandPanel.add(btnLoad);
        ss.add(commandPanel, BorderLayout.SOUTH);

        add(ss);
        setResizable(false);
        setSize(1024, 205);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void btnLevelActionPerformed(java.awt.event.ActionEvent evt) {
        JToggleButton bt = (JToggleButton) evt.getSource();
        if (bt.getText().equals("Easy")) {
            bt.setText("Hard");
            selectedLevel = GameConstants.HARD;
        } else {
            bt.setText("Easy");
            selectedLevel = GameConstants.EASY;
        }
    }

    private void cbMapActionPerformed(java.awt.event.ActionEvent evt) {
        selectedMap = ((JComboBox) evt.getSource()).getSelectedIndex();
    }

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser("save/");
        int showOpenDialog = fileChooser.showOpenDialog(null);
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getPath();
            try {
                loadFile(fileName);
            } catch (IOException ex) {
                Logger.getLogger(GameLoad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {
        GameMap map = GameMap.getInstance("src/maps/" + GameConstants.MAP_FILENAME[selectedMap] + ".txt");
        GameMapView mapView = new GameMapView(map);
        GameCanvas gameCanvas = new GameCanvas(mapView);
        GameEngine gameEngine = new GameEngine(gameCanvas, selectedLevel, selectedMap);
        loadGame(gameEngine, gameCanvas);
    }

    private void loadFile(String fileName) throws FileNotFoundException, IOException {
        FileInputStream fis;
        ObjectInputStream in;
        fis = new FileInputStream(fileName);
        in = new ObjectInputStream(fis);
        Storage storage = GameMemoryStorage.getInstance();
        try {
            storage = ((GameMemoryStorage) storage).createNewInstance((GameMemoryStorage) in.readObject());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameLoad.class.getName()).log(Level.SEVERE, null, ex);
        }
        in.close();

        GameLoad.loading = true;
        GameMap map = GameMap.getInstance("src/maps/" + GameConstants.MAP_FILENAME[storage.getPlayer().getMapId()] + ".txt");
        GameMapView mapView = new GameMapView(map);
        GameCanvas gameCanvas = new GameCanvas(mapView);
        GameEngine gameEngine = new GameEngine(gameCanvas, storage.getPlayer().getLevel(), storage.getPlayer().getMapId());

        List<Person> personList = storage.getPersonList();
        for (Person person : personList) {
            gameCanvas.initPerson(person);
        }

        List<Building> buildingList = storage.getBuildingList();
        for (Building building : buildingList) {
            gameCanvas.initBuilding(building);
        }

        List<Animal> animalList = storage.getAnimalList();
        for (Animal animal : animalList) {
            gameCanvas.initAnimal(animal.getCage(), animal);
        }

        loadGame(gameEngine, gameCanvas);
    }

    private void loadGame(final GameEngine gameEngine, final GameCanvas gameCanvas) {
        menu.setVisible(false);

        String logoName = (selectedMap == 0) ? "mapLoad1.png" : ((selectedMap == 1) ? "mapLoad2.png" : "mapLoad3.png");

        BackgroundPanel ss = new BackgroundPanel(new BorderLayout(), logoName);
        ss.add(new JLabel("Loading " + cbMap.getSelectedItem().toString() + " Map...", SwingConstants.CENTER), BorderLayout.SOUTH);
        loadingScreen = new JWindow();
        loadingScreen.setSize(737, 200);
        loadingScreen.setLocationRelativeTo(null);
        loadingScreen.add(ss);
        loadingScreen.setVisible(true);

        new Thread(new Runnable() {

            @Override
            public void run() {
                new GameLoad(gameEngine, gameCanvas);
            }
        }).start();
    }

    public static void main(String args[]) {
        BackgroundPanel ss = new BackgroundPanel(new BorderLayout(), "splashLogo.png");
        ss.add(new JLabel("Loading Game...", SwingConstants.CENTER), BorderLayout.SOUTH);
        JWindow window = new JWindow();
        window.setSize(737, 200);
        window.setLocationRelativeTo(null);
        window.add(ss);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                menu = new GameStart();
            }
        });
    }
}
