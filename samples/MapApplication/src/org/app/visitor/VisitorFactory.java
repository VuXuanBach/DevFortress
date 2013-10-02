/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.visitor;

import java.util.Random;
import org.app.map.ZooMap;

/**
 *
 * @author Tam
 */
public class VisitorFactory {
    public Visitor createVisitor(int number, ZooMap map){
        Visitor visitor = null;
        Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(10);
        if ((number % 5)==0)
                visitor = new VIPVisitor(25,25, map, number, 100*randomInt);
        else visitor = new CasualVisitor(25,25, map, number, 50*randomInt);
        
        return visitor;
    }
}
