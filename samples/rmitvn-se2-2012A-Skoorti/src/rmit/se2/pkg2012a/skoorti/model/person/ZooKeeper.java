package rmit.se2.pkg2012a.skoorti.model.person;

import rmit.se2.pkg2012a.skoorti.model.util.Movable;

public class ZooKeeper extends Staff {

    public ZooKeeper(String name, int age, int gender, Movable mb, int cost, int salary) {
        super(name, age, gender, mb, cost, salary);
    }

    @Override
    public String getImage() {
        return "keeper.png";
    }
    
}
