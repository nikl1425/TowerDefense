package Towers;


import Grid.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BomberTower implements GameObject {

    public Image bomberClicked = new Image("data/Towers/Bomber/Bomber_Click.png");

    public static int newBuyingCost = 100;
    public static int newRefundValue = 80;
    public static int newUpgradeCost = 50;
    public static double newRange = 100;
    public static double newAttackPower = 0.6;
    private static int bomberTowerX;
    private static int bomberTowerY;
    public int buyingCost;
    public int upgradeCost;
    public int refundValue;
    public double range;
    public double attackPower;
    public int bombertowerX;
    public int bombertowerY;
    protected type towerType;
    float x;
    public int coolDown = 1500;
    public long lastShot =0;


    float y;
    public static Shape radius = null;
    public Shape Radius;

    public static int getBomberTowerX() {
        return bomberTowerX;
    }

    public static void setBomberTowerX(int bomberTowerX) {
        BomberTower.bomberTowerX = bomberTowerX;
    }

    public static int getBomberTowerY() {
        return bomberTowerY;
    }

    public static void setBomberTowerY(int bomberTowerY) {
        BomberTower.bomberTowerY = bomberTowerY;
    }


    public enum type{BASIC, QUICK, SNIPER, BOMBER}

    public BomberTower() throws SlickException {
        this.bombertowerX = getBomberTowerX();
        this.bombertowerY = getBomberTowerY();
        this.buyingCost = newBuyingCost;
        this.refundValue = newRefundValue;
        this.upgradeCost = newUpgradeCost;
        this.range = newRange;
        this.attackPower = newAttackPower;
        this.towerType = type.BASIC;
        this.Radius = getRadius();


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
        bomberClicked.draw(getBomberTowerX(), getBomberTowerY(), 64, 64);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException, InterruptedException {

    }

    public static Shape getRadius() {
        return radius;
    }

    public static void setRadius(Shape radius) {
        BasicTower.radius = radius;
    }

}

