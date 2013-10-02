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
public class MBI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Reportee> reporters = new ArrayList<>();
        ConcreteVisitor v = new ConcreteVisitor();

        


        Director d = new Director();
        d.salary = 1000;
reporters.add(d);
        reporters.add(
                new Manager(900));
        reporters.add(
               new TechnicalArchitect(800));
        reporters.add(
                new ConfigurationManager(700));
        reporters.add(
                new TeamLead(600));
        reporters.add(
                new ReleaseManager(700));
        reporters.add(
                new SeniorDeveloper(500));
        reporters.add(
                new Developer(400));

        for (Reportee r : reporters) {
            r.report(v);
        }
        

       v.display();
    }
}