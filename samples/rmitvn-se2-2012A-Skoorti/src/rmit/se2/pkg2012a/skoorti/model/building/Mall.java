/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.building;

import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;

/**
 *
 * @author Le Anh Duy
 */
public class Mall extends Building {
    
    public Mall(String name, Coordinate currentCoordinate, int width, int height) {
        super(name, currentCoordinate, width, height, "mall1.png");
        super.setUpdatable(true);
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
        return "A new mall has been built successfully";
    }
    
    public void updateBuilding(){
        super.setImageName("mall2.png");
        promote();
    }
}
