/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.util;

import java.util.Observable;

/**
 *
 * @author zozo
 */
public class CustomObservable extends Observable {
  public void changed() {
    this.setChanged();
  }
}
