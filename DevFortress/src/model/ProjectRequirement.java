package model;

import java.io.Serializable;
import model.skill.Skill;
import java.util.ArrayList;
import java.util.Objects;

public class ProjectRequirement implements Serializable {

    private Skill requiredSkill;
    private long point, currentPoint;
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

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        if (point == 0) {
            this.point = point;
            this.currentPoint = point;
        } else {
            long amount = point - this.point;
            this.point += amount;
            currentPoint += amount;

        }
    }

    public long getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(long currentPoint) {
        this.currentPoint = (currentPoint >= 0) ? currentPoint : 0;
    }

    public ArrayList<Developer> getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public void setAssignedDeveloper(ArrayList<Developer> assignedDeveloper) {
        this.assignedDeveloper = assignedDeveloper;
    }

    public double getPercentFunction() {
        return (getPoint() - getCurrentPoint()) * 100 / (getPoint() * 1.0);
    }

    public void removeDeveloper(String name) {
        for (int i = 0; i < getAssignedDeveloper().size(); i++) {
            Developer dev = getAssignedDeveloper().get(i);
            if(dev.getName().equalsIgnoreCase(name)){
                assignedDeveloper.remove(i);
                return;
            }
        }
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
