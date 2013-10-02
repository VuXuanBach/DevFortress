package model.skill.implement;

import model.skill.Skill;

public class ConfigManage implements Skill {

    private int level, cost;
    private int skillPointRate;
    private int trainCostRate;

    public ConfigManage() {
        level = 0;
        cost = 5;
        skillPointRate = 2;
        trainCostRate = 2;
    }

    @Override
    public String getName() {
        return "Config Management";
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
        return point / ((10 - level) + 2);
    }

    @Override
    public int getUpLevelCost() {
        return (cost + skillPointRate) * trainCostRate;
    }
}
