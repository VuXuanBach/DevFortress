/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.gamestrategy;

import rmit.se2.pkg2012a.skoorti.model.animal.AnimalBehavior;
import rmit.se2.pkg2012a.skoorti.model.animal.NormalAnimalBehavior;

/**
 *
 * @author zozo
 */
public class EasyLevel implements GameLevel {

    @Override
    public boolean hasEntranceFee() {
        return true;
    }

    @Override
    public boolean hasAnimalEscapeLimit() {
        return false;
    }

    @Override
    public double itemDepreciation() {
        return 1;
    }

    @Override
    public AnimalBehavior getAnimalBehavior() {
        return new NormalAnimalBehavior();
    }

    @Override
    public int maxBudget() {
        return 5000;
    }

    @Override
    public int maxAnimalNumber() {
        return 50;
    }

    @Override
    public int maxVisitorNumber() {
        return 500;
    }
    
}
