/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.storage;

import java.io.Serializable;
import rmit.se2.pkg2012a.skoorti.model.research.Research;
import rmit.se2.pkg2012a.skoorti.model.person.Person;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import java.util.List;
import rmit.se2.pkg2012a.skoorti.model.GamePlayer;
import rmit.se2.pkg2012a.skoorti.model.animal.Food;

/**
 *
 * @author duynguyen
 */
public interface Storage extends Serializable{
  GamePlayer getPlayer();
  public Storage addPerson(Person newPerson);
  public Storage removePerson(Person person);
  public List<Person> getPersonList();
  //public Storage setPersonList(List<Person> personList);
  public Storage addBuilding(Building newBuilding);
  public Storage removeBuilding(Building building);
  public List<Building> getBuildingList();
  //public Storage setBuildingList(List<Building> buildingList);
  public Storage addAnimal(Animal newAnimal);
  public Storage removeAnimal(Animal animal);
  public List<Animal> getAnimalList();
  public Storage setAnimalList(List<Animal> animalList);
  public Storage setInitialAnimals(List<Animal> newAnimalList);
  public List<Animal> getInitialAnimals();
  public Storage setInitialBuildings(List<Building> newBuildingList);
  public List<Building> getInitialBuildings();
  public Storage addInitialAnimal(Animal newAnimal);
  public Storage addInitialBuilding(Building newBuilding);
  public Storage addResearch(Research research);
  public Storage removeResearch(Research research);
  public List<Research> getResearches();
  public Storage setResearches(List<Research> researches);
  public Storage setFoodList(List<Food> food);
  public List<Food> getFoodList();
}
