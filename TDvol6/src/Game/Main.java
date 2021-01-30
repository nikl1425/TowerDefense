package Game;

import Player.Player;
import Bullets.Bullets;
import Enemies.Enemy;
import Towers.Tower;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {



    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main("Tower-Defense"));
        app.setAlwaysRender(true);
        app.setDisplayMode(960,700,false);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {

        //Nedenstående skal laves til en menu.
        //this.addState(new MenuState());
        GameState gs = new GameState();

        //gs.objects.add(new LoadMap());
        gs.objects.add(new Tower());
        //gs.objects.add(new Enemy());
        //gs.objects.add(new Bullets());
        gs.objects.add(new Player());
        this.addState(gs);
        StartMenuState ms = new StartMenuState();
        this.addState(ms);


        //Starter spillet op i gamemenuen
        enterState(0);

    }

    public Main(String name) {
        super(name);
    }

}


/*



 */