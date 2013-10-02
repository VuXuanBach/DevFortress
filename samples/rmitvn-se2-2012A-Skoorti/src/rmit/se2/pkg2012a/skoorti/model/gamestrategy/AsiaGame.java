/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.gamestrategy;

/**
 *
 * @author zozo
 */
public class AsiaGame extends BaseGameStrategy {

    @Override
    public float getInitialMoney() {
        return 1000;
    }

    @Override
    protected String gameName() {
        return "asia";
    }

    @Override
    public int getMaxRisk() {
        return 150;
    }

    @Override
    public int entranceFee() {
        return 10;
    }

    @Override
    public int maxAnimalEscape() {
        return 15;
    }

    @Override
    public int newVisitorInterval() {
        return 10;
    }

    @Override
    public int riskDecrease() {
        return 10;
    }

    
    
}
