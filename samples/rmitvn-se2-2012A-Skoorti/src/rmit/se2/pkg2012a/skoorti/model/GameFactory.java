/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model;

import rmit.se2.pkg2012a.skoorti.model.building.Museum;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.storage.Storage;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;
import rmit.se2.pkg2012a.skoorti.model.research.Research;
import rmit.se2.pkg2012a.skoorti.model.research.AnimalResearch;
import rmit.se2.pkg2012a.skoorti.model.animal.Food;
import rmit.se2.pkg2012a.skoorti.model.person.Clown;
import rmit.se2.pkg2012a.skoorti.model.person.NormalMoveBehavior;
import rmit.se2.pkg2012a.skoorti.model.building.Restaurant;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.person.VisitorMoveBehavior;
import rmit.se2.pkg2012a.skoorti.model.person.Visitor;
import rmit.se2.pkg2012a.skoorti.model.person.Staff;
import rmit.se2.pkg2012a.skoorti.model.person.ZooKeeperMoveBehavior;
import rmit.se2.pkg2012a.skoorti.model.person.ZooKeeper;
import rmit.se2.pkg2012a.skoorti.model.building.Cage;
import rmit.se2.pkg2012a.skoorti.model.building.Toilet;
import rmit.se2.pkg2012a.skoorti.model.building.Gym;
import rmit.se2.pkg2012a.skoorti.model.building.Mall;
import rmit.se2.pkg2012a.skoorti.model.animal.AnimalBehavior;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmit.se2.pkg2012a.skoorti.GameConstants;

/**
 *
 * @author zozo
 */
public class GameFactory implements Serializable{

    private static GameFactory instance = null;
    private AnimalBehavior animalBehavior;
    private List<String> firstNameList, lastNameList;

    private GameFactory() {
    }

    public Building createBuilding(String type) {
        Building b = null;
        if (type.equals("cage")) {
            b = new Cage(type, new Coordinate(), GameConstants.CAGE_WIDTH, GameConstants.CAGE_HEIGHT);
        } else if (type.equals("restaurant")) {
            b = new Restaurant(type, new Coordinate(), GameConstants.RESTAURANT_WIDTH, GameConstants.RESTAURANT_HEIGHT);
        } else if (type.equals("museum")) {
            b = new Museum(type, new Coordinate(), GameConstants.MUSEUM_WIDTH, GameConstants.MUSEUM_HEIGHT);
            b.setUpdatable(true);
        } else if (type.equals("mall")) {
            b = new Mall("museum", new Coordinate(), GameConstants.MALL_WIDTH, GameConstants.MALL_HEIGHT);
            b.setUpdatable(true);
        } else if (type.equals("gym")) {
            b = new Gym("gym", new Coordinate(), GameConstants.GYM_WIDTH, GameConstants.GYM_HEIGHT);
        } else if (type.equals("toilet")) {
            b = new Toilet("toilet", new Coordinate(), GameConstants.TOILET_WIDTH, GameConstants.TOILET_HEIGHT);
        }
        return b;
    }

    public Visitor createVisitor() {
        Random rd = new Random();
        List<Coordinate> gateNeighbors = GameMap.getInstance().getGateNeighbors();
        Visitor v = new Visitor(getRandomName(), rd.nextInt(68) + 3, rd.nextInt(2) + 1, rd.nextInt(2), 
                rd.nextInt(GameConstants.MAXVISITORBUDGET - GameConstants.MINVISITORBUDGET) + GameConstants.MINVISITORBUDGET,
                new VisitorMoveBehavior(gateNeighbors.get(rd.nextInt(gateNeighbors.size())), 8, 0));
        int[] animal_types = GameConstants.ANIMALTYPES;
        v.addAnimalType(animal_types[rd.nextInt(animal_types.length)]);
        v.addAnimalType(animal_types[rd.nextInt(animal_types.length)]);
        return v;
    }

    public Staff createStaff(String type) {
        Staff s = null;
        if (type.endsWith("clown")) {
            Random rd = new Random();
            List<Coordinate> gateNeighbors = GameMap.getInstance().getGateNeighbors();
            s = new Clown(getRandomName(), rd.nextInt(68) + 3, rd.nextInt(2) + 1,
                    new NormalMoveBehavior(gateNeighbors.get(rd.nextInt(gateNeighbors.size())), 8), 100, 10, 1);
        }
        if (type.endsWith("zooKeeper")) {
            Random rd = new Random();
            s = new ZooKeeper(getRandomName(), rd.nextInt(68) + 3, rd.nextInt(2) + 1,
                    new ZooKeeperMoveBehavior(null, 0, GameConstants.DOWN, null), 150, 20);
        }
        return s;
    }

    public Animal createAnimal(String[] animalString) {
        // for now just create an animal
        Animal a = new Animal(Integer.valueOf(animalString[2]));
        a.setImageName(animalString[4]);
        a.setThumbnail(animalString[5]);
        a.setName(animalString[0]);
        a.setDescription(animalString[1]);
        a.setBehavior(this.getAnimalBehavior());
        a.setCost(Float.valueOf(animalString[3]));
        return a;
    }

    public Food createFood(String[] foodString) {
        Food f = new Food(Integer.parseInt(foodString[3]));
        f.setImageName(foodString[2]);
        f.setName(foodString[0]);
        f.setDescription(foodString[1]);
        f.setCost(Integer.parseInt(foodString[4]));
        f.setRisk(Integer.parseInt(foodString[5]));
        return f;
    }

    public static GameFactory getInstance() {
        if (instance == null) {
            instance = new GameFactory();
        }
        return instance;
    }

    public Storage getGameStorage() {
        return GameMemoryStorage.getInstance();
    }

    public Research createResearch(String[] researchString) {
        // checks the type first
        String type = researchString[0];
        Research r = null;
        float cost = Float.valueOf(researchString[1]);
        String name = researchString[2];
        String description = researchString[3];
        int complete = Integer.valueOf(researchString[4]);

        if (type.compareTo("animal") == 0) {
            AnimalResearch ar = new AnimalResearch(name, description, complete);
            String[] animalString =
                    Arrays.copyOfRange(researchString, 5, researchString.length);
            ar.setAnimal(this.createAnimal(animalString));
            ar.setCost(cost);
            r = ar;
        } else {
        }
        return r;
    }

    public String getRandomName() {
        try {
            if (firstNameList == null || lastNameList == null) {
                Scanner scan = new Scanner(new File("src/files/firstname.csv"));
                firstNameList = new ArrayList<String>();
                while (scan.hasNextLine()) {
                    firstNameList.add(scan.nextLine());
                }
                scan.close();
                scan = new Scanner(new File("src/files/lastname.csv"));
                lastNameList = new ArrayList<String>();
                while (scan.hasNextLine()) {
                    lastNameList.add(scan.nextLine());
                }
                scan.close();
            }
            Random rd = new Random();
            return firstNameList.get(rd.nextInt(firstNameList.size() - 1)) + " " + lastNameList.get(rd.nextInt(lastNameList.size() - 1));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameFactory.class.getName()).log(Level.SEVERE, null, ex);
            return "Unknown";
        }
    }

    public AnimalBehavior getAnimalBehavior() {
        return animalBehavior;
    }

    public void setAnimalBehavior(AnimalBehavior animalBehavior) {
        this.animalBehavior = animalBehavior;
    }
}
