package Towers;


import Grid.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class SniperTower {

    public Image sniperClicked = new Image("data/Towers/Sniper/Sniper_Click.png");

    public static int newBuyingCost = 400;
    public static int newRefundValue = (newBuyingCost / 3) * 2;
    public static int newUpgradeCost = newBuyingCost/2;
    public static double newRange = 100;
    public static double newAttackPower = 0.6;
    private static int sniperTowerX;
    private static int sniperTowerY;
    public static int newTowerLevel = 1;
    public int buyingCost;
    public int upgradeCost;
    public int refundValue;
    public double range;
    public double attackPower;
    public int towerX;
    public int towerY;
    public int coolDown = 2000;
    public long lastShot = 0;
    public int rcoolDown = 1;
    public long rlastShot = 0;
    public Image sniperPlaced = new Image("data/Towers/Sniper/Sniper_Click.png");
    public int towerLevel;
    public int maxTowerLevel = 3;
    public static Shape radius = null;
    public Shape Radius;

    /**
     * Creates a new Sniper with a x and y pos.
     *
     * @throws SlickException
     */
    public SniperTower() throws SlickException {
        this.towerX = getSniperTowerX();
        this.towerY = getSniperTowerY();
        this.buyingCost = newBuyingCost;
        this.refundValue = newRefundValue;
        this.upgradeCost = newUpgradeCost;
        this.range = newRange;
        this.attackPower = newAttackPower;
        this.Radius = getRadius();
        this.towerLevel = newTowerLevel;
        Image sniperPlaced = this.sniperPlaced;

    }

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

    public static Shape getRadius() {
        return radius;
    }

    public static void setRadius(Shape radius) {
        SniperTower.radius = radius;
    }

}

