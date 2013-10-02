/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model;

import rmit.se2.pkg2012a.skoorti.model.util.Mediator;
import java.util.Observable;
import java.util.Observer;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author zozo
 */
public class MediatorTest {
    
    private boolean result = false;
    private int check = 0;
    
    public MediatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        result = false;
        check = 0;
    }
    
    @After
    public void tearDown() {
        Mediator.clearAll();
    }

    @Test
    public void testSubscribe() {
        Mediator.subscribe("testSubscribe", new Observer(){

            @Override
            public void update(Observable o, Object o1) {
                result = true;
            }
            
        });
        Mediator.publish("testSubscribe");
        // TODO review the generated test code and remove the default call to fail.
        if (!result) {
            fail("Fail because the handler is not called");
        }
    }

    @Test
    public void testMultipleSubscribe() {
        Mediator.subscribe("testMultipleSubscribe", new Observer(){

            @Override
            public void update(Observable o, Object o1) {
                check++;
            }
            
        });
        Mediator.subscribe("testMultipleSubscribe", new Observer(){

            @Override
            public void update(Observable o, Object o1) {
                check++;
            }
            
        });
        Mediator.publish("testMultipleSubscribe");
        
        if (check == 2) {
            result = true;
        }
        
        if (!result) {
            fail("Fail because the handlers are not called");
        }
    }
    
    @Test
    public void testUnsubscribe() {
        Observer o = new Observer(){

            @Override
            public void update(Observable o, Object o1) {
                result = true;
            }
            
        };
        Mediator.subscribe("testUnsubscribe", o);
        Mediator.unsubscribe("testUnsubscribe", o);
        Mediator.publish("testUnsubscribe");
        // TODO review the generated test code and remove the default call to fail.
        if (result) {
            fail("Fail because the handler is not called");
        }
    }
    
    @Test
    public void testPublishWithParam() {
        Mediator.subscribe("testPublishWithParam", new Observer(){

            @Override
            public void update(Observable o, Object o1) {
                if (String.valueOf(o1).compareTo("test") == 0) {
                    result = true;
                }
            }
            
        });
        Mediator.publish("testPublishWithParam", "test");
        // TODO review the generated test code and remove the default call to fail.
        if (!result) {
            fail("Fail because the handler is not called");
        }
    }
    

}
