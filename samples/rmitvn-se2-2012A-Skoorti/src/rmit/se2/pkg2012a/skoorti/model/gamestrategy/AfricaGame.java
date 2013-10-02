/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.gamestrategy;

/**
 *
 * @author zozo
 */
public class AfricaGame extends BaseGameStrategy {

    @Override
    protected String gameName() {
        return "africa";
    }

    @Override
    public float getInitialMoney() {
        return 500;
    }

    @Override
    public int getMaxRisk() {
        return 200;
    }

    @Override
    public int entranceFee() {
        return 5;
    }

    @Override
    public int maxAnimalEscape() {
        return 20;
    }

    @Override
    public int newVisitorInterval() {
        return 15;
    }

    @Override
    public int riskDecrease() {
        return 15;
    }
    
}
