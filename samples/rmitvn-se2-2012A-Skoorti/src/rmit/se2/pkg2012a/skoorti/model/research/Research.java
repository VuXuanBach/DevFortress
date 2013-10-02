/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.research;

import java.io.Serializable;
import java.util.Observable;
import rmit.se2.pkg2012a.skoorti.model.util.Costable;
import rmit.se2.pkg2012a.skoorti.model.util.Logable;

/**
 *
 * @author zozo
 */
public abstract class Research extends Observable implements Costable, Logable, Serializable {

    protected String name, description;
    protected int complete, current;
    protected boolean pause, active;
    private float cost;

    public Research(String name, String description, int complete) {
        this.name = name;
        this.description = description;
        this.complete = complete;
        current = 0;
        pause = false;
        active = false;
    }

    public int getCompleteTime() {
        return complete;
    }

    public void setCompleteTime(int completeTime) {
        this.complete = completeTime;
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

    public int getCurrent() {
        return current;
    }

    public int increaseCurrent() {
        int cur = ++current;
        this.setChanged();
        this.notifyObservers(cur);
        return cur;
    }
    
    public boolean isCompleted() {
        return complete == current;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public abstract void complete();
    
    @Override
    public void setCost(float cost) {
        this.cost = cost;
    }
    
    @Override
    public float getCost() {
        return cost;
    }

    public void pause() {
        pause = !pause;
    }
}
