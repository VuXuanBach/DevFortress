/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.animal.Food;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;
import rmit.se2.pkg2012a.skoorti.model.storage.Storage;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;
import rmit.se2.pkg2012a.skoorti.view.dialog.AnimalDialog;
import rmit.se2.pkg2012a.skoorti.view.dialog.FeedAnimalDialog;
import rmit.se2.pkg2012a.skoorti.view.util.ImageUtil;

/**
 *
 * @author zozo
 */
public class DialogFactory implements Serializable{

    private static DialogFactory instance;
    private AnimalDialog animalDialog;
    private Storage storage;
    private Component mainView;
    private Map<String, FeedAnimalDialog> feedAnimalDialogs;
    
    public void closeAllDialog() {
        for(FeedAnimalDialog fd : feedAnimalDialogs.values()) {
            fd.setVisible(false);
        }
        animalDialog.setVisible(false);
    }

    private DialogFactory() {
        mainView = null; // this should be the main jframe
        storage = GameMemoryStorage.getInstance();
        feedAnimalDialogs = new HashMap<String, FeedAnimalDialog>();
    }

    public static DialogFactory getInstance() {
        if (instance == null) {
            instance = new DialogFactory();
        }
        return instance;
    }

    public void displayNotice(String msg) {
        JOptionPane.showMessageDialog(mainView, msg);
    }

    private FeedAnimalDialog createFeedAnimalDialog(Animal animal) throws IOException {
        if (feedAnimalDialogs.containsKey(animal.getName())) {
            FeedAnimalDialog fad = feedAnimalDialogs.get(animal.getName());
            fad.renderAnimalDetail(animal);
            return null;
        }
        final FeedAnimalDialog feedAnimalDialog;
        feedAnimalDialog = new FeedAnimalDialog(new javax.swing.JFrame(), false);
        feedAnimalDialog.setAnimal(animal);
        List<Food> foods = storage.getFoodList();
        for (Food f : foods) {
            final Food finalFood = f;
            String fileName = f.getImageName();
            BufferedImage food = ImageUtil.getImageFromFile(fileName, 64, 64);
            JButton btn = new JButton();
            btn.setIcon(new ImageIcon(food));
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    feedAnimalDialog.setCurrentFood(finalFood);
                }
            });
            feedAnimalDialog.getFoodPanel().add(btn);
        }
        feedAnimalDialog.setTitle("Please choose the food to feed the animal");
        feedAnimalDialog.getFoodPanel().setPreferredSize(new Dimension(480, 360));
        feedAnimalDialog.setPreferredSize(new Dimension(600, 490));
        feedAnimalDialogs.put(animal.getName(), feedAnimalDialog);
        
        Mediator.subscribe("feedAnimalDialog:destroy", new Observer(){

            @Override
            public void update(Observable o, Object o1) {
                String animalName = o1.toString();
                feedAnimalDialogs.remove(animalName);
            }
            
        });
        
        return feedAnimalDialog;
    }

    public FeedAnimalDialog feedAnimalDialog(Animal animal) throws IOException {
        final FeedAnimalDialog feedAnimalDialog = createFeedAnimalDialog(animal);
        if (feedAnimalDialog != null) {
            feedAnimalDialog.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    feedAnimalDialog.dispose();
                    feedAnimalDialogs.remove(feedAnimalDialog.getAnimal().getName());
                }
            });
            feedAnimalDialog.pack();
            feedAnimalDialog.setVisible(true);
            feedAnimalDialog.setLocationRelativeTo(null);
        }
        
        return feedAnimalDialog;
    }

    public AnimalDialog animalDialog() throws IOException {
        createAnimalDialog();
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
//                animalDialog.addWindowListener(new java.awt.event.WindowAdapter() {
//
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.out.println("OK");
//                    }
//                });
                animalDialog.pack();
                animalDialog.setVisible(true);
            }
        });
        
        return animalDialog;
    }

    private void createAnimalDialog() throws IOException {
        animalDialog = new AnimalDialog(new javax.swing.JFrame(), true);
        List<Animal> animals = storage.getInitialAnimals();
        animalDialog.setAnimals(animals);
    }
}
