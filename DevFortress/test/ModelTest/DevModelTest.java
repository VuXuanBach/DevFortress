package ModelTest;

/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
import java.util.ArrayList;
import model.Developer;
import model.DeveloperEvent;
import model.Project;
import model.facade.DevModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hoang Hai
 */
public class DevModelTest {

    private static DevModel instance;

    public DevModelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = DevModel.getInstance();
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of startGame method
     */
    @Test
    public void testStartGame1() throws Exception {
        System.out.println("Test method startGame 1: Generate default developers");
        instance.startGame();
        int num_dev = instance.getDevelopers().getDeveloperList().size();
        int expResult = 7;
        assertEquals(expResult, num_dev);
    }

    @Test
    public void testStartGame2() throws Exception {
        System.out.println("Test method startGame 2: Generate skill of default developers");
        instance.startGame();
        ArrayList<Developer> devList = instance.getDevelopers().getDeveloperList();
        boolean valid = true;
        for (Developer dev : devList) {
            if (dev.getSkills().size() <= 0) {
                valid = false;
                break;
            }
        }
        assertTrue(valid);
    }

    @Test
    public void testStartGame3() throws Exception {
        System.out.println("Test method startGame 3: Generate the available developers list for hiring");
        instance.startGame();
        int num_dev = instance.getAvailableDevelopers().size();
        assertTrue(num_dev > 0);
    }
    
    @Test
    public void testStartGame4() throws Exception {
        System.out.println("Test method startGame 4: Generate the available project list");
        instance.startGame();
        int num_project = instance.getAvailableProjects().size();
        assertTrue(num_project > 0);
    }

    @Test
    public void testNextTurn1() throws Exception {
        System.out.println("Test method nextTurn 1: Increase the turn");
        instance.startGame();
        int turn_before = instance.getWeek();
        instance.nextTurn();
        int turn_after = instance.getWeek();
        int expResult = 1;
        assertTrue(turn_after - turn_before == expResult);
    }
    
    @Test
    public void testNextTurn2() throws Exception {
        System.out.println("Test method nextTurn 2: Feed developer when have enough food");
        instance.startGame();   
        instance.nextTurn();
        
        ArrayList<Developer> devList = instance.getDevelopers().getDeveloperList();
        boolean hasFood = true;
        for (Developer dev : devList) {
            if (!dev.isHasFood()) {
                hasFood = false;
                break;
            }
        }
        assertTrue(hasFood);
    }
    
    @Test
    public void testNextTurn3() throws Exception {
        System.out.println("Test method nextTurn 3: Feed developer when NOT have enough food");
        instance.startGame();   
        instance.setFoodStorage(0);
        instance.nextTurn();
        
        ArrayList<Developer> devList = instance.getDevelopers().getDeveloperList();
        boolean hasFood = true;
        for (Developer dev : devList) {
            if (!dev.isHasFood()) {
                hasFood = false;
                break;
            }
        }
        assertFalse(hasFood);
    }
    
    @Test
    public void testNextTurn4() throws Exception {
        System.out.println("Test method nextTurn 4: Generate event for assigned developers");
        instance.startGame();
        instance.acceptProject(instance.getAvailableProjects().get(0).getName());
        
        Project p =instance.getProjects().getProjectList().get(0);
        Developer dev1 = instance.getDevelopers().getDeveloperList().get(0);
        Developer dev2 = instance.getDevelopers().getDeveloperList().get(1);
        Developer dev3 = instance.getDevelopers().getDeveloperList().get(2);
        Developer dev4 = instance.getDevelopers().getDeveloperList().get(3);
        Developer dev5 = instance.getDevelopers().getDeveloperList().get(4);
        
        p.assignDeveloper(dev1, p.getTypeProject());
        p.assignDeveloper(dev2, p.getTypeProject());
        p.assignDeveloper(dev3, p.getTypeProject());
        p.assignDeveloper(dev4, p.getTypeProject());
        p.assignDeveloper(dev5, p.getTypeProject());
        
        instance.nextTurn();
        
        ArrayList<Developer> devList = instance.getDevelopers().getDeveloperList();
        boolean changeEvent = false;
        for (Developer dev : devList) {
            if (dev.getEvent()!=DeveloperEvent.NOTHING) {
                changeEvent = true;
            }
        }
        assertTrue(changeEvent);
    }
    
    @Test
    public void testNextTurn5() throws Exception {
        System.out.println("Test method nextTurn 5: Generate event for unassigned developers");
        instance.startGame();
        instance.acceptProject(instance.getAvailableProjects().get(0).getName());
        
        Project p =instance.getProjects().getProjectList().get(0);
        Developer dev1 = instance.getDevelopers().getDeveloperList().get(0);
        Developer dev2 = instance.getDevelopers().getDeveloperList().get(1);
        Developer dev3 = instance.getDevelopers().getDeveloperList().get(2);
        Developer dev4 = instance.getDevelopers().getDeveloperList().get(3);
        Developer dev5 = instance.getDevelopers().getDeveloperList().get(4);
        
        instance.nextTurn();
        
        ArrayList<Developer> devList = instance.getDevelopers().getDeveloperList();
        boolean changeEvent = false;
        for (Developer dev : devList) {
            if (dev.getEvent()==DeveloperEvent.NOTHING) {
                changeEvent = true;
            }
        }
        assertTrue(changeEvent);
    }
    
    @Test
    public void testAcceptProject1() throws Exception {
        System.out.println("Test method acceptProject 1: Accept project with name in the list available project");
        instance.startGame();
       
        instance.acceptProject(instance.getAvailableProjects().get(0).getName());
        
        assertTrue(instance.getProjects().getProjectList().size()>0);
    }
}
