/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.building;

import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;

/**
 *
 * @author Le Anh Duy
 */
public class Gym extends Building {

    public Gym(String name, Coordinate currentCoordinate, int width, int height) {
        super(name, currentCoordinate, width, height, "gym.png");
    }

    @Override
    public float getCost() {
        return 200;
    }

    @Override
    public void setCost(float cost) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String log() {
        return "You have built a new gym";
    }

    @Override
    public void updateBuilding() {
    }
}
