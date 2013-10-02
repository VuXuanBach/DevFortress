package model.manage;

import java.io.Serializable;
import model.Developer;
import java.util.ArrayList;

public class ManageDeveloper implements Serializable{

    private ArrayList<Developer> developerList;

    public ManageDeveloper() {
        developerList = new ArrayList<Developer>();
    }

    public boolean addDeveloper(Developer developer) {
        if (developerList.size() < 20) {
            developerList.add(developer);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeDeveloper(Developer developer) {
        return (developer != null) ? developerList.remove(developer) : false;
    }

    public Developer searchDeveloper(String name) {
        for (Developer d : developerList) {
            if (d.getName().equals(name)) {
                return d;
            }
        }
        return null;
    }

    public ArrayList<Developer> getDeveloperList() {
        return developerList;
    }

    public ArrayList<Developer> getFreeDevelopers() {
        ArrayList<Developer> free = new ArrayList<>();
        for (Developer dev : developerList) {
            if (dev.getAssignedProject() == null) {
                free.add(dev);
            }
        }
        return free;
    }
}
