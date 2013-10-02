/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model;

import java.io.Serializable;
import rmit.se2.pkg2012a.skoorti.GameConstants;

/**
 *
 * @author duynguyen
 */
public class GamePlayer implements Serializable{
    private float budget;
    private int level;
    private int mapId;
    private int currentRisk;
    private int animalEscape = 0;

    public GamePlayer(float budget, int level, int mapName, int currentRisk) {
        this.budget = budget;
        this.level = level;
        this.mapId = mapName;
        this.currentRisk = currentRisk;
    }

    public GamePlayer() {
        this.budget = 2000;
        this.level = GameConstants.EASY;
        this.mapId = GameConstants.MAPUS;
        this.currentRisk = 0;
    }

    public int getAnimalEscape() {
        return animalEscape;
    }

    public float getBudget() {
        return budget;
    }

    public int getCurrentRisk() {
        return currentRisk;
    }

    public int getLevel() {
        return level;
    }

    public int getMapId() {
        return mapId;
    }
    
    public float updateBudget(float amount) {
        budget += amount;
        return budget;
    }
    
    public int updateRisk(int amount) {
        currentRisk += amount;
        return currentRisk;
    }
    
    public int incrementEscapes() {
        return ++animalEscape;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void setCurrentRisk(int currentRisk) {
        this.currentRisk = currentRisk;
    }
}
