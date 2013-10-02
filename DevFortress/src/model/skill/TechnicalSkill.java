/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package model.skill;

/**
 *
 * @author Hoang Hai
 */
public class TechnicalSkill implements Skill {

    private int level, cost, skillPointRate, trainCostRate;
    private Type type;
    private String name;

    public TechnicalSkill(String name, int cost, int skillPointRate, int trainCostRate, Type type ) {
        this.name = name;
        this.level = 0;
        this.cost = cost;
        this.skillPointRate = skillPointRate;
        this.trainCostRate = trainCostRate;      
        this.type=type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
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
    public int getPointCost() {
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
