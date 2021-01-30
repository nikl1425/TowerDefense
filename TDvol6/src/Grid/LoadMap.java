package Grid;

import Enemies.Enemy;
import Towers.BasicTower;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

//Pathfinding imports
import org.newdawn.slick.util.pathfinding.*;


public class LoadMap implements TileBasedMap,GameObject{

    Image grassTile = new Image("data/Tiles/GrassTile.png");
    Image pathTile = new Image("data/Tiles/PathTile.png");
    Image grassBorderTile = new Image ("data/Tiles/GrassBorderTile.png");
    Image menuTile = new Image("data/Tiles/MenuTile.png");
    Image currencyTile = new Image("data/Tiles/CurrencyTile.png");
    Image nextLevelTile = new Image("data/Tiles/NextLevelTile.png");
    Image basicMenu = new Image("data/Towers/Basic/Basic_Menu.png");
    public Image basicPlaced = new Image("data/Towers/Basic/Basic_Click.png");
    Image bomberMenu = new Image("data/Towers/Bomber/Bomber_Menu.png");
    public Image bomberPlaced = new Image("data/Towers/Bomber/Bomber_Click.png");
    Image quickMenu = new Image("data/Towers/Quick/Quick_Menu.png");
    public Image quickPlaced = new Image("data/Towers/Quick/Quick_Click.png");
    Image sniperMenu = new Image("data/Towers/Sniper/Sniper_Menu.png");
    public Image sniperPlaced = new Image("data/Towers/Sniper/Sniper_Click.png");


    public static final int WIDTH = 11;
    public static final int HEIGHT = 15;
    public boolean walkable[][];

    BasicTower basicTower = new BasicTower();


    private static final int w = 64;

    public static final int[][] MAP = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 4, 3, 6, 3},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 2, 3, 8, 3, 10, 3},
            {1, 1, 1, 0, 0, 0, 1, 0, 1, 2, 3, 3, 3, 3, 3},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 2, 3, 20, 20, 20, 3}, // 20 = nothing
            {1, 1, 0, 0, 0, 1, 1, 0, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 0, 1, 1, 0, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 0, 0, 0, 0, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 3, 20, 20, 20, 3},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 3, 13, 3, 3, 3},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 2, 3, 3, 3, 3, 3}

            //0 = Road
            //1 = Grass
            //2 = Border to Menu
            //3 = Menu-tile

            //4 = BasicTower in Menu
            //5 = Sets Basic Tower

            //6 = BomberTower in Menu
            //7 = Sets bomber Tower

            //8 = Quick Tower in Menu
            //9 = Sets Quick Tower

            //10 = Sniper Tower in Menu
            //11 = Sets Sniper Tower

            //12 = CurrencyImage

    };

    public LoadMap() throws SlickException {
    }


    @Override
    public Vector2f getPosition() {
        return null;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        //

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
                    basicPlaced.draw(i*w,j*w,w,w);
                }
                //BOMB TOWER ON MENU
                if (MAP[j][i] == 6) {
                    bomberMenu.draw(i*w,j*w,w,w);
                }
                //BOMB TOWER ON MAP
                if (MAP[j][i] == 7) {
                    bomberPlaced.draw(i*w,j*w,w,w);
                }
                //QUICK TOWER ON MENU
                if (MAP[j][i] == 8) {
                    quickMenu.draw(i*w,j*w,w,w);
                }
                //QUICK TOWER ON MAP
                if (MAP[j][i] == 9) {
                    quickPlaced.draw(i*w,j*w,w,w);
                }
                //SNIPER TOWER ON MENU
                if (MAP[j][i] == 10) {
                    sniperMenu.draw(i*w,j*w,w,w);
                }
                //SNIPER TOWER ON MAP
                if (MAP[j][i] == 11) {
                    sniperPlaced.draw(i*w,j*w,w,w);
                }
                //CURRENCY
                if (MAP[j][i] == 12) {
                    currencyTile.draw(i*w,j*w,w,w);
                }
                //CURRENCY
                if (MAP[j][i] == 13) {
                    nextLevelTile.draw(i*w,j*w,w,w);
                }

            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
        walkable = new boolean[WIDTH][HEIGHT];
        for (int j = 0; j < WIDTH; j++) {
            for (int i = 0; i < HEIGHT; i++) {
                if (MAP[j][i] == 0){
                    walkable[j][i] = true;
                }
                else{
                    walkable[j][i] = false;
                }
            }
        }

    }

    @Override
    public void pathFinderVisited(int x, int y) {}

    @Override
    public boolean blocked(PathFindingContext ctx, int x, int y) {
        return MAP[y][x] != 0;
    }

    @Override
    public float getCost(PathFindingContext ctx, int x, int y) {
        return 1.0f;
    }

    @Override
    public int getHeightInTiles() {
        return HEIGHT;
    }

    @Override
    public int getWidthInTiles() {
        return WIDTH;
    }
}

