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
public class Museum extends Building {

    public Museum(String name, Coordinate currentCoordinate, int width, int height) {
        super(name, currentCoordinate, width, height, "museum1.png");
        super.setUpdatable(true);
    }

    @Override
    public float getCost() {
        return 150;
    }

    @Override
    public void setCost(float cost) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String log() {
        return "Your zoo has a new museum";
    }

    public void updateBuilding() {
        super.setImageName("museum2.png");
        promote();
    }
}
