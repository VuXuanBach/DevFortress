/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.thread;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmit.se2.pkg2012a.skoorti.GameEngine;
import rmit.se2.pkg2012a.skoorti.model.GameFactory;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.research.Research;
import rmit.se2.pkg2012a.skoorti.model.storage.Storage;

/**
 *
 * @author zozo
 */
public class UpdateGameThread extends GameThread implements Serializable{
    
    private Storage gs;
    private GameEngine ge;
    
    public UpdateGameThread(GameEngine ge) {
        this.ge = ge;
        gs = GameFactory.getInstance().getGameStorage();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(10000);
                // update researches
                List<Research> researches = gs.getResearches();
                if (researches != null) {
                    for (Research r : researches) {
                        if (r.isActive() && !r.isPause()) {
                            r.increaseCurrent();
                        }

                        if (r.isCompleted()) {
                            r.complete();
                        }
                    }
                }
                
                // update animals
                List<Animal> animals = gs.getAnimalList();
                if (animals != null) {
                    for (Animal animal : animals) {
                        animal.updateStatus();
                    }
                }
                
                // decrease risk based on map
                int r = ge.getCurrentRisk() - 5;
                if (r <= 0) {
                    r = 0;
                }
                ge.setCurrentRisk(r);
            } catch (InterruptedException ex) {
                Logger.getLogger(UpdateGameThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
