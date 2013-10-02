/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.building;

import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.building.Building;

/**
 *
 * @author Le Anh Duy
 */
public class Restaurant extends Building {

    public Restaurant(String name, Coordinate currentCoordinate, int width, int height) {
        super(name, currentCoordinate, width, height, "restaurant1.png");
    }

    @Override
    public float getCost() {
        return 250;
    }

    @Override
    public void setCost(float cost) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String log() {
        return "Yeah!!! New restaurant... more food";
    }

    @Override
    public void updateBuilding() {
        super.setImageName("restaurant2.png");
    }
}
