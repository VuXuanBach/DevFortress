/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.facade.DevModel;
import model.skill.Skill;
import java.util.ArrayList;

/**
 *
 * @author Nguyen Ba Dao
 */
public class TestGenDev {

    public static void main(String[] args) {
        DevModel model = DevModel.getInstance();
        ArrayList<Developer> devList = Utilities.generateDevList();
        System.out.println("===========================================");
        System.out.println("available developers: " + devList.size());
        System.out.println("===========================================");
        for (Developer dev : devList) {
            System.out.print(dev.getName() + " - " + dev.getGender() + "\n");
            for (Skill skill : dev.getSkills()) {
                System.out.print(skill.getName() + " - lv: " + skill.getLevel()
                        + " - current skill point: " + skill.getPointCost() + " - up level cost: $" + skill.getUpLevelCost() + "\n");
            }

            System.out.println("Hire Price: $" + dev.getHireSalary());
            System.out.println("Current Salary per Month: $" + dev.getSalary());
            System.out.println("---------------------");
        }
    }
}
