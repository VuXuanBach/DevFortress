/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.building;

import java.io.Serializable;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.Costable;
import rmit.se2.pkg2012a.skoorti.model.util.Logable;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;

/**
 *
 * @author zozo
 */
public abstract class Building implements Costable, Logable, Serializable {

    private String name;
    private String imageName;
    private float cost;
    private Coordinate currentCoordinate;
    private int width, height;
    private int numOfVisits;
    private boolean updatable;
    private boolean sold;
    private int level = 1;

    public Building(String name, Coordinate currentCoordinate, int width, int height, String imageName) {
        this.name = name;
        this.currentCoordinate = currentCoordinate;
        this.width = width;
        this.height = height;
        this.numOfVisits = 0;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getCost() {
        return cost;
    }

    public int getNumOfVisits() {
        return numOfVisits;
    }

    public void incrementVisits() {
        numOfVisits++;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Building other = (Building) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (Float.floatToIntBits(this.cost) != Float.floatToIntBits(other.cost)) {
            return false;
        }
        if (this.currentCoordinate != other.currentCoordinate && (this.currentCoordinate == null || !this.currentCoordinate.equals(other.currentCoordinate))) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + Float.floatToIntBits(this.cost);
        hash = 89 * hash + (this.currentCoordinate != null ? this.currentCoordinate.hashCode() : 0);
        hash = 89 * hash + this.width;
        hash = 89 * hash + this.height;
        return hash;
    }

    public abstract void updateBuilding();

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int promote() {
        Mediator.publish("game:cost", -getCost() / 2);
        return ++level;
    }
}
