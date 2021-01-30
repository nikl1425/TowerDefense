package Towers;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public class BasicTower  {

    public Image basicClicked = new Image("data/Towers/Basic/Basic_Click.png");
    public static int newBuyingCost = 300;
    public static int newRefundValue = (newBuyingCost / 3) * 2;
    public static int newUpgradeCost = newBuyingCost / 2;
    public static double newRange = 100;
    public static int newAttackPower = 1;
    private static int basictowerX;
    private static int basictowerY;
    public static int newTowerLevel = 1;
    private static boolean basicTowerCollides = false;
    public int buyingCost;
    public int upgradeCost;
    public int refundValue;
    public double range;
    public int attackPower;
    public int towerX;
    public int towerY;
    public int coolDown = 800;
    public int ap = 1;
    public boolean isBasicTowerCollides;
    public long lastShot = 0;
    public int rcoolDown = 1;
    public long rlastShot = 0;

    public int towerLevel;
    public int maxTowerLevel = 3;

    public static Shape radius = null;
    public Shape Radius;

    public Image basicPlaced = new Image("data/Towers/Basic/Basic_Click.png");


    /**
     * Creates a new BasicTower with a x and y pos.
     *
     * @throws SlickException
     */
    public BasicTower() throws SlickException {
        this.towerX = getBasictowerX();
        this.towerY = getBasictowerY();
        this.buyingCost = newBuyingCost;
        this.refundValue = newRefundValue;
        this.upgradeCost = newUpgradeCost;
        this.range = newRange;
        this.attackPower = newAttackPower;
        this.Radius = getRadius();
        this.towerLevel = newTowerLevel;
        Image basicPlaced = this.basicPlaced;
        this.isBasicTowerCollides = basicTowerCollides;

    }

    public static int getBasictowerX() {
        return basictowerX;
    }

    public static void setBasictowerX(int basictowerX) {
        BasicTower.basictowerX = basictowerX;
    }

    public static int getBasictowerY() {
        return basictowerY;
    }

    public static void setBasictowerY(int basictowerY) {
        BasicTower.basictowerY = basictowerY;
    }

    public static Shape getRadius() {
        return radius;
    }

    public static void setRadius(Shape radius) {
        BasicTower.radius = radius;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }


}