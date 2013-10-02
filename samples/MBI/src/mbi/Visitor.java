/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mbi;

/**
 *
 * @author DELL XPS
 */
public interface Visitor {
    void visit(Director d);
    void visit(Manager d);
    void visit(TechnicalArchitect d);
    void visit(ConfigurationManager d);
    void visit(TeamLead d);
    void visit(ReleaseManager d);
    void visit(SeniorDeveloper d);
    void visit(Developer d);
}