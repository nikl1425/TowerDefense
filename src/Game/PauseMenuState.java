package Game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PauseMenuState extends BasicGameState {
    public PauseMenuState() throws SlickException {
    }

    @Override
    public int getID() {
        return 2;
    }

    Image pauseMenu = new Image("data/Tiles/pauseMenu.png");


    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        pauseMenu.draw(0, 0);

        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        //g.drawString(mouseX+","+mouseY,100,100);

        //RESUME GAME
        if (mouseX < 565 && mouseX > 400 && mouseY < 345 && mouseY > 285) {
            g.setColor(Color.black);
            g.drawRoundRect(400, 285, 565-400, 345-285, 10);
            if (input.isMousePressed(0)) {
                sbg.enterState(1);
            }
        }
        //Resume press esc
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            sbg.enterState(1);
        }

        //NEW GAME
        if (mouseX < 600 && mouseX > 375 && mouseY < 480 && mouseY > 415) {
            g.setColor(Color.black);
            g.drawRoundRect(375, 415, 600-375, 480-415, 10);
            if (input.isMousePressed(0)) {
                gc.reinit();
                sbg.enterState(1);
            }
        }

        //END GAME
        if (mouseX < 600 && mouseX > 375 && mouseY < 605 && mouseY > 540) {
            g.setColor(Color.black);
            g.drawRoundRect(375, 540, 600-375, 605-540, 10);
            if (input.isMousePressed(0)) {
                System.exit(0);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
    }

}
