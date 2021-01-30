package Game;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.TrueTypeFont;
import java.awt.Font;

public class GameOverState extends BasicGameState{
    public GameOverState() throws SlickException {
    }

    @Override
    public int getID() {
        return 3;
    }

    Image gameOver = new Image("data/Tiles/gameoverMenu.png");
    TrueTypeFont gameOverFont;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        Font font = new Font("Agency FB", Font.PLAIN, 32);
        gameOverFont = new TrueTypeFont(font,false);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        gameOver.draw(0, 0);

        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        //g.drawString(mouseX+","+mouseY,100,100);
        g.setFont(gameOverFont);
        g.setColor(Color.black);
        g.drawString(String.valueOf(GameState.currentLevel),520,323);

        //NEW GAME
        if (mouseX < 605 && mouseX > 380 && mouseY < 515 && mouseY > 435) {
            g.setColor(org.newdawn.slick.Color.black);
            g.drawRoundRect(385, 435, 600-380, 515-435, 10);
            if (input.isMousePressed(0)) {
                gc.reinit();
                sbg.enterState(1);
                gc.pause();
            }
        }

        //END GAME
        if (mouseX < 610 && mouseX > 385 && mouseY < 630 && mouseY > 560) {
            g.setColor(org.newdawn.slick.Color.black);
            g.drawRoundRect(385, 560, 610-385, 630-560, 10);
            if (input.isMousePressed(0)) {
                System.exit(0);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }
}
