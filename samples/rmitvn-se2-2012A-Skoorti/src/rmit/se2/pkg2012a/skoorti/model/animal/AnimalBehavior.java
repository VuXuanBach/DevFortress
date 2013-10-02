/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.animal;

import java.io.Serializable;

/**
 *
 * @author zozo
 */
public interface AnimalBehavior extends Serializable{
    public int getHungerLevel();
    public int getAngerLevel();
    public int getDecreaseAmount();
}
