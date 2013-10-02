/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mbi;

/**
 *
 * @author DELL XPS
 */
public class Developer implements Reportee {
    
    public double salary;

    public Developer(double salary) {
        this.salary = salary;
    }

    @Override
    public void report(Visitor v) {
        v.visit(this);
    }
}
