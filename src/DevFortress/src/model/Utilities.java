package model;

import model.skill.Skill;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Utilities {

    public static ArrayList<String> maleList, femaleList, usedNames, projectList, usedProjects;
    public static String[] coding = {"Java", "C", "CPP", "CS", "VB", "Python", "PHP", "Ruby", "Perl", "Lisp", "Haskell", "Erlang", "Prolog", "Forth"};
    public static String[] database = {"SQL", "PLSQL", "TSQL"};
    public static String[] server = {"Unix", "Windows", "Oracle"};
    public static String[] other = {"VHDL", "UIDev", "Scalability", "Document", "Performance"};
    public static String[] meta = {"Design", "Algorithm", "Analysis"};
    public static String[] personal = {"TeamPlayer", "Communication"};
    public static String[] config = {"ConfigManage"};

    /**
     * Auto generate a random developer list for hiring
     *
     * @return ArrayList<Developer>
     */
    public static ArrayList<Developer> generateDevList() {
        int availableDev;
        ArrayList<Skill> skills;
        ArrayList<Developer> devList = new ArrayList<Developer>();

        availableDev = 1 + (int) (Math.random() * 10);

        for (int i = 0; i < availableDev; i++) {
            String[] dev = generateDev();
            skills = generateSkills(1 + (int) (Math.random() * 10));
            devList.add(new Developer(dev[0], dev[1], skills));
        }
        return devList;
    }

    /**
     * Auto generate name and gender for a developer
     *
     * @return String[]{name, gender}
     */
    public static String[] generateDev() {
        boolean goodData;
        int r;
        String name, gender;

        r = (int) (Math.random() * 10);

        if (r < 7) {
            //male case: 70%
            gender = "male";
            while (true) {
                goodData = true;
                r = (int) (Math.random() * maleList.size());
                for (String usedName : usedNames) {
                    if (maleList.get(r).equalsIgnoreCase(usedName)) {
                        goodData = false;
                        break;
                    }
                }
                if (goodData) {
                    name = maleList.get(r);
                    break;
                }
            }
        } else {
            //female case: 30%
            gender = "female";
            while (true) {
                goodData = true;
                r = (int) (Math.random() * femaleList.size());
                for (String usedName : usedNames) {
                    if (femaleList.get(r).equalsIgnoreCase(usedName)) {
                        goodData = false;
                        break;
                    }
                }
                if (goodData) {
                    name = femaleList.get(r);
                    break;
                }
            }
        }

        usedNames.add(name);
        return new String[]{name, gender};
    }

    /**
     * Auto generate a list of skills for a developer
     *
     * @param numSkills number of skills will be generated
     * @return ArrayList<Skill>
     */
    public static ArrayList<Skill> generateSkills(int numSkills) {
        ArrayList<Skill> skillList = new ArrayList<Skill>();
        int r;

        while (skillList.size() < numSkills) {
            String skillname = "Unknown";
            r = 1 + (int) (Math.random() * 10);
            if (r <= 5) {
                //technical area
                if (r <= 2) {
                    //coding
                    skillname = coding[(int) (Math.random() * coding.length)];
                } else if (r <= 3) {
                    //database
                    skillname = database[(int) (Math.random() * database.length)];
                } else if (r <= 4) {
                    //server
                    skillname = server[(int) (Math.random() * server.length)];
                } else {
                    //other
                    skillname = other[(int) (Math.random() * other.length)];
                }
            } else if (r <= 7) {
                //meta area
                skillname = meta[(int) (Math.random() * meta.length)];
            } else if (r <= 9) {
                //personal area
                skillname = personal[(int) (Math.random() * personal.length)];
            } else {
                //config area
                skillname = config[(int) (Math.random() * config.length)];
            }

            if (!skillname.equals("Unknown")) {
                boolean dup = false;
                for (Skill skill : skillList) {
                    if (skill.getClass().getName().endsWith(skillname)) {
                        dup = true;
                        break;
                    }
                }

                if (!dup) {
                    try {
                        Class<?> c = Class.forName("model.skill.implement." + skillname);
                        Constructor<?> cons = c.getConstructor();
                        Skill newSkill = (Skill) cons.newInstance();

                        //random level for the skill
                        r = 1 + (int) (Math.random() * 8);
                        for (int i = 0; i < r; i++) {
                            newSkill.upLevel();
                        }

                        skillList.add(newSkill);
                    } catch (Exception e) {
                        System.err.println("generate skills list failed!");
                    }
                }
            }
        }

        return skillList;
    }

    /**
     * Auto generate a project for player
     *
     * @return Project
     */
    public static Project generateProject() {
        String name;
        int level, r, count = 0;
        Project p = null;
        level = 1 + (int) (Math.random() * 5);

        while (true) {
            boolean dup = false;
            r = (int) (Math.random() * projectList.size());
            for (String usedProject : usedProjects) {
                if (usedProject.equals(projectList.get(r))) {
                    dup = true;
                    break;
                }
            }
            if (!dup) {
                name = projectList.get(r);
                p = new Project(name, level);
                usedProjects.add(name);
                break;
            } else {
                count++;
            }

            if (count > projectList.size()) {
                name = "Mysterious Project";
                p = new Project(name, level);
                break;
            }
        }
        return p;
    }

    /**
     * Auto generate events for a developer
     *
     * @param dev the developer who take the event
     */
    public static void generateEvent(Developer dev) {
        int r = (int) (Math.random() * 142);
        if (r < 92) {
            if (r < 10) {
                dev.setEvent(Event.INTERNS);
                return;
            }
            if (r < 20) {
                dev.setEvent(Event.IDIOT_MARKETING);
                return;
            }
            if (r < 25) {
                dev.setEvent(Event.REDUNDACIES);
                return;
            }
            if (r < 30) {
                dev.setEvent(Event.EXCERISE);
                return;
            }
            if (r == 30) {
                dev.setEvent(Event.BONUS);
                return;
            }
            if (r <= 35) {
                dev.setEvent(Event.FEATURE_CUT);
                return;
            }
            if (r <= 45) {
                dev.setEvent(Event.FEATURE_CUT);
                return;
            }
            if (r <= 55) {
                dev.setEvent(Event.HOLIDAY);
                return;
            }
            if (r <= 60) {
                dev.setEvent(Event.BACKUP_FAILED);
                return;
            }
            if (r == 61) {
                dev.setEvent(Event.HACKED);
                return;
            }
            if (r <= 66) {
                dev.setEvent(Event.SOLUTION_NOT_SCALE);
                return;
            }
            if (r <= 71) {
                dev.setEvent(Event.NEW_TECH);
                return;
            }
            if (r <= 81) {
                dev.setEvent(Event.REQUIRE_CHANGE);
                return;
            }
            if (r == 82) {
                dev.setEvent(Event.KILL_DEV);
                return;
            }
            if (r <= 92) {
                dev.setEvent(Event.SICK);
            }
        } else {
            dev.setEvent(Event.NOTHING);
        }
    }

    /**
     * build a list of human name from CSV file
     */
    public static void loadNameList() {
        try {
            String[] nextLine;

            maleList = new ArrayList<String>();
            femaleList = new ArrayList<String>();
            usedNames = new ArrayList<String>();
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("nameList.csv")));
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length == 2) {
                    maleList.add(nextLine[0]);
                    femaleList.add(nextLine[1]);
                }
            }
        } catch (IOException ex) {
            System.err.println("nameList.csv is not found!");
        }
    }

    /**
     * build a list of project name from CSV file
     */
    public static void loadProjectNameList() {
        try {
            String[] nextLine;

            projectList = new ArrayList<String>();
            usedProjects = new ArrayList<String>();
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("projectList.csv")));
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                if (!nextLine[0].equals("")) {
                    projectList.add(nextLine[0]);
                }
            }
        } catch (IOException ex) {
            System.err.println("projectList.csv is not found!");
        }
    }
}
