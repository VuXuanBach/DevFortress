/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.facade.DevModel;

/**
 *
 * @author Nguyen Ba Dao
 */
public class TestGenProject {

    public static void main(String[] args) {
        DevModel model = DevModel.getInstance();
//        for (String project : Utilities.projectList) {
//            System.out.println(project);
//        }
//        System.out.println("Total: " + Utilities.projectList.size() + " projects");
        
        System.out.println("====================================");
        for (int i = 0; i < 2000; i++) {
            Project p = Utilities.generateProject();
            model.getProjects().addProject(p);
            printTest(p);
        }

    }
    
    private static void printTest(Project project){
        System.out.println(project.getName() + " - Level: " + project.getLevel() + " - Main requirement: " + project.getTypeProject().getRequiredSkill().getName());
            System.out.println("Requirements:");
            for (ProjectRequirement pjReg : project.getRequirements()) {
                System.out.println("\t" + pjReg.getRequiredSkill().getName() + " - " + pjReg.getPoint() + " points");
            }
            System.out.println("Project type: " + project.getTypeProject().getRequiredSkill().getName());
            System.out.println("-------------------------");
    }
}
