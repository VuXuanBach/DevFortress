/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.animal;

import rmit.se2.pkg2012a.skoorti.model.animal.AnimalBehavior;

/**
 *
 * @author zozo
 */
public class NormalAnimalBehavior implements AnimalBehavior {

    @Override
    public int getHungerLevel() {
        return 30;
    }

    @Override
    public int getAngerLevel() {
        return 80;
    }

    @Override
    public int getDecreaseAmount() {
        return 5;
    }
    
}
