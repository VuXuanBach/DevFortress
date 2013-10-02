package model.skill;

public interface Skill {

    public String getName();

    public int getLevel();

    public int getUpLevelCost();

    public void upLevel();

    public int getCost();

    public long calPoint(long point);
}
