/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.thread;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmit.se2.pkg2012a.skoorti.model.GameFactory;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;
import rmit.se2.pkg2012a.skoorti.model.person.Visitor;

/**
 *
 * @author zozo
 */
public class NewVisitorThread extends GameThread implements Serializable {

    private int interval;
    
    public NewVisitorThread(int interval) {
        this.interval = interval*1000;
    }
    
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(this.interval);
                Visitor v = GameFactory.getInstance().createVisitor();
                Mediator.publish("person:new", v);
                //running = false;
            } catch (InterruptedException ex) {
                Logger.getLogger(rmit.se2.pkg2012a.skoorti.GameEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
