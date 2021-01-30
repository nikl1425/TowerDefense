package Grid;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.*;

public class LoadMap implements TileBasedMap,GameObject{
    Image grassTile = new Image("data/Tiles/GrassTile.png");
    Image pathTile = new Image("data/Tiles/PathTile.png");
    Image grassBorderTile = new Image ("data/Tiles/GrassBorderTile.png");
    Image menuTile = new Image("data/Tiles/MenuTile.png");
    Image currencyTile = new Image("data/Tiles/CurrencyTile.png");
    Image nextLevelTile = new Image("data/Tiles/NextLevelTile.png");
    Image basicMenu = new Image("data/Towers/Basic/Basic_Menu.png");
    Image upgradeTile = new Image("data/Tiles/UpgradeTile.png");
    Image upgradeTileMarked = new Image("data/Tiles/UpgradeTile_Marked.png");
    Image sellTileMarked = new Image("data/TIles/CurrencyTIle_Marked.png");
    Image bomberMenu = new Image("data/Towers/Bomber/Bomber_Menu.png");
    Image quickMenu = new Image("data/Towers/Quick/Quick_Menu.png");
    Image sniperMenu = new Image("data/Towers/Sniper/Sniper_Menu.png");

    public static final int WIDTH = 11;
    public static final int HEIGHT = 15;
    private static final int w = 64;

    public static  int[][] MAP = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 4, 3, 6, 3},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 2, 3, 8, 3, 10, 3},
            {1, 1, 1, 0, 0, 0, 1, 0, 1, 2, 3, 12, 3, 14, 3},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 2, 3, 20, 20, 20, 3}, // 20 = nothing
            {1, 1, 0, 0, 0, 1, 1, 0, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 0, 1, 1, 0, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 0, 0, 0, 0, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 3, 16, 3, 3, 3},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3}
    };

    private int [][]mapinit = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 4, 3, 6, 3},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 2, 3, 8, 3, 10, 3},
            {1, 1, 1, 0, 0, 0, 1, 0, 1, 2, 3, 12, 3, 14, 3},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 2, 3, 20, 20, 20, 3}, // 20 = nothing
            {1, 1, 0, 0, 0, 1, 1, 0, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 0, 1, 1, 0, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 0, 0, 0, 0, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 3, 16, 3, 3, 3},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3}
    };



    public LoadMap() throws SlickException {
    }


    /**
     * When the program is being started up or resat,
     * reset the MAP variabel. Since it's a static int, the only way to reset it
     * is to make it equals another int.
     *
     * @param gc: A generic game container that handles the game loop, fps recording and managing the input system
     * @param stateBasedGame: A state based game isolated different stages of the game
     *                       (menu, ingame, hiscores, etc) into different states
     *                       so they can be easily managed and maintained.
     */
    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
        MAP = mapinit;
    }

    /**
     * For every tile in the doubelarray, MAP, render for a specific tile
     *
     * @param gc: A generic game container that handles the game loop, fps recording and managing the input system
     * @param stateBasedGame: A state based game isolated different stages of the game
     *                      (menu, ingame, hiscores, etc) into different states
     *                       so they can be easily managed and maintained.
     * @param g: A graphics context that can be used to render primatives to the accelerated canvas provided by LWJGL
     */
    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        //Green background
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                grassTile.draw(i*w,j*w,w,w);

            }
        }
        for (int j = 0; j < WIDTH; j++) {
            for (int i = 0; i < HEIGHT; i++) {
                //PATH = 0
                if (MAP[j][i] == 0) {
                    pathTile.draw(i * w, j * w, w, w);
                }
                //GRASS = 1
                if (MAP[j][i] == 1) {
                    grassTile.draw(i*w,j*w,w,w);
                }
                //RIGHT GRASS BORDER
                if (MAP[j][i] == 2) {
                    grassBorderTile.draw(i*w,j*w,w,w);
                }
                //TILE IN MENU
                if (MAP[j][i] == 3) {
                    menuTile.draw(i*w,j*w,w,w);
                }
                //BASIC TOWER IN MENU
                if (MAP[j][i] == 4) {
                    basicMenu.draw(i*w,j*w,w,w);
                }
                //BASIC TOWER ON MAP
                if (MAP[j][i] == 5) {
                   // basicPlaced.draw(i*w,j*w,w,w);
                }
                //BOMB TOWER ON MENU
                if (MAP[j][i] == 6) {
                    bomberMenu.draw(i*w,j*w,w,w);
                }
                //BOMB TOWER ON MAP
                if (MAP[j][i] == 7) {
                   // bomberPlaced.draw(i*w,j*w,w,w);
                }
                //QUICK TOWER ON MENU
                if (MAP[j][i] == 8) {
                    quickMenu.draw(i*w,j*w,w,w);
                }
                //QUICK TOWER ON MAP
                if (MAP[j][i] == 9) {
                   // quickPlaced.draw(i*w,j*w,w,w);
                }
                //SNIPER TOWER ON MENU
                if (MAP[j][i] == 10) {
                    sniperMenu.draw(i*w,j*w,w,w);
                }
                //SNIPER TOWER ON MAP
                if (MAP[j][i] == 11) {
                 //   sniperPlaced.draw(i*w,j*w,w,w);
                }
                //CURRENCY
                if (MAP[j][i] == 12) {
                    currencyTile.draw(i*w,j*w,w,w);
                }
                //CURRENCY MARKED
                if (MAP[j][i] == 13) {
                    sellTileMarked.draw(i*w,j*w,w,w);
                }
                //UPGRADE
                if (MAP[j][i] == 14) {
                    upgradeTile.draw(i*w,j*w,w,w);
                }
                //UPGRADE MARKED
                if (MAP[j][i] == 15) {
                    upgradeTileMarked.draw(i*w,j*w,w,w);
                }
                //NEXT WAVE
                if (MAP[j][i] == 16) {
                    nextLevelTile.draw(i*w,j*w,w,w);
                }
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {

    }

    /**
     * Used for the pathfinder class (from Slick2D)
     * Notification that the path finder visited a given tile.
     *
     * @param x: The x coordinate of the tile that was visited
     * @param y: The y coordinate of the tile that was visited
     */
    @Override
    public void pathFinderVisited(int x, int y) {}


    /**
     * Used for the pathfinder class (from Slick2D)
     * Check if the given location is blocked, i.e. blocks movement of the supplied mover.
     *
     * @param ctx: The context describing the pathfinding at the time of this request
     * @param x The x coordinate of the tile we're moving to
     * @param y The y coordinate of the tile we're moving to
     * @return true if the location is blocked
     */
    @Override
    public boolean blocked(PathFindingContext ctx, int x, int y) {
        return MAP[y][x] != 0;
    }

    /**
     *Used for the pathfinder class (from Slick2D)
     * Get the cost of moving through the given tile. This can be used to make certain areas more desirable.
     * A simple and valid implementation of this method would be to return 1 in all cases
     *
     * @param ctx: The context describing the pathfinding at the time of this request
     * @param x: The x coordinate of the tile we're moving to
     * @param y: The y coordinate of the tile we're moving to
     * @return The relative cost of moving across the given tile
     */
    @Override
    public float getCost(PathFindingContext ctx, int x, int y) {
        return 1.0f;
    }

    /**
     * Get the width of the tile map
     *
     * @return The number of tiles across the map
     */
    @Override
    public int getHeightInTiles() {
        return HEIGHT;
    }

    /**
     *Get the height of the tile map
     *
     * @return The number of tiles down the map
     */
    @Override
    public int getWidthInTiles() {
        return WIDTH;
    }

}