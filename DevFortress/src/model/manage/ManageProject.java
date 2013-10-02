package model.manage;

import java.io.Serializable;
import model.Project;
import java.util.ArrayList;

public class ManageProject implements Serializable {

    private ArrayList<Project> projectList;

    public ManageProject() {
        projectList = new ArrayList<Project>();
    }

    public boolean addProject(Project p) {
        return projectList.add(p);
    }

    public boolean dropProject(Project p) {
        return (p != null) ? projectList.remove(p) : false;
    }

    public Project searchProject(String name) {
        for (Project p : projectList) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Project> getProjectList() {
        return projectList;
    }
}
