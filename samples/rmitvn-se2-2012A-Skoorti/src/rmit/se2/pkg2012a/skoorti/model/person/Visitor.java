
package rmit.se2.pkg2012a.skoorti.model.person;

import java.util.ArrayList;
import java.util.List;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.GameMap;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.building.*;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;
import rmit.se2.pkg2012a.skoorti.model.util.Movable;
import rmit.se2.pkg2012a.skoorti.model.util.Randomizer;

public class Visitor extends Person {

    private int budget;
    private int funLevel;
    private int hungryLevel;
    private int bladderLevel;
    private long lastUpdateTime;
    private int type;
    private boolean visited;
    private int changeMood;
    private long changeMoodTime;
    private Building nextDestination;
    private List<Integer> animalTypes;
    private List<Building> destinations;
    private int[] exit;

    public Visitor(String name, int age, int gender, int type, int budget, Movable mb) {
        super(name, age, gender, mb);
        this.type = type;
        if (type == GameConstants.VIP) {
            budget *= GameConstants.VIPLEVEL;
        }
        this.budget = budget;
        // initial data for levels (max 100 and min 0)
        this.hungryLevel = 100;
        this.funLevel = 100;
        this.bladderLevel = 100;
        this.lastUpdateTime = System.currentTimeMillis();
        animalTypes = new ArrayList<Integer>();
        destinations = new ArrayList<Building>();
        exit = null;
        validateDestinations();
        visited = false;
        changeMoodTime = System.currentTimeMillis();
        changeMood = GameConstants.INVALID;
    }

    public int getChangeMood() {
        return changeMood;
    }

    public int getBladderLevel() {
        return bladderLevel;
    }

    public void setBladderLevel(int bladderLevel) {
        this.bladderLevel = bladderLevel;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getFunLevel() {
        return funLevel;
    }

    public void setFunLevel(int funLevel) {
        this.funLevel = funLevel;
    }

    public int getHungryLevel() {
        return hungryLevel;
    }

    public void setHungryLevel(int hungryLevel) {
        this.hungryLevel = hungryLevel;
    }

    @Override
    public String getImage() {
        boolean isMale = (getGender() == GameConstants.MALE) ? true : false;
        int age = getAge();
        if (age > 0 && age < 13) {
            return (isMale) ? "littleboy.png" : "littlegirl.png";
        } else if (age <= 19) {
            return (isMale) ? "teenboy.png" : "teengirl.png";
        } else if (age < 55) {
            return (isMale) ? "man.png" : "woman.png";
        } else {
            return (isMale) ? "oldman.png" : "oldwoman.png";
        }
    }

    public Visitor interactBuilding(Building b) {
        // calculates the rates for this person when interacting with a particular building
        return this;
    }

    public Visitor addAnimalType(int type) {
        if (animalTypes.contains(new Integer(type))) {
            int[] animal_types = GameConstants.ANIMALTYPES;
            type = (type == animal_types[animal_types.length - 1])
                    ? type = animal_types[0] : type++;
        }
        animalTypes.add(new Integer(type));
        return this;
    }

    @Override
    public int move() {
        if (System.currentTimeMillis() - changeMoodTime > 1000) {
            changeMood = GameConstants.INVALID;
        }
        if (exit != null) {
            return super.getMb().move(exit).getDirection();
        }
        decrementLevels();
        if ((super.getMb() instanceof VisitorMoveBehavior) && ((VisitorMoveBehavior) super.getMb()).isArrived()) {
            validateMovement();
            nextDestination = (destinations == null || destinations.isEmpty()) ? null : destinations.remove(0);
        }
        validateDestinations();
        if (destinations != null && exit != null) {
            checkMood();
        }

        return super.getMb().move(convertToIntArrayDestination(nextDestination)).getDirection();
    }

    public void decrementLevels() {
        if (System.currentTimeMillis() - lastUpdateTime > 20000) {
            funLevel -= 5;
            hungryLevel -= 3;
            bladderLevel -= 4;
            if (bladderLevel < 50 || hungryLevel < 50) {
                funLevel -= 10;
            }
            lastUpdateTime = System.currentTimeMillis();
        }
    }

    public void initDestinations() {
        List<Animal> animalList = GameMemoryStorage.getInstance().getAnimalList();
        List<Building> buildingList = GameMemoryStorage.getInstance().getBuildingList();
        int count = 0;
        while (count < 2) {
            //add more destinations that visitor will go to
            destinations = new ArrayList<Building>();
            count = 0;
            for (int i = 0; i < animalList.size(); i++) {
                Animal animal = animalList.get(i);
                if (animal != null && ((animalTypes.contains(animal.getType()) && Randomizer.getInstance().isOccured(0.7))
                        || (!animalTypes.contains(animal.getType()) && Randomizer.getInstance().isOccured(0.7)))) {
                    addDestination(animal.getCage());
                    count++;
                }
            }

            for (Building building : buildingList) {
                if (!(building instanceof Cage) && Randomizer.getInstance().isOccured(0.7)) {
                    addDestination(building);
                    count++;
                }
            }
        }
    }

    private void addDestination(Building b) {
        if (!destinations.contains(b)) {
            destinations.add(b);
        }
    }

    private void validateMovement() {
        System.out.println("Validating..." + nextDestination);
        if (nextDestination instanceof Toilet) {
            System.out.println("Meet toilet:");
            bladderLevel = 100;
            decrementBudget(GameConstants.TOILETPRICE);
            changeMood = GameConstants.BLADDER;
            changeMoodTime = System.currentTimeMillis();
        } else if (nextDestination instanceof Restaurant) {
            System.out.println("meet restaurant");
            hungryLevel = 100;
            decrementBudget(GameConstants.RESTAURANTPRICE);
            changeMood = GameConstants.HUNGRY;
            changeMoodTime = System.currentTimeMillis();
        } else if (nextDestination instanceof Cage) {
            System.out.println("meet cage");
            List<Animal> animalList = GameMemoryStorage.getInstance().getAnimalList();
            for (Animal animal : animalList) {
                if (animal.getCage().equals(nextDestination)) {
                    if ((animal != null) && animalTypes.contains(new Integer(animal.getType()))) {
                        decrementBudget(
                                (int) (GameConstants.CAGEPRICE * GameConstants.FAVORITELEVEL));
                        gainFun(20);
                    } else if (animal != null) {
                        decrementBudget(GameConstants.CAGEPRICE);
                        gainFun(5);
                    }
                }
            }
        } else if (nextDestination instanceof Gym) {
            System.out.println("meet gym");
            hungryLevel -= 20;
            bladderLevel -= 10;
            decrementBudget(GameConstants.GYMPRICE);
            gainFun(10);
        } else if (nextDestination instanceof Mall) {
            System.out.println("meet mall");
            decrementBudget(GameConstants.MALLPRICE);
            gainFun(15);
        } else if (nextDestination instanceof Museum) {
            System.out.println("meet museum");
            decrementBudget(GameConstants.MUSEUMPRICE);
            gainFun(10);
        }
        System.out.println("not meet");
    }

    public void gainFun(int amount) {
        funLevel += amount;
        changeMood = GameConstants.HAPPY;
        changeMoodTime = System.currentTimeMillis();
    }

    private void validateDestinations() {
        if (destinations == null && exit == null) {
            if (GameMemoryStorage.getInstance().getBuildingList().size() >= 3) {
                visited = true;
                initVisitorMoveBehavior();
            }
        } else if (destinations != null && destinations.isEmpty()) {
            if (GameMemoryStorage.getInstance().getBuildingList().size() < 3) {
                super.setMb(new NormalMoveBehavior(super.getMb().getCoordinate(), super.getMb().getSpeed()));
                destinations = null;
            } else if (!visited) {
                visited = true;
                initVisitorMoveBehavior();
            } else if (exit == null && nextDestination == null) {
                exit();
            }
        }
    }

    private void initVisitorMoveBehavior() {
        super.setMb(new VisitorMoveBehavior(super.getMb().getCoordinate(), super.getMb().getSpeed(), super.getMb().getDirection()));
        destinations = new ArrayList<Building>();
        if (GameMemoryStorage.getInstance().getBuildingList().size() < 5) {
            for (Building b : GameMemoryStorage.getInstance().getBuildingList()) {
                addDestination(b);
            }
        } else {
            initDestinations();
        }
    }

    /**
     * check the time when visitor need to go to restaurant or toilet
     */
    private void checkMood() {
        if (funLevel < 20 || budget < 100) {//go home
            exit();
        } else if (bladderLevel < 50) {
            if (bladderLevel < 10) {
                exit();
            } else {
                Toilet t = getToilet();
                if (t == null) {
                    exit();
                } else {
                    addDestination(t);
                    reverseDestinations();
                }
            }
        } else if (hungryLevel < 50) {
            if (hungryLevel < 10) {
                exit();
            } else {
                Restaurant r = getRestaurant();
                if (r == null) {
                    exit();
                } else {
                    addDestination(r);
                    reverseDestinations();
                }
            }
        }
    }

    public Building getNextDestination() {
        return nextDestination;
    }

    private Toilet getToilet() {
        for (Building b : GameMemoryStorage.getInstance().getBuildingList()) {
            if (b instanceof Toilet) {
                return (Toilet) b;
            }
        }
        return null;
    }

    private Restaurant getRestaurant() {
        for (Building b : GameMemoryStorage.getInstance().getBuildingList()) {
            if (b instanceof Restaurant) {
                return (Restaurant) b;
            }
        }
        return null;
    }

    private void reverseDestinations() {
        List<Building> reversed = new ArrayList<Building>();
        for (Building b : destinations) {
            reversed.add(b);
        }
        destinations = reversed;
    }

    private void exit() {
        if (!(super.getMb() instanceof VisitorMoveBehavior)) {
            super.setMb(new VisitorMoveBehavior(super.getMb().getCoordinate(), super.getMb().getSpeed(), super.getMb().getDirection()));
        }
        exit = GameMap.getInstance().getRandomGate();
        destinations = null;
        nextDestination = null;
    }

    private void decrementBudget(int amount) {
        if (type == GameConstants.VIP) {
            amount *= 1.5;
        }
        budget -= amount;
        Mediator.publish("game:cost", amount);
    }

    private int[] convertToIntArrayDestination(Building b) {
        if (b == null) {
            return null;
        }
        int row = GameMap.getInstance().parseRow(b.getCurrentCoordinate());
        int col = GameMap.getInstance().parseCol(b.getCurrentCoordinate());
        int tileWidth = b.getWidth() / 32;
        int tileHeight = b.getHeight() / 32;
        if (row > 0 && GameMap.getInstance().getBlockStatus(row - 1, col) == GameMap.ROAD) {
            return new int[]{row - 1, col + tileWidth / 2, GameConstants.DOWN};
        }
        if (col > 0 && GameMap.getInstance().getBlockStatus(row, col - 1) == GameMap.ROAD) {
            return new int[]{row + tileHeight / 2, col - 1, GameConstants.RIGHT};
        }
        if (row + tileHeight < GameMap.getInstance().getHeight() && GameMap.getInstance().getBlockStatus(row + tileHeight, col) == GameMap.ROAD) {
            return new int[]{row + tileHeight, col + tileWidth / 2, GameConstants.UP};
        }
        if (col + tileWidth < GameMap.getInstance().getWidth() && GameMap.getInstance().getBlockStatus(row, col + tileWidth) == GameMap.ROAD) {
            return new int[]{row + tileHeight / 2, col + tileWidth, GameConstants.LEFT};
        }
        return null;
    }

    @Override
    public String toString() {
        String s = "   Name:\t" + getName()
                + "\n   Age:\t" + getAge()
                + "\n   Budget:\t" + budget
                + "\n   Gender:\t" + ((getGender() == GameConstants.MALE) ? "Male" : "Female")
                + "\n   Type:\t" + ((type == GameConstants.VIP) ? "VIP" : "Normal")
                + "\n\n   ++++++ Personal status ++++++ \n"
                + "      Fun:\t" + funLevel
                + "\n      Hungry:\t" + hungryLevel
                + "\n      Bladder:\t" + bladderLevel
                + "\n      Favorite animal: ";
        if (animalTypes.isEmpty()) {
            s += "none";
        } else {
            for (int i = 0; i
                    < animalTypes.size() - 1; i++) {
                s = s + GameConstants.FAVORANIMALS[animalTypes.get(i) - GameConstants.ANIMALTYPES[0]] + ", ";
            }
            s = s + GameConstants.FAVORANIMALS[animalTypes.get(animalTypes.size() - 1) - GameConstants.ANIMALTYPES[0]];
        }
        return s;
    }
}
