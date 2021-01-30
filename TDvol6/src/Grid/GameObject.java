package Grid;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public interface GameObject {

    Vector2f getPosition();

    void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException;

    void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException;

    void update(GameContainer gc, StateBasedGame stateBasedGame, int i) throws SlickException, InterruptedException;
}
