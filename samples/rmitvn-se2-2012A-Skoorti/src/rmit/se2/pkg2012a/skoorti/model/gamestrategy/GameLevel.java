/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.gamestrategy;

import rmit.se2.pkg2012a.skoorti.model.animal.AnimalBehavior;

/**
 *
 * @author zozo
 */
public interface GameLevel {
    public boolean hasEntranceFee();
    public boolean hasAnimalEscapeLimit();
    public double itemDepreciation();
    public AnimalBehavior getAnimalBehavior();
    public int maxBudget();
    public int maxAnimalNumber();
    public int maxVisitorNumber();
}
