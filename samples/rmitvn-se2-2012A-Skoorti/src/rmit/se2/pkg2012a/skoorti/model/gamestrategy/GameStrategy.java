/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.gamestrategy;

import java.io.Serializable;
import java.util.List;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.animal.Food;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.research.Research;

/**
 *
 * @author zozo
 */
public interface GameStrategy extends Serializable {
    public float getInitialMoney();
    public int getMaxRisk();
    public int entranceFee();
    public int maxAnimalEscape();
    public int newVisitorInterval();
    public int riskDecrease();
    
    public List<Animal> initialAnimals();
    public List<Building> initialBuildings();
    public List<Research> initialResearches();
    public List<Food> initialFood();
}
