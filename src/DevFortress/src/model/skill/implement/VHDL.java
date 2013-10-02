package model.skill.implement;

import model.skill.Technical;
import model.skill.Type;

public class VHDL implements Technical {

    private int level, cost;
    private int skillPointRate;
    private int trainCostRate;

    public VHDL() {
        level = 0;
        cost = 2;
        skillPointRate = 2;
        trainCostRate = 2;
    }

    @Override
    public Type getType() {
        return Type.OTHER;
    }

    @Override
    public String getName() {
        return "VHDL";
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