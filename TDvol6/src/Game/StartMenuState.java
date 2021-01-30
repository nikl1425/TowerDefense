package Game;

import Enemies.Enemy;
import Player.Player;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartMenuState extends BasicGameState{
    Player player = new Player();
    Enemy enemy = new Enemy();
    public StartMenuState() throws SlickException {
    }

    @Override
    public int getID() {
        return 0;
    }

    Image startMenu = new Image("data/StartMenu.png");

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        startMenu.draw(0,0);

        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        //START GAME
        if(mouseX < 550 && mouseX > 410 && mouseY < 350 && mouseY > 310){
            g.setColor(Color.black);
            g.drawRoundRect(400,300,160,60,10);
            if(input.isMousePressed(0)) {
                sbg.enterState(1);
                gc.pause();
                player.reset();
            }

        }
        //Choose Map
        if(mouseX < 550 && mouseX > 410 && mouseY < 470 && mouseY > 420){
            g.setColor(Color.black);
            g.drawRoundRect(400,410,160,60,10);
            if(input.isMousePressed(0)) {
                System.out.println("CHOOSE MAP WILL BE IN NEXT UPDATE");
            }
        }
        //START GAME
        if(mouseX < 520 && mouseX > 455 && mouseY < 570 && mouseY > 530){
            g.setColor(Color.black);
            g.drawRoundRect(445,520,75,60,10);
            if(input.isMousePressed(0)) {
                //Make a dialog box...
                System.exit(0);
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }


}
