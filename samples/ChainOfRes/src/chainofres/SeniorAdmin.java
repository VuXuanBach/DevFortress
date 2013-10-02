/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chainofres;

/**
 *
 * @author DELL XPS
 */
public class SeniorAdmin implements Handler {

    @Override
    public boolean solveProblems(Problem p) {
        if (p instanceof Hacked) {
            System.out.println("solved");
            return true;
        } else {
            System.out.println("pass to boss");
            if (new CTO().solveProblems(p)) {
                return true;
            }
        }
        System.out.println("not handle");
        return false;
    }
    
}
