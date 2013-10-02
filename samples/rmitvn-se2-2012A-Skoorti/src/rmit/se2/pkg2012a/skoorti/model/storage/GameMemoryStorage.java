
package rmit.se2.pkg2012a.skoorti.model.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rmit.se2.pkg2012a.skoorti.model.GamePlayer;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.animal.Food;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.person.Person;
import rmit.se2.pkg2012a.skoorti.model.research.Research;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;

public class GameMemoryStorage implements Storage {

    public static Storage _instance;
    private GamePlayer player;
    private List<Person> persons;
    private Map<Coordinate, Building> buildings;
    private List<Animal> animals;
    private List<Building> initialBuildings;
    private List<Animal> initialAnimals;
    private List<Research> researches;
    private List<Food> foodList;

    private GameMemoryStorage() {
        this.persons = new ArrayList<Person>();
        this.buildings = new HashMap<Coordinate, Building>();
        this.animals = new ArrayList<Animal>();
        this.initialBuildings = new ArrayList<Building>();
        this.initialAnimals = new ArrayList<Animal>();
        this.researches = new ArrayList<Research>();
        this.foodList = new ArrayList<Food>();
    }
    
    private GameMemoryStorage(GamePlayer player) {
        this();
        this.player = player;
    }

    private GameMemoryStorage(List<Building> initialBuildings, List<Animal> initialAnimals) {
        this();
        this.initialBuildings = initialBuildings;
        this.initialAnimals = initialAnimals;
    }

    public static Storage getInstance() {
        if (_instance == null) {
            _instance = new GameMemoryStorage();
        }
        return _instance;
    }

    public static Storage getInstance(List<Building> initialBuildings, List<Animal> initialAnimals) {
        if (_instance == null) {
            _instance = new GameMemoryStorage(initialBuildings, initialAnimals);
        }
        return _instance;
    }

    public static Storage getInstance(GamePlayer player) {
        if (_instance == null) {
            _instance = new GameMemoryStorage(player);
        }
        return _instance;
    }

    public Storage setPlayer(GamePlayer player) {
        this.player = player;
        return _instance;
    }

    public static Storage createNewInstance(Storage newInstance){
        _instance = newInstance;
        return _instance;
    }
    
        public static Storage createNewInstance(){
        _instance = new GameMemoryStorage();
        return _instance;
    }
   
    @Override
    public Storage addPerson(Person newPerson) {
        synchronized (persons) {
            if (!persons.contains(newPerson)) {
                persons.add(newPerson);
            }
            return this;
        }
    }

    @Override
    public Storage removePerson(Person person) {
        synchronized (persons) {
            for (int i = 0; i < persons.size(); i++) {
                if (persons.get(i).equals(person)) {
                    persons.remove(i);
                    break;
                }
            }
            return this;
        }
    }

    public Building getBuildingByCoordinate(Coordinate c) {
        synchronized (buildings) {
            return buildings.get(c);
        }
    }

    @Override
    public List<Person> getPersonList() {
        synchronized (persons) {
            return persons;
        }
    }

//  @Override
//  public Storage setPersonList(List<Person> personList) {
//    this.persons = personList;
//    return this;
//  }
    @Override
    public Storage addBuilding(Building newBuilding) {
        synchronized (buildings) {
            if (!buildings.containsValue(newBuilding)) {
                buildings.put(newBuilding.getCurrentCoordinate(), newBuilding);
            }
            return this;
        }
    }

    @Override
    public Storage removeBuilding(Building building) {
        synchronized (buildings) {
            for (Coordinate c : buildings.keySet()) {
                if (buildings.get(c).equals(building)) {
                    buildings.remove(c);
                    break;
                }
            }
            return this;
        }
    }

    @Override
    public List<Building> getBuildingList() {
        synchronized (buildings) {
            return new ArrayList<Building>(buildings.values());
        }
    }

//  @Override
//  public Storage setBuildingList(List<Building> buildingList) {
//    this.buildings = buildingList;
//    return this;
//  }
    @Override
    public Storage addAnimal(Animal newAnimal) {
        synchronized (animals) {
            if (!animals.contains(newAnimal)) {
                animals.add(newAnimal);
            }
            return this;
        }
    }

    @Override
    public Storage removeAnimal(Animal animal) {
        synchronized (animals) {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i).equals(animal)) {
                    animals.remove(i);
                    break;
                }
            }
            return this;
        }
    }

    @Override
    public List<Animal> getAnimalList() {
        synchronized (animals) {
            return animals;
        }
    }

    @Override
    public Storage setAnimalList(List<Animal> animalList) {
        synchronized (animals) {
            this.animals = animalList;
            return this;
        }
    }

    @Override
    public Storage setInitialAnimals(List<Animal> newAnimalList) {
        synchronized (initialAnimals) {
            this.initialAnimals = newAnimalList;
            return this;
        }
    }

    @Override
    public List<Animal> getInitialAnimals() {
        synchronized (initialAnimals) {
            return initialAnimals;
        }
    }

    @Override
    public Storage setInitialBuildings(List<Building> newBuildingList) {
        synchronized (initialBuildings) {
            this.initialBuildings = newBuildingList;
            return this;
        }
    }

    @Override
    public List<Building> getInitialBuildings() {
        synchronized (initialBuildings) {
            return initialBuildings;
        }
    }

    @Override
    public Storage addInitialAnimal(Animal newAnimal) {
        synchronized (initialAnimals) {
            if (!initialAnimals.contains(newAnimal)) {
                initialAnimals.add(newAnimal);
            }
            return this;
        }
    }

    @Override
    public Storage addInitialBuilding(Building newBuilding) {
        synchronized (initialBuildings) {
            if (!initialBuildings.contains(newBuilding)) {
                initialBuildings.add(newBuilding);
            }
            return this;
        }
    }

    @Override
    public Storage addResearch(Research research) {
        synchronized (researches) {
            if (!this.researches.contains(research)) {
                researches.add(research);
            }
            return this;
        }
    }

    @Override
    public List<Research> getResearches() {
        synchronized (researches) {
            return this.researches;
        }
    }

    @Override
    public Storage setResearches(List<Research> researches) {
        synchronized (researches) {
            this.researches = researches;
            return this;
        }
    }

    @Override
    public Storage removeResearch(Research research) {
        synchronized (researches) {
            for (Research r : this.researches) {
                if (r.equals(research)) {
                    researches.remove(researches.indexOf(r));
                }
            }
            return this;
        }
    }

    @Override
    public Storage setFoodList(List<Food> food) {
        this.foodList = food;
        return this;
    }

    @Override
    public List<Food> getFoodList() {
        return this.foodList;
    }

    @Override
    public GamePlayer getPlayer() {
        return player;
    }
}
