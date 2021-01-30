package Player;

import Grid.GameObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Player implements GameObject {
    public static int currency = 0;
    public static int lives;
    public static final int STARTINGCURRENCY = 2000;
    private static final int STARTINGLIVES = 25;

    public static int getSTARTINGCURRENCY() {
        return STARTINGCURRENCY;
    }


    @Override
    public Vector2f getPosition() {
        return null;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
        reset();

        if (lives <= 0){
            reset();
            stateBasedGame.enterState(0);
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        g.setColor(Color.green);
        g.drawString("$$$:   "+String.valueOf(currency),715,325);
        g.drawString("Lives:   "+String.valueOf(lives),715,355);
        g.drawString("------------------",715,385);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int i) throws SlickException, InterruptedException {
    }



    public  void reset(){
        lives = STARTINGLIVES;
        currency = getSTARTINGCURRENCY();
    }

    public  void addCredits(int amount){
        currency += amount;
    }

    public void addLife(){
        lives++;
    }
    public void decreaseLife(){
        lives--;
    }
    public int getLives(){
        return lives;
    }

    public int getCredits(){
        return currency;
    }
}
