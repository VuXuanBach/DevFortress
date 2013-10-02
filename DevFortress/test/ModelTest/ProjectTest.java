/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package ModelTest;

import model.Developer;
import model.Project;
import model.Utilities;
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
public class ProjectTest {

    Project instance;

    public ProjectTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Utilities.loadProjectNameList();
        Utilities.loadNameList();
        instance = Utilities.generateProject();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAssignDeveloper1() throws Exception {
        System.out.println("Test method assignDeveloper 1: Assign a free developer to project");
        Developer dev = Utilities.generateDevList().get(0);
        assertTrue(instance.assignDeveloper(dev, instance.getTypeProject()));
    }

    @Test
    public void testAssignDeveloper2() throws Exception {
        System.out.println("Test method assignDeveloper 2: Assign a busy developer to project");
        Developer dev = Utilities.generateDevList().get(0);
        dev.setAssignedProject(new Project("Test Project", 1));
        assertFalse(instance.assignDeveloper(dev, instance.getTypeProject()));
    }

    @Test
    public void testRemoveDeveloper1() throws Exception {
        System.out.println("Test method removeDeveloper 1: Remove developer from project");
        Developer dev = Utilities.generateDevList().get(0);
        instance.assignDeveloper(dev, instance.getTypeProject());       
        assertTrue(instance.removeDeveloper(dev.getName()));
    }

    @Test
    public void testRemoveDeveloper2() throws Exception {
        System.out.println("Test method assignDeveloper 2: Remove unknown developer from project");
        Developer dev = Utilities.generateDevList().get(0);
        assertFalse(instance.removeDeveloper(dev.getName()));
    }
}
