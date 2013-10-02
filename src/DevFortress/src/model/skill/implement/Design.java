package model.skill.implement;

import model.skill.Skill;

public class Design implements Skill {

    private int level, cost;
    private int skillPointRate;
    private int trainCostRate;

    public Design() {
        level = 0;
        cost = 2;
        skillPointRate = 2;
        trainCostRate = 5;
    }

    @Override
    public String getName() {
        return "Design";
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
    public int getCost() {
        return cost;
    }

    @Override
    public long calPoint(long point) {
        return point + (2 * level);
    }

    @Override
    public int getUpLevelCost() {
        return cost * skillPointRate * trainCostRate;
    }
}
