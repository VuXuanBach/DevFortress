/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.thread;

import java.io.Serializable;

/**
 *
 * @author zozo
 */
abstract public class GameThread extends Thread implements Serializable{
    protected volatile boolean running = true;
    
  public void stopLoop() {
    running = false;
  }
}
