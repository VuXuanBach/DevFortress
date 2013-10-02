package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.ImageIcon;
import model.skill.Algorithm;
import model.skill.ConfigManage;
import model.skill.Design;
import model.skill.Skill;
import model.skill.TeamPlayer;
import model.skill.TechnicalSkill;

public class Developer implements Serializable {

    private String name, gender;
    private ArrayList<Skill> skills;
    private int happyPoint;
    private Project assignedProject;
    private int salary, hireSalary;
    private final int salaryRate = 1;
    private final int hireSalaryRate = 7;
    private boolean hasFood, hasBeer;
    private DeveloperEvent event;
    private ProjectRequirement projectRequirement;
    private ImageIcon image,avatar;

    public Developer(String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.skills = new ArrayList<Skill>();
        this.happyPoint = 100;
        event = DeveloperEvent.NOTHING;
        assignedProject = null;
        hasFood = true;
        hasBeer = false;
    }

    public Developer(String name, String gender, ArrayList<Skill> skills) {
        this.name = name;
        this.gender = gender;
        this.skills = skills;
        this.happyPoint = 100;
        assignedProject = null;
        hasFood = true;
        event = DeveloperEvent.NOTHING;
        calSalary();
    }

    public DeveloperEvent getEvent() {
        return event;
    }

    public void setEvent(DeveloperEvent event) {
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
        if (happyPoint < 0) {
            this.happyPoint = happyPoint;
        }
    }

    public int getSalary() {
        return salary;
    }

    public void calSalary() {
        int totalSalary = 0, totalHireSalary = 0;

        for (Skill skill : skills) {
            if (skill.getLevel() < 6) {
                totalSalary += skill.getPointCost() * salaryRate;
                totalHireSalary += skill.getPointCost() * hireSalaryRate;
            } else {
                totalSalary += skill.getPointCost() * salaryRate * 2;
                totalHireSalary += skill.getPointCost() * hireSalaryRate * 3;
            }
        }
        this.salary = totalSalary;
        this.hireSalary = totalHireSalary;
    }

    public int getHireSalary() {
        return hireSalary;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
    }

    public ProjectRequirement getProjectRequirement() {
        return projectRequirement;
    }

    public void setProjectRequirement(ProjectRequirement projectRequirement) {
        this.projectRequirement = projectRequirement;
    }

    public ImageIcon getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageIcon avatar) {
        this.avatar = avatar;
    }
    
    

    public Skill searchSkill(String skillName) {
        for (Skill skill : skills) {
            if (skill.getName().equals(skillName)) {
                return skill;
            }
        }
        return null;
    }

    public long totalPoint(Project project, ProjectRequirement pr) {
        long point = 0;
        Skill main = searchSkill(project.getTypeProject().getRequiredSkill().getName());
        if (!hasFood) {
            return 1;
        } else {
            if (main != null) {
                Skill[] skills = {project.getTypeProject().getRequiredSkill(), new Design(), new Algorithm(), new TeamPlayer(), new ConfigManage()};
                for (Skill defaultSkill : skills) {
                    Skill skill = (searchSkill(defaultSkill.getName()) != null) ? searchSkill(defaultSkill.getName()) : defaultSkill;
                    if (defaultSkill.equals("Algorithms")) {
                        ((Algorithm) skill).setTechnical(main.getLevel());
                    }
                    if (defaultSkill.equals("Team Player")) {
                        ((TeamPlayer) skill).setNumMember(pr.getAssignedDeveloper().size());
                    }
                    point = skill.calPoint(point);

                }
            } else {

                Skill erlang = (searchSkill("Erlang") != null) ? searchSkill("Erlang") : SkillFactory.createSkill("Erlang");
                Skill forth = (searchSkill("Forth") != null) ? searchSkill("Forth") : SkillFactory.createSkill("Forth");
                Skill haskell = (searchSkill("Haskell") != null) ? searchSkill("Haskell") : SkillFactory.createSkill("Haskell");

                if (erlang.getLevel() != 0 || forth.getLevel() != 0 || haskell.getLevel() != 0) {

                    if (erlang.getLevel() >= forth.getLevel() && erlang.getLevel() >= haskell.getLevel()) {
                        main = erlang;
                    } else {
                        if (forth.getLevel() >= haskell.getLevel()) {
                            main = forth;
                        } else {
                            main = haskell;
                        }
                    }

                    Skill[] skills = {main, new Design(), new Algorithm(), new TeamPlayer(), new ConfigManage()};
                    for (Skill defaultSkill : skills) {
                        Skill skill = (searchSkill(defaultSkill.getName()) != null) ? searchSkill(defaultSkill.getName()) : defaultSkill;
                        if (defaultSkill.equals("Algorithms")) {
                            ((Algorithm) skill).setTechnical(main.getLevel());
                        }
                        if (defaultSkill.equals("Team Player")) {
                            ((TeamPlayer) skill).setNumMember(pr.getAssignedDeveloper().size());
                        }
                        point = skill.calPoint(point);
                    }

                } else {
                    Skill[] skills = {new Design(), new Algorithm(), new TeamPlayer(), new ConfigManage()};
                    Skill lowest = (getSkills().size() > 0) ? getSkills().get(0) : null;
                    for (int i = 0; i < getSkills().size() && lowest != null; i++) {
                        Skill current = getSkills().get(i);
                        if (!(lowest instanceof TechnicalSkill)) {
                            continue;
                        }
                        if (lowest.getLevel() > current.getLevel() || (lowest.getLevel() == current.getLevel() && lowest.getName().compareTo(current.getName()) > 0)) {
                            lowest = current;
                        }
                    }
                    point += ((lowest.getLevel() / 2) < 1) ? 1 : (lowest.getLevel() / 2.0);
                    for (Skill defaultSkill : skills) {
                        Skill skill = (searchSkill(defaultSkill.getName()) != null) ? searchSkill(defaultSkill.getName()) : defaultSkill;
                        if (defaultSkill.equals("Algorithms")) {
                            ((Algorithm) skill).setTechnical(lowest.getLevel() / 2);
                        }
                        if (defaultSkill.equals("Team Player")) {
                            ((TeamPlayer) skill).setNumMember(pr.getAssignedDeveloper().size());
                        }
                        if (skill != null) {
                            point = skill.calPoint(point);
                        }
                    }
                }
            }
            if (hasBeer) {
                point *= 0.5;
            }
            return point;
        }
    }

    public boolean isHasFood() {
        return hasFood;
    }

    public void setHasFood(boolean hasFood) {
        this.hasFood = hasFood;
    }

    public boolean isHasBeer() {
        return hasBeer;
    }

    public void setHasBeer(boolean hasBeer) {
        this.hasBeer = hasBeer;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setHireSalary(int hireSalary) {
        this.hireSalary = hireSalary;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
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

    public ImageIcon getImage() {
        return image;
    }

    public Skill getMainSkill() {
        Skill main = SkillFactory.createSkill("Unknown");
        for (int i = 0; i < skills.size(); i++) {
            Skill skill = skills.get(i);
            if (skill instanceof TechnicalSkill && main.getLevel() < skill.getLevel()) {
                main = skill;
            }
        }
        return main;
    }
}
