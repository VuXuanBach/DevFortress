/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view.panel;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import rmit.se2.pkg2012a.skoorti.GameEngine;
import rmit.se2.pkg2012a.skoorti.GameLoad;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;

/**
 *
 * @author Le Anh Duy
 */
public class SettingsPanel extends JPanel {

    private GameEngine gameEngine;
    private AboutFrame aboutFrame;

    public SettingsPanel(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        initComponents();
    }

    private void initComponents() {
        JPanel commandPanel = new JPanel(new GridLayout(4, 1));
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new SaveListener());
        JButton btnHelp = new JButton("Help");
        btnHelp.addActionListener(new HelpListener());
        JButton btnAbout = new JButton("About");
        btnAbout.addActionListener(new AboutListener());
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ExitListener());


        commandPanel.add(btnSave);
        commandPanel.add(btnHelp);
        commandPanel.add(btnAbout);
        commandPanel.add(btnExit);
        add(commandPanel);
    }

    private class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gameEngine.stopGame();
            JFileChooser fileChooser = new JFileChooser("save/");
            int showSaveDialog = fileChooser.showSaveDialog(null);
            if (showSaveDialog == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    String fileName = file.getPath();
                    save(fileName);
                } catch (IOException ex) {
                    Logger.getLogger(GameLoad.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "Saved successfully!");
            }
            gameEngine.continueGame();
        }

        private void save(String fileName) throws IOException {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(GameMemoryStorage.getInstance());
            out.close();
        }
    }

    private class HelpListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //help access
                String htmlFilePath = "UserManual/manual.html"; // path to your new file
                File htmlFile = new File(htmlFilePath);
                // open the default web browser for the HTML page
                //Desktop.getDesktop().browse(htmlFile.toURI());
                // if a web browser is the default HTML handler, this might work too
                Desktop.getDesktop().open(htmlFile);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AboutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(aboutFrame == null){
                aboutFrame = new AboutFrame();
            }
            aboutFrame.setVisible(true);
        }
    }

    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(1);
        }
    }
}
