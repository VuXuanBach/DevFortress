package model.skill;

import java.io.Serializable;

public interface Skill extends Serializable {

    public String getName();

    public int getLevel();

    public int getUpLevelCost();

    public void upLevel();

    public int getPointCost();

    public long calPoint(long point);
}
