/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.gamestrategy;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.GameFactory;
import rmit.se2.pkg2012a.skoorti.model.animal.Food;
import rmit.se2.pkg2012a.skoorti.model.research.Research;

/**
 *
 * @author zozo
 */
public abstract class BaseGameStrategy implements GameStrategy {

    protected List<String[]> parse(String path) {
        // http://www.roseindia.net/java/beginners/java-read-file-line-by-line.shtml
        List<String> lines = new ArrayList<String>();
        List<String[]> result = new ArrayList<String[]>();
        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(path);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                lines.add(strLine);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        for (String line : lines) {
            List<String> attributes = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(line, "::");
            while (st.hasMoreTokens()) {
                attributes.add(st.nextToken());
            }
            result.add(attributes.toArray(new String[0]));
        }
        return result;
    }

    @Override
    public List<Animal> initialAnimals() {
        List<Animal> animals = new ArrayList<Animal>();
        List<String[]> animalStrings = this.parse("levels/" + this.gameName() + "/animals.txt");
        for (String[] str : animalStrings) {
            animals.add(GameFactory.getInstance().createAnimal(str));
        }
        return animals;
    }

    @Override
    public List<Building> initialBuildings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Research> initialResearches() {
        List<Research> researches = new ArrayList<Research>();
        List<String[]> researchString = this.parse("levels/" + this.gameName() + "/researches.txt");
        for (String[] str : researchString) {
            researches.add(GameFactory.getInstance().createResearch(str));
        }
        return researches;
    }
    
    @Override
    public List<Food> initialFood() {
        List<Food> foods = new ArrayList<Food>();
        List<String[]> foodString = this.parse("levels/" + this.gameName() + "/foods.txt");
        for (String[] str : foodString) {
            foods.add(GameFactory.getInstance().createFood(str));
        }
        return foods;
    }
    
    protected abstract String gameName();
}
