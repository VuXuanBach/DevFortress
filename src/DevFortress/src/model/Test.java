/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
package model;

import model.skill.implement.*;
import model.skill.Skill;
import model.skill.Type;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;

public class Test {

    public static String[] coding = {"Java", "C", "CPP", "CS", "VB", "Python", "PHP", "Ruby", "Perl", "Lisp", "Haskell", "Erlang", "Prolog", "Forth"};
    public static String[] database = {"SQL", "PLSQL", "TSQL"};
    public static String[] server = {"Unix", "Windows", "Oracle"};
    public static String[] other = {"UIDev", "Scalability", "Document", "Performance", "VDHL"};
    private static int level, weekLength;
    private static ArrayList<ProjectRequirement> requirements;

    public static void main(String[] args) throws Exception {
//        level = 5;
//        requirements = generateRequirement();
//        addPointRequirement();
//        for (int i = 0; i < requirements.size(); i++) {
//            System.out.println(requirements.get(i).getRequiredSkill().getName() + "     -     " + requirements.get(i).getPoint());
//        }

        String a = "Ac";
        String b = "Acd";
        Developer p = new Developer("Le Hoang Hai", "male");
        Utilities.generateEvent(p);
        System.out.println(p.getEvent());
    }

    private static ArrayList<ProjectRequirement> generateRequirement() {
        ArrayList<ProjectRequirement> requirements = new ArrayList<>();
        Type[] types = {Type.CODING, Type.DATABASE, Type.CODING, Type.SERVER, Type.CODING, Type.OTHER};
        Random r = new Random();
        int requirement = 0;
        switch (level) {
            case 1:
                requirement = 1 + (int) (Math.random() * 4);
                break;
            case 2:
                requirement = 1 + (int) (Math.random() * 8);
                break;
            case 3:
                requirement = 6 + (int) (Math.random() * 7);
                break;
            case 4:
                requirement = 12 + (int) (Math.random() * 13);
                break;
            case 5:
                requirement = 1 + (int) (Math.random() * 25);
                break;
            default:
                requirement = 0;
        }
        System.out.println(requirement);
        for (int i = 0; i < requirement; i++) {
            boolean flag = false;
            ProjectRequirement pr;
            do {
                //Random required skill and check if duplicate or not. If skill is duplicated, random again
                pr = new ProjectRequirement(generateTechnicalSkill(types[r.nextInt(types.length)]));
                for (int j = 0; j < requirements.size(); j++) {
                    //Compare existed required skill with the new required skill (same class or not)
                    if (requirements.get(j).getRequiredSkill().getClass().equals(pr.getRequiredSkill().getClass())) {
                        flag = true;
                        break;
                    }
                    if (j == requirements.size() - 1) {
                        flag = false;
                    }
                }

            } while (flag);
            requirements.add(pr);
        }
        return requirements;
    }

    private static Skill generateTechnicalSkill(Type typeSkill) {
        Skill skill = null;
        int count = 0;
        while (count < 10) {
            String skillname = "Unknown";
            if (typeSkill.equals(Type.CODING)) {
                skillname = Utilities.coding[(int) (Math.random() * Utilities.coding.length)];
            }
            if (typeSkill.equals(Type.DATABASE)) {
                skillname = Utilities.database[(int) (Math.random() * Utilities.database.length)];
            }
            if (typeSkill.equals(Type.SERVER)) {
                skillname = Utilities.server[(int) (Math.random() * Utilities.server.length)];
            }
            if (typeSkill.equals(Type.OTHER)) {
                skillname = Utilities.other[(int) (Math.random() * Utilities.other.length)];
            }

            try {
                Class<?> c = Class.forName("model.skill.implement." + skillname);
                Constructor<?> cons = c.getConstructor();
                skill = (Skill) cons.newInstance();
                return skill;
            } catch (Exception e) {
                count++;
            }
        }
        return null;
    }

    private static void addPointRequirement() {
        int total = generateTotalPoint();
        for (int i = 0; i < requirements.size(); i++) {
            if (i == requirements.size() - 1) {
                requirements.get(i).setPoint(total);
                total = 0;
            } else {
                boolean goodData = true;
                while (goodData) {
                    int part = 2 + (int) (Math.random() * 9);
                    if (total / part != 0 && (total - total / part > requirements.size() - i - 1)) {
                        requirements.get(i).setPoint(total / part);
                        total -= total / part;
                        goodData = false;
                    } else {
                        if (total == requirements.size() - i - 1) {
                            requirements.get(i).setPoint(1);
                            total -= 1;
                            goodData = false;
                        }
                    }
                }
            }
        }
    }

    private static int generateTotalPoint() {
        int point = 0;
        switch (level) {
            case 1:
                weekLength = 1 + (int) (Math.random() * 16);
                point = requirements.size() * level * level + (int) ((Math.random() * 25) * weekLength);
                break;
            case 2:
                weekLength = 1 + (int) (Math.random() * 32);
                point = requirements.size() * level * level + (int) ((10 + (Math.random() * 15)) * weekLength);
                break;
            case 3:
                weekLength = 24 + (int) (Math.random() * 25);
                point = requirements.size() * level * level + (int) ((25 + (Math.random() * 25)) * weekLength);
                break;
            case 4:
                weekLength = 48 + (int) (Math.random() * 49);
                point = requirements.size() * level * level + (int) ((40 + (Math.random() * 10)) * weekLength);
                break;
            case 5:
                weekLength = 1 + (int) (Math.random() * 96);
                point = requirements.size() * level * level + (int) ((50 + (Math.random() * 50)) * weekLength);
                break;
            default:
                weekLength = 1;
                point = 1 + (int) ((Math.random() * 100) * weekLength / 4);
                break;
        }
        System.out.println(point + "  -   week:" + weekLength);
        return point;
    }
}
