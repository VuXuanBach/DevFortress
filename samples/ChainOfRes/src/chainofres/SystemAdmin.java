/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chainofres;

/**
 *
 * @author DELL XPS
 */
public class SystemAdmin implements Handler {

    @Override
    public boolean solveProblems(Problem p) {
        if (p instanceof Overload) {
            System.out.println("solved");
            return true;
        } else {
            System.out.println("pass to boss");
            if (new SeniorAdmin().solveProblems(p)) {
                return true;
            }
        }
        System.out.println("not handle");
        return false;
    }
}
