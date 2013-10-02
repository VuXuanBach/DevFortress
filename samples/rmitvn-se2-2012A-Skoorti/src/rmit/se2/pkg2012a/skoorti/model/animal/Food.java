/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.animal;

import java.io.Serializable;
import rmit.se2.pkg2012a.skoorti.model.util.Costable;

/**
 *
 * @author zozo
 */
public class Food implements Costable, Serializable {

    private String imageName, name, description;
    private float cost;
    private int risk;
    private int value;

    public Food(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Food other = (Food) obj;
        if ((this.imageName == null) ? (other.imageName != null) : !this.imageName.equals(other.imageName)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.value != other.value) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.imageName != null ? this.imageName.hashCode() : 0);
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 79 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 79 * hash + this.value;
        return hash;
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getRisk() {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }
}
