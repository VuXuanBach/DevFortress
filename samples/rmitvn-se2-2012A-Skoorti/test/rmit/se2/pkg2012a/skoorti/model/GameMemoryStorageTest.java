
package rmit.se2.pkg2012a.skoorti.model;

import org.junit.*;
import org.mockito.Mockito;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.building.*;
import rmit.se2.pkg2012a.skoorti.model.person.Clown;
import rmit.se2.pkg2012a.skoorti.model.person.Visitor;
import rmit.se2.pkg2012a.skoorti.model.person.ZooKeeper;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;
import rmit.se2.pkg2012a.skoorti.model.storage.Storage;

public class GameMemoryStorageTest {
    Storage storage;
    
    public GameMemoryStorageTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        storage = GameMemoryStorage.createNewInstance();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddPerson() {
        Visitor v = Mockito.mock(Visitor.class);
        Clown c = Mockito.mock(Clown.class);
        ZooKeeper z = Mockito.mock(ZooKeeper.class);
        
        assert(storage.getPersonList().isEmpty());
        storage.addPerson(v);
        assert(storage.getPersonList().size()==1);
        assert(storage.getPersonList().contains(v));
        storage.addPerson(c);
        assert(storage.getPersonList().size()==2);
        assert(storage.getPersonList().contains(c));
        //add person already in list
        storage.addPerson(v);
        assert(storage.getPersonList().size()==2);
        storage.addPerson(z);
        assert(storage.getPersonList().size()==3);
        assert(storage.getPersonList().contains(z));
    }
    
    @Test
    public void testRemovePerson() {
        Visitor v = Mockito.mock(Visitor.class);
        Clown c = Mockito.mock(Clown.class);
        ZooKeeper z = Mockito.mock(ZooKeeper.class);

        storage.addPerson(v);
        storage.addPerson(c);
        assert(storage.getPersonList().size()==2);
        storage.removePerson(c);
        assert(storage.getPersonList().size()==1);
        assert(!storage.getPersonList().contains(c));
        storage.addPerson(c);
        storage.addPerson(z);
        assert(storage.getPersonList().size()==3);
        storage.removePerson(v);
        assert(storage.getPersonList().size()==2);
        storage.removePerson(z);
        //remove person not in list
        assert(storage.getPersonList().size()==1);
        storage.removePerson(v);
        assert(storage.getPersonList().size()==1);
        assert(storage.getPersonList().contains(c));
        assert(!storage.getPersonList().contains(v));
        assert(!storage.getPersonList().contains(z));
    }
    
    @Test
    public void testGetPersonList() {
        Visitor v = Mockito.mock(Visitor.class);
        Clown c = Mockito.mock(Clown.class);
        ZooKeeper z = Mockito.mock(ZooKeeper.class);
        storage.addPerson(v);
        assert(storage.getPersonList().size()==1);
        storage.addPerson(c);
        assert(storage.getPersonList().size()==2);
        storage.addPerson(z);
        assert(storage.getPersonList().size()==3);
    }
    
    @Test
    public void testAddAnimal() {
        Animal a1 = Mockito.mock(Animal.class);
        Animal a2 = Mockito.mock(Animal.class);
        Animal a3 = Mockito.mock(Animal.class);
        
        assert(storage.getAnimalList().isEmpty());
        storage.addAnimal(a1);
        assert(storage.getAnimalList().size()==1);
        assert(storage.getAnimalList().contains(a1));
        storage.addAnimal(a2);
        assert(storage.getAnimalList().size()==2);
        assert(storage.getAnimalList().contains(a2));
        //add animal already in list
        storage.addAnimal(a1);
        assert(storage.getAnimalList().size()==2);
        storage.addAnimal(a3);
        assert(storage.getAnimalList().size()==3);
        assert(storage.getAnimalList().contains(a3));
    }
    
    @Test
    public void testRemoveAnimal() {
        Animal a1 = Mockito.mock(Animal.class);
        Animal a2 = Mockito.mock(Animal.class);
        Animal a3 = Mockito.mock(Animal.class);

        storage.addAnimal(a1);
        storage.addAnimal(a2);
        assert(storage.getAnimalList().size()==2);
        storage.removeAnimal(a2);
        assert(storage.getAnimalList().size()==1);
        assert(!storage.getAnimalList().contains(a2));
        storage.addAnimal(a2);
        storage.addAnimal(a3);
        assert(storage.getAnimalList().size()==3);
        storage.removeAnimal(a3);
        assert(storage.getAnimalList().size()==2);
        storage.removeAnimal(a1);
        assert(storage.getAnimalList().size()==1);
        //remove animal not in list
        storage.removeAnimal(a3);
        assert(storage.getAnimalList().size()==1);
        assert(storage.getAnimalList().contains(a2));
        assert(!storage.getAnimalList().contains(a1));
        assert(!storage.getAnimalList().contains(a3));
    }
    
    @Test
    public void testGetAnimalList() {
        Animal a1 = Mockito.mock(Animal.class);
        Animal a2 = Mockito.mock(Animal.class);
        Animal a3 = Mockito.mock(Animal.class);
        
        storage.addAnimal(a1);
        assert(storage.getAnimalList().size()==1);
        storage.addAnimal(a2);
        assert(storage.getAnimalList().size()==2);
        storage.addAnimal(a3);
        assert(storage.getAnimalList().size()==3);
    }
    
    @Test
    public void testAddBuilding() {
        Restaurant r = Mockito.mock(Restaurant.class);
        Cage c = Mockito.mock(Cage.class);
        Mall m = Mockito.mock(Mall.class);
        Gym g = Mockito.mock(Gym.class);
        Toilet t = Mockito.mock(Toilet.class);
        Museum m2 = Mockito.mock(Museum.class);
        
        assert(storage.getBuildingList().isEmpty());
        storage.addBuilding(r);
        assert(storage.getBuildingList().size()==1);
        assert(storage.getBuildingList().contains(r));
        storage.addBuilding(c);
        storage.addBuilding(m);
        System.out.println(storage.getBuildingList().size());
        assert(storage.getBuildingList().size()==3);
        assert(storage.getBuildingList().contains(c));
        assert(storage.getBuildingList().contains(m));
        //add building already in list
        storage.addBuilding(c);
        assert(storage.getBuildingList().size()==3);
        storage.addBuilding(g);
        storage.addBuilding(t);
        storage.addBuilding(m2);
        assert(storage.getBuildingList().size()==6);
        assert(storage.getBuildingList().contains(g));
        assert(storage.getBuildingList().contains(t));
        assert(storage.getBuildingList().contains(m2));
    }
    
    @Test
    public void testRemoveBuilding() {
        Restaurant r = Mockito.mock(Restaurant.class);
        Cage c = Mockito.mock(Cage.class);
        Mall m = Mockito.mock(Mall.class);
        Gym g = Mockito.mock(Gym.class);
        Toilet t = Mockito.mock(Toilet.class);
        Museum m2 = Mockito.mock(Museum.class);

        storage.addBuilding(r);
        storage.addBuilding(c);
        storage.addBuilding(m);
        assert(storage.getBuildingList().size()==3);
        storage.removeBuilding(c);
        assert(storage.getBuildingList().size()==2);
        assert(!storage.getBuildingList().contains(c));
        storage.addBuilding(g);
        storage.addBuilding(t);
        assert(storage.getBuildingList().size()==4);
        storage.removeBuilding(r);
        assert(storage.getBuildingList().size()==3);
        storage.removeBuilding(m);
        assert(storage.getBuildingList().size()==2);
        //remove building not in list
        storage.removeBuilding(c);
        storage.removeBuilding(r);
        assert(storage.getBuildingList().size()==2);
        assert(storage.getBuildingList().contains(g));
        assert(storage.getBuildingList().contains(t));
        assert(!storage.getBuildingList().contains(m));
        assert(!storage.getBuildingList().contains(r));
    }
    
    @Test
    public void testGetBuildingList() {
        Restaurant r = Mockito.mock(Restaurant.class);
        Cage c = Mockito.mock(Cage.class);
        Mall m = Mockito.mock(Mall.class);
        Gym g = Mockito.mock(Gym.class);
        Toilet t = Mockito.mock(Toilet.class);
        Museum m2 = Mockito.mock(Museum.class);
        
        storage.addBuilding(r);
        assert(storage.getBuildingList().size()==1);
        storage.addBuilding(c);
        storage.addBuilding(m);
        assert(storage.getBuildingList().size()==3);
        storage.addBuilding(g);
        storage.addBuilding(t);
        storage.addBuilding(m2);
        assert(storage.getBuildingList().size()==6);
    }
}
