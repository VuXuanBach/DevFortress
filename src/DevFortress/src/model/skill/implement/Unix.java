package model.skill.implement;

import model.skill.Technical;
import model.skill.Type;

public class Unix implements Technical {

    private int level, cost;
    private int skillPointRate;
    private int trainCostRate;

    public Unix() {
        level = 0;
        cost = 1;
        skillPointRate = 2;
        trainCostRate = 2;
    }

    @Override
    public Type getType() {
        return Type.SERVER;
    }

    @Override
    public String getName() {
        return "Unix";
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void upLevel() {
        level++;
        cost += skillPointRate;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public long calPoint(long point) {
        return point + level;
    }

    @Override
    public int getUpLevelCost() {
        return (cost + skillPointRate) * trainCostRate;
    }
}