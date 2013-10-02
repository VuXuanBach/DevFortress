package model.skill.implement;

import model.skill.Skill;

public class TeamPlayer implements Skill {

    private int level, cost, numMember;
    private int skillPointRate;
    private int trainCostRate;

    public TeamPlayer() {
        level = 0;
        cost = 2;
        numMember = 1;
        skillPointRate = 2;
        trainCostRate = 5;
    }

    public void setNumMember(int numMember) {
        this.numMember = numMember;
    }

    @Override
    public String getName() {
        return "Team Player";
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
        return point + (level * numMember);
    }

    @Override
    public int getUpLevelCost() {
        return cost * skillPointRate * trainCostRate;
    }
}
