package model.skill;

import model.skill.Skill;

public class Analysis implements Skill {

    private int level, cost;
    private int skillPointRate;
    private int trainCostRate;

    public Analysis() {
        level = 0;
        cost = 2;
        skillPointRate = 2;
        trainCostRate = 5;
    }

    @Override
    public String getName() {
        return "Analysis";
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
        return point + (3 * level);
    }

    @Override
    public int getUpLevelCost() {
        return cost * skillPointRate * trainCostRate;
    }
}
