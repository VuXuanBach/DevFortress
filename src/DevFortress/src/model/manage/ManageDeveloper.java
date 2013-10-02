package model.manage;

import model.Developer;
import java.util.ArrayList;

public class ManageDeveloper {

    private ArrayList<Developer> developerList;

    public ManageDeveloper() {
        developerList = new ArrayList<Developer>();
    }

    public boolean addDeveloper(Developer developer) {
        developerList.add(developer);
        return true;
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
}
