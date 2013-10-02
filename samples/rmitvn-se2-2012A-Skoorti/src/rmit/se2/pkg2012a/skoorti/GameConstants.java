
package rmit.se2.pkg2012a.skoorti;

public class GameConstants {

    public static final int TILE_SIZE = 32;
    public static final int STD_SPEED = 8;
    public static final int SAFE_DISTANCE = 32;
    public static final int GAME_WIDTH = 770;
    public static final int GAME_HEIGHT = 715;
    public static final int INVALID = -1;
    public static final int MALE = 1;
    public static final int FEMALE = 2;
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public static final int[][] MOVECOLLECTIONS = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    public static final int[][] MOVEDELTA = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    public static final int AGRESSIVE = 200;
    public static final int SHY = 201;
    public static final int NAUGHTY = 202;
    public static final int PASSIVE = 203;
    public static final int GENTLE = 204;
    public static final int[] ANIMALTYPES = {AGRESSIVE, SHY, NAUGHTY, PASSIVE, GENTLE};
    public static final String[] FAVORANIMALS = {"agressive", "shy", "naughty", "passive", "gentle"};
    //Visitor type
    public static final int NORMAL = 0;
    public static final int VIP = 1;
    public static final double VIPLEVEL = 1.5;
    public static final double FAVORITELEVEL = 1.5;
    public static final int MAXVISITORBUDGET = 1000;
    public static final int MINVISITORBUDGET = 100;
    //Price of building
    public static final int GYMPRICE = 5;
    public static final int RESTAURANTPRICE = 15;
    public static final int TOILETPRICE = 2;
    public static final int CAGEPRICE = 3;
    public static final int MALLPRICE = 50;
    public static final int MUSEUMPRICE = 2;
    // Buiding size
    public static final int CAGE_WIDTH = TILE_SIZE * 4;
    public static final int CAGE_HEIGHT = TILE_SIZE * 5;
    public static final int MUSEUM_WIDTH = TILE_SIZE * 6;
    public static final int MUSEUM_HEIGHT = TILE_SIZE * 4;
    public static final int MALL_WIDTH = TILE_SIZE * 5;
    public static final int MALL_HEIGHT = TILE_SIZE * 6;
    public static final int RESTAURANT_WIDTH = TILE_SIZE * 6;
    public static final int RESTAURANT_HEIGHT = TILE_SIZE * 5;
    public static final int GYM_WIDTH = TILE_SIZE * 5;
    public static final int GYM_HEIGHT = TILE_SIZE * 3;
    public static final int TOILET_WIDTH = TILE_SIZE * 2;
    public static final int TOILET_HEIGHT = TILE_SIZE * 2;
    // Options
    public static final int BUILDING_CHOICE_NUM = 6;
    public static final int STAFF_CHOICE_NUM = 2;
    public static final int ANIMAL_CHOICE_NUM = 4;
    // Map name
    public static final int MAPUS = 0;
    public static final int MAPSING = 1;
    public static final int MAPSAHARA = 2;
    public static final String[] MAP_FILENAME = {"us", "asia", "africa"};
    //Levels
    public static final int EASY = 0;
    public static final int HARD = 1;
    //change moods
    public static final int HAPPY = 0;
    public static final int N_HAPPY = 1;
    public static final int HUNGRY = 2;
    public static final int N_HUNGRY = 3;
    public static final int BLADDER = 4;
    public static final int N_BLADDER = 5;
    public static final int MOOD_SIZE = 20;
}
