/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model;

import rmit.se2.pkg2012a.skoorti.model.util.Mediator;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import java.util.Observable;
import java.util.Observer;
import org.junit.*;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.animal.AnimalMoveBehavior;
import rmit.se2.pkg2012a.skoorti.model.animal.NormalAnimalBehavior;
import rmit.se2.pkg2012a.skoorti.model.building.Cage;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;

/**
 *
 * @author zozo
 */
public class AnimalTest {
    
    private boolean result = false;
    private Animal animal;
    
    public AnimalTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        animal = new Animal(2,98,GameConstants.AGRESSIVE);
        animal.setBehavior(new NormalAnimalBehavior());
        animal.setMoveBehavior(new AnimalMoveBehavior());
        animal.setCage(new Cage("Test", new Coordinate(0,0), 100, 200));
        result = false;
    }
    
    @After
    public void tearDown() {
        
    }

    @Test
    public void testHungry() throws InterruptedException, Exception {
        int check = 0;
        Mediator.subscribe("animal:hungry", new Observer() {
            @Override
            public void update(Observable o, Object o1) {
                result = true;
            }
        });
        while(!result) {
            Thread.sleep(2000);
            try {
                animal.updateStatus();
            } catch (ArrayIndexOutOfBoundsException e) {
                
            }
            check++;
            if (check > 5) {
                throw new Exception("Timeout, something's wrong");
            }
        }
    }
    
    @Test
    public void testEscape() throws InterruptedException, Exception {
        int check = 0;
        Mediator.subscribe("animal:escape", new Observer() {
            @Override
            public void update(Observable o, Object o1) {
                result = true;
            }
        });
        while(!result) {
            Thread.sleep(2000);
            try {
                animal.updateStatus();
            } catch (ArrayIndexOutOfBoundsException e) {
                
            }
            check++;
            if (check > 5) {
                throw new Exception("Timeout, something's wrong");
            }
        }
    }
}
