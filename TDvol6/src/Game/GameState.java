package Game;

import Enemies.Enemy;
import Grid.GameObject;
import Grid.LoadMap;
import Bullets.Bullets;
import Player.Player;
import Towers.*;
import com.sun.xml.internal.bind.v2.model.annotation.Quick;
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

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static org.newdawn.slick.Color.transparent;

public class GameState extends BasicGameState {
    long timeSinceStart = 0;
    public LoadMap loadmap;
    private static final int e = 48;
    private static final int r = 32;
    public static LinkedList<GameObject> objects = new LinkedList<>();
    private int timePassedEnemy;
    List<Enemy> enemies = new ArrayList<>();
    List<BasicTower> basicTowers = new ArrayList<>();
    List<BomberTower> bomberTowers = new ArrayList<>();
    List<SniperTower> sniperTowers = new ArrayList<>();
    List<QuickTower> quickTowers = new ArrayList<>();
    Bullets bullet = new Bullets();
    public boolean waveinProgress = true;
    public ArrayList<Bullets> bulletList = new ArrayList<>();
    Enemy enemy = new Enemy();
    public Shape bulletCircle = null;
    public boolean collides = false;
    public int bulletCount;
    LoadMap loadMap = new LoadMap();
    int w = 64;
    Player player = new Player();
    public boolean hasbeenDead;
    int pauseClick;
    private Image basicbulletSheet = null;
    int maxHealth = 5;
    float angle;
    public boolean radiusVisited;


    public GameState() throws SlickException {
    }

    @Override
    public int getID() {
        return 1;
    }


    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
        basicbulletSheet = new Image("data/towers/Basic/basicBullet.png");

        timePassedEnemy = 0;
        objects.add(enemy);

        System.out.println(objects);

        for (Enemy enemies : enemies) {
            enemies.init(gc, stateBasedGame);
        }


        loadmap = new LoadMap();
        loadmap.init(gc, stateBasedGame);
        for (GameObject obj : objects) {
            obj.init(gc, stateBasedGame);
        }

    }

    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        loadmap.render(gc, stateBasedGame, g);


        for (Enemy enemies : enemies) {
            enemies.render(gc, stateBasedGame, g);

            Rectangle bar = new Rectangle(enemies.getStartPosX() * w + 8, enemies.getStartPosY() * w, 50 * enemies.getHP() / maxHealth, 6);
            GradientFill fill = new GradientFill(enemies.getStartPosX() * w + 8, 0, new Color(255, 60, 0),
                    enemies.getStartPosX() * w + 60, 0, new Color(255, 180, 0));

            g.setColor(Color.darkGray);
            g.fillRect(enemies.getStartPosX() * w + 8, enemies.getStartPosY() * w, 50, 6);
            g.fill(bar, fill);


            enemies.e1.draw(enemies.getStartPosX() * w + 8, enemies.getStartPosY() * w + 8, w - 16, w - 16);
        }
        for (GameObject obj : objects) {
            obj.render(gc, stateBasedGame, g);
        }


        for (int i = 0; i < bulletList.size(); i++) {
            Bullets bullets = bulletList.get(i);
            basicbulletSheet.draw(bullets.location.getX(), bullets.location.getY(), 32, 32);
            bulletCircle = new Circle(bullets.location.getX(), bullets.location.getY(), 10);
            for (Enemy enemy : enemies) {
                enemy.Playrect = new Circle(enemy.getStartPosX() * w + r, enemy.getStartPosY() * w + r, 25);
                if (bulletCircle.intersects(enemy.Playrect) && bulletList.size() > 0) {
                    bulletCount++;
                    bulletList.remove(i);
                    enemy.setHP(enemy.getHP() - 1);
                    collides = true;
                } else {
                    collides = false;
                }
            }

            if (bulletCircle.getX() > (loadMap.HEIGHT - 6) * w || bulletCircle.getY() > loadMap.WIDTH * w) {
                bulletList.remove(i);
            }
            g.setColor(transparent);
            g.fill(bulletCircle);
        }

    }


    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException {
        timeSinceStart += delta;
        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        ListIterator<Enemy> it = enemies.listIterator();



/*
        if (mouseX < 12 * w && mouseX > 12 * w - w && mouseY < 11 * w && mouseY > 11 * w - w) {
            if (input.isMousePressed(0)) {


                pauseClick++;
                if (pauseClick % 2 == 1)

                if (pauseClick % 2 == 0) {
                    gc.pause();
                }
            }
        }
       */


        for (int i = 0; i < bulletList.size(); i++) {
            bullet = bulletList.get(i);
            bullet.move();
        }

        if (Tower.isBasicPlaced()) {
            basicTowers.add(new BasicTower());
            System.out.println(basicTowers);
            System.out.println(Tower.isBasicPlaced());
            Tower.setBasicPlaced(false);
        }

        if (Tower.isBomberPlaced()) {
            bomberTowers.add(new BomberTower());
            System.out.println(basicTowers);
            System.out.println(Tower.isBomberPlaced());
            Tower.setBomberPlaced(false);
        }

        if (Tower.isSniperPlaced()) {
            sniperTowers.add(new SniperTower());
            System.out.println(sniperTowers);
            System.out.println(Tower.isSniperPlaced());
            Tower.setSniperPlaced(false);
        }

        if (Tower.isQuickPlaced()) {
            quickTowers.add(new QuickTower());
            System.out.println(quickTowers);
            System.out.println(Tower.isQuickPlaced());
            Tower.setQuickPlaced(false);
        }


        if (waveinProgress) {
            timePassedEnemy += delta;
            if (timePassedEnemy > 1000) {
                enemies.add(new Enemy());
                timePassedEnemy = 0;
            }
            if (enemies.size() == 10) {
                waveinProgress = false;
                hasbeenDead = false;
            }
        }


        if (enemies.size() == 0) {
            hasbeenDead = true;
            waveinProgress = true;
        }


        basicbulletSheet.rotate(90f);
        basicbulletSheet.setCenterOfRotation(16, 16);


        if (!Tower.isBomberPlaced()) {
            for (BomberTower bomberTower1 : bomberTowers) {
                for (Enemy enemy : enemies) {
                    enemy.Playrect = new Circle(enemy.getStartPosX() * w + r, enemy.getStartPosY() * w + r, 10);
                    if (enemy.Playrect.intersects(bomberTower1.Radius)) {
                        loadmap.bomberPlaced.setRotation((float) getTargetAngle(bomberTower1.bombertowerX, bomberTower1.bombertowerY, enemy.getStartPosX(), enemy.getStartPosY()));
                        loadmap.bomberPlaced.setCenterOfRotation(32, 32);
                    }
                    if (timeSinceStart > bomberTower1.coolDown + bomberTower1.lastShot) {
                        if (enemy.Playrect.intersects(bomberTower1.Radius)) {
                            addNewBullet2(bomberTower1.bombertowerX, bomberTower1.bombertowerY, enemy.getStartPosX(), enemy.getStartPosY(), 10);
                            bomberTower1.lastShot = timeSinceStart;
                        }

                    }
                }
            }
        }


        if (!Tower.isBasicPlaced()) {
            for (BasicTower basicTower1 : basicTowers) {
                for (Enemy enemy : enemies) {
                    enemy.Playrect = new Circle(enemy.getStartPosX() * w + r, enemy.getStartPosY() * w + r, 10);
                    if (enemy.Playrect.intersects(basicTower1.Radius)) {
                        loadmap.basicPlaced.setRotation((float) getTargetAngle(basicTower1.towerX, basicTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY()));
                        loadmap.basicPlaced.setCenterOfRotation(32, 32);
                    }
                    if (timeSinceStart > basicTower1.coolDown + basicTower1.lastShot) {
                        if (enemy.Playrect.intersects(basicTower1.Radius)) {
                            addNewBullet2(basicTower1.towerX, basicTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY(), 10);
                            basicTower1.lastShot = timeSinceStart;
                        }

                    }
                }
            }
        }


        if (!Tower.isSniperPlaced()) {
            for (SniperTower sniperTower1 : sniperTowers) {
                for (Enemy enemy : enemies) {
                    enemy.Playrect = new Circle(enemy.getStartPosX() * w + r, enemy.getStartPosY() * w + r, 10);
                    if (enemy.Playrect.intersects(sniperTower1.Radius)) {
                        loadmap.sniperPlaced.setRotation((float) getTargetAngle(sniperTower1.towerX, sniperTower1.towerY, enemies.get(0).getStartPosX(), enemies.get(0).getStartPosY()));
                        loadmap.sniperPlaced.setCenterOfRotation(32, 32);
                    }
                    if (timeSinceStart > sniperTower1.coolDown + sniperTower1.lastShot) {
                        if (enemy.Playrect.intersects(sniperTower1.Radius)) {
                            addNewBullet2(sniperTower1.towerX, sniperTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY(), 30);
                            sniperTower1.lastShot = timeSinceStart;
                        }

                    }
                }
            }
        }

        if (!Tower.isQuickPlaced()) {
            for (QuickTower quickTower1 : quickTowers) {
                for (Enemy enemy : enemies) {
                    enemy.Playrect = new Circle(enemy.getStartPosX() * w + r, enemy.getStartPosY() * w + r, 10);

                    if (enemy.Playrect.intersects(quickTower1.Radius)){
                        loadmap.quickPlaced.setRotation((float) getTargetAngle(quickTower1.towerX, quickTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY()));
                        loadmap.quickPlaced.setCenterOfRotation(32, 32);
                    }
                    if (timeSinceStart > quickTower1.coolDown + quickTower1.lastShot) {
                        if (enemy.Playrect.intersects(quickTower1.Radius)) {
                            radiusVisited = true;
                            loadmap.quickPlaced.setRotation((float) getTargetAngle(quickTower1.towerX, quickTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY()));
                            loadmap.quickPlaced.setCenterOfRotation(32, 32);
                            addNewBullet2(quickTower1.towerX, quickTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY(), 5);
                            quickTower1.lastShot = timeSinceStart;
                        }

                    }
                }
            }
        }


        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).isActive) {
                enemies.remove(enemies.get(i));
            }
        }


        for (Enemy enemyList : enemies) {
            if (enemyList.counter >= enemyList.path.getLength() - 1) {
                player.decreaseLife();
                bulletCount = 0;
                enemyList.isActive = false;
            }


            if (enemyList.getHP() <= 0) {
                enemyList.isActive = false;
                bulletCount = 0;
                Player.currency += 100;
            }
            enemyList.update(gc, stateBasedGame, delta);
        }


        if (input.isKeyPressed(Input.KEY_1)) {
            enemies.add(new Enemy());
        }

        for (GameObject obj : objects)
            try {
                obj.update(gc, stateBasedGame, delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        //Go to Menu
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            gc.getInput().clearKeyPressedRecord();
            stateBasedGame.enterState(0);
        }


    }


    public double getDistanceBetween(float startX, float startY, float endX, float endY) {
        float dx = endX - startX;
        float dy = endY - startY;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

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


    public void addNewBullet2(float x1, float y1, float x2, float y2, float speed) throws SlickException {
        bulletList.add(new Bullets(x1 * 64 + 16, y1 * 64 + 16, x2 * 64 + 24, y2 * 64 + 24, speed));
    }
}





/*
        if (!Tower.isBasicPlaced()) {
            timePassedBullet += delta;
            if (timePassedBullet > 800 && enemy.isActive) {
                timePassedBullet = 0;
                for (BasicTower basicTower1 : basicTowers) {
                    for (Enemy enemy : enemies) {
                        enemy.Playrect = new Circle(enemy.getStartPosX() * w + r, enemy.getStartPosY() * w + r, 25);
                        if(enemies.get(currentX).Playrect.intersects(basicTower1.Radius)) {
                            enemy = enemies.get(currentX);
                            addNewBullet2(basicTower1.towerX, basicTower1.towerY, enemy.getStartPosX(), enemy.getStartPosY());
                            radiusVisited = true;
                            //System.out.println(enemy.HP);
                        }
                        if(radiusVisited && !enemies.get(currentX).Playrect.intersects(basicTower1.Radius)) {
                            if (currentX <= 8){
                                currentX++;
                            }
                            radiusVisited = false;
                        }

                        if (radiusVisited && !enemy.isActive) {
                            if (currentX <= 8){
                                currentX++;
                            }
                            radiusVisited = false;
                        }
                        System.out.println(currentX);
                    }
                }
            }
        }
*/