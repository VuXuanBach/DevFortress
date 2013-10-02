/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package ModelTest;

import model.Developer;
import model.Project;
import model.SkillFactory;
import model.skill.Skill;
import model.skill.TechnicalSkill;
import model.skill.Type;
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
public class DeveloperTest {
    private static Developer instance;
    public DeveloperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Developer("Tester", "male");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testTotalPoint1() throws Exception {
        System.out.println("Test method totalPoint 1: Calculate developer having main skill of the project");
        Project p = new Project("Test Project 1", 5);
        Skill s = p.getTypeProject().getRequiredSkill();
        s.upLevel();
        instance.getSkills().add(s);
        long before =p.getTypeProject().getCurrentPoint();
        assertTrue(instance.totalPoint(p, p.getTypeProject())>0);
    }
    
    @Test
    public void testTotalPoint2() throws Exception {
        System.out.println("Test method totalPoint 2: Calculate developer NOT having main skill of the project");
        Project p = new Project("Test Project 1", 5);
        instance.getSkills().add(SkillFactory.createSkill("C++"));
        long before =p.getTypeProject().getCurrentPoint();
        assertTrue(instance.totalPoint(p, p.getTypeProject())>0);
    }
    
    @Test
    public void testTotalPoint3() throws Exception {
        System.out.println("Test method totalPoint 3: Calculate developer having Erlang or Forth or Haskell skill");
        Project p = new Project("Test Project 1", 5);
        instance.getSkills().add(SkillFactory.createSkill("Forth"));
        long before =p.getTypeProject().getCurrentPoint();
        assertTrue(instance.totalPoint(p, p.getTypeProject())>0);
    }
    
}
