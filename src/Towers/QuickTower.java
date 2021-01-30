package Towers;


import Grid.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class QuickTower {

    public Image quickClicked = new Image("data/Towers/Quick/Quick_Click.png");

    public static int newBuyingCost = 800;
    public static int newRefundValue = (newBuyingCost / 3) * 2;
    public static int newUpgradeCost = newBuyingCost/2;
    public static double newRange = 100;
    public static double newAttackPower = 0.6;
    public static int newTowerLevel = 1;
    private static int quickTowerX;
    private static int quickTowerY;
    public int buyingCost;
    public int upgradeCost;
    public int refundValue;
    public double range;
    public double attackPower;
    public int towerX;
    public int towerY;
    public int coolDown = 300;
    public long lastShot =0;
    public int rcoolDown = 1;
    public long rlastShot = 0;
    public Image quickPlaced = new Image("data/Towers/Quick/Quick_Click.png");
    public int towerLevel;
    public int maxTowerLevel = 3;
    public static Shape radius = null;
    public Shape Radius;

    /**
     * Creates a new Quicktower with a x and y pos.
     *
     * @throws SlickException
     */
    public QuickTower() throws SlickException {
        this.towerX = getQuickTowerX();
        this.towerY = getQuickTowerY();
        this.buyingCost = newBuyingCost;
        this.refundValue = newRefundValue;
        this.upgradeCost = newUpgradeCost;
        this.range = newRange;
        this.attackPower = newAttackPower;
        this.Radius = getRadius();
        this.towerLevel = newTowerLevel;
        Image quickPlaced = this.quickPlaced;
    }


    public static int getQuickTowerX() {
        return quickTowerX;
    }

    public static void setQuickTowerX(int quickTowerX) {
        QuickTower.quickTowerX = quickTowerX;
    }

    public static int getQuickTowerY() {
        return quickTowerY;
    }

    public static void setQuickTowerY(int quickTowerY) {
        QuickTower.quickTowerY = quickTowerY;
    }

    public static Shape getRadius() {
        return radius;
    }

    public static void setRadius(Shape radius) {
        QuickTower.radius = radius;
    }
}

