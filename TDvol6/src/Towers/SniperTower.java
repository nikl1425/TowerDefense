package Towers;


import Grid.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class SniperTower implements GameObject {

    public Image sniperClicked = new Image("data/Towers/Sniper/Sniper_Click.png");

    public static int newBuyingCost = 100;
    public static int newRefundValue = 80;
    public static int newUpgradeCost = 50;
    public static double newRange = 100;
    public static double newAttackPower = 0.6;
    private static int sniperTowerX;
    private static int sniperTowerY;
    public int buyingCost;
    public int upgradeCost;
    public int refundValue;
    public double range;
    public double attackPower;
    public int towerX;
    public int towerY;
    protected type towerType;
    float x;
    public int coolDown = 3000;
    public long lastShot = 0;


    float y;
    public static Shape radius = null;
    public Shape Radius;

    public static int getSniperTowerX() {
        return sniperTowerX;
    }

    public static void setSniperTowerX(int sniperTowerX) {
        SniperTower.sniperTowerX = sniperTowerX;
    }

    public static int getSniperTowerY() {
        return sniperTowerY;
    }

    public static void setSniperTowerY(int sniperTowerY) {
        SniperTower.sniperTowerY = sniperTowerY;
    }


    public enum type {BASIC, QUICK, SNIPER, BOMBER}

    public SniperTower() throws SlickException {
        this.towerX = getSniperTowerX();
        this.towerY = getSniperTowerY();
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
        sniperClicked.draw(getSniperTowerX(), getSniperTowerY(), 64, 64);

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

