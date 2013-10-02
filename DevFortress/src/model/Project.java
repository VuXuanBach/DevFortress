package model;

import java.io.Serializable;
import model.skill.Skill;
import model.skill.Type;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.ImageIcon;

public class Project implements Serializable {

    private int level;
    private String name;
    private double payment;
    private int weekLength;
    private int turn;
    private ArrayList<Developer> projectDeveloper;
    private ArrayList<ProjectRequirement> requirements;
    private ProjectRequirement typeProject;
    private ImageIcon image;

    public Project(String name, int level) {
        this.level = level;
        this.name = name;
        this.turn = 0;
        this.requirements = generateRequirement();
        payment = 1;
        weekLength = 1;
        projectDeveloper = new ArrayList<>();
        addPointRequirement();
        findMainRequirement();
    }

    public long getTotalPoint() {
        int total = 0;
        for (int i = 0; i < requirements.size(); i++) {
            total += requirements.get(i).getPoint();
        }
        return total;
    }

    public long getCurrentPoint() {
        int total = 0;
        for (int i = 0; i < requirements.size(); i++) {
            total += requirements.get(i).getCurrentPoint();
        }
        return total;
    }

    public double getPercentFunction() {
        return (getTotalPoint() - getCurrentPoint()) * 100 / (getTotalPoint() * 1.0);
    }

    public double getPercentTime(int currentTurn) {
        return (currentTurn - turn) / (weekLength * 1.0) * 100;
    }

    private void findMainRequirement() {
        ProjectRequirement main = requirements.get(0);
        for (int i = 1; i < requirements.size(); i++) {
            if (requirements.get(i).getPoint() > main.getPoint()) {
                main = requirements.get(i);
            }

            if (requirements.get(i).getPoint() == main.getPoint()
                    && main.getRequiredSkill().getName().compareToIgnoreCase(requirements.get(i).getRequiredSkill().getName()) > 0) {
                main = requirements.get(i);
            }
        }
        typeProject = main;
    }

    private void addPointRequirement() {
        int total = generateTotalPoint();

        for (int i = 0; i < requirements.size(); i++) {
            if (i == requirements.size() - 1) {
                requirements.get(i).setPoint(total);
                total = 0;
            } else {
                while (true) {
                    int part = 2 + (int) (Math.random() * total);
                    if (total / part != 0 && (total - total / part > requirements.size() - i - 1)) {
                        requirements.get(i).setPoint(total / part);
                        total -= total / part;
                        break;
                    } else {
                        if (total == requirements.size() - i - 1) {
                            requirements.get(i).setPoint(1);
                            total -= 1;
                            break;
                        }
                    }
                }
            }
        }
    }

    private int generateTotalPoint() {
        int point = 0;

        switch (level) {
            case 1:
                weekLength = 1 + (int) (Math.random() * 16);
                point = requirements.size() * 3 + (int) ((Math.random() * 5) * weekLength);
                break;
            case 2:
                weekLength = 1 + (int) (Math.random() * 32);
                point = requirements.size() * 3 + (int) ((Math.random() * 20) * weekLength / 4);
                break;
            case 3:
                weekLength = 24 + (int) (Math.random() * 25);
                point = requirements.size() * 3 + (int) ((Math.random() * 40) * weekLength / 4);
                break;
            case 4:
                weekLength = 48 + (int) (Math.random() * 49);
                point = requirements.size() * 3 + (int) ((Math.random() * 40) * weekLength / 4);
                break;
            case 5:
                weekLength = 1 + (int) (Math.random() * 96);
                point = requirements.size() * 3 + (int) ((Math.random() * 100) * weekLength / 4);
                break;
            default:
                weekLength = 1;
                point = requirements.size() * 3 + (int) ((Math.random() * 20) * weekLength / 4);
                break;
        }

        payment = point * (int) ((Math.random() * level) + 1) * ((Math.random() * level) + 1);
        return point;
    }

    private ArrayList<ProjectRequirement> generateRequirement() {
        ArrayList<ProjectRequirement> requirements = new ArrayList<>();
        Type[] types = {Type.CODING, Type.DATABASE, Type.CODING, Type.SERVER, Type.CODING, Type.OTHER};
        int requirement = 0;
        int unknown = 0;

        switch (level) {
            case 1:
                requirement = 1 + (int) (Math.random() * 4);
                unknown = 1;
                break;
            case 2:
                requirement = 1 + (int) (Math.random() * 8);
                unknown = (requirement >= 2) ? 2 : requirement;
                break;
            case 3:
                requirement = 6 + (int) (Math.random() * 7);
                unknown = (requirement >= 3) ? 3 : requirement;
                break;
            case 4:
                requirement = 12 + (int) (Math.random() * 13);
                unknown = (requirement >= 4) ? 4 : requirement;
                break;
            case 5:
                requirement = 5 + (int) (Math.random() * 21);
                unknown = requirement;
                break;
            default:
                requirement = 0;
                unknown = 0;
        }

        for (int i = 0; i < requirement; i++) {
            boolean dup;
            ProjectRequirement pr;

            while (true) {
                dup = false;
                //Random required skill and check if duplicate or not. If skill is duplicated, random again
                pr = new ProjectRequirement(generateTechnicalSkill(types[(int) (Math.random() * types.length)]));

                for (int j = 0; j < requirements.size(); j++) {
                    //Compare existed required skill with the new required skill (same class or not)
                    if (requirements.get(j).getRequiredSkill().getName().equals(pr.getRequiredSkill().getName())) {
                        dup = true;
                        break;
                    }
                }

                if (!dup) {
                    requirements.add(pr);
                    break;
                }
            }
        }

        for (int i = 0; i < unknown; i++) {
            int index = (int) (Math.random() * requirements.size());
            requirements.get(index).setUnkown(true);
        }
        return requirements;
    }

    public ProjectRequirement addProjectRequirement(long point) {
        boolean dup;
        ProjectRequirement pr;
        Type[] types = {Type.CODING, Type.DATABASE, Type.CODING, Type.SERVER, Type.CODING, Type.OTHER};

        while (true) {
            dup = false;
            //Random required skill and check if duplicate or not. If skill is duplicated, random again
            pr = new ProjectRequirement(generateTechnicalSkill(types[(int) (Math.random() * types.length)]));

            for (int i = 0; i < requirements.size(); i++) {
                //Compare existed required skill with the new required skill (same class or not)
                if (requirements.get(i).getRequiredSkill().getName().equals(pr.getRequiredSkill().getName())) {
                    dup = true;
                    break;
                }
            }

            if (!dup) {
                requirements.add(pr);
                pr.setPoint(point);
                break;
            }
        }
        return pr;
    }

    private Skill generateTechnicalSkill(Type typeSkill) {
        Skill skill = null;

        while (true) {
            String skillname = "Unknown";

            if (typeSkill.equals(Type.CODING)) {
                skillname = Utilities.coding[(int) (Math.random() * Utilities.coding.length)];
            }
            if (typeSkill.equals(Type.DATABASE)) {
                skillname = Utilities.database[(int) (Math.random() * Utilities.database.length)];
            }
            if (typeSkill.equals(Type.SERVER)) {
                skillname = Utilities.server[(int) (Math.random() * Utilities.server.length)];
            }
            if (typeSkill.equals(Type.OTHER)) {
                skillname = Utilities.other[(int) (Math.random() * Utilities.other.length)];
            }

            if (!skillname.equals("Unknown")) {
                try {
                    skill = SkillFactory.createSkill(skillname);
                    break;
                } catch (Exception e) {
                    System.err.println("Generate Technical Skill failed!");
                }
            }
        }

        return skill;
    }

    public boolean assignDeveloper(Developer developer, ProjectRequirement requirement) {
        if (developer.getAssignedProject() == null) {
            developer.setAssignedProject(this);
            developer.setProjectRequirement(requirement);
            requirement.getAssignedDeveloper().add(developer);
            return true;
        }
        return false;
    }

    public boolean removeDeveloper(String name) {
        for (int i = 0; i < requirements.size(); i++) {
            ArrayList<Developer> devs = requirements.get(i).getAssignedDeveloper();
            for (int j = 0; j < devs.size(); j++) {
                if (devs.get(j).getName().equals(name)) {
                    devs.get(j).setAssignedProject(null);
                    devs.get(j).setProjectRequirement(null);
                    requirements.get(i).removeDeveloper(name);
                    return true;
                }
            }
        }
        return false;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public double getPayment() {
        return payment;
    }

    public int getWeekLength() {
        return weekLength;
    }

    public ArrayList<ProjectRequirement> getRequirements() {
        return requirements;
    }

    public ProjectRequirement getTypeProject() {
        return typeProject;
    }

    public int getTurn() {
        return turn;
    }

    public void acceptProject(int turn) {
        this.turn = turn;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public void setRequirements(ArrayList<ProjectRequirement> requirements) {
        this.requirements = requirements;
    }

    public void setTypeProject(ProjectRequirement typeProject) {
        this.typeProject = typeProject;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Project other = (Project) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public ProjectRequirement searchProjectRequirement(String name) {
        ProjectRequirement search = null;
        for (ProjectRequirement pr : requirements) {
            if (pr.getRequiredSkill().getName().equals(name)) {
                search = pr;
                break;
            }
        }
        return search;
    }

    public ArrayList<Developer> getProjectDeveloper() {
        projectDeveloper = new ArrayList<Developer>();
        for (ProjectRequirement pr : requirements) {
            for (Developer dev : pr.getAssignedDeveloper()) {
                projectDeveloper.add(dev);
            }
        }
        return projectDeveloper;
    }

    public ArrayList<ProjectRequirement> getUnfinishedRequirement() {
        ArrayList<ProjectRequirement> prList = new ArrayList<ProjectRequirement>();
        for (ProjectRequirement pr : requirements) {
            if (pr.getCurrentPoint() > 0) {
                prList.add(pr);
            }
        }
        return prList;
    }
}
