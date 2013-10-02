/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chainofres;

/**
 *
 * @author DELL XPS
 */
public class CTO implements Handler {

    @Override
    public boolean solveProblems(Problem p) {
        if (p instanceof Reboot) {
            System.out.println("solved");
            return true;
        }
        System.out.println("not handle");
        return false;
    }
}
