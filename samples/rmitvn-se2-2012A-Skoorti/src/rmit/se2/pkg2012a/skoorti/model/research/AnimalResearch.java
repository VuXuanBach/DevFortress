/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.research;

import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;

/**
 *
 * @author zozo
 */
public class AnimalResearch extends Research {
    
    private Animal animal;

    public AnimalResearch(String name, String description, int complete) {
        super(name, description, complete);
    }
    
    @Override
    public void complete() {
        Mediator.publish("game:addAnimal", this.getAnimal());
        Mediator.publish("status:research");
        Mediator.publish("game:log", this);
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public String log() {
        return this.name + " has been completed. You have a new animal called " + this.animal.getName();
    }
    
    
    
}
