package model.skill;

import model.skill.Skill;

public class Algorithm implements Skill {

    private int level, cost, technical;
    private int skillPointRate;
    private int trainCostRate;

    public Algorithm() {
        level = 0;
        cost = 3;
        technical = 1;
        skillPointRate = 2;
        trainCostRate = 5;
    }

    public void setTechnical(int technical) {
        this.technical = technical;
    }

    @Override
    public String getName() {
        return "Algorithms";
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
        return point + (technical * level);
    }

    @Override
    public int getUpLevelCost() {
        return cost * skillPointRate * trainCostRate;
    }
}
