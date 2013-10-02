/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.person;

import rmit.se2.pkg2012a.skoorti.model.util.Movable;

/**
 *
 * @author duynguyen
 */
public abstract class Staff extends Person {

    private int cost;
    private int salary;

    public Staff(String name, int age, int gender, Movable mb, int cost, int salary) {
        super(name, age, gender, mb);
        this.cost = cost;
        this.salary = salary;
    }

    public Staff(String name, int age, int gender, Movable mb) {
        super(name, age, gender, mb);
    }

    @Override
    public int move() {
        return super.move();
    }

    public int getCost() {
        return cost;
    }
    
    public int getSalary() {
        return salary;
    }

    public abstract String getImage();
}
