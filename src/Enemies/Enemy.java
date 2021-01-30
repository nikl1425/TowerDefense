package Enemies;

import Grid.GameObject;
import Grid.LoadMap;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;


public class Enemy implements GameObject {
    private int w = 64;
    private float startPosX = -1;
    private float startPosY = 2;
    private int pathstartX = 0;
    private int pathstartY = 2;
    private int endPosX = 2;
    private int endPosY = 11;
    private float newstartPosX = -1;
    private float newstartPosY = 2;
    private int rotationCenter = 24;
    private int speedDivider = 500;
    private float speed = 0.75f / speedDivider;
    private static final int MAX_PATH_LENGTH = 100;
    private int HP;
    public int startHP = 4;
    public float counter = 0f;
    public boolean isActive = true;

    private LoadMap loadMap = new LoadMap();
    private static Shape playerRectangle = null;
    public Shape Playrect;
    AStarPathFinder pathfinder = new AStarPathFinder(loadMap, MAX_PATH_LENGTH, false);
    public Path path = pathfinder.findPath(null, pathstartX, pathstartY, endPosX, endPosY);

    public SpriteSheet e1 = new SpriteSheet("data/Tiles/Enemy.png", w, w);


    public Enemy() throws SlickException {
        this.startPosX = getNewstartPosX();
        this.startPosY = getNewstartPosY();
        this.setHP(4);
        e1.draw(getStartPosX() * w + 8, getStartPosY() * w + 8, w - 16, w - 16);
        this.Playrect = playerRectangle;
    }

    /**
     * When the program is being started up or resat,
     * loadsMap is initialized for the pathfinder
     * @param gc: A generic game container that handles the game loop, fps recording and managing the input system
     * @param sbg: A state based game isolated different stages of the game
     *                       (menu, ingame, hiscores, etc) into different states
     *                       so they can be easily managed and maintained.
     */
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        loadMap = new LoadMap();

    }


    /**
     * @param gc: A generic game container that handles the game loop, fps recording and managing the input system
     * @param sbg: A state based game isolated different stages of the game
     *                      (menu, ingame, hiscores, etc) into different states
     *                       so they can be easily managed and maintained.
     * @param delta: delta is the change in time since the last render or update was made.
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {


        //Sets the framefrate cap to the monitors refresh rate.
        gc.setVSync(true);

        //The center of rotation is set to half the size of the image, this gives a fluent rotation around the center of the image.
        e1.setCenterOfRotation(rotationCenter, rotationCenter);


        //This checks whether the enemy is on the map or not. If it isn't - then start moving.
        if (getStartPosX() <= 0 && isActive) {

            //Defines the speed
            setStartPosX(getStartPosX() + delta * speed);

            //Rotationcenter
            e1.setCenterOfRotation(rotationCenter, rotationCenter);

            //Image of enemy remains in its original state.
            e1.setRotation(0);


            //If the enemies are active, and the pathfinding is still in progress.

        } else if (counter <= path.getLength()-1 && isActive == true) {

            //If the enemy can go right, then go right.
            if (path.getX((int) counter) < path.getX((int) counter + 1)) {

                //Defines speed
                setStartPosX(getStartPosX() + delta * speed);

                //Rotation remains the same as the original IMG
                e1.setRotation(0f);

                //If the enemy enemy can go left, go left
            } else if (path.getX((int) counter) > path.getX((int) counter + 1)) {

                //Defines speed
                setStartPosX(getStartPosX() - delta * speed);

                //Rotate the IMG to face left
                e1.setRotation(180f);

                //If the enemy can move down - move down.
            } else if (path.getY((int) counter) < path.getY((int) counter + 1)) {

                //Defines the speed
                setStartPosY(getStartPosY() + delta * speed);

                //Rotates the enemy for downwards fare.
                e1.setRotation(90f);


                //If the enemy can move up, then move up.
            } else if (path.getY((int) counter) > path.getY((int) counter + 1)) {

                //Defines speed.
                setStartPosY(getStartPosY() - delta * speed);

                //Set rotation to face upwards.
                e1.setRotation(270f);
            }

            //Counter is illustrating the speed of the enemy.
            counter += delta * speed;

        }
    }

    /**
     * @param gc: A generic game container that handles the game loop, fps recording and managing the input system
     * @param sbg: A state based game isolated different stages of the game
     *                      (menu, ingame, hiscores, etc) into different states
     *                       so they can be easily managed and maintained.
     * @param g: A graphics context that can be used to render primatives to the accelerated canvas provided by LWJGL
     */
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
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

    public void takeDamage(int damage) {
        HP = HP - damage;
    }
}