package Bullets;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;


public class Bullets {

    float startX = 0;
    float startY = 0;
    float destX = 0;
    float destY = 0;
    private float speed; //how fast this moves.
    private float dx; //destinationX in vector
    private float dy; //destinationY in vector
    public Point location = new Point(0, 0); //Vector


    /**
     * Constuctor for the bullet, used in GameState
     * Creates a bullet with the below variables
     *
     * @param startX The x coordinate of the starting position
     * @param startY The y coordinate of the starting position
     * @param destX The x coordinate of the destination position
     * @param destY The y coordinate of the destination position
     * @param speed How fast the dx and dy moves
     */
    public Bullets(float startX, float startY, float destX, float destY, float speed) throws SlickException {
        this.startX = startX;
        this.startY = startY;
        location.setLocation(startX, startY);
        this.destX = destX;
        this.destY = destY;
        this.speed = speed;
        recalculateVector(destX, destY);
    }

    public Bullets() {
    }

    /**
     * Calculates the new position of the bullet when it's being fired from a tower
     *
     * @param destX The x coordinate of the destination position
     * @param destY The y coordinate of the destination position
     */
    public void recalculateVector(float destX, float destY) {
        float rad = (float) (Math.atan2(destX - startX, startY - destY));
        this.dx = (float) Math.sin(rad) * speed;
        this.dy = -(float) Math.cos(rad) * speed;
    }

    /**
     * moves the bullet and sets the new position
     */
    public void move() {
        float x = location.getX();
        float y = location.getY();
        x += dx;
        y += dy;
        location.setLocation(x, y);
    }
}
