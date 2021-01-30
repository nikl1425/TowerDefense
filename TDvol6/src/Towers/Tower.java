package Towers;

import Grid.LoadMap;
import Player.Player;
import Enemies.Enemy;
import Grid.GameObject;

import com.sun.xml.internal.bind.v2.model.annotation.Quick;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tests.InputTest;

import java.util.ArrayList;
import java.util.List;

import static org.newdawn.slick.Color.red;
import static org.newdawn.slick.Color.transparent;

public class Tower implements GameObject {
    LoadMap loadmap = new LoadMap();
    Player player = new Player();
    BasicTower basicTower = new BasicTower();
    BomberTower bomberTower = new BomberTower();
    SniperTower sniperTower = new SniperTower();
    QuickTower quickTower = new QuickTower();


    public static void setBasicPlaced(boolean basicPlaced) {
        Tower.basicPlaced = basicPlaced;
    }

    public static boolean isBasicPlaced() {
        return basicPlaced;
    }

    public static boolean isBomberPlaced() {
        return bomberPlaced;
    }

    public static void setBomberPlaced(boolean bomberPlaced) {
        Tower.bomberPlaced = bomberPlaced;
    }

    public static boolean isSniperPlaced() {
        return sniperPlaced;
    }

    public static void setSniperPlaced(boolean sniperPlaced) {
        Tower.sniperPlaced = sniperPlaced;
    }

    public static boolean isQuickPlaced() {
        return quickPlaced;
    }

    public static void setQuickPlaced(boolean quickPlaced) {
        Tower.quickPlaced = quickPlaced;
    }

    public boolean isBomberPressed() {
        return bomberPressed;
    }

    public void setBomberPressed(boolean bomberPressed) {
        this.bomberPressed = bomberPressed;
    }

    public boolean isQuickPressed() {
        return quickPressed;
    }

    public void setQuickPressed(boolean quickPressed) {
        this.quickPressed = quickPressed;
    }

    public boolean isSniperPressed() {
        return sniperPressed;
    }

    public void setSniperPressed(boolean sniperPressed) {
        this.sniperPressed = sniperPressed;
    }

    public boolean isBasicPressed() {
        return basicPressed;
    }

    public void setBasicPressed(boolean basicPressed) {
        this.basicPressed = basicPressed;
    }

    public enum type {BASIC, QUICK, SNIPER, BOMBER}
    //Basic: low attack speed, normal dmg, normal range, low cost
    //Quick: fast attack speed, low dmg, normal range, medium cost
    //Sniper: slow attack speed, high dmg, high range, high cost
    //Bomber: slow attack, medium dmg, low range, medium cost


    Image basicClicked = new Image("data/Towers/Basic/Basic_Click.png");
    Image basicMarked = new Image("data/Towers/Basic/Basic_Menu_Marked.png");
    Image quickClicked = new Image("data/Towers/Quick/Quick_Click.png");
    Image quickMarked = new Image("data/Towers/Quick/Quick_Menu_Marked.png");
    Image bomberClicked = new Image("data/Towers/Bomber/Bomber_Click.png");
    Image bomberMarked = new Image("data/Towers/Bomber/Bomber_Menu_Marked.png");
    Image sniperClicked = new Image("data/Towers/Sniper/Sniper_Click.png");
    Image sniperMarked = new Image("data/Towers/Sniper/Sniper_Menu_Marked.png");

    public static int w = 64;

    public boolean basicPressed = false;
    public boolean bomberPressed = false;
    public boolean quickPressed = false;
    public boolean sniperPressed = false;



    public static boolean basicPlaced = false;
    public static boolean bomberPlaced = false;
    public static boolean sniperPlaced = false;
    public static boolean quickPlaced = false;





    public double range;
    public double attackPower;
    public int level = 1;
    public static Shape radius;
    Enemy enemy = new Enemy();
    public Color myAlphaColor = new Color(1f, 1f, 1f, 0.5f);
    List<BasicTower> basicTowers = new ArrayList<>();
    List<BomberTower> bomberTowers = new ArrayList<>();
    List<SniperTower> sniperTowers = new ArrayList<>();
    List<QuickTower> quickTowers = new ArrayList<>();

    public Tower() throws SlickException {
    }

    @Override
    public Vector2f getPosition() {
        return null;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        //BASIC TOWER
        if (isBasicPressed()) {
            if (player.getCredits() >= 100) {
                addBasicTower(mouseX, mouseY, g);
                if (input.isMousePressed(0)) {
                    for (int j = 0; j < LoadMap.MAP.length; j++) {
                        for (int i = 0; i < LoadMap.MAP[0].length; i++) {
                            //Sørger for at museklikket er inden for mappet
                            if (mouseX / 64 <= 14 && mouseY / 64 <= 11 && LoadMap.MAP[mouseY / 64][mouseX / 64] == 1) {
                                setBasicPlaced(true);
                                basicTowers.add(new BasicTower());
                                BasicTower.setBasictowerX(mouseX / 64);
                                BasicTower.setBasictowerY(mouseY / 64);
                                System.out.println(basicTower.towerX + "," + basicTower.towerY);


                                LoadMap.MAP[mouseY / 64][mouseX / 64] = 5;

                                player.addCredits(-100);


                                if (input.isKeyDown(42) || input.isKeyDown(54)) {
                                    setBasicPressed(true);
                                } else {
                                    basicPressed = false;
                                }

                                if (player.getCredits() < 100) {
                                    basicPressed = false;
                                }
                                System.out.println("Tower Coordinates = " + BasicTower.getBasictowerX() + "," + BasicTower.getBasictowerY());
                            }
                        }
                    }
                }
            }
            basicTower.towerX = BasicTower.getBasictowerX();
            basicTower.towerY = BasicTower.getBasictowerY();
        }



        //1st Row: Basic Tower
        if (mouseX < 12 * w && mouseX > 12 * w - w && mouseY < 2 * w && mouseY > 2 * w - w) {
            if (input.isMousePressed(0)) {
                setBasicPressed(true);
                setBasicPlaced(false);
            }
            g.drawString("Tower:   Basic", 715, 415);
            g.drawString("Costs:   100$", 715, 445);
            g.drawString("Upgrade:   150$", 715, 475);
            g.drawString("Level:   " + level, 715, 505);
            g.drawString("AttackPower:   " + attackPower, 715, 535);
            g.drawString("Range:   " + range, 715, 565);
            basicMarked.draw(11 * w, 1 * w);
            if (player.getCredits() < 100) {
                g.drawString("Not enough $$$", 715, 595);
            }
        }
        //BOMBER TOWER
        if (isBomberPressed()) {
            addBomberTower(mouseX,mouseY,g);
            if (player.getCredits() >= 400) {
                addBomberTower(mouseX, mouseY);
                if (input.isMousePressed(0)) {
                    for (int j = 0; j < loadmap.MAP.length; j++) {
                        for (int i = 0; i < loadmap.MAP[0].length; i++) {
                            //Sørger for at museklikket er inden for mappet
                            if (mouseX / 64 < 14 && mouseY / 64 < 11 && loadmap.MAP[(int) mouseY / 64][(int) mouseX / 64] == 1) {
                                setBomberPlaced(true);

                                bomberTowers.add(new BomberTower());
                                System.out.println("hej");

                                BomberTower.setBomberTowerX(mouseX / 64);
                                BomberTower.setBomberTowerY(mouseY / 64);
                                //Erstatter et punkt på 2D-array-mappet

                                loadmap.MAP [mouseY / 64][ mouseX / 64] = 7;

                                player.addCredits(-400);

                                System.out.println("Tower Coordinates = " + BomberTower.getBomberTowerX() + "," + BomberTower.getBomberTowerY());


                                if (input.isKeyDown(42) || input.isKeyDown(54)) {
                                    setBomberPressed(true);
                                } else {
                                    bomberPressed = false;
                                }

                                if (player.getCredits() < 100) {
                                    bomberPressed = false;
                                }
                            }
                        }
                    }
                }
            }
            bomberTower.bombertowerX = BomberTower.getBomberTowerX();
            bomberTower.bombertowerY = BomberTower.getBomberTowerY();
        }
        //1st Row: Bomber Tower
        if (mouseX < 14 * w && mouseX > 14 * w - w && mouseY < 2 * w && mouseY > 2 * w - w) {
            if (input.isMousePressed(0)) {
                setBomberPressed(true);
                setBomberPlaced(false);
            }
            g.drawString("Tower:   Bomber", 715, 415);
            g.drawString("Costs:   400$", 715, 445);
            g.drawString("Upgrade:   450$", 715, 475);
            g.drawString("Level:   " + level, 715, 505);
            g.drawString("AttackPower:   " + attackPower, 715, 535);
            g.drawString("Range:   " + range, 715, 565);
            bomberMarked.draw(13 * w, 1 * w, w, w);
            if (player.getCredits() < 400) {
                g.drawString("Not enough $$$", 715, 595);
            }
        }

        //QUICK TOWER
        if (isQuickPressed()) {
            addQuickTower(mouseX,mouseY,g);
            if (player.getCredits() >= 200) {
                addQuickTower(mouseX, mouseY);
                if (input.isMousePressed(0)) {
                    for (int j = 0; j < loadmap.MAP.length; j++) {
                        for (int i = 0; i < loadmap.MAP[0].length; i++) {
                            //Sørger for at museklikket er inden for mappet
                            if (mouseX / 64 < 14 && mouseY / 64 < 11 && loadmap.MAP[(int) mouseY / 64][(int) mouseX / 64] == 1) {
                                setQuickPlaced(true);

                                quickTowers.add(new QuickTower());
                                System.out.println("hej");

                                QuickTower.setQuickTowerX(mouseX / 64);
                                QuickTower.setQuickTowerY(mouseY / 64);
                                //Erstatter et punkt på 2D-array-mappet

                                loadmap.MAP [mouseY / 64][ mouseX / 64] = 9;

                                player.addCredits(-200);



                                if (input.isKeyDown(42) || input.isKeyDown(54)) {
                                    setQuickPressed(true);
                                } else {
                                    quickPressed = false;
                                }

                                if (player.getCredits() < 100) {
                                    quickPressed = false;
                                }
                            }
                        }
                    }
                }
            }
            quickTower.towerX = QuickTower.getQuickTowerX();
            quickTower.towerY = QuickTower.getQuickTowerY();
        }
        //2nd Row: Quick Tower
        if (mouseX < 12 * w && mouseX > 12 * w - w && mouseY < 4 * w && mouseY > 4 * w - w) {
            if (input.isMousePressed(0)) {
                setQuickPressed(true);
                setQuickPlaced(false);
            }
            g.drawString("Tower:   Quick", 715, 415);
            g.drawString("Costs:   200$", 715, 445);
            g.drawString("Upgrade:   250$", 715, 475);
            g.drawString("Level:   " + level, 715, 505);
            g.drawString("AttackPower:   " + attackPower, 715, 535);
            g.drawString("Range:   " + range, 715, 565);
            quickMarked.draw(11 * w, 3 * w, w, w);
            if (player.getCredits() < 200) {
                g.drawString("Not enough $$$", 715, 595);
            }
        }
        //SNIPER TOWER
        if (isSniperPressed()) {
            addSniperTower(mouseX,mouseY,g);
            if (player.getCredits() >= 500) {
                addSniperTower(mouseX, mouseY);
                if (input.isMousePressed(0)) {
                    for (int j = 0; j < loadmap.MAP.length; j++) {
                        for (int i = 0; i < loadmap.MAP[0].length; i++) {
                            //Sørger for at museklikket er inden for mappet
                            if (mouseX / 64 < 14 && mouseY / 64 < 11 && loadmap.MAP[(int) mouseY / 64][(int) mouseX / 64] == 1) {
                                setSniperPlaced(true);

                                sniperTowers.add(new SniperTower());
                                System.out.println("hej");

                                SniperTower.setSniperTowerX(mouseX / 64);
                                SniperTower.setSniperTowerY(mouseY / 64);
                                //Erstatter et punkt på 2D-array-mappet

                                loadmap.MAP [mouseY / 64][ mouseX / 64] = 11;

                                player.addCredits(-400);

                                System.out.println("Tower Coordinates = " + SniperTower.getSniperTowerX() + "," + SniperTower.getSniperTowerY());


                                if (input.isKeyDown(42) || input.isKeyDown(54)) {
                                    setSniperPressed(true);
                                } else {
                                    sniperPressed = false;
                                }

                                if (player.getCredits() < 100) {
                                    sniperPressed = false;
                                }
                            }
                        }
                    }
                }
            }
            sniperTower.towerX = SniperTower.getSniperTowerX();
            sniperTower.towerY = SniperTower.getSniperTowerY();
        }
        //2nd Row: Sniper Tower
        if (mouseX < 14 * w && mouseX > 14 * w - w && mouseY < 4 * w && mouseY > 4 * w - w) {
            if (input.isMousePressed(0)) {
                setSniperPressed(true);
                setSniperPlaced(false);
            }
            g.drawString("Tower:   Sniper", 715, 415);
            g.drawString("Costs:   500$", 715, 445);
            g.drawString("Upgrade:   600$", 715, 475);
            g.drawString("Level:   " + level, 715, 505);
            g.drawString("AttackPower:   " + attackPower, 715, 535);
            g.drawString("Range:   " + range, 715, 565);
            sniperMarked.draw(13 * w, 3 * w, w, w);
            if (player.getCredits() < 500) {
                g.drawString("Not enough $$$", 715, 595);
            }
        }

        //Removes any tower that has been lifted from the menu
        if (input.isMousePressed(1)) {
            setBasicPressed(false);
            setQuickPressed(false);
            setBomberPressed(false);
            setSniperPressed(false);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta) throws SlickException, InterruptedException {

    }


    public void addBasicTower(int mouseX, int mouseY, Graphics g) {
        basicTower.basicClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
        basicTower.radius = new Circle(mouseX - w / 2 + 32, mouseY - w / 2 + 32, 128);
        g.setColor(myAlphaColor);
        g.fill(basicTower.radius);
    }

    public void addBomberTower(int mouseX, int mouseY, Graphics g){
        bomberTower.bomberClicked.draw(mouseX-w/2,mouseY-w/2,w,w);
        bomberTower.radius = new Circle(mouseX-w/2+32,mouseY-w/2+32,200);
        g.setColor(myAlphaColor);
        g.fill(bomberTower.radius);
    }

    public void addSniperTower(int mouseX, int mouseY, Graphics g){
        sniperTower.sniperClicked.draw(mouseX-w/2,mouseY-w/2,w,w);
        sniperTower.radius = new Circle(mouseX-w/2+32, mouseY-w/2+32, 350);
        g.setColor(myAlphaColor);
        g.fill(sniperTower.radius);
    }

    public void addQuickTower(int mouseX, int mouseY, Graphics g){
        quickTower.quickClicked.draw(mouseX-w/2,mouseY-w/2,w,w);
        quickTower.radius = new Circle(mouseX-w/2+32, mouseY-w/2+32, 100);
        g.setColor(myAlphaColor);
        g.fill(quickTower.radius);

    }

    public void addBomberTower(int mouseX, int mouseY) {
        bomberClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
    }

    public void addQuickTower(int mouseX, int mouseY) {
        quickClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
    }

    public void addSniperTower(int mouseX, int mouseY) {
        sniperClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
    }


}