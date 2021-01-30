package Game;

import Enemies.Enemy;
import Grid.GameObject;
import Grid.LoadMap;
import Bullets.Bullets;
import Player.Player;
import Towers.*;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.*;

import static org.newdawn.slick.Color.transparent;
import static org.newdawn.slick.Color.white;

public class GameState extends BasicGameState {
    Bullets bullet = new Bullets();
    LoadMap loadMap = new LoadMap();
    Enemy enemy = new Enemy();
    Player player = new Player();

    public static LinkedList<GameObject> objects = new LinkedList<>();
    List<Bullets> bulletList = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();
    List<BasicTower> basicTowers = new ArrayList<>();
    List<BomberTower> bomberTowers = new ArrayList<>();
    List<SniperTower> sniperTowers = new ArrayList<>();
    List<QuickTower> quickTowers = new ArrayList<>();
    TrueTypeFont pauseFont;

    long timeSinceStart = 0;
    long rtimeSinceStart = 0;
    int w = 64;
    private static final int r = 32;
    public boolean waveinProgress = false;
    private boolean spawninProgress = false;
    public boolean collides = false;
    private int timePassedEnemy;
    private int startWaveCount;
    boolean radiusVisited;
    private int enemySpawns;
    private int enemyCounter;
    int enemyHPCounter;
    static int currentLevel;
    private boolean upgradePressed;
    private boolean sellTower;
    public int bulletCount;

    public Shape bulletCircle = null;

    private Image basicbulletSheet = new Image("data/towers/Basic/basicBullet.png");

    public GameState() throws SlickException {
    }

    /**
     * Get this state
     *
     * @return this State
     */
    @Override
    public int getID() {
        return 1;
    }

    /**
     * Clear the objects and add them again to the list.
     * If this is not done, the objectlist will fill up with the same objects
     * Clear all the lists if the game is reset
     *
     * @param gc: A generic game container that handles the game loop, fps recording and managing the input system
     * @param stateBasedGame: A state based game isolated different stages of the game
     *                      (menu, ingame, hiscores, etc) into different states
     *                       so they can be easily managed and maintained.
     */
    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
        //Clear all all objects & add them again
        objects.clear();
        objects.add(new Tower());
        objects.add(new Player());
        objects.add(new Enemy());

        //Clear the lists
        bulletList.clear();
        enemies.clear();
        basicTowers.clear();
        sniperTowers.clear();
        quickTowers.clear();
        bomberTowers.clear();

        //Set the variables when the state is initialized
        enemyCounter = 10;
        enemyHPCounter = 5;
        currentLevel = 0;
        sellTower = false;
        upgradePressed = false;
        timePassedEnemy = 0;

        //Loads this seperate, else the map is over-riding all drawn during the game
        loadMap.init(gc, stateBasedGame);

        //Run init for all objects
        for (GameObject obj : objects) {
            obj.init(gc, stateBasedGame);
        }

        java.awt.Font font = new java.awt.Font("Agency FB", java.awt.Font.BOLD, 16);
        pauseFont = new TrueTypeFont(font,false);

    }

    /**
     * Everything that needs to be drawn, draw it here
     * Draws all towers when clicked
     * Draws the bullet
     * Runs the possibility to upgrade and sell towers
     *
     * @param gc: A generic game container that handles the game loop, fps recording and managing the input system
     * @param stateBasedGame: A state based game isolated different stages of the game
     *                      (menu, ingame, hiscores, etc) into different states
     *                       so they can be easily managed and maintained.
     * @param g: A graphics context that can be used to render primatives to the accelerated canvas provided by LWJGL
     */
    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        //Loads this seperate, else the map is over-riding all drawn during the game
        loadMap.render(gc, stateBasedGame, g);
        //Render all objects
        for (GameObject obj : objects) {
            obj.render(gc, stateBasedGame, g);
        }
        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        if (sellTower || upgradePressed) {
            if (input.isMousePressed(1)) {
                sellTower = false;
                upgradePressed = false;
            }
        }

        //Draws this tower
        for (BasicTower basicTower : basicTowers) {
            basicTower.basicClicked.draw(basicTower.towerX * w, basicTower.towerY * w, w, w);
        }
        //Draws this tower
        for (SniperTower sniperTower : sniperTowers) {
            sniperTower.sniperClicked.draw(sniperTower.towerX * w, sniperTower.towerY * w, w, w);
        }
        //Draws this tower
        for (QuickTower quickTower : quickTowers) {
            quickTower.quickClicked.draw(quickTower.towerX * w, quickTower.towerY * w, w, w);
        }
        //Draws this tower
        for (BomberTower bomberTower : bomberTowers) {
            bomberTower.bomberClicked.draw(bomberTower.bombertowerX * w, bomberTower.bombertowerY * w, w, w);
        }

        //This draws for each enemy its healthbar based on it current HP
        for (Enemy enemies : enemies) {
            Rectangle bar = new Rectangle(enemies.getStartPosX() * w + 8,
                    enemies.getStartPosY() * w,
                    50 * enemies.getHP() / ((enemy.startHP + currentLevel)), 6);
            GradientFill fill = new GradientFill(enemies.getStartPosX() * w + 8,
                    0, new Color(255, 60, 0),
                    enemies.getStartPosX() * w + 60, 0, new Color(255, 180, 0));
           // g.drawString("" + enemies.getHP(), enemies.getStartPosX() * w, enemies.getStartPosY() * w);

            g.setColor(Color.darkGray);
            g.fillRect(enemies.getStartPosX() * w + 8, enemies.getStartPosY() * w, 50, 6);
            g.fill(bar, fill);

            enemies.e1.draw(enemies.getStartPosX() * w + 8,
                    enemies.getStartPosY() * w + 8, w - 16, w - 16);
        }

        //Controls the bullets
        for (int i = 0; i < bulletList.size(); i++) {
            Bullets bullets = bulletList.get(i);
            basicbulletSheet.draw(bullets.location.getX(), bullets.location.getY(), 32, 32);
            bulletCircle = new Circle(bullets.location.getX(), bullets.location.getY(), 10);
            for (Enemy enemy : enemies) {
                enemy.Playrect = new Circle(enemy.getStartPosX() * w + r,
                        enemy.getStartPosY() * w + r, 25);
                if (bulletList.size() > 0 && bulletCircle.intersects(enemy.Playrect)) {
                    bulletCount++;
                    collides = true;
                    enemy.setHP(enemy.getHP() - 1);
                    if (collides) {
                        bulletList.remove(i);
                    } else {
                        collides = false;
                    }
                }
            }
            //If the bullet runs out of the screen, remove it
            if (bulletCircle.getX() > (loadMap.HEIGHT - 6) * w || bulletCircle.getY() > loadMap.WIDTH * w) {
                bulletList.remove(i);
            }
            //g.setColor(transparent);
            //g.fill(bulletCircle);
        }

        g.setColor(Color.green);
        g.drawString("Level:   " + currentLevel, 715, 385);

        //SELL TOWERS
        if (loadMap.MAP[mouseY / w][mouseX / w] == 12) {
            if (input.isMousePressed(0)) {
                sellTower = true;
                upgradePressed = false;
            }
        }
        //Remove sellTower or Upgradepressed if mouse is on a tower in the menu
        if (loadMap.MAP[mouseY / w][mouseX / w] == 8 || loadMap.MAP[mouseY / w][mouseX / w] == 6 ||
                loadMap.MAP[mouseY / w][mouseX / w] == 10 || loadMap.MAP[mouseY / w][mouseX / w] == 4) {
            sellTower = false;
            upgradePressed = false;
        }

        //Visible if selltower is pressed
        if (sellTower) {
            loadMap.MAP[4][11] = 13;
        } else {
            loadMap.MAP[4][11] = 12;
        }
        //Sell Basic
        if (loadMap.MAP[mouseY / w][mouseX / w] == 5 && basicTowers.listIterator().hasNext()) {
            BasicTower b = getBasicTower(mouseY / w, mouseX / w);
            g.drawString("BasicTower", 715, 445);
            g.drawString("Refund value:   " + basicTowers.listIterator().next().refundValue, 715, 475);
            g.drawString("Upgrade Cost:   " + basicTowers.listIterator().next().upgradeCost, 715, 505);
            if (b != null) {
                g.drawString("Tower Level:   " + b.towerLevel, 715, 535);
            }
            if (sellTower) {
                if (input.isMousePressed(0)) {
                    BasicTower t = getBasicTower(mouseY / w, mouseX / w);
                    if (t != null) {
                        player.addCredits(t.refundValue);
                        basicTowers.remove(t);
                    }
                    loadMap.MAP[mouseY / w][mouseX / w] = 1;
                    sellTower = false;
                }
            }
        }

        //Sell Bomber
        if (loadMap.MAP[mouseY / w][mouseX / w] == 7 && bomberTowers.listIterator().hasNext()) {
            BomberTower b = getBomberTower(mouseY / w, mouseX / w);
            g.drawString("BomberTower", 715, 445);
            g.drawString("Refund value:   " + bomberTowers.listIterator().next().refundValue, 715, 475);
            g.drawString("Upgrade Cost:   " + bomberTowers.listIterator().next().upgradeCost, 715, 505);
            if (b != null) {
                g.drawString("Tower Level:   " + b.towerLevel, 715, 535);
            }
            if (sellTower) {
                if (input.isMousePressed(0)) {
                    BomberTower t = getBomberTower(mouseY / w, mouseX / w);
                    if (t != null) {
                        player.addCredits(t.refundValue);
                        bomberTowers.remove(t);
                    }
                    loadMap.MAP[mouseY / w][mouseX / w] = 1;
                    sellTower = false;
                }
            }
        }

        //Sell QuickTower
        if (loadMap.MAP[mouseY / w][mouseX / w] == 9 && quickTowers.listIterator().hasNext()) {
            QuickTower b = getQuickTower(mouseY / w, mouseX / w);
            g.drawString("QuickTower", 715, 445);
            g.drawString("Refund value:   " + quickTowers.listIterator().next().refundValue, 715, 475);
            g.drawString("Upgrade Cost:   " + quickTowers.listIterator().next().upgradeCost, 715, 505);
            if (b != null) {
                g.drawString("Tower Level:   " + b.towerLevel, 715, 535);
            }
            if (sellTower) {
                if (input.isMousePressed(0)) {
                    QuickTower t = getQuickTower(mouseY / 64, mouseX / 64);
                    if (t != null) {
                        player.addCredits(t.refundValue);
                        quickTowers.remove(t);
                    }
                    loadMap.MAP[mouseY / w][mouseX / w] = 1;
                    sellTower = false;
                }
            }
        }
        //Sell Sniper
        if (loadMap.MAP[mouseY / w][mouseX / w] == 11 && sniperTowers.listIterator().hasNext()) {
            SniperTower s = getSniperTower(mouseY / w, mouseX / w);
            g.drawString("SniperTower", 715, 445);
            g.drawString("Refund Value:  " + sniperTowers.listIterator().next().refundValue, 715, 475);
            g.drawString("Upgrade Cost:   " + sniperTowers.listIterator().next().upgradeCost, 715, 505);
            if (s != null) {
                g.drawString("Tower Level:   " + s.towerLevel, 715, 535);
            }
            if (sellTower) {
                if (input.isMousePressed(0)) {
                    SniperTower t = getSniperTower(mouseY / 64, mouseX / 64);
                    if (t != null) {
                        player.addCredits(t.refundValue);
                        sniperTowers.remove(t);
                    }
                    loadMap.MAP[mouseY / w][mouseX / w] = 1;
                    sellTower = false;
                }
            }
        }


        //UPGRADE TOWERS
        if (loadMap.MAP[mouseY / w][mouseX / w] == 14) {
            if (input.isMousePressed(0)) {
                upgradePressed = true;
            }
        }
        //Visible if upgradePressed is pressed
        if (upgradePressed) {
            loadMap.MAP[4][13] = 15;
        } else {
            loadMap.MAP[4][13] = 14;
        }

        //Upgrade Basic
        if (loadMap.MAP[mouseY / w][mouseX / w] == 5 && basicTowers.listIterator().hasNext()) {
            if (upgradePressed) {
                if (input.isMousePressed(0)) {
                    BasicTower t = getBasicTower(mouseY / w, mouseX / w);
                    if (t != null) {
                        if (t.towerLevel < t.maxTowerLevel) {
                            if (player.getCredits() >= t.upgradeCost) {
                                t.towerLevel++;
                                t.ap = t.ap + 1;
                                t.coolDown = t.coolDown - 50;

                                player.addCredits(-t.upgradeCost);
                            }
                        }
                        upgradePressed = false;
                    }
                }
            }
        }

        //Upgrade Bomber
        if (loadMap.MAP[mouseY / w][mouseX / w] == 7 && bomberTowers.listIterator().hasNext()) {
            if (upgradePressed) {
                if (input.isMousePressed(0)) {
                    BomberTower t = getBomberTower(mouseY / w, mouseX / w);
                    if (t != null) {
                        if (t.towerLevel < t.maxTowerLevel) {
                            if (player.getCredits() >= t.upgradeCost) {
                                t.towerLevel++;
                                t.attackPower++;
                                //t.Radius = t.setRadius();
                                t.coolDown = t.coolDown - 300;

                                player.addCredits(-t.upgradeCost);
                            }
                        }
                        upgradePressed = false;
                    }
                }
            }
        }

        //Upgrade Quick
        if (loadMap.MAP[mouseY / w][mouseX / w] == 9 && quickTowers.listIterator().hasNext()) {
            if (upgradePressed) {
                if (input.isMousePressed(0)) {
                    QuickTower t = getQuickTower(mouseY / w, mouseX / w);
                    if (t != null) {
                        if (t.towerLevel < t.maxTowerLevel) {
                            if (player.getCredits() >= t.upgradeCost) {
                                t.towerLevel++;
                                t.attackPower++;
                                //t.Radius = t.setRadius();
                                t.coolDown = t.coolDown - 20;

                                player.addCredits(-t.upgradeCost);
                            }
                        }
                        upgradePressed = false;
                    }
                }
            }
        }

        //Upgrade Sniper
        if (loadMap.MAP[mouseY / w][mouseX / w] == 11 && sniperTowers.listIterator().hasNext()) {
            if (upgradePressed) {
                if (input.isMousePressed(0)) {
                    SniperTower t = getSniperTower(mouseY / w, mouseX / w);
                    if (t != null) {
                        if (t.towerLevel < t.maxTowerLevel) {
                            if (player.getCredits() >= t.upgradeCost) {
                                t.towerLevel++;
                                t.attackPower++;
                                //t.Radius = t.setRadius();
                                t.coolDown = t.coolDown - 500;

                                player.addCredits(-t.upgradeCost);
                            }
                        }
                        upgradePressed = false;
                    }
                }
            }
        }

        g.setFont(pauseFont);
        g.setColor(white);
        g.drawString("PRESS 'ESC' FOR PAUSE", 100,8);

    }


    /**
     * Controls the movement of moveable objects
     *
     * @param gc: A generic game container that handles the game loop, fps recording and managing the input system
     * @param stateBasedGame: A state based game isolated different stages of the game
     *                      (menu, ingame, hiscores, etc) into different states
     *                       so they can be easily managed and maintained.
     * @param delta: The amount of time thats passed since last update in milliseconds
     */
    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
        timeSinceStart += delta;
        rtimeSinceStart += delta;
        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();


        basicbulletSheet.rotate(90f);
        basicbulletSheet.setCenterOfRotation(16, 16);

        // Move this bullet
        for (int i = 0; i < bulletList.size(); i++) {
            bullet = bulletList.get(i);
            bullet.move();
        }

        //Add this tower to the this towerList
        if (Tower.isBasicPlaced()) {
            basicTowers.add(new BasicTower());
            System.out.println(basicTowers);
            Tower.setBasicPlaced(false);
        }

        //Add this tower to the this towerList
        if (Tower.isBomberPlaced()) {
            bomberTowers.add(new BomberTower());
            System.out.println(bomberTowers);
            Tower.setBomberPlaced(false);
        }

        //Add this tower to the this towerList
        if (Tower.isSniperPlaced()) {
            sniperTowers.add(new SniperTower());
            System.out.println(sniperTowers);
            Tower.setSniperPlaced(false);
        }

        //Add this tower to the this towerList
        if (Tower.isQuickPlaced()) {
            quickTowers.add(new QuickTower());
            System.out.println(quickTowers);
            Tower.setQuickPlaced(false);
        }

        //For this tower, calculate how often this tower will shoot bullets
        for (BasicTower basicTower1 : basicTowers) {
            for (Enemy enemy : enemies) {
                enemy.Playrect = new Circle(enemy.getStartPosX() * w + r,
                        enemy.getStartPosY() * w + r, 10);
                if (rtimeSinceStart > basicTower1.rcoolDown + basicTower1.rlastShot) {
                    if (enemy.Playrect.intersects(basicTower1.Radius)) {
                        basicTower1.basicClicked.setRotation((float) getTargetAngle(basicTower1.towerX,
                                basicTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY()));
                        basicTower1.basicClicked.setCenterOfRotation(32, 32);
                        basicTower1.rlastShot = rtimeSinceStart;
                    }
                }
                if (timeSinceStart > basicTower1.coolDown + basicTower1.lastShot) {
                    if (enemy.Playrect.intersects(basicTower1.Radius)) {
                        addNewBullet2(basicTower1.towerX, basicTower1.towerY, enemy.getStartPosX(),
                                enemy.getStartPosY(), 10);
                        basicTower1.lastShot = timeSinceStart;
                    }

                }
            }
        }

        //For this tower, calculate how often this tower will shoot bullets
        for (BomberTower bomberTower1 : bomberTowers) {
            for (Enemy enemy : enemies) {
                enemy.Playrect = new Circle(enemy.getStartPosX() * w + r,
                        enemy.getStartPosY() * w + r, 10);
                if (rtimeSinceStart > bomberTower1.rcoolDown + bomberTower1.rlastShot) {
                    if (enemy.Playrect.intersects(bomberTower1.Radius)) {
                        bomberTower1.bomberClicked.setRotation((float) getTargetAngle(bomberTower1.bombertowerX,
                                bomberTower1.bombertowerY, enemy.getStartPosX(), enemy.getStartPosY()));
                        bomberTower1.bomberClicked.setCenterOfRotation(32, 32);
                        bomberTower1.rlastShot = rtimeSinceStart;
                    }
                }
                if (timeSinceStart > bomberTower1.coolDown + bomberTower1.lastShot) {
                    if (enemy.Playrect.intersects(bomberTower1.Radius)) {
                        addNewBullet2(bomberTower1.bombertowerX, bomberTower1.bombertowerY, enemy.getStartPosX(),
                                enemy.getStartPosY(), 10);
                        bomberTower1.lastShot = timeSinceStart;
                    }
                }
            }
        }
        //For this tower, calculate how often this tower will shoot bullets
        for (SniperTower sniperTower1 : sniperTowers) {
            for (Enemy enemy : enemies) {
                enemy.Playrect = new Circle(enemy.getStartPosX() * w + r,
                        enemy.getStartPosY() * w + r, 10);

                if (rtimeSinceStart > sniperTower1.rcoolDown + sniperTower1.rlastShot) {
                    if (enemy.Playrect.intersects(sniperTower1.Radius)) {
                        sniperTower1.sniperClicked.setRotation((float) getTargetAngle(sniperTower1.towerX,
                                sniperTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY()));
                        sniperTower1.sniperClicked.setCenterOfRotation(32, 32);
                        sniperTower1.rlastShot = rtimeSinceStart;
                    }
                }

                if (timeSinceStart > sniperTower1.coolDown + sniperTower1.lastShot) {
                    if (enemy.Playrect.intersects(sniperTower1.Radius)) {
                        addNewBullet2(sniperTower1.towerX, sniperTower1.towerY, enemy.getStartPosX(),
                                enemy.getStartPosY(), 50);
                        sniperTower1.lastShot = timeSinceStart;
                    }

                }
            }
        }
        //For this tower, calculate how often this tower will shoot bullets
        for (QuickTower quickTower1 : quickTowers) {
            for (Enemy enemy : enemies) {
                enemy.Playrect = new Circle(enemy.getStartPosX() * w + r,
                        enemy.getStartPosY() * w + r, 10);

                if (rtimeSinceStart > quickTower1.rcoolDown + quickTower1.rlastShot) {
                    if (enemy.Playrect.intersects(quickTower1.Radius)) {
                        quickTower1.quickClicked.setRotation((float) getTargetAngle(quickTower1.towerX,
                                quickTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY()));
                        quickTower1.quickClicked.setCenterOfRotation(32, 32);
                        quickTower1.rlastShot = rtimeSinceStart;
                    }
                }


                if (timeSinceStart > quickTower1.coolDown + quickTower1.lastShot) {
                    if (enemy.Playrect.intersects(quickTower1.Radius)) {
                        radiusVisited = true;
                        addNewBullet2(quickTower1.towerX, quickTower1.towerY, enemy.getStartPosX(),
                                enemy.getStartPosY(), 5);
                        quickTower1.lastShot = timeSinceStart;
                    }
                }

            }
        }

        //A spawn is in progress
        if (spawninProgress) {
            timePassedEnemy += delta;
            if (timePassedEnemy > 800) {
                enemies.add(new Enemy());
                enemySpawns++;
                timePassedEnemy = 0;
            }
        }
        //When enough enemies has spawned, stop the spawninProgress
        if (enemySpawns == enemyCounter) {
            spawninProgress = false;
            //hasbeenDead = false;
            enemySpawns = 0;
            enemyCounter++;
        }

        //When no more enemies on maps
        if (enemies.size() == 0) {
            waveinProgress = false;
            startWaveCount = 0;
        }

        //Start a new level when there's no more enemies on the map
        if (loadMap.MAP[mouseY / w][mouseX / w] == 16) {
            if (input.isMousePressed(0) && startWaveCount == 0 && !waveinProgress) {
                startWaveCount++;
                if (startWaveCount == 1 && enemies.size() == 0 && !waveinProgress) {
                    waveinProgress = true;
                    gc.resume();
                    spawninProgress = true;
                    currentLevel++;
                }
            }
        }

        //For each new level, increase the HP of each enemy
        if (currentLevel < currentLevel + 1 && !waveinProgress) {
            for (Enemy enemyHP : enemies) {
                if (enemyHP.getStartPosX() <= 0 && enemyHP.getHP() < enemyHP.startHP + currentLevel) {
                    enemyHP.setHP(enemyHP.getHP() + currentLevel);
                }
            }
        }


        //For each enemies, if enemies has finished their way, decrease player HP
        //and set them inactive
        for (Enemy enemyList : enemies) {
            if (enemyList.counter >= enemyList.path.getLength() - 1) {
                player.decreaseLife();
                bulletCount = 0;
                enemyList.isActive = false;
            }

            //If enemies' hp is zero, set them inactive and remove from the list
            if (enemyList.getHP() <= 0) {
                enemyList.isActive = false;
                bulletCount = 0;
                player.addCredits(20);
            }
            enemyList.update(gc, stateBasedGame, delta);
        }
        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).isActive) {
                enemies.remove(enemies.get(i));
            }
        }

        //For all objects, update
        for (GameObject obj : objects)
            try {
                obj.update(gc, stateBasedGame, delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        //Go to Menu
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            gc.getInput().clearKeyPressedRecord();
            stateBasedGame.enterState(2);
        }


    }


    /**
     *
     * @param startX The x coordinate of the starting position used to tower rotate
     * @param startY The y coordinate of the starting position used to tower rotate
     * @param endX The x coordinate of the end position used to tower rotate
     * @param endY The y coordinate of the end position used to tower rotate
     * @return
     */
    public double getDistanceBetween(float startX, float startY, float endX, float endY) {
        float dx = endX - startX;
        float dy = endY - startY;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     *
     * @param startX The x coordinate of the starting position used to tower rotate
     * @param startY The y coordinate of the starting position used to tower rotate
     * @param targetX The x coordinate of the target position used to tower rotate
     * @param targetY The y coordinate of the target position used to tower rotate
     * @return the final angle of the rotation
     */
    public double getTargetAngle(float startX, float startY, float targetX, float targetY) {
        double dist = getDistanceBetween(startX, startY, targetX, targetY);
        double sinNewAng = (startY - targetY) / dist;
        double cosNewAng = (targetX - startX) / dist;
        double angle;

        if (sinNewAng > 0) {
            if (cosNewAng > 0) {
                angle = 90 - Math.toDegrees(Math.asin(sinNewAng));
            } else {
                angle = Math.toDegrees(Math.asin(sinNewAng)) + 270;
            }
        } else {
            angle = Math.toDegrees(Math.acos(cosNewAng)) + 90;
        }
        return angle;
    }


    /**
     *
     * @param x1 start X of the bullet
     * @param y1 start Y of the bullet
     * @param x2 end X of the bullet
     * @param y2 end Y of the bullet
     * @param speed
     * @throws SlickException
     */
    public void addNewBullet2(float x1, float y1, float x2, float y2, float speed) throws SlickException {
        bulletList.add(new Bullets(x1 * 64 + 16, y1 * 64 + 16,
                x2 * 64 + 24, y2 * 64 + 24, speed));
    }

    /**
     * Get a specific tower on the list according to mouse x,y
     *
     * @param x mouseX-pos
     * @param y mouseY-pos
     * @return a specific tower on the this towerlist
     */
    public BasicTower getBasicTower(int x, int y) {
        BasicTower thisBasicTower = null;
        for (BasicTower t : basicTowers) {
            if (x == t.towerY && y == t.towerX) {
                thisBasicTower = t;
            }
        }
        return thisBasicTower;
    }

    /**
     * Get a specific tower on the list according to mouse x,y
     *
     * @param x mouseX-pos
     * @param y mouseY-pos
     * @return a specific tower on the this towerlist
     */
    public BomberTower getBomberTower(int x, int y) {
        BomberTower thisBomberTower = null;
        for (BomberTower t : bomberTowers) {
            if (x == t.bombertowerY && y == t.bombertowerX) {
                thisBomberTower = t;
            }
        }
        return thisBomberTower;
    }

    /**
     * Get a specific tower on the list according to mouse x,y
     *
     * @param x mouseX-pos
     * @param y mouseY-pos
     * @return a specific tower on the this towerlist
     */
    public QuickTower getQuickTower(int x, int y) {
        QuickTower thisQuickTower = null;
        for (QuickTower t : quickTowers) {
            if (x == t.towerY && y == t.towerX) {
                thisQuickTower = t;
            }
        }
        return thisQuickTower;
    }

    /**
     * Get a specific tower on the list according to mouse x,y
     *
     * @param x mouseX-pos
     * @param y mouseY-pos
     * @return a specific tower on the this towerlist
     */
    public SniperTower getSniperTower(int x, int y) {
        SniperTower thisSniperTower = null;
        for (SniperTower t : sniperTowers) {
            if (x == t.towerY && y == t.towerX) {
                thisSniperTower = t;
            }
        }
        return thisSniperTower;
    }
}
