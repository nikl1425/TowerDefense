package Game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartMenuState extends BasicGameState {
    public StartMenuState() throws SlickException {
    }

    @Override
    public int getID() {
        return 0;
    }

    Image startMenu = new Image("data/Tiles/StartMenu.png");

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        startMenu.draw(0, 0);

        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        //g.drawString(mouseX+","+mouseY,100,100);

        //NEW GAME
        if (mouseX < 610 && mouseX > 385 && mouseY < 380 && mouseY > 305) {
            g.setColor(Color.black);
            g.drawRoundRect(385, 310, 605-385, 380-305, 10);
            if (input.isMousePressed(0)) {
                gc.reinit();
                sbg.enterState(1);
                gc.pause();
            }
        }
        //Start via press enter
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            gc.reinit();
            sbg.enterState(1);
            gc.pause();
        }

        //END GAME
        if (mouseX < 610 && mouseX > 385 && mouseY < 570 && mouseY > 500) {
            g.setColor(Color.black);
            g.drawRoundRect(385, 500, 610-385, 570-500, 10);
            if (input.isMousePressed(0)) {
                System.exit(0);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }


}
