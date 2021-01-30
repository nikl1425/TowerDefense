package Enemies;

import Player.Player;
import Grid.GameObject;
import Grid.LoadMap;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;


public class Enemy implements GameObject, Mover {
    public float currX;
    public float currY;
    public SpriteSheet e1 = new SpriteSheet("data/enemy.png", w, w);
    LoadMap loadMap = new LoadMap();
    private static final int w = 64;
    private float startPosX = -1;
    private float startPosY = 2;
    int pathstartX = 0;
    int pathstartY = 2;
    int rotationCenter = 24;
    int speedDivider = 500;
    public int endPosX = 2;
    public int endPosY = 11;
    private static final int MAX_PATH_LENGTH = 100;
    public float counter = 0f;
    Player player = new Player();
    public float speed = 0.75f / speedDivider;
    AStarPathFinder pathfinder = new AStarPathFinder(loadMap, MAX_PATH_LENGTH, false);
    public Path path = pathfinder.findPath(null, pathstartX, pathstartY, endPosX, endPosY);
    public boolean isActive = true;
    private float newstartPosX = -1;
    private float newstartPosY = 2;
    private int HP;
    public static Shape playerRectangle = null;
    public Shape Playrect;


    int pauseClick;


    public Enemy() throws SlickException {
        this.startPosX = getNewstartPosX();
        this.startPosY = getNewstartPosY();
        this.setHP(5);
        e1.draw(getStartPosX() * w + 8, getStartPosY() * w + 8, w - 16, w - 16);
        this.Playrect = playerRectangle;
        //this.PlayerRectangle = getPlayerRectangle();
    }

    public static Shape getPlayerRectangle() {
        return playerRectangle;
    }

    public static void setPlayerRectangle(Shape playerRectangle) {
        Enemy.playerRectangle = playerRectangle;
    }


    @Override
    public Vector2f getPosition() {
        return null;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        loadMap = new LoadMap();


    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();

        gc.setVSync(true);
        e1.setCenterOfRotation(rotationCenter, rotationCenter);
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        if (input.isKeyPressed(Input.KEY_P)) {
            gc.pause();
        }

        if (input.isKeyPressed(Input.KEY_R)) {
            gc.resume();
        }

        if (mouseX < 12 * w && mouseX > 12 * w - w && mouseY < 11 * w && mouseY > 11 * w - w) {
            if (input.isMousePressed(0)) {
                pauseClick++;
                if (pauseClick % 2 == 1)
                    gc.resume();
                if (pauseClick % 2 == 0) {
                    gc.pause();
                }
            }
        }


        if (getStartPosX() <= 0 && isActive) {
            setStartPosX(getStartPosX() + delta * speed);
            e1.setCenterOfRotation(rotationCenter, rotationCenter);
            e1.setRotation(0);
        } else if (counter <= path.getLength() - 1 && isActive == true) {
            if (path.getX((int) counter) < path.getX((int) counter + 1)) {
                setStartPosX(getStartPosX() + delta * speed);
                e1.setRotation(0f);
            } else if (path.getX((int) counter) > path.getX((int) counter + 1)) {
                setStartPosX(getStartPosX() - delta * speed);
                e1.setRotation(180f);
            } else if (path.getY((int) counter) < path.getY((int) counter + 1)) {
                setStartPosY(getStartPosY() + delta * speed);
                e1.setRotation(90f);
            } else if (path.getY((int) counter) > path.getY((int) counter + 1)) {
                setStartPosY(getStartPosY() - delta * speed);
                e1.setRotation(270f);
            }

            currX = getStartPosX();
            currY = getStartPosY();

            counter += delta * speed;


            if (counter >= path.getLength() - 1) {
               // resetEnemies();
                //player.decreaseLife();
            }

            if (player.getLives() <= 0) {
                sbg.enterState(0);
                gc.reinit();
            }

        }

    }


    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if (isActive) {
            spawnMob();
        }
    }

    public void resetEnemies() {
        setStartPosX(-1);
        setStartPosY(2);
        counter = 0;
        isActive = false;
    }


    public void spawnMob() {
       //e1.draw(getStartPosX() * w + 8, getStartPosY() * w + 8, w - 16, w - 16);
    }


    public float getStartPosX() {
        return startPosX;
    }

    public void setStartPosX(float startPosX) {
        this.startPosX = startPosX;
    }

    public float getStartPosY() {
        return startPosY;
    }

    public void setStartPosY(float startPosY) {
        this.startPosY = startPosY;
    }

    public float getNewstartPosX() {
        return newstartPosX;
    }

    public void setNewstartPosX(float newstartPosX) {
        this.newstartPosX = newstartPosX;
    }

    public float getNewstartPosY() {
        return newstartPosY;
    }

    public void setNewstartPosY(float newstartPosY) {
        this.newstartPosY = newstartPosY;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}



