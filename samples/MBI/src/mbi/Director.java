/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mbi;

import java.util.ArrayList;

/**
 *
 * @author DELL XPS
 */
public class Director implements Reportee {
    
    public double salary;
    
    @Override
    public void report(Visitor v) {
        v.visit(this);
    }    
}
