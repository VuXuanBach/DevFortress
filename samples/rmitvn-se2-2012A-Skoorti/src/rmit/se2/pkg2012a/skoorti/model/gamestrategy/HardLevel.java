/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.gamestrategy;

import rmit.se2.pkg2012a.skoorti.model.animal.AgressiveAnimalBehavior;
import rmit.se2.pkg2012a.skoorti.model.animal.AnimalBehavior;

/**
 *
 * @author zozo
 */
public class HardLevel implements GameLevel {

    @Override
    public boolean hasEntranceFee() {
        return false;
    }

    @Override
    public boolean hasAnimalEscapeLimit() {
        return true;
    }

    @Override
    public double itemDepreciation() {
        return 0.5;
    }

    @Override
    public AnimalBehavior getAnimalBehavior() {
        return new AgressiveAnimalBehavior();
    }

    @Override
    public int maxBudget() {
        return 10000;
    }

    @Override
    public int maxAnimalNumber() {
        return 100;
    }

    @Override
    public int maxVisitorNumber() {
        return 1000;
    }
    
}
