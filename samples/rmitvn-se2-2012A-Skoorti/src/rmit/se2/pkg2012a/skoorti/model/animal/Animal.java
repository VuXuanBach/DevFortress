package rmit.se2.pkg2012a.skoorti.model.animal;

import java.io.Serializable;
import java.util.List;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.model.building.Cage;
import rmit.se2.pkg2012a.skoorti.model.person.Person;
import rmit.se2.pkg2012a.skoorti.model.person.ZooKeeper;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.Costable;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;
import rmit.se2.pkg2012a.skoorti.model.util.Movable;
import rmit.se2.pkg2012a.skoorti.view.AnimalView;

public class Animal implements Costable, Cloneable, Serializable {

    private String imageName, name, description, thumbnail;
    private Cage cage;
    private ZooKeeper zooKeeper;
    private AnimalBehavior behavior;
    private int hunger, anger, type, views;
    private Movable movable;
    private float cost;
    private boolean inCage;
    private boolean attacking;
    private long timeAttacking;
    private int oldX, oldY;

    public Animal(int type) {
        this(100, 0, type, null);
    }

    public Animal(int hunger, int anger, int type) {
        this(hunger, anger, type, null);
    }

    public Animal(int hunger, int anger, int type, Movable moveBehavior) {
        this.hunger = hunger;
        this.anger = anger;
        this.type = type;
        this.views = 0;
        inCage = true;
        this.movable = moveBehavior;
    }

    public Animal() {
        this(100, 0, GameConstants.AGRESSIVE);
    }

    public Cage getCage() {
        return cage;
    }

    public AnimalBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(AnimalBehavior behavior) {
        this.behavior = behavior;
    }

    public Movable getMovable() {
        return movable;
    }

    public void setMoveBehavior(Movable moveBehavior) {
        this.movable = moveBehavior;
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void setCage(Cage cage) {
        this.cage = cage;
    }

    public Coordinate getCurrentCoordinate() {
        return movable.getCoordinate();
    }

    public float getAnimalCost() {
        return cost;
    }

    public int getViews() {
        return views;
    }

    public void incrementViews() {
        this.views++;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isFined() {
        return inCage;
    }

    public void setFined(boolean fined) {
        this.inCage = fined;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int getType() {
        return type;
    }

    public void feed(Food f) {
        if (hunger >= 100) {
            Mediator.publish("animal:feed", "Dont feed me anymore, I am full already");
            return;
        }

        int v = f.getValue();
        // should implement some algorithms here to make the game harder or easier
        anger -= v;
        if (anger <= 0) {
            anger = 0;
        }
        hunger += v;
        if (hunger >= 100) {
            hunger = 100;
        }
        Mediator.publish("animal:feed", f);
    }

    public void updateStatus() {
        int decreaseAmount = behavior.getDecreaseAmount();
        hunger -= decreaseAmount;
        anger += decreaseAmount;
        attacking = false;
        if (inCage) {
            oldX = movable.getCoordinate().getX();
            oldY = movable.getCoordinate().getY();
        }

        if (hunger <= 0) {
            hunger = 0;
        }

        if (anger >= 100) {
            anger = 100;
        }

        if (zooKeeper != null) {
            hunger += decreaseAmount;
            anger -= decreaseAmount;
        }

        if (hunger == 0) {
            //Mediator.publish("animal:angry", this);
        }

        if (anger >= 100 && inCage) {
            Mediator.publish("animal:escape", this);
            cage.setOpened(true);
            anger = 100;
            inCage = false;
            movable.setSpeed(8);

            int x = movable.getCoordinate().getX();
            int y = movable.getCoordinate().getY();
            switch (cage.getDirection()) {
                case GameConstants.UP:
                    movable.getCoordinate().setY(y - 32);
                    break;
                case GameConstants.DOWN:
                    movable.getCoordinate().setY(y + 32 * 4);
                    break;
                case GameConstants.RIGHT:
                    movable.getCoordinate().setX(x + 32 * 4);
                    break;
                case GameConstants.LEFT:
                    movable.getCoordinate().setX(x - 32);
                    break;
                default:
                    inCage = true;
                    break;
            }
            return;
        }

        if (hunger < behavior.getHungerLevel()) {
            // notifies that the animal is hungry and need to be fed
            Mediator.publish("animal:hungry", this);
            return;
        }

        if (anger <= 0 && !inCage) {
            anger = 0;
            inCage = true;
            attacking = false;
            cage.setOpened(false);
            movable.setSpeed(0);
            movable.getCoordinate().setX(oldX);
            movable.getCoordinate().setY(oldY);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAnger() {
        return anger;
    }

    public void setAnger(int anger) {
        this.anger = anger;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public boolean isHungry() {
        return hunger <= behavior.getHungerLevel();
    }

    public boolean isAngry() {
        return anger > behavior.getAngerLevel();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int move() {
        movable.move(null);
//        if (System.currentTimeMillis() - timeAttacking > 2000)
//            attacking = false;
        attackPeople();
        return movable.getDirection();
    }

    @Override
    public float getCost() {
        return cost + cage.getCost();
    }

    @Override
    public void setCost(float cost) {
        this.cost = cost;
    }

    private void attackPeople() {
        List<Person> persons = GameMemoryStorage.getInstance().getPersonList();
        for (Person p : persons) {
            if (!p.isBurn() && isKillingPeople(movable, p.getMb().getCoordinate())) {
                attacking = true;
                p.setBurn(true);
                hunger += 20;
                anger -= 30;
            }
        }
    }

//    public static void main (String[] args) {
//        Movable m = new NormalMoveBehavior(new Coordinate(96, 96), 8);
//        Coordinate vc = new Coordinate(97, 95);
//        System.out.println(isKillingPeople(m, vc));
//    }
    private static boolean isKillingPeople(Movable animalMovable, Coordinate pCoord) {
        Coordinate aCoord = animalMovable.getCoordinate();
        int animalDirection = animalMovable.getDirection();
        int[] aMove = GameConstants.MOVEDELTA[animalDirection];
        int[] limits = new int[4];
        limits[animalDirection] = (aMove[0] + aMove[1]) * GameConstants.SAFE_DISTANCE;
        limits[(animalDirection + 2) % 4] = 0;
        aMove = GameConstants.MOVEDELTA[(animalDirection + 1) % 4];
        limits[(animalDirection + 1) % 4] = (aMove[0] + aMove[1]) * GameConstants.SAFE_DISTANCE;
        aMove = GameConstants.MOVEDELTA[(animalDirection + 3) % 4];
        limits[(animalDirection + 3) % 4] = (aMove[0] + aMove[1]) * GameConstants.SAFE_DISTANCE;
//        for (int i = 0; i < limits.length; i++) {
//            System.out.println("limit[" + i + "] = " + limits[i]);
//        }
        limits[0] += aCoord.getY();
        limits[1] += aCoord.getX();
        limits[2] += aCoord.getY();
        limits[3] += aCoord.getX();
        if (pCoord.getX() > limits[1] && pCoord.getX() < limits[3] && pCoord.getY() > limits[0] && pCoord.getY() < limits[2]) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Animal other = (Animal) obj;
        if ((this.imageName == null) ? (other.imageName != null) : !this.imageName.equals(other.imageName)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if ((this.thumbnail == null) ? (other.thumbnail != null) : !this.thumbnail.equals(other.thumbnail)) {
            return false;
        }
        if (this.cage != other.cage && (this.cage == null || !this.cage.equals(other.cage))) {
            return false;
        }
        if (this.behavior != other.behavior && (this.behavior == null || !this.behavior.equals(other.behavior))) {
            return false;
        }
        if (this.hunger != other.hunger) {
            return false;
        }
        if (this.anger != other.anger) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.views != other.views) {
            return false;
        }
        if (this.movable != other.movable && (this.movable == null || !this.movable.equals(other.movable))) {
            return false;
        }
        if (Float.floatToIntBits(this.cost) != Float.floatToIntBits(other.cost)) {
            return false;
        }
        if (this.inCage != other.inCage) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.imageName != null ? this.imageName.hashCode() : 0);
        hash = 11 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 11 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 11 * hash + (this.thumbnail != null ? this.thumbnail.hashCode() : 0);
        hash = 11 * hash + (this.cage != null ? this.cage.hashCode() : 0);
        hash = 11 * hash + (this.behavior != null ? this.behavior.hashCode() : 0);
        hash = 11 * hash + this.hunger;
        hash = 11 * hash + this.anger;
        hash = 11 * hash + this.type;
        hash = 11 * hash + this.views;
        hash = 11 * hash + (this.movable != null ? this.movable.hashCode() : 0);
        hash = 11 * hash + Float.floatToIntBits(this.cost);
        hash = 11 * hash + (this.inCage ? 1 : 0);
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
