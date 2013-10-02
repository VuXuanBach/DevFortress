/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.animal;

/**
 *
 * @author zozo
 */
public class AgressiveAnimalBehavior implements AnimalBehavior {

    @Override
    public int getHungerLevel() {
        return 35;
    }

    @Override
    public int getAngerLevel() {
        return 70;
    }

    @Override
    public int getDecreaseAmount() {
        return 5;
    }
}
