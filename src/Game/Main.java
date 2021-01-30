package Game;

import Player.Player;
import Enemies.Enemy;
import Towers.Tower;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main("The Tower Defense Game"));
        app.setAlwaysRender(true);
        app.setDisplayMode(960,700,false);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        GameState gs = new GameState();
        gs.objects.add(new Tower());
        gs.objects.add(new Player());
        gs.objects.add(new Enemy());
        this.addState(gs); //State 1

        StartMenuState ms = new StartMenuState();
        PauseMenuState ps = new PauseMenuState();
        GameOverState gos = new GameOverState();

        this.addState(ms); //State 0
        this.addState(ps); //State 2
        this.addState(gos); //State 3

        enterState(0);
    }

    public Main(String name) {
        super(name);
    }
}
