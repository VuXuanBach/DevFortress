/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package model;

import model.skill.Algorithm;
import model.skill.Analysis;
import model.skill.Communication;
import model.skill.ConfigManage;
import model.skill.Design;
import model.skill.Skill;
import model.skill.TeamPlayer;
import model.skill.TechnicalSkill;
import model.skill.Type;

/**
 *
 * @author Hoang Hai
 */
public class SkillFactory {

    public static Skill createSkill(String skillName) {
        Type type = Type.EFFECT;
        Skill skill;
        for (String s : Utilities.coding) {
            if (skillName.equalsIgnoreCase(s)) {
                type = Type.CODING;
                break;
            }
        }
        for (String s : Utilities.database) {
            if (skillName.equalsIgnoreCase(s)) {
                type = Type.DATABASE;
                break;
            }
        }
        for (String s : Utilities.server) {
            if (skillName.equalsIgnoreCase(s)) {
                type = Type.SERVER;
                break;
            }
        }
        for (String s : Utilities.other) {
            if (skillName.equalsIgnoreCase(s)) {
                type = Type.EFFECT;
                break;
            }
        }

        if (skillName.equalsIgnoreCase("Lisp") || skillName.equalsIgnoreCase("Haskell") || skillName.equalsIgnoreCase("Forth")) {
            skill = new TechnicalSkill(skillName, 2, 4, 4, type);
        } else if (skillName.equalsIgnoreCase("VHDL")) {
            skill = new TechnicalSkill(skillName, 2, 2, 2, type);
        } else if (skillName.equalsIgnoreCase("Design")) {
            skill = new Design();
        } else if (skillName.equalsIgnoreCase("Algorithms")) {
            skill = new Algorithm();
        } else if (skillName.equalsIgnoreCase("Analysis")) {
            skill = new Analysis();
        } else if (skillName.equalsIgnoreCase("Team Player")) {
            skill = new TeamPlayer();
        } else if (skillName.equalsIgnoreCase("Communication")) {
            skill = new Communication();
        } else if (skillName.equalsIgnoreCase("Config Management")) {
            skill = new ConfigManage();
        } else {
            skill = new TechnicalSkill(skillName, 1, 2, 2, type);
        }
        return skill;
    }

}
