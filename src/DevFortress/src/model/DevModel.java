package model;

import java.util.ArrayList;
import model.manage.ManageDeveloper;
import model.manage.ManageProject;
import model.skill.Skill;

public class DevModel {

    private int week;
    private double balance;
    private ManageDeveloper developers;
    private ManageProject projects;

    public DevModel() {
        week = 0;
        balance = 1000;
        developers = new ManageDeveloper();
        projects = new ManageProject();
        Utilities.loadNameList();
        Utilities.loadProjectNameList();
    }
    
    public void startGame(){
        ArrayList<Skill> skills;
        
        //Create 7 developers for player
        for (int i = 0; i < 7; i++) {
            String[] dev = Utilities.generateDev();
            skills = Utilities.generateSkills(1 + (int) (Math.random() * 10));
            developers.addDeveloper(new Developer(dev[0], dev[1], skills));
        }
        //Generate list of project
        generateProjectList();
    }
    
    public void nextTurn(){
        ArrayList<Developer> list = developers.getDeveloperList();
        for (int i = 0; i < list.size(); i++) {
            Developer dev = list.get(i);
        }
    }
    
    
    
    private void generateProjectList(){
        int numProject = 3+(int) Math.random()*8;
        for (int i = 0; i < numProject; i++) {
            projects.addProject(Utilities.generateProject());
        }
    }

    public int getWeek() {
        return week;
    }

    public double getBalance() {
        return balance;
    }

    public ManageDeveloper getDevelopers() {
        return developers;
    }

    public ManageProject getProjects() {
        return projects;
    }
}
