package model.skill;

import model.skill.Skill;

public class Communication implements Skill {

    private int level, cost;
    private int skillPointRate;
    private int trainCostRate;

    public Communication() {
        level = 0;
        cost = 2;
        skillPointRate = 2;
        trainCostRate = 5;
    }

    @Override
    public String getName() {
        return "Communication";
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void upLevel() {
        level++;
        cost *= skillPointRate;
    }

    @Override
    public int getPointCost() {
        return cost;
    }

    @Override
    public long calPoint(long point) {
        return point;
    }

    @Override
    public int getUpLevelCost() {
        return cost * skillPointRate * trainCostRate;
    }
}
