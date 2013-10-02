/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mbi;

/**
 *
 * @author DELL XPS
 */
public class ConcreteVisitor implements Visitor{

    private double total;
    
    @Override
    public void visit(Director d) {
        total += d.salary;
    }

    @Override
    public void visit(Manager d) {
        total += d.salary;
    }

    @Override
    public void visit(TechnicalArchitect d) {
        total += d.salary;
    }

    @Override
    public void visit(ConfigurationManager d) {
        total += d.salary;
    }

    @Override
    public void visit(TeamLead d) {
        total += d.salary;
    }

    @Override
    public void visit(ReleaseManager d) {
        total += d.salary;
    }

    @Override
    public void visit(SeniorDeveloper d) {
        total += d.salary;
    }

    @Override
    public void visit(Developer d) {
        total += d.salary;
    }
    
    public void display() {
        System.out.println(total);
    }    
}
