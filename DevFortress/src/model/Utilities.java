package model;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import model.facade.DevModel;
import model.skill.Skill;
import model.skill.Type;
import view.util.ViewUtilities;

public class Utilities implements Serializable {

    public static ArrayList<String> maleList, femaleList, usedNames, projectList, usedProjects;
    public static String[] coding = {"Java", "C", "C++", "C#", "VB", "Python", "PHP", "Ruby", "Perl", "Lisp", "Haskell", "Erlang", "Prolog", "Forth"};
    public static String[] database = {"SQL", "PL/SQL", "T-SQL"};
    public static String[] server = {"Unix", "Windows", "Oracle"};
    public static String[] other = {"VHDL", "UI Development", "Scalability", "Documentation", "Performance"};
    public static String[] meta = {"Design", "Algorithms", "Analysis"};
    public static String[] personal = {"Team Player", "Communication"};
    public static String[] config = {"Config Management"};
    public static final int PROJECT_IMAGE = 18;
    public static final int MALE_IMAGE = 42;
    public static final int FEMALE_IMAGE = 34;

    public Utilities() {
    }

    /**
     * Auto generate a random developer list for hiring
     *
     * @return ArrayList<Developer>
     */
    public static ArrayList<Developer> generateDevList() {
        int availableDev;

        ArrayList<Developer> devList = new ArrayList<Developer>();

        availableDev = 1 + (int) (Math.random() * 10);

        for (int i = 0; i < availableDev; i++) {
            devList.add(generateDeveloper());
        }
        return devList;
    }

    public static Developer generateDeveloper() {
        String[] dev = generateDevInfo();
        ArrayList<Skill> skills;
        skills = generateSkills(1 + (int) (Math.random() * 10));
        Developer developer = new Developer(dev[0], dev[1], skills);
        ImageIcon image, avatar;
        if (dev[1].equalsIgnoreCase("female")) {
            int num = 1 + (int) (Math.random() * FEMALE_IMAGE);
            image = ViewUtilities.makeIcon("characters/female" + num + ".png");
            avatar = ViewUtilities.makeIcon("characters/female" + num + "a.png");
        } else {

            int num = 1 + (int) (Math.random() * MALE_IMAGE);
            image = ViewUtilities.makeIcon("characters/male" + num + ".png");
            avatar = ViewUtilities.makeIcon("characters/male" + num + "a.png");
        }
        developer.setImage(image);
        developer.setAvatar(avatar);
        return developer;
    }

    public static ArrayList<String> getAllSkill() {
        ArrayList<String> skill = new ArrayList<>();
        skill.addAll(Arrays.asList(coding));
        skill.addAll(Arrays.asList(database));
        skill.addAll(Arrays.asList(server));
        skill.addAll(Arrays.asList(other));
        skill.addAll(Arrays.asList(meta));
        skill.addAll(Arrays.asList(personal));
        skill.addAll(Arrays.asList(config));
        return skill;
    }

    /**
     * Auto generate name and gender for a developer
     *
     * @return String[]{name, gender}
     */
    public static String[] generateDevInfo() {
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
            Type type = Type.EFFECT;
            r = 1 + (int) (Math.random() * 10);
            if (r <= 5) {
                //technical area
                if (r <= 2) {
                    //coding
                    skillname = coding[(int) (Math.random() * coding.length)];
                    type = Type.CODING;
                } else if (r <= 3) {
                    //database
                    skillname = database[(int) (Math.random() * database.length)];
                    type = Type.DATABASE;
                } else if (r <= 4) {
                    //server
                    skillname = server[(int) (Math.random() * server.length)];
                    type = Type.SERVER;
                } else {
                    //other
                    skillname = other[(int) (Math.random() * other.length)];
                    type = Type.OTHER;
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
                        Skill s = SkillFactory.createSkill(skillname);

//                        Class<?> c = Class.forName("model.skill.implement." + skillname);
//                        Constructor<?> cons = c.getConstructor();
//                        Skill newSkill = (Skill) cons.newInstance();

                        //random level for the skill
                        r = 1 + (int) (Math.random() * 8);
                        for (int i = 0; i < r; i++) {
                            s.upLevel();
                        }

                        skillList.add(s);
                    } catch (Exception e) {
                        e.printStackTrace();
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
        Project project = null;
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
                project = new Project(name, level);
                usedProjects.add(name);
                break;
            } else {
                count++;
            }

            if (count > projectList.size()) {
                name = "Mysterious Project";
                project = new Project(name, level);
                break;
            }
        }
        int num = 1 + (int) (Math.random() * PROJECT_IMAGE);
        project.setImage(ViewUtilities.makeIcon("projects/proj" + num + ".png"));
        return project;
    }

    /**
     * Auto generate events for a developer
     *
     * @param dev the developer who take the event
     */
    public static void generateEvent(Developer dev) {
        int r = (int) (Math.random() * 132);
        if (r < 82) {
            if (r < 10) {
                dev.setEvent(DeveloperEvent.INTERNS);
                DevModel.getInstance().getEventLog().add(dev.getName() + " has a intership student to help with project");
                return;
            }
            if (r < 20) {
                dev.setEvent(DeveloperEvent.IDIOT_MARKETING);
                DevModel.getInstance().getEventLog().add("Some idiot market the project that " + dev.getName() + " is working");
                return;
            }
            if (r < 25) {
                dev.setEvent(DeveloperEvent.REDUNDACIES);
                DevModel.getInstance().getEventLog().add("One developer in " + dev.getName() + "'s team will be removed");
                return;
            }
            if (r < 30) {
                dev.setEvent(DeveloperEvent.EXCERISE);
                DevModel.getInstance().getEventLog().add("All team members of " + dev.getName() + "'s team have a team exercise and are not focused to the project");
                return;
            }
            if (r == 30) {
                dev.setEvent(DeveloperEvent.BONUS);
                DevModel.getInstance().getEventLog().add("Because of excellent " + dev.getName() + "'s work, the company will receive some extra money");
                return;
            }
            if (r <= 35) {
                dev.setEvent(DeveloperEvent.FEATURE_CUT);
                DevModel.getInstance().getEventLog().add("Customer of " + dev.getName() + "'s project allow to reduce one area in the project");
                return;
            }
            if (r <= 45) {
                dev.setEvent(DeveloperEvent.HOLIDAY);
                DevModel.getInstance().getEventLog().add(dev.getName() + " has a holiday without working on the project");
                return;
            }
            if (r <= 50) {
                dev.setEvent(DeveloperEvent.BACKUP_FAILED);
                DevModel.getInstance().getEventLog().add(dev.getName() + " face a failed backup and need to rework some fields");
                return;
            }
            if (r == 51) {
                dev.setEvent(DeveloperEvent.HACKED);
                DevModel.getInstance().getEventLog().add("The company has been hacked by Anonymous hackers. All projects are halted");
                return;
            }
            if (r <= 56) {
                dev.setEvent(DeveloperEvent.SOLUTION_NOT_SCALE);
                DevModel.getInstance().getEventLog().add("Solution for " + dev.getName() + "'s team is not scaled");
                return;
            }
            if (r <= 61) {
                dev.setEvent(DeveloperEvent.NEW_TECH);
                DevModel.getInstance().getEventLog().add(dev.getName() + "'s team use new technology to solve the project problem");
                return;
            }
            if (r <= 71) {
                dev.setEvent(DeveloperEvent.REQUIRE_CHANGE);
                DevModel.getInstance().getEventLog().add("Project of " + dev.getName() + "'s team has some changes in the requirement");
                return;
            }
            if (r == 72) {
                dev.setEvent(DeveloperEvent.KILL_DEV);
                DevModel.getInstance().getEventLog().add(dev.getName() + " KILLS SOMEONE !!! Everyone in team are too scared to work");
                return;
            }
            dev.setEvent(DeveloperEvent.SICK);
            DevModel.getInstance().getEventLog().add(dev.getName() + " catchs a cold and need to rest at home.");
        } else {
            dev.setEvent(DeveloperEvent.NOTHING);
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

    public static String format2DecimalDouble(Double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value);
    }
}
