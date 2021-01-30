package Bullets;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;


public class Bullets {

    float startX = 0;
    float startY = 0;
    float destX = 0;
    float destY = 0;
    public Point location = new Point(0, 0);
    private float speed; //how fast this moves.
    private float dx;
    private float dy;



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


    public void recalculateVector(float destX, float destY) {
        float rad = (float) (Math.atan2(destX - startX, startY - destY));
        //Can set different speeds here, if you wanted.
        this.dx = (float) Math.sin(rad) * speed;
        this.dy = -(float) Math.cos(rad) * speed;
    }

    public void move() {
        float x = location.getX();
        float y = location.getY();
        x += dx;
        y += dy;
        location.setLocation(x, y);
    }

}
