/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chainofres;

/**
 *
 * @author DELL XPS
 */
public class ChainOfRes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Problem p = new Overload();
        Handler h = new SystemAdmin();
        h.solveProblems(p);        
        
        p = new Hacked();
        h.solveProblems(p);
        
        p = new Reboot();
        h.solveProblems(p);
        
    }
}
