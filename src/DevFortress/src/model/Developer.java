package model;

import model.skill.Skill;
import java.util.ArrayList;
import java.util.Objects;

public class Developer {

    private String name, gender;
    private ArrayList<Skill> skills;
    private int happyPoint;
    private boolean assigned;
    private int salary, hireSalary;
    private final int salaryRate = 1;
    private final int hireSalaryRate = 7;
    private Event event;

    public Developer(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.skills = new ArrayList<Skill>();
        this.happyPoint = 100;
        event = Event.NOTHING;
        assigned = false;
    }

    public Developer(String name, String gender, ArrayList<Skill> skills) {
        this.name = name;
        this.gender = gender;
        this.skills = skills;
        this.happyPoint = 100;
        assigned = false;
        event = Event.NOTHING;
        calSalary();
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public int getHappyPoint() {
        return happyPoint;
    }

    public void setHappyPoint(int happyPoint) {
        this.happyPoint = happyPoint;
    }

    public int getSalary() {
        return salary;
    }

    public void calSalary() {
        int totalSalary = 0, totalHireSalary = 0;

        for (Skill skill : skills) {
            if (skill.getLevel() < 6) {
                totalSalary += skill.getCost() * salaryRate;
                totalHireSalary += skill.getCost() * hireSalaryRate;
            } else {
                totalSalary += skill.getCost() * salaryRate * 2;
                totalHireSalary += skill.getCost() * hireSalaryRate * 3;
            }
        }
        this.salary = totalSalary;
        this.hireSalary = totalHireSalary;
    }

    public int getHireSalary() {
        return hireSalary;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.name);
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
        final Developer other = (Developer) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
