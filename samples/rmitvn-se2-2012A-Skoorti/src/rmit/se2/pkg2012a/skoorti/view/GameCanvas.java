package rmit.se2.pkg2012a.skoorti.view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmit.se2.pkg2012a.skoorti.GameConstants;
import rmit.se2.pkg2012a.skoorti.GameLoad;
import rmit.se2.pkg2012a.skoorti.model.GameMap;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.animal.Fire;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.building.Cage;
import rmit.se2.pkg2012a.skoorti.model.person.*;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;
import rmit.se2.pkg2012a.skoorti.view.util.BufferedImageLoader;
import rmit.se2.pkg2012a.skoorti.view.util.InputManager;
import rmit.se2.pkg2012a.skoorti.view.util.SpriteSheet;
import rmit.se2.pkg2012a.skoorti.view.util.SpriteSheetManager;

public class GameCanvas extends Canvas implements Serializable {

    //Double buffering
    private Image dbImage;
    private Graphics dbg;
    //JPanel variables
    static final Dimension gameDim = new Dimension(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
    //input manager
    private InputManager inputManager;
    //Game Objects
    private GameMapView map;
    public static Building currentSelectedBuilding;
    public Person currentPerson;
    private boolean building;
    //Animator
    private ArrayList<PersonView> people;
    private ArrayList<BuildingView> buildings;
    private ArrayList<AnimalView> animals;
    // Navigator
    private GameNavigator navigator;
    //BufferedImage variables
    private BufferedImageLoader loader;
    private SpriteSheet availableImage;
    private SpriteSheet unavailableImage;

    public GameCanvas(GameMapView map) {
        this.map = map;
        navigator = new GameNavigator(map);
        people = new ArrayList<PersonView>();
        buildings = new ArrayList<BuildingView>();
        animals = new ArrayList<AnimalView>();

        initInputManager();
        initBufferedImage();

        setPreferredSize(gameDim);
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocus();
    }

    public GameMapView getMapView() {
        return map;
    }

    public GameNavigator getNavigator() {
        return navigator;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public ArrayList<PersonView> getPeople() {
        return people;
    }

    public ArrayList<BuildingView> getBuildings() {
        return buildings;
    }

    public ArrayList<AnimalView> getAnimals() {
        return animals;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public GameCanvas checkOutOfMap() {
        synchronized (people) {
            for (int i = 0; i < people.size(); i++) {
                if (people.get(i).getModel().isDead()) {
                    Mediator.publish("game:removePerson", people.remove(i).getModel());
                    continue;
                }
                Coordinate c = people.get(i).getModel().getMb().getCoordinate();
                int w = GameMap.getInstance().getWidth() * 32, h = GameMap.getInstance().getHeight() * 32;
                if (c.getX() > w || c.getY() > h || c.getX() < 0 || c.getY() < 0) {
                    Mediator.publish("game:removePerson", people.remove(i).getModel());
                }
                for (Coordinate gate : GameMap.getInstance().getGates()) {
                    if (c.equals(gate)) {
                        Mediator.publish("game:removePerson", people.remove(i).getModel());
                    }
                }
            }
        }
        return this;
    }

    public GameCanvas checkAnimalIsSold() {
        synchronized (animals) {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i).getModel().getCage().isSold()) {
                    Mediator.publish("game:removeAnimal", animals.remove(i).getModel());
                }
            }
        }
        return this;
    }

    /**
     * selects the person on the map
     *
     * @param x cursor position x
     * @param y cursor position y
     * @return true if there is a person at the cursor position
     */
    public boolean selectPerson(int x, int y) {
        synchronized (people) {
            for (PersonView personView : people) {
                personView.setSelected(false);
                int px = personView.getModel().getCurrentCoordinate().getX();
                int py = personView.getModel().getCurrentCoordinate().getY();
                if (x >= px && x <= px + 32 && y >= py && y <= py + 32) {
                    currentPerson = personView.getModel();
                    personView.setSelected(true);
                    Mediator.publish("status:personDetail", personView.getModel());
                    return true;
                }
            }
        }
        Mediator.publish("status:personDetail", null);
        return false;
    }

    /**
     * selects the building on the map
     *
     * @param x cursor position x
     * @param y cursor position y
     * @return true if there is a building at the cursor position
     */
    public boolean selectBuilding(int x, int y) {
        synchronized (buildings) {
            for (BuildingView buildingView : buildings) {
                Building b = buildingView.getModel();
                int px = b.getCurrentCoordinate().getX();
                int py = b.getCurrentCoordinate().getY();
                if (x >= px && x <= px + b.getWidth() && y >= py && y <= py + b.getHeight()) {
                    Object[] selectionValues = null;
                    String initialSelection = "";
                    if (b.isUpdatable()) {
                        selectionValues = new Object[]{"Update", "Sell"};
                        initialSelection = "Update";
                    } else {
                        selectionValues = new Object[]{"Sell"};
                        initialSelection = "Sell";
                    }
                    Object selection = JOptionPane.showInputDialog(null,
                            "What would you like to do with this " + b.getName() + "?",
                            "Building options", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
                    if (selection != null && selection.toString().equals("Update")) {
                        // updates
                        buildingView.updateBuilding();
                    } else if (selection != null && selection.toString().equals("Sell")) {
                        // sells
                        Building building = buildingView.getBuilding();
                        Mediator.publish("building:remove", building);
                        building.setSold(true);
                        buildings.remove(buildingView);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void initInputManager() {
        inputManager = new InputManager(this);
        inputManager.addKeyMapping("Up", KeyEvent.VK_UP);
        inputManager.addKeyMapping("Down", KeyEvent.VK_DOWN);
        inputManager.addKeyMapping("Left", KeyEvent.VK_LEFT);
        inputManager.addKeyMapping("Right", KeyEvent.VK_RIGHT);
        inputManager.addMouseMapping("LeftClick", MouseEvent.BUTTON1);
        inputManager.addMouseMapping("Deselect", MouseEvent.BUTTON2);
    }

    private void initBufferedImage() {
        loader = new BufferedImageLoader();
        try {
            availableImage = new SpriteSheet(loader.loadImage("/images/available.png"));
            unavailableImage = new SpriteSheet(loader.loadImage("/images/unavailable.png"));
        } catch (IOException ex) {
            Logger.getLogger(GameCanvas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * create a PersonView
     *
     * @param p person model
     */
    public void initPerson(Person p) {

        SpriteSheetManager ss = SpriteSheetManager.getInstance();
        // set burn sprites
        BurnView burnView = new BurnView(ss.getBurningSprites("burn.png"));
        burnView.setFrameDelay(80);
        burnView.start();

        String imageName = p.getImage();
        ArrayList<BufferedImage> moveDownSprites = ss.getDownSprites("person", imageName);
        ArrayList<BufferedImage> moveRightSprites = ss.getRightSprites("person", imageName);
        ArrayList<BufferedImage> moveUpSprites = ss.getUpSprites("person", imageName);
        ArrayList<BufferedImage> moveLeftSprites = ss.getLeftSprites("person", imageName);

        PersonView personView = new PersonView(moveDownSprites, moveRightSprites, moveUpSprites, moveLeftSprites, p);
        personView.setBurnView(burnView);
        personView.setFrameDelay(80);
        synchronized (people) {
            people.add(personView);
        }
        navigator.addView(personView);
        personView.start();

        if (personView.getModel() instanceof ZooKeeper) {
            boolean added = false;
            List<Animal> animalList = GameMemoryStorage.getInstance().getAnimalList();
            for (Animal animal : animalList) {
                if (animal.getZooKeeper() == null) {
                    ZooKeeper zooKeeper = (ZooKeeper) personView.getModel();
                    Coordinate cageCoordinate = animal.getCage().getCurrentCoordinate();
                    zooKeeper.setMb(new ZooKeeperMoveBehavior(null, 0, GameConstants.DOWN, cageCoordinate));
                    animal.setZooKeeper(zooKeeper);
                    added = true;
                    break;
                }
            }
            if (!added) {
                GameMemoryStorage.getInstance().removePerson(personView.getModel());
                people.remove(personView);
            }
        }
    }

    /**
     * creates a BuildingView
     *
     * @param b building model
     */
    public void initBuilding(Building b) {
        String imageName = b.getImageName();
        ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
        if (b instanceof Cage) {
            sprites = SpriteSheetManager.getInstance().getBuildingSprites("cage", imageName);
        } else {
            sprites = SpriteSheetManager.getInstance().getBuildingSprites("", imageName);
        }
        BuildingView bv = new BuildingView(sprites, b);
        bv.setFrameDelay(500);
        bv.build();
        synchronized (buildings) {
            buildings.add(bv);
            //GameMemoryStorage.getInstance().addBuilding(b);
        }
        navigator.addView(bv);
        if (!GameLoad.loading) {
            Mediator.publish("building:new", bv);
        }
    }

    /**
     * create an AnimalView
     *
     * @param b the cage of the animal
     * @param a the animal model
     */
    public void initAnimal(Building b, Animal a) {
        Fire fire = new Fire();
        String fireImage = fire.getImageName();
        SpriteSheetManager ss = SpriteSheetManager.getInstance();
        ArrayList<BufferedImage> fireDownSprites = ss.getDownSprites("fire", fireImage);
        ArrayList<BufferedImage> fireRightSprites = ss.getRightSprites("fire", fireImage);
        ArrayList<BufferedImage> fireUpSprites = ss.getUpSprites("fire", fireImage);
        ArrayList<BufferedImage> fireLeftSprites = ss.getLeftSprites("fire", fireImage);
        FireView fireView = new FireView(fireDownSprites, fireRightSprites, fireUpSprites, fireLeftSprites, fire);
        fireView.setFrameDelay(80);
        fireView.start();

        // set animal
        String imageName = a.getImageName();
        ArrayList<BufferedImage> moveDownSprites = ss.getDownSprites("animal", imageName);
        ArrayList<BufferedImage> moveRightSprites = ss.getRightSprites("animal", imageName);
        ArrayList<BufferedImage> moveUpSprites = ss.getUpSprites("animal", imageName);
        ArrayList<BufferedImage> moveLeftSprites = ss.getLeftSprites("animal", imageName);
        a.setMoveBehavior(new NormalMoveBehavior(new Coordinate(b.getCurrentCoordinate().getX(),
                b.getCurrentCoordinate().getY()), 0));
        b.setName(a.getName());
        AnimalView animalView = new AnimalView(moveDownSprites, moveRightSprites, moveUpSprites, moveLeftSprites, a);
        animalView.setFireView(fireView);
        animalView.setFrameDelay(80);
        animals.add(animalView);
        navigator.addView(animalView);
        animalView.start();
    }

    /*
     * Draw all game content in this method
     */
    public synchronized void draw(Graphics g) {
        map.draw(g);

        synchronized (animals) {
            for (AnimalView animalView : animals) {
                // remove the animal if the cage is sold
                if (animalView.getModel().isFined()) {
                    g.drawImage(animalView.sprite,
                            animalView.getModel().getCage().getCurrentCoordinate().getX() - GameNavigator.getCurrentX() + 32,
                            animalView.getModel().getCage().getCurrentCoordinate().getY() - GameNavigator.getCurrentY() + 64,
                            64, 64, null);
                } else {
                    g.drawImage(animalView.sprite,
                            animalView.getC().getX(),
                            animalView.getC().getY(),
                            64, 64, null);
                }
                if (animalView.getModel().isAttacking()) {
                    g.drawImage(animalView.getFireView().sprite,
                            animalView.getFireView().getC().getX(), animalView.getFireView().getC().getY(), null);
                }
            }
        }

        synchronized (buildings) {
            for (BuildingView buildingView : buildings) {
                g.drawImage(buildingView.sprite, buildingView.getC().getX(),
                        buildingView.getC().getY(), null);
            }
        }

        synchronized (people) {
            for (PersonView personView : people) {
                if (personView.isSelected()) {
                    g.setColor(Color.GREEN);
                    g.drawOval(personView.getC().getX(), personView.getC().getY() + 20, 32, 12);
                }
                if (personView.getModel() instanceof Visitor) {
                    drawEmoticon((Visitor) personView.getModel(), g);
                }
                g.drawImage(personView.sprite, personView.getC().getX(),
                        personView.getC().getY(), 32, 32, null);
                if (personView.getModel().isBurn()) {
                    g.drawImage(personView.getBurnView().sprite,
                            personView.getC().getX(),
                            personView.getC().getY(), null);
                }
            }
        }

        if (currentSelectedBuilding != null) {
            int x = ((int) (inputManager.MOUSE.x / 32)) * 32;
            int y = ((int) (inputManager.MOUSE.y / 32)) * 32;
            if (isBuilding()) {
                g.drawImage(availableImage.grabSprite(0, 0, currentSelectedBuilding.getWidth(),
                        currentSelectedBuilding.getHeight()), x, y, null);
            } else {
                g.drawImage(unavailableImage.grabSprite(0, 0, currentSelectedBuilding.getWidth(),
                        currentSelectedBuilding.getHeight()), x, y, null);
            }
        }
    }

    public void gameRender() {
        if (!isFocusOwner()) {
            setFocusable(true);
            requestFocus();
        }
        if (dbImage == null) { // Create the buffer
            dbImage = createImage(GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
            if (dbImage == null) {
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }
        //Clear the screen
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, GameConstants.GAME_WIDTH, GameConstants.GAME_HEIGHT);
        //Draw Game elements
        draw(dbg);
    }

    public void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if (dbImage != null && g != null) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync(); //For some operating systems
            if (g != null) {
                g.dispose();
            }
        } catch (Exception e) {
            Logger.getLogger(GameCanvas.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void drawEmoticon(Visitor visitor, Graphics g) {
        SpriteSheetManager ss = SpriteSheetManager.getInstance();
        g.drawImage(ss.getEmoticonImage("emoticons.png", visitor.getChangeMood()),
                visitor.getCurrentCoordinate().getX() - GameNavigator.getCurrentX() + 8,
                visitor.getCurrentCoordinate().getY() - GameNavigator.getCurrentY() - 20, null);
    }
}
