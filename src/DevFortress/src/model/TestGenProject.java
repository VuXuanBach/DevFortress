/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Nguyen Ba Dao
 */
public class TestGenProject {

    public static void main(String[] args) {
        DevModel model = new DevModel();
//        for (String project : Utilities.projectList) {
//            System.out.println(project);
//        }
//        System.out.println("Total: " + Utilities.projectList.size() + " projects");

        for (int i = 0; i < 2000; i++) {
            model.getProjects().addProject(Utilities.generateProject());
        }
        System.out.println("====================================");
        for (Project project : model.getProjects().getProjectList()) {
            System.out.println(project.getName() + " - Level: " + project.getLevel() + " - Main requirement: " + project.getTypeProject().getRequiredSkill().getName());
            System.out.println("Requirements:");
            for (ProjectRequirement pjReg : project.getRequirements()) {
                System.out.println("\t" + pjReg.getRequiredSkill().getName() + " - " + pjReg.getPoint() + " points");
            }
            System.out.println("Project type: " + project.getTypeProject().getRequiredSkill().getName());
            System.out.println("-------------------------");

        }
    }
}
