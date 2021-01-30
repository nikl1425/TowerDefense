package Player;

import Grid.GameObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Player implements GameObject {

    public static int currency = 0;
    public static int lives;
    public static final int STARTINGCURRENCY = 1500;
    private static final int STARTINGLIVES = 25;

    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {
        reset();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        g.setColor(Color.green);
        g.drawString("$$$:   " + String.valueOf(currency), 715, 325);
        g.drawString("Lives:   " + String.valueOf(lives), 715, 355);
        g.drawString("------------------", 715, 415);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int i)
            throws SlickException, InterruptedException {
        //GAME OVER
        if (lives <= 0) {
            reset();
            stateBasedGame.enterState(3);
        }
    }

    /**
     * Used in init as a reset lives and currency
     */
    public void reset() {
        lives = STARTINGLIVES;
        currency = getSTARTINGCURRENCY();
    }

    public void addCredits(int amount) {
        currency += amount;
    }

    public void decreaseLife() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public int getCredits() {
        return currency;
    }

    public static int getSTARTINGCURRENCY() {
        return STARTINGCURRENCY;
    }

}
