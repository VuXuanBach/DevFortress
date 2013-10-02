/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.person;

import java.util.List;
import rmit.se2.pkg2012a.skoorti.model.util.Movable;
import rmit.se2.pkg2012a.skoorti.model.person.Staff;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;

/**
 *
 * @author duynguyen
 */
public class Clown extends Staff {
    //style of clown determines how much fun visitor gets after meeting clown

    private int style;

    public Clown(String name, int age, int gender, Movable mb, int cost, int salary, int style) {
        super(name, age, gender, mb, cost, salary);
        this.style = style;
    }
    
    public int move() {
        int direction = super.move();
        lookAround();
        return direction;
    }

    public int getStyle() {
        return style;
    }

    public String getImage() {
        return "clown.png";
    }

    private void lookAround() {
        Coordinate clownCoor = this.getMb().getCoordinate();
        List<Person> personList = GameMemoryStorage.getInstance().getPersonList();
        for (Person person : personList) {
            Coordinate pCoor = person.getMb().getCoordinate();
            if (person instanceof Visitor && person.getMb().getSpeed()!=0 && person.getMb().getStopTime()>10000 &&
                    pCoor.getX()>clownCoor.getX()-16 && pCoor.getX()<clownCoor.getX()+16
                    && pCoor.getY()>clownCoor.getY()-16 && pCoor.getY()<clownCoor.getY()+16) {
                ((Visitor)person).gainFun(5);
//                super.getMb().stop();
//                person.getMb().stop();
            }
        }
    }
}
