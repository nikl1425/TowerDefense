package Bullets;

import Enemies.Enemy;
import Game.GameState;
import Grid.GameObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class ImprovedBullet implements GameObject {
    Vector2f position;
    Vector2f direction;
    Enemy enemy = new Enemy();

    // speed <1 betyder den bevæger bagud!!!

    ArrayList<ImprovedBullet> bulletList = new ArrayList<ImprovedBullet>();
    float speed;

    public ImprovedBullet(GameObject owner, GameObject target, float speed) throws SlickException {
        //starter ved den der skyder;
        position = owner.getPosition();
        //offset x+noget;
        //position.x+=1;
        direction = target.getPosition().sub(owner.getPosition()).normalise();
        this.speed = speed;
        //tilføjer bullet til objects
        GameState.objects.add(this);
    }

    @Override
    public Vector2f getPosition() {
        return position;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
     position.set(50,50);

    }

    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        // tegn noget på position
        for (int i = 0; i < bulletList.size(); i++) {
            ImprovedBullet bullet = bulletList.get(i);
            g.fillRect(bullet.position.x,bullet.position.y,5,5);
        }


    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException, InterruptedException {
        position.add(direction).scale(speed);
        //Update the bullet's position.
        for(int i = 0;i<bulletList.size();i++)
        {
            ImprovedBullet bullet = bulletList.get(i);

            bullet.update(gc, stateBasedGame, delta);


            //NOTE: Will need to determine if this hit something or went off the screen. Or otherwise, the list will get filled with invalid bullets.
            //You'll have to add that yourself.
        }
        // if collision
    }

    public void drawRect(Graphics g){
        g.drawRect(500,500,10,10);
    }

    private void addNewBullet() throws SlickException {

    }
}
