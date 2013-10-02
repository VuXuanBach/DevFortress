/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.gamestrategy;

/**
 *
 * @author zozo
 */
public class UsGame extends BaseGameStrategy {

    @Override
    public float getInitialMoney() {
        return 2000;
    }

    @Override
    protected String gameName() {
        return "us";
    }

    @Override
    public int getMaxRisk() {
        return 100;
    }

    @Override
    public int entranceFee() {
        return 20;
    }

    @Override
    public int maxAnimalEscape() {
        return 10;
    }

    @Override
    public int newVisitorInterval() {
        return 5;
    }

    @Override
    public int riskDecrease() {
        return 5;
    }
}
