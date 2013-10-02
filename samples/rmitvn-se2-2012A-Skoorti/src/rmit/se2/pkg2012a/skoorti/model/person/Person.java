/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmit.se2.pkg2012a.skoorti.model.person;

import java.io.Serializable;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.Movable;

/**
 *
 * @author duynguyen
 */
public abstract class Person implements Serializable{

    private String name;
    private int age;
    private int gender;
    private Movable mb;
    private boolean burn;
    private boolean dead;

    public Person() {
    }

    public Person(String name, int age, int gender, Movable mb) {
        super();
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mb = mb;
    }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

    public Movable getMb() {
        return mb;
    }

    public void setMb(Movable mb) {
        this.mb = mb;
    }

    public int getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public boolean isBurn() {
        return burn;
    }

    public void setBurn(boolean burn) {
        this.burn = burn;
        if(burn){
            mb.setSpeed(0);
        }
    }
    
    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Uses to render the view of the person in the map
     *
     * @return Coordinate
     */
    public Coordinate getCurrentCoordinate() {
        return mb.getCoordinate();
    }

    public int move() {
        return mb.move(null).getDirection();
    }

    public abstract String getImage();

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Person other = (Person) obj;
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    if (this.age != other.age) {
      return false;
    }
    if (this.gender != other.gender) {
      return false;
    }
    if (this.mb.getCoordinate() != other.mb.getCoordinate() && 
            (this.mb == null || this.mb.getCoordinate()==null || 
            !this.mb.getCoordinate().equals(other.mb.getCoordinate()))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
    hash = 47 * hash + this.age;
    hash = 47 * hash + this.gender;
    hash = 47 * hash + (this.mb.getCoordinate() != null ? this.mb.getCoordinate().hashCode() : 0);
    return hash;
  }
}
