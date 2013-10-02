package model.facade;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Developer;
import model.DeveloperEvent;
import model.PackageItem;
import model.Project;
import model.ProjectRequirement;
import model.SkillFactory;
import model.Utilities;
import model.exception.GameOverException;
import model.exception.InvalidException;
import model.exception.LimitException;
import model.exception.MoneyException;
import model.manage.ManageDeveloper;
import model.manage.ManageProject;
import model.skill.Skill;
import view.util.ViewUtilities;

public class DevModel {

    private int week;
    private int foodStorage;
    private int beer;
    private int computers;
    private double balance;
    private ManageDeveloper developers;
    private ManageProject projects;
    private ArrayList<Project> availableProjects;
    private ArrayList<Developer> availableDevelopers;
    private ArrayList<String> eventLog;
    private static DevModel instance;
    private static final String USERDATA = "user.dat";
    private static final String GENERALDATA = "general.dat";
    private static final String OTHERDATA = "other.dat";

    private DevModel() {
        week = 0;
        balance = 1000;
        developers = new ManageDeveloper();
        projects = new ManageProject();
        foodStorage = 35;
        beer = 0;
        computers = 7;
        Utilities.loadNameList();
        Utilities.loadProjectNameList();
    }

    public static DevModel getInstance() {
        if (instance == null) {
            instance = new DevModel();
        }
        return instance;
    }

    public void startGame() {
        ArrayList<Skill> skills;
        eventLog = new ArrayList<String>();
        //Create 7 developers for player
        for (int i = 0; i < 7; i++) {
            developers.addDeveloper(Utilities.generateDeveloper());
        }

        //Generate list of developer
        generateDeveloperList();

        //Generate list of project
        generateProjectList();

        //Add a level 1 project for new game
        Project project = new Project("Easy Project", 1);
        int num = 1 + (int) (Math.random() * Utilities.PROJECT_IMAGE);
        project.setImage(ViewUtilities.makeIcon("projects/proj" + num + ".png"));
        availableProjects.add(project);


        save();
    }

    public void nextTurn() throws GameOverException {
        save();
        week++;
        ArrayList<String> event = new ArrayList<>();
        ArrayList<Developer> list = developers.getDeveloperList();
        feedDeveloper();
        for (int i = 0; i < list.size(); i++) {
            Developer dev = list.get(i);
//            if (dev.getAssignedProject() != null) {
//                Utilities.generateEvent(dev);
//            } else {
                dev.setEvent(DeveloperEvent.NOTHING);
    //            }
        }
        for (int i = 0; i < projects.getProjectList().size(); i++) {
            Project p = projects.getProjectList().get(i);
            calculateFunctionProject(p);
            effectHandle(p);
            happinessEffect(p);
            if (!checkProject(p)) {
                projects.getProjectList().remove(i);
                i--;
            }
            System.out.println(p.getName() + "-" + p.getCurrentPoint());
        }
        for (Developer dev : developers.getDeveloperList()) {
            dev.setHasBeer(false);
        }
        gameOver();

    }

    public double removeProject(String name) {
        Project p = projects.searchProject(name);
        if (p != null) {
            double removePayment = p.getPayment() * (0.25 + (0.75 * p.getPercentTime(week) / 100));
            return removePayment;
        }
        return 0;
    }

    private void gameOver() throws GameOverException {
        if (balance < 0) {
            throw new GameOverException("Game over !!!");
        }
    }

    private boolean checkProject(Project p) {
        int count = 0;
        for (ProjectRequirement pr : p.getRequirements()) {
            if (pr.getCurrentPoint() <= 0) {
                count++;
            }
        }
        if (count == p.getRequirements().size()) {
            balance += (long) (p.getPayment() * 0.75);
            eventLog.add("Project " + p.getName() + " is finished");
            return false;
        }

        if (p.getTurn() + p.getWeekLength() < week) {
            double removePayment = p.getPayment() * (0.25 + (0.75 * p.getPercentTime(week) / 100));
            balance -= removePayment;
            eventLog.add("Project " + p.getName() + " is late and canceled with the " + removePayment + "$ fine");
            return false;
        }
        return true;
    }

    public void useBeer(Developer dev) {
        if (beer > 0) {
            dev.setHasBeer(true);
            beer--;
        }
    }

    public void acceptProject(String projectName) throws InvalidException {
        Project p = searchAvailableProject(projectName);
        if (p != null) {
            p.setTurn(week);
            projects.addProject(p);
            availableProjects.remove(p);
            balance += p.getPayment() * 0.25;

        } else {
            throw new InvalidException("The project name is not existed");
        }
    }

    public void hireDeveloper(String name) throws Exception {
        if (computers <= developers.getDeveloperList().size()) {
            throw new LimitException("Not have computer for new developer");
        }
        for (int i = 0; i < availableDevelopers.size(); i++) {
            if (availableDevelopers.get(i).getName().equals(name)) {
                if (balance < availableDevelopers.get(i).getHireSalary()) {
                    throw new MoneyException("Not enough money to hire this developer");
                }
                if (developers.getDeveloperList().size() >= 20) {
                    throw new LimitException("Can not have more than 20 developers");
                }
                developers.addDeveloper(availableDevelopers.get(i));
                balance -= availableDevelopers.get(i).getHireSalary();
                availableDevelopers.remove(i);
            }
        }
    }

    public void buyItem(PackageItem pi) throws MoneyException {
        if (pi != null) {
            if (balance >= pi.getPrice()) {
                if (pi.getItemName().equalsIgnoreCase("Beer")) {
                    beer += pi.getQuantity();
                    balance -= pi.getPrice();
                }
                if (pi.getItemName().equalsIgnoreCase("Food")) {
                    foodStorage += pi.getQuantity();
                    balance -= pi.getPrice();
                }
                if (pi.getItemName().equalsIgnoreCase("Computer")) {
                    computers += pi.getQuantity();
                    balance -= pi.getPrice();
                }
            } else {
                throw new MoneyException("Not enough money to buy this item");
            }
        }
    }

    public ArrayList<PackageItem> getListFood() {
        ArrayList<PackageItem> food = new ArrayList<>();
        food.add(new PackageItem("Food", 10, 50));
        food.add(new PackageItem("Food", 20, 90));
        food.add(new PackageItem("Food", 50, 200));
        food.add(new PackageItem("Food", 100, 350));
        food.add(new PackageItem("Food", 250, 875));
        return food;
    }

    public ArrayList<PackageItem> getListBeer() {
        ArrayList<PackageItem> beer = new ArrayList<>();
        beer.add(new PackageItem("Beer", 5, 50));
        beer.add(new PackageItem("Beer", 10, 90));
        beer.add(new PackageItem("Beer", 25, 200));
        beer.add(new PackageItem("Beer", 50, 350));
        beer.add(new PackageItem("Beer", 125, 875));
        return beer;
    }

    public ArrayList<PackageItem> getListComputer() {
        ArrayList<PackageItem> computer = new ArrayList<>();
        computer.add(new PackageItem("Computer", 1, 200));
        return computer;
    }

    public void upSkill(Developer dev, String name) throws MoneyException, InvalidException {
        Skill skill = dev.searchSkill(name);
        if (skill != null && balance >= skill.getUpLevelCost()) {
            if (skill.getLevel() < 10) {
                balance -= skill.getUpLevelCost();
                skill.upLevel();
            } else {
                throw new InvalidException("Can not up level this skill. Max level is 10");
            }
        } else {
            if (skill == null) {
                skill = SkillFactory.createSkill(name);
                dev.getSkills().add(skill);
                if (balance >= skill.getUpLevelCost()) {
                    balance -= skill.getUpLevelCost();
                    skill.upLevel();
                }
            } else {
                throw new MoneyException("Not enough money to up level this skill");
            }
        }
    }

    public void changeDevRequirement(Developer dev, String requirement) {
        Project p = dev.getAssignedProject();
        ProjectRequirement pr = p.searchProjectRequirement(requirement);
        ProjectRequirement search = p.searchProjectRequirement(pr.getRequiredSkill().getName());
        if (search != null) {
            p.removeDeveloper(dev.getName());
            p.assignDeveloper(dev, search);
        }
    }

    private Project searchAvailableProject(String projectName) {
        for (int i = 0; i < availableProjects.size(); i++) {
            if (availableProjects.get(i).getName().equals(projectName)) {
                return availableProjects.get(i);
            }
        }
        return null;
    }

    private void calculateFunctionProject(Project project) {
        long point, totalPoint = 0;
        for (ProjectRequirement pr : project.getRequirements()) {
            point = 0;
            ArrayList<Developer> developerList = pr.getAssignedDeveloper();
            for (Developer dev : developerList) {
                point += dev.totalPoint(project, pr);
            }
            pr.setCurrentPoint(pr.getCurrentPoint() - point);
            if (pr.getCurrentPoint() <= 0) {
                for (int i =0;i<developerList.size();i++) {
                    Developer dev = developerList.get(0);
                    dev.setAssignedProject(null);
                    dev.setProjectRequirement(null);
                    developerList.remove(0);
                }
            }
        }
    }

    private void feedDeveloper() {
        int count = 0;
        for (Developer dev : developers.getDeveloperList()) {
            if (foodStorage > 0) {
                dev.setHasFood(true);
                foodStorage--;
                count++;
            } else {
                dev.setHasFood(false);
                int happiness = (dev.getHappyPoint() >= 30) ? dev.getHappyPoint() - 30 : 0;
                dev.setHappyPoint(happiness);
            }
        }
        if (count == developers.getDeveloperList().size()) {
            eventLog.add("Developers used " + count + " food. " + foodStorage + " food left.");
        }
    }

    private void generateProjectList() {
        int numProject = 3 + (int) Math.random() * 8;
        availableProjects = new ArrayList<>();
        for (int i = 0; i < numProject; i++) {
            availableProjects.add(Utilities.generateProject());
        }
    }

    private void generateDeveloperList() {
        availableDevelopers = Utilities.generateDevList();
    }

    private void effectHandle(Project project) {

        ArrayList<Developer> developerList = project.getProjectDeveloper();
        for (Developer dev : developerList) {
            switch (dev.getEvent()) {
                case SICK: {
                    ProjectRequirement pr = dev.getProjectRequirement();
                    long devPoint = dev.totalPoint(project, pr);
                    pr.setCurrentPoint(pr.getCurrentPoint() + devPoint / 2);

                    break;
                }
                case KILL_DEV: {
                    Developer killedDev;
                    while (true) {
                        int r = (int) (Math.random() * developerList.size());
                        if (!developerList.get(r).equals(dev)) {
                            killedDev = developerList.get(r);
                            break;
                        }
                    }
                    project.removeDeveloper(killedDev.getName());
                    developerList.remove(killedDev);
                    for (Developer developer : developerList) {
                        ProjectRequirement pr = developer.getProjectRequirement();
                        long devPoint = developer.totalPoint(project, pr);
                        pr.setCurrentPoint(pr.getCurrentPoint() + devPoint - 1);
                    }
                    break;
                }
                case REQUIRE_CHANGE: {
                    ArrayList<ProjectRequirement> prList = project.getRequirements();
                    int r = (int) (Math.random() * prList.size());
                    ProjectRequirement pr = project.getRequirements().get(r);
                    pr.setPoint(pr.getPoint() + 20);
                    pr.setCurrentPoint(pr.getCurrentPoint() + 20);
                    break;
                }
                case NEW_TECH: {
                    long bonusPoint = 50;
                    ArrayList<ProjectRequirement> prList = project.getRequirements();

                    while (bonusPoint != 0) {
                        int r = (int) (Math.random() * prList.size());
                        ProjectRequirement pr = project.getRequirements().get(r);
                        if (pr.getCurrentPoint() > bonusPoint) {
                            pr.setCurrentPoint(pr.getCurrentPoint() - bonusPoint);
                            bonusPoint = 0;
                        } else {
                            bonusPoint -= pr.getCurrentPoint();
                            pr.setCurrentPoint(0);
                        }
                    }
                    break;
                }
                case SOLUTION_NOT_SCALE: {
                    ArrayList<ProjectRequirement> prList = project.getRequirements();
                    int r = (int) (Math.random() * prList.size());
                    ProjectRequirement pr = project.getRequirements().get(r);
                    pr.setPoint(pr.getPoint() + 10);
                    pr.setCurrentPoint(pr.getCurrentPoint() + 10);
                    break;
                }
                case HACKED: {
                    for (Developer developer : developerList) {
                        ProjectRequirement pr = developer.getProjectRequirement();
                        long devPoint = developer.totalPoint(project, pr);
                        pr.setCurrentPoint(pr.getCurrentPoint() + devPoint);
                    }
                    break;
                }
                case BACKUP_FAILED: {
                    ArrayList<ProjectRequirement> prList = project.getRequirements();
                    int r = (int) (Math.random() * prList.size());
                    ProjectRequirement pr = project.getRequirements().get(r);
                    pr.setPoint(pr.getPoint() + 25);
                    pr.setCurrentPoint(pr.getCurrentPoint() + 25);
                    break;
                }
                case HOLIDAY: {
                    ProjectRequirement pr = dev.getProjectRequirement();
                    long devPoint = dev.totalPoint(project, pr);
                    pr.setCurrentPoint(pr.getCurrentPoint() + devPoint - 1);
                    break;
                }
                case FEATURE_CUT: {
                    ArrayList<ProjectRequirement> prList = project.getRequirements();
                    int r = (int) (Math.random() * prList.size());
                    ProjectRequirement pr = project.getRequirements().get(r);
                    for (Developer developer : pr.getAssignedDeveloper()) {
                        developer.setAssignedProject(null);
                        developer.setProjectRequirement(null);
                        pr.removeDeveloper(developer.getName());
                    }
                    prList.remove(pr);
                    break;
                }
                case BONUS: {
                    int min = 100;
                    int max = 1000;
                    int bonusMoney = min + (int) (Math.random() * (max - min + 1));
                    balance += bonusMoney;
                    break;
                }
                case EXCERISE: {
                    for (Developer developer : developerList) {
                        ProjectRequirement pr = developer.getProjectRequirement();
                        long devPoint = developer.totalPoint(project, pr);


                        pr.setCurrentPoint(pr.getCurrentPoint() + devPoint - 5);

                        int min = 60;
                        int max = 100;
                        int happy = min + (int) (Math.random() * (max - min + 1));
                        developer.setHappyPoint(happy);
                    }
                    break;
                }
                case REDUNDACIES: {
                    Developer expelledDev;
                    int r = (int) (Math.random() * developerList.size());
                    expelledDev = developerList.get(r);

                    project.removeDeveloper(expelledDev.getName());
                    for (Developer developer : developerList) {
                        int min = 0;
                        int max = 40;
                        int unhappy = min + (int) (Math.random() * (max - min + 1));
                        developer.setHappyPoint(unhappy);
                    }
                    break;
                }
                case IDIOT_MARKETING: {
                    int min = 0;
                    int max = 40;
                    int unhappy = min + (int) (Math.random() * (max - min + 1));
                    Developer developer;

                    int r = (int) (Math.random() * developerList.size());
                    developer = developerList.get(r);
                    developer.setHappyPoint(unhappy);
                    project.addProjectRequirement(10);
                    break;
                }
                case INTERNS: {
                    long bonusPoint = 5;
                    int min = 60;
                    int max = 100;
                    int happy = min + (int) (Math.random() * (max - min + 1));
                    ArrayList<ProjectRequirement> prList = project.getRequirements();
                    Developer developer;

                    while (bonusPoint != 0) {
                        int r = (int) (Math.random() * prList.size());
                        ProjectRequirement pr = project.getRequirements().get(r);
                        if (pr.getCurrentPoint() > bonusPoint) {
                            pr.setCurrentPoint(pr.getCurrentPoint() - bonusPoint);
                            bonusPoint = 0;
                        } else {
                            bonusPoint -= pr.getCurrentPoint();
                            pr.setCurrentPoint(0);
                        }
                    }

                    int r = (int) (Math.random() * developerList.size());
                    developer = developerList.get(r);
                    developer.setHappyPoint(happy);
                    break;
                }
                case NOTHING:
                    break;
                default:
            }
        }
    }

    public void save() {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(USERDATA)));
            oos.writeObject(developers);
            oos.writeObject(projects);
            oos.writeObject(null);
            oos.close();

            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(GENERALDATA)));
            oos.writeInt(week);
            oos.writeInt(foodStorage);
            oos.writeInt(beer);
            oos.writeInt(computers);
            oos.writeDouble(balance);
            oos.writeObject(null);
            oos.close();

            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(OTHERDATA)));
            oos.writeObject(Utilities.maleList);
            oos.writeObject(Utilities.femaleList);
            oos.writeObject(Utilities.projectList);
            oos.writeObject(Utilities.usedNames);
            oos.writeObject(Utilities.usedProjects);
            oos.writeObject(null);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(DevModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(USERDATA)));
            Object obj = null;
            for (int i = 0; (obj = ois.readObject()) != null && i < 2; i++) {
                switch (i) {
                    case 0:
                        developers = (ManageDeveloper) obj;
                        System.out.println("load dev");
                        break;
                    case 1:
                        projects = (ManageProject) obj;
                        System.out.println("load project");
                        break;
                }
            }
            ois.close();

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(GENERALDATA)));
            obj = null;
            for (int i = 0; (obj = ois.readInt()) != null && i < 4; i++) {
                System.out.println("read general data");
                switch (i) {
                    case 0:
                        week = (int) obj;
                        break;
                    case 1:
                        foodStorage = (int) obj;
                        break;
                    case 2:
                        beer = (int) obj;
                        break;
                    case 3:
                        computers = (int) obj;
                        break;
                }
            }
            ois.close();

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(GENERALDATA)));
            obj = null;
            if ((obj = ois.readDouble()) != null) {
                balance = (double) obj;
                System.out.println("read balance");
            }
            ois.close();

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(OTHERDATA)));
            obj = null;
            for (int i = 0; (obj = ois.readObject()) != null && i < 5; i++) {
                System.out.println("read utilities");
                switch (i) {
                    case 0:
                        Utilities.maleList = (ArrayList<String>) obj;
                        break;
                    case 1:
                        Utilities.femaleList = (ArrayList<String>) obj;
                        break;
                    case 2:
                        Utilities.projectList = (ArrayList<String>) obj;
                        break;
                    case 3:
                        Utilities.usedNames = (ArrayList<String>) obj;
                        break;
                    case 4:
                        Utilities.usedProjects = (ArrayList<String>) obj;
                        break;
                }
            }
            ois.close();
        } catch (EOFException ex) {
            System.out.println("EOF");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DevModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DevModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void happinessEffect(Project p) {
        for (ProjectRequirement pr : p.getRequirements()) {
            for (Developer dev : pr.getAssignedDeveloper()) {
                if (dev.getMainSkill().equals(p.getTypeProject().getRequiredSkill())) {
                    dev.setHappyPoint(dev.getHappyPoint() - 5);
                }
                if (dev.getHappyPoint() < 50) {
                    eventLog.add(dev.getName() + " feel a bit unhappy");
                }
                if (dev.getHappyPoint() <= 0) {
                    int chance = (int) (Math.random() * 1000);
                    if (chance == 0) {
                        dev.getAssignedProject().removeDeveloper(dev.getName());
                        developers.removeDeveloper(dev);
                        eventLog.add(dev.getName() + " is angry and leaves the company !!!");
                    }
                }
            }
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

    public int getFoodStorage() {
        return foodStorage;
    }

    public void setFoodStorage(int foodStorage) {
        this.foodStorage = foodStorage;
    }

    public int getBeer() {
        return beer;
    }

    public void setBeer(int beer) {
        this.beer = beer;
    }

    public ArrayList<Project> getAvailableProjects() {
        return availableProjects;
    }

    public ArrayList<Developer> getAvailableDevelopers() {
        return availableDevelopers;
    }

    public ArrayList<String> getEventLog() {
        return eventLog;
    }

    public void setEventLog(ArrayList<String> eventLog) {
        this.eventLog = eventLog;
    }
}
