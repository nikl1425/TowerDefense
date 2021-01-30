package Towers;


import Grid.GameObject;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class BasicTower implements GameObject {

    public Image basicClicked = new Image("data/Towers/Basic/Basic_Click.png");

    public static int newBuyingCost = 100;
    public static int newRefundValue = 80;
    public static int newUpgradeCost = 50;
    public static double newRange = 100;
    public static double newAttackPower = 0.6;
    private static int basictowerX;
    private static int basictowerY;
    public int buyingCost;
    public int upgradeCost;
    public int refundValue;
    public double range;
    public double attackPower;
    public int towerX;
    public int towerY;
    protected type towerType;
    float x;
    public int coolDown = 800;
    public long lastShot =0;


    float y;
    public static Shape radius = null;
    public Shape Radius;




    public enum type{BASIC, QUICK, SNIPER, BOMBER}

    public BasicTower() throws SlickException {
        this.towerX = getBasictowerX();
        this.towerY = getBasictowerY();
        this.buyingCost =newBuyingCost;
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
        basicClicked.draw(getBasictowerX(), getBasictowerY(), 64, 64);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException, InterruptedException {


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

}

