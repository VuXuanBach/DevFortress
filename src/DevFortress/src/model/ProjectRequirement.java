package model;

import model.skill.Skill;
import java.util.ArrayList;
import java.util.Objects;

public class ProjectRequirement {

    private Skill requiredSkill;
    private int point;
    private boolean unkown;
    private ArrayList<Developer> assignedDeveloper;

    public ProjectRequirement(Skill requiredSkill) {
        this.requiredSkill = requiredSkill;
        this.point = 0;
        assignedDeveloper = new ArrayList<>();
        unkown = false;
    }

    public boolean isUnkown() {
        return unkown;
    }

    public void setUnkown(boolean unkown) {
        this.unkown = unkown;
    }

    public Skill getRequiredSkill() {
        return requiredSkill;
    }

    public void setRequiredSkill(Skill requiredSkill) {
        this.requiredSkill = requiredSkill;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ArrayList<Developer> getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public void setAssignedDeveloper(ArrayList<Developer> assignedDeveloper) {
        this.assignedDeveloper = assignedDeveloper;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.requiredSkill);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProjectRequirement other = (ProjectRequirement) obj;
        if (!Objects.equals(this.requiredSkill, other.requiredSkill)) {
            return false;
        }
        return true;
    }
}
