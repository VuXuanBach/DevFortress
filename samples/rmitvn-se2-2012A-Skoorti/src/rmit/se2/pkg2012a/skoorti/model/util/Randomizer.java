/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.util;

import java.util.Random;

/**
 *
 * @author duynguyen
 */
public class Randomizer {
  private static Randomizer _instance;
  Random r;

  public Randomizer() {
    r = new Random();
  }
  
  public static Randomizer getInstance() {
    if (_instance == null)
      _instance = new Randomizer();
    return _instance;
  }
  
  //not implemented yet
  public boolean isOccured(double prob) {
    if (prob<0 || prob >1)
      return false;
    return r.nextInt(1000000) < prob * 1000000;
  }
}
