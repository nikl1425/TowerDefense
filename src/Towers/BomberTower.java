package Towers;


import Grid.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BomberTower {

    public Image bomberClicked = new Image("data/Towers/Bomber/Bomber_Click.png");

    public static int newBuyingCost = 450;
    public static int newRefundValue = (newBuyingCost / 3) * 2;
    public static int newUpgradeCost = newBuyingCost/2;
    public static double newRange = 100;
    public static double newAttackPower = 0.6;
    private static int bomberTowerX;
    private static int bomberTowerY;
    public static int newTowerLevel = 1;
    public int buyingCost;
    public int upgradeCost;
    public int refundValue;
    public double range;
    public double attackPower;
    public int bombertowerX;
    public int bombertowerY;
    public int coolDown = 1500;
    public long lastShot =0;
    public int rcoolDown = 1;
    public long rlastShot = 0;
    public Image bomberPlaced = new Image("data/Towers/Bomber/Bomber_Click.png");
    public int towerLevel;
    public int maxTowerLevel = 3;
    public static Shape radius = null;
    public Shape Radius;

    /**
     * Creates a new Bombertower with a x and y pos.
     *
     * @throws SlickException
     */
    public BomberTower() throws SlickException {
        this.bombertowerX = getBomberTowerX();
        this.bombertowerY = getBomberTowerY();
        this.buyingCost = newBuyingCost;
        this.refundValue = newRefundValue;
        this.upgradeCost = newUpgradeCost;
        this.range = newRange;
        this.attackPower = newAttackPower;
        this.Radius = getRadius();
        this.towerLevel = newTowerLevel;
        Image bomberPlaced = this.bomberPlaced;

    }


    /**
     *
     * @return
     */
    public static int getBomberTowerX() {
        return bomberTowerX;
    }

    /**
     *
     * @return
     */
    public static void setBomberTowerX(int bomberTowerX) {
        BomberTower.bomberTowerX = bomberTowerX;
    }

    /**
     *
     * @return
     */
    public static int getBomberTowerY() {
        return bomberTowerY;
    }

    /**
     *
     * @return
     */
    public static void setBomberTowerY(int bomberTowerY) {
        BomberTower.bomberTowerY = bomberTowerY;
    }

    /**
     *
     * @return
     */
    public static Shape getRadius() {
        return radius;
    }

    /**
     *
     * @return
     */
    public static void setRadius(Shape radius) {
        BomberTower.radius = radius;
    }

}