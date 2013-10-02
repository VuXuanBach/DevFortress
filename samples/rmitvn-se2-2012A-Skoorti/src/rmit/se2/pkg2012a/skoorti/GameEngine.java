
package rmit.se2.pkg2012a.skoorti;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmit.se2.pkg2012a.skoorti.model.GameFactory;
import rmit.se2.pkg2012a.skoorti.model.GameMap;
import rmit.se2.pkg2012a.skoorti.model.GamePlayer;
import rmit.se2.pkg2012a.skoorti.model.animal.AgressiveAnimalBehavior;
import rmit.se2.pkg2012a.skoorti.model.animal.Animal;
import rmit.se2.pkg2012a.skoorti.model.animal.Food;
import rmit.se2.pkg2012a.skoorti.model.animal.NormalAnimalBehavior;
import rmit.se2.pkg2012a.skoorti.model.building.Building;
import rmit.se2.pkg2012a.skoorti.model.building.Cage;
import rmit.se2.pkg2012a.skoorti.model.gamestrategy.*;
import rmit.se2.pkg2012a.skoorti.model.person.Person;
import rmit.se2.pkg2012a.skoorti.model.person.Visitor;
import rmit.se2.pkg2012a.skoorti.model.research.Research;
import rmit.se2.pkg2012a.skoorti.model.storage.GameMemoryStorage;
import rmit.se2.pkg2012a.skoorti.model.storage.Storage;
import rmit.se2.pkg2012a.skoorti.model.util.Coordinate;
import rmit.se2.pkg2012a.skoorti.model.util.Costable;
import rmit.se2.pkg2012a.skoorti.model.util.Mediator;
import rmit.se2.pkg2012a.skoorti.thread.NewVisitorThread;
import rmit.se2.pkg2012a.skoorti.thread.UpdateGameThread;
import rmit.se2.pkg2012a.skoorti.view.*;
import rmit.se2.pkg2012a.skoorti.view.dialog.AnimalDialog;
import rmit.se2.pkg2012a.skoorti.view.util.InputManager;

public class GameEngine implements Runnable, Serializable {

    private GameCanvas canvas;
    private Storage storage;
    //Game variables
    private Thread game;
    private volatile boolean running = false, operating = true;
    private long period = 40 * 1000000; //ms -> nano
    private static final int DELAYS_BEFORE_YIELDS = 10;
    private NewVisitorThread newVisitorThread;
    private UpdateGameThread updateGameThread;
    // Game player
    private GamePlayer player;
    // Game strategy
    private GameStrategy gameStrategy;
    private GameLevel gameLevel;

    public GameEngine(final GameCanvas c, final int level, int mapId) {
        this.canvas = c;
        switch (mapId) {
            case GameConstants.MAPUS:
                gameStrategy = new UsGame();
                break;
            case GameConstants.MAPSAHARA:
                gameStrategy = new AfricaGame(); // this is for testing
                break;
            default:
                gameStrategy = new AsiaGame();
                break;
        }
        // this should be chosen by users
        if (GameMemoryStorage.getInstance().getPlayer() != null) {
            storage = GameMemoryStorage.getInstance();
            player = GameMemoryStorage.getInstance().getPlayer();
        } else {
            player = new GamePlayer(gameStrategy.getInitialMoney(), level, mapId, 0);
            storage = ((GameMemoryStorage) GameMemoryStorage.getInstance()).setPlayer(player);
        }
        if (level == GameConstants.HARD) {
            this.gameLevel = new HardLevel();
        } else {
            this.gameLevel = new EasyLevel();
        }

        GameFactory.getInstance().setAnimalBehavior(gameLevel.getAnimalBehavior());
        storage.setInitialAnimals(gameStrategy.initialAnimals());
        storage.setResearches(gameStrategy.initialResearches());
        storage.setFoodList(gameStrategy.initialFood());

        newVisitorThread = new NewVisitorThread(gameStrategy.newVisitorInterval());
        newVisitorThread.start();

        updateGameThread = new UpdateGameThread(this);
        updateGameThread.start();

        // subscribes to events
        subscribeEvents();
    }

    private void subscribeEvents () {
        Mediator.subscribe("person:new", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                int visitors = storage.addPerson((Person) o1).getPersonList().size();
                canvas.initPerson((Person) o1);
                Mediator.publish("game:visitor:change", visitors); // notify the view

                if (gameLevel.hasEntranceFee()) {
                    Mediator.publish("game:cost", gameStrategy.entranceFee());
                }

                // checks number of visitors
                if (visitors > gameLevel.maxVisitorNumber()) {
                    Map data = new HashMap();
                    data.put("result", true);
                    data.put("reason", "You have reached max number of visitor.");
                    Mediator.publish("game:end", data);
                }
            }
        });

        Mediator.subscribe("game:end", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                newVisitorThread.stopLoop();
                updateGameThread.stopLoop();

                Map data = (HashMap) o1;

                boolean result = Boolean.valueOf(data.get("result").toString());
                Object reasonObj = data.get("reason");
                String reason = result ? "You have won" : "You have lost";
                if (reasonObj == null) {
                    reasonObj = reason;
                }
                JOptionPane.showMessageDialog(null, reasonObj, reason, 1);
                running = false;
                operating = false;
                System.exit(0);
            }
        });

        Mediator.subscribe("building:new", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                newBuilding(((BuildingView) o1));
            }
        });

        Mediator.subscribe("building:remove", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                Building b = (Building) o1;
                // based on the game level to return the money
                float v = sellValue((Costable) b);
                Mediator.publish("game:cost", v);
                // also calculate the cost for animal
                if (b instanceof Cage) {
                    Cage c = (Cage) b;
                    Animal a = c.getAnimal();
                    Mediator.publish("game:cost", sellValue((Costable) a));
                    storage.removeAnimal(a);
                    Mediator.publish("status:animal");
                }
                storage.removeBuilding(b);
                int tileSize = GameConstants.TILE_SIZE;
                GameMap.getInstance().removeBuildingStatus(
                        b.getCurrentCoordinate().getX() / tileSize,
                        b.getCurrentCoordinate().getY() / tileSize,
                        b.getWidth() / tileSize, b.getHeight() / tileSize);
                Mediator.publish("status:building");
            }
        });

        Mediator.subscribe("animal:escape", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                // the animal breaks out and eat people
                animalEscape();
            }
        });

        Mediator.subscribe("animal:hungry", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                try {
                    // render some dialogs to feed animals
                    DialogFactory.getInstance().feedAnimalDialog((Animal) o1);
                } catch (IOException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Mediator.subscribe("game:notice", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                // displays notice dialog
                DialogFactory.getInstance().displayNotice(String.valueOf(o1));
            }
        });

        Mediator.subscribe("visitor:changeMood", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                System.out.println("Mood changed " + ((Visitor) o1).getFunLevel());
            }
        });

        Mediator.subscribe("animal:feed", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if (o1 instanceof Food) {
                    Food c = (Food) o1;
                    //TODO: decrease the money of the user here
                    Mediator.publish("game:cost", -c.getCost());
                    // also increase the risk value
                    player.updateRisk(c.getRisk());
                    if (player.getCurrentRisk() >= gameStrategy.getMaxRisk()) {
                        // reach the max risk value
                        Map data = new HashMap();
                        data.put("result", false);
                        data.put("reason", "You have reached max risk.");
                        Mediator.publish("game:end", data);
                    } else {
                        // notify other GUI about the new event
                        Mediator.publish("game:risk:change", player.getCurrentRisk());
                    }
                }
            }
        });

        Mediator.subscribe("game:addAnimal", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                // when a new animal research is completed
                Animal animal = (Animal) o1;
                storage.addInitialAnimal(animal);
            }
        });

        //remove visitor from the storage
        Mediator.subscribe("game:removePerson", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                Person person = (Person) o1;
                int visitors = storage.removePerson(person).getPersonList().size();
                Mediator.publish("game:visitor:change", visitors); // notify the view
            }
        });

        // users decided to buy the animal
        Mediator.subscribe("game:newAnimal", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                synchronized (new Object()) {
                    Object[] params = (Object[]) o1;
                    BuildingView bv = (BuildingView) params[1];
                    if (params[0] == null) {
                        // users do not choose the animal, we should remove the cage
                        canvas.getBuildings().remove(bv);
                        return;
                    }
                    Animal a = (Animal) params[0];

                    // also we need to check if the cost is enough to buy
                    if (!enoughMoney(a.getAnimalCost() + bv.getModel().getCost())) {
                        // do something about this case
                        canvas.getBuildings().remove(bv);
                        return;
                    }
                    Cage cg = ((Cage) bv.getModel());
                    a.setCage(cg);
                    cg.setAnimal(a);
                    canvas.initAnimal((Cage) bv.getModel(), a);
                    // add the animal to storage
                    int animalCount = storage.addAnimal(a).getAnimalList().size();

                    // publish event to update views
                    Mediator.publish("game:log", bv.getModel());
                    Mediator.publish("status:building");
                    Mediator.publish("status:animal");
                    Mediator.publish("game:cost", -a.getCost());

                    // checks number of current animals
                    if (animalCount > gameLevel.maxAnimalNumber()) {
                        Map data = new HashMap();
                        data.put("result", true);
                        data.put("reason", "You have reached max number of animal.");
                        Mediator.publish("game:end", data);
                    }
                }
            }
        });

        //when cage of animal is sold
        Mediator.subscribe("game:removeAnimal", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                Animal animal = (Animal) o1;
                storage.removeAnimal(animal);
            }
        });

        //purchase a research item
        Mediator.subscribe("research:new", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                // checks the cost
                Research r = ((Research) o1);
                if (enoughMoney(r.getCost())) {
                    r.setActive(true);
                    Mediator.publish("game:cost", -r.getCost());
                } else {
                    JOptionPane.showMessageDialog(null, "You dont have enough money");
                }
            }
        });

        //events related to game budget
        Mediator.subscribe("game:cost", new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                // updates the current money
                float cost = Float.valueOf(o1.toString());
                player.updateBudget(cost);
                Map data = new HashMap();
                if (player.getBudget() <= 0) {
                    //lose
                    data.put("result", false);
                    data.put("reason", "You have run out of money!");
                    Mediator.publish("game:end", data);
                } else if (player.getBudget() > gameLevel.maxBudget()) {
                    // win
                    data.put("result", true);
                    data.put("reason", "You have reached the maximum money!");
                    Mediator.publish("game:end", data);
                } else {
                    Mediator.publish("status:budget", player.getBudget());
                }
            }
        });
    }
    public GameCanvas getCanvas() {
        return canvas;
    }

    public int getLevel() {
        return player.getLevel();
    }

    public void setLevel(int level) {
        switch (level) {
            case GameConstants.EASY:
                GameFactory.getInstance().setAnimalBehavior(new NormalAnimalBehavior());
                break;
            default:
                GameFactory.getInstance().setAnimalBehavior(new AgressiveAnimalBehavior());
                break;
        }
        player.setLevel(level);
    }

    private void newBuilding(final BuildingView bv) {
        // when render the building, also store the animal for update later
        try {
            final int tileSize = GameConstants.TILE_SIZE;
            final Building b = bv.getModel();
            // set building status for the block data
            canvas.getMapView().getMap().setBuildingStatus(
                    b.getCurrentCoordinate().getX() / tileSize,
                    b.getCurrentCoordinate().getY() / tileSize,
                    b.getWidth() / tileSize, b.getHeight() / tileSize);
            storage.addBuilding(b);
            if (b instanceof Cage) {
                // display the animal dialog
                final AnimalDialog animalDialog = DialogFactory.getInstance().animalDialog();
                animalDialog.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent we) {
                        super.windowClosed(we);
                        Animal a = animalDialog.getCurrentAnimal();
                        if (a != null) {
                            try {
                                a = (Animal) a.clone();
                            } catch (CloneNotSupportedException ex) {
                                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            canvas.getBuildings().remove(bv);
                            GameMap.getInstance().removeBuildingStatus(
                                    b.getCurrentCoordinate().getX() / tileSize,
                                    b.getCurrentCoordinate().getY() / tileSize,
                                    b.getWidth() / tileSize, b.getHeight() / tileSize);
                            return;
                        }
                        Object[] params = new Object[]{a, bv};
                        Mediator.publish("game:newAnimal", params);
                    }
                });
            } else {
                // for each map, we will have a different cost for building
                float cost = b.getCost();
                Mediator.publish("game:cost", -cost);
                Mediator.publish("game:log", b);
                Mediator.publish("status:building");
            }
        } catch (Exception e) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void startGame() {
        if (game == null || !running) {
            game = new Thread(this);
            game.start();
            running = true;
        }
    }

    public void stopGame() {
        if (running) {
            running = false;
        }
    }

    public void continueGame() {
        running = true;
    }

    private void handleInput() {
        InputManager inputManager = canvas.getInputManager();
        if (inputManager.isKeyPressed("Up")) {
            canvas.getNavigator().navigate(GameNavigator.PAN_UP);
        }
        if (inputManager.isKeyPressed("Down")) {
            canvas.getNavigator().navigate(GameNavigator.PAN_DOWN);
        }
        if (inputManager.isKeyPressed("Left")) {
            canvas.getNavigator().navigate(GameNavigator.PAN_LEFT);
        }
        if (inputManager.isKeyPressed("Right")) {
            canvas.getNavigator().navigate(GameNavigator.PAN_RIGHT);
        }

        int mouseX = inputManager.MOUSE.x;
        int mouseY = inputManager.MOUSE.y;
        int positionX = mouseX + GameNavigator.getCurrentX();
        int positionY = mouseY + GameNavigator.getCurrentY();
        if (inputManager.isMouseClicked("LeftClick")) {
            if (canvas.isBuilding()) {
                GameCanvas.currentSelectedBuilding.setCurrentCoordinate(
                        new Coordinate(
                        ((int) positionX / 32) * 32,
                        ((int) positionY / 32) * 32));
                canvas.initBuilding(GameCanvas.currentSelectedBuilding);
                canvas.setBuilding(false);
                GameCanvas.currentSelectedBuilding = null;
            } else {
                if (!canvas.selectPerson(inputManager.MOUSE.x + GameNavigator.getCurrentX(),
                        inputManager.MOUSE.y + GameNavigator.getCurrentY())) {
                    canvas.selectBuilding(inputManager.MOUSE.x + GameNavigator.getCurrentX(),
                            inputManager.MOUSE.y + GameNavigator.getCurrentY());
                }
            }
        }
        if (GameCanvas.currentSelectedBuilding != null
                && mouseX < GameConstants.GAME_WIDTH
                && mouseY < GameConstants.GAME_HEIGHT) {
            canvas.setBuilding(GameNavigator.isAvailableBlock(
                    positionX, positionY,
                    GameCanvas.currentSelectedBuilding.getWidth() / 32,
                    GameCanvas.currentSelectedBuilding.getHeight() / 32));
        }
    }

    private void gameUpdate() {
        ArrayList<PersonView> people = canvas.checkOutOfMap().getPeople();
        synchronized (people) {
            for (PersonView personView : people) {
                personView.update(System.currentTimeMillis());
                personView.updateViewCoordinate();
            }
        }
        ArrayList<AnimalView> animals = canvas.checkAnimalIsSold().getAnimals();
        synchronized (animals) {
            for (AnimalView animalView : animals) {
                animalView.update(System.currentTimeMillis());
                animalView.updateViewCoordinate();
            }
        }
        ArrayList<BuildingView> buildings = canvas.getBuildings();
        synchronized (buildings) {
            for (BuildingView buildingView : buildings) {
                buildingView.draw(System.currentTimeMillis());
                buildingView.updateViewCoordinate();
            }
        }
    }

    @Override
    public void run() {
        while (operating) {
            long beforeTime, afterTime, diff, sleepTime, overSleepTime = 0, delays = 0;
            while (running) {
                beforeTime = System.nanoTime();
                handleInput();
                gameUpdate();
                canvas.gameRender();
                canvas.paintScreen();

                afterTime = System.nanoTime();
                diff = afterTime - beforeTime;
                sleepTime = (period - diff) - overSleepTime;
                // if the sleep time is between 0 and the period, we can sleep
                if (sleepTime < period && sleepTime > 0) {
                    try {
                        game.sleep(sleepTime / 1000000L);
                        overSleepTime = 0;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameCanvas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } // The diff is greater than the period
                else if (diff > period) {
                    overSleepTime = diff - period;
                } // Accumulate the amount of delays and eventually yeild
                else if (++delays >= DELAYS_BEFORE_YIELDS) {
                    game.yield();
                    delays = 0;
                    overSleepTime = 0;
                } // the loop takes less than expected, but we need to make up for overSleepTime
                else {
                    overSleepTime = 0;
                }
            }
        }
    }

    public boolean enoughMoney(float cost) {
        return player.getBudget() > cost;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public float getBudget() {
        return player.getBudget();
    }

    public int getNumberAnimal() {
        return storage.getAnimalList().size();
    }

    public int getNumberBuilding() {
        return storage.getBuildingList().size();
    }

    public int getNumberCompletedResearch() {
        int count = 0;
        List<Research> researches = storage.getResearches();
        for (Research r : researches) {
            if (r.isCompleted()) {
                count++;
            }
        }
        return count;
    }

    public int getNumberVisitor() {
        int count = 0;
        List<Person> persons = storage.getPersonList();

        for (Person p : persons) {
            if (p instanceof Visitor) {
                count++;
            }
        }
        return count;
    }

    public GameStrategy getGameStrategy() {
        return this.gameStrategy;
    }

    public int getCurrentRisk() {
        return player.getCurrentRisk();
    }

    public void setCurrentRisk(int currentRisk) {
        player.setCurrentRisk(currentRisk);
        Mediator.publish("game:risk:change", player.getCurrentRisk());
    }

    private void animalEscape() {
        player.incrementEscapes();
        // checks the condition only if users choose hard
        if (gameLevel.hasAnimalEscapeLimit()) {
            if (player.getAnimalEscape() >= gameStrategy.maxAnimalEscape()) {
                // reach the max risk value
                Map data = new HashMap();
                data.put("result", false);
                data.put("reason", "Your zoo has been closed because of too many animal escapes.");
                Mediator.publish("game:end", data);
                return;
            }
        }

        // notify view
        Mediator.publish("status:escape", player.getAnimalEscape());
    }

    public int getAnimalEscape() {
        return player.getAnimalEscape();
    }

    private float sellValue(Costable c) {
        return (float) (c.getCost() * gameLevel.itemDepreciation());
    }
}
