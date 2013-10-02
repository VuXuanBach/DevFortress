/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.research;

import java.util.Observable;
import java.util.Observer;
import org.junit.*;
import static org.junit.Assert.*;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;

/**
 *
 * @author zozo
 */
public class AnimalResearchTest {
    
    private AnimalResearch animalResearch;
    
    public AnimalResearchTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        animalResearch = new AnimalResearch("Test", "Test", 10);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of complete method, of class AnimalResearch.
     */
    @Test
    public void testComplete() {
        final StringBuilder sb = new StringBuilder();
        Mediator.subscribe("game:addAnimal", new Observer(){

            @Override
            public void update(Observable o, Object o1) {
                // o1 should be null because we dont use animal in test cases
                if (o1 != null) {
                    sb.append("should be null because we dont use animal in test cases").append("\n");
                }
            }
            
        });
        
        animalResearch.complete();
        
        if (sb.length() > 0) {
            fail(sb.toString());
        }
    }
}
