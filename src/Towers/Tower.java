package Towers;

import Grid.LoadMap;
import Player.Player;
import Enemies.Enemy;
import Grid.GameObject;


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
    List<BasicTower> basicTowers = new ArrayList<>();
    List<BomberTower> bomberTowers = new ArrayList<>();
    List<SniperTower> sniperTowers = new ArrayList<>();
    List<QuickTower> quickTowers = new ArrayList<>();

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
    public double attackPower;
    public int level = 1;
    public static Shape radius;
    public Color myAlphaColor = new Color(1f, 1f, 1f, 0.5f);

    public Tower() throws SlickException {
    }

    /**
     * @param gc:             A generic game container that handles the game loop, fps recording and managing the input system
     * @param stateBasedGame: A state based game isolated different stages of the game
     *                        (menu, ingame, hiscores, etc) into different states
     *                        so they can be easily managed and maintained
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame stateBasedGame) throws SlickException {

    }

    /**
     *
     * @param gc
     * @param stateBasedGame
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();

        /**
         * this Method is used to Render Graphics for BasicTower
         * It takes Input from mouse && gets mouseX, mouseY
         * if basicSet == true, then place basicTower at mousecoordinates
         * If the player has enough credit the if-statement allows the player to buy tower
         * Bought towers can be placed
         * Set tiled ID == 5 if basicIsPlaced
         */
        //BASIC TOWER
        if(isBasicPressed()) {
            if(player.getCredits() >= basicTower.buyingCost) {
                addBasicTower(mouseX, mouseY, g);
                if(input.isMousePressed(0)) {
                    for (int j = 0; j < LoadMap.MAP.length; j++) {
                        for (int i = 0; i < LoadMap.MAP[0].length; i++) {
                            //Sørger for at museklikket er inden for mappet
                            if(LoadMap.MAP[mouseY / w][mouseX / w] == 1) {
                                setBasicPlaced(true);
                                basicTowers.add(new BasicTower());
                                BasicTower.setBasictowerX(mouseX / w);
                                BasicTower.setBasictowerY(mouseY / w);

                                LoadMap.MAP[mouseY / w][mouseX / w] = 5;

                                player.addCredits(-basicTower.buyingCost);

                                if(input.isKeyDown(42) || input.isKeyDown(54)) {
                                    setBasicPressed(true);
                                } else {
                                    basicPressed = false;
                                }

                                if(player.getCredits() < basicTower.buyingCost) {
                                    basicPressed = false;
                                }
                            }
                        }
                    }
                }
            }
            basicTower.towerX = BasicTower.getBasictowerX();
            basicTower.towerY = BasicTower.getBasictowerY();
        }
/**
 * If mouse is hovering over Basic
 * And plaers has enough credit
 * place basic at mouse
 * Display Strings (TYPE, COSTS, UPGRADE, LEVEL)
 */

        //1st Row: Basic Tower
        if(LoadMap.MAP[mouseY / w][mouseX / w] == 4) {
            if(input.isMousePressed(0) && player.getCredits() >= basicTower.buyingCost &&
                    !quickPressed && !bomberPressed && !sniperPressed) {
                setBasicPressed(true);
                //setBasicPlaced(false);
            }
            if(player.getCredits() < basicTower.buyingCost) {
                g.drawString("Not enough $$$", 715, 595);
            }
            g.drawString("Tower:   Basic", 715, 445);
            g.drawString("Costs:   " + basicTower.buyingCost, 715, 475);
            g.drawString("Upgrade:   " + basicTower.upgradeCost, 715, 505);
            g.drawString("Level:   " + level, 715, 535);
            g.drawString("Level:   " + attackPower, 715, 565);
            basicMarked.draw(11 * w, w);

        }

        /**
         * this Method is used to Render Graphics for BomberTower
         * It takes Input from mouse && gets mouseX, mouseY
         * if BomberSet == true, then place BomberTower at mousecoordinates
         * If the player has enough credit the if-statement allows the player to buy tower
         * Bought towers can be placed
         * Set tiled ID == 7 if bomberIsPlaced
         *
         */
        //BOMBER TOWER
        if(isBomberPressed()) {
            addBomberTower(mouseX, mouseY, g);
            if(player.getCredits() >= bomberTower.buyingCost) {
                addBomberTower(mouseX, mouseY);
                if(input.isMousePressed(0)) {
                    for (int j = 0; j < loadmap.MAP.length; j++) {
                        for (int i = 0; i < loadmap.MAP[0].length; i++) {
                            //Sørger for at museklikket er inden for mappet
                            if(loadmap.MAP[(int) mouseY / w][(int) mouseX / w] == 1) {
                                setBomberPlaced(true);

                                bomberTowers.add(new BomberTower());

                                BomberTower.setBomberTowerX(mouseX / w);
                                BomberTower.setBomberTowerY(mouseY / w);
                                //Erstatter et punkt på 2D-array-mappet

                                loadmap.MAP[mouseY / w][mouseX / w] = 7;

                                player.addCredits(-bomberTower.buyingCost);

                                if(input.isKeyDown(42) || input.isKeyDown(54)) {
                                    setBomberPressed(true);
                                } else {
                                    bomberPressed = false;
                                }

                                if(player.getCredits() < bomberTower.buyingCost) {
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

        /**
         * If mouse is hovering over Bomber
         * And plaers has enough credit
         * place bomber at mouse
         * Display Strings (TYPE, COSTS, UPGRADE, LEVEL)
         */

        //1st Row: Bomber Tower
        if(LoadMap.MAP[mouseY / w][mouseX / w] == 6) {
            if(input.isMousePressed(0) && player.getCredits() >= bomberTower.buyingCost &&
                    !quickPressed && !basicPressed && !sniperPressed) {
                setBomberPressed(true);
                //setBomberPlaced(false);
            }
            if(player.getCredits() < bomberTower.buyingCost) {
                g.drawString("Not enough $$$", 715, 595);
            }
            g.drawString("Tower:   Bomber", 715, 445);
            g.drawString("Costs:   " + bomberTower.buyingCost, 715, 475);
            g.drawString("Upgrade:   " + bomberTower.upgradeCost, 715, 505);
            g.drawString("Level:   " + level, 715, 535);
            g.drawString("AttackPower:   " + attackPower, 715, 565);
            bomberMarked.draw(13 * w, w, w, w);

        }

        /**
         * this Method is used to Render Graphics for QuickTower
         * It takes Input from mouse && gets mouseX, mouseY
         * if QuickSet == true, then place quickTower at mousecoordinates
         * If the player has enough credit the if-statement allows the player to buy tower
         * Bought towers can be placed
         * Set tiled ID == 9 if QuickIsPlaced
         *
         */

        //QUICK TOWER
        if(isQuickPressed()) {
            addQuickTower(mouseX, mouseY, g);
            if(player.getCredits() >= quickTower.buyingCost) {
                addQuickTower(mouseX, mouseY);
                if(input.isMousePressed(0)) {
                    for (int j = 0; j < loadmap.MAP.length; j++) {
                        for (int i = 0; i < loadmap.MAP[0].length; i++) {
                            //Sørger for at museklikket er inden for mappet
                            if(loadmap.MAP[mouseY / w][mouseX / w] == 1) {
                                setQuickPlaced(true);
                                quickTowers.add(new QuickTower());
                                QuickTower.setQuickTowerX(mouseX / w);
                                QuickTower.setQuickTowerY(mouseY / w);
                                //Erstatter et punkt på 2D-array-mappet

                                loadmap.MAP[mouseY / w][mouseX / w] = 9;

                                player.addCredits(-quickTower.buyingCost);

                                if(input.isKeyDown(42) || input.isKeyDown(54)) {
                                    setQuickPressed(true);
                                } else {
                                    quickPressed = false;
                                }

                                if(player.getCredits() < quickTower.buyingCost) {
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

        /**
         * If mouse is hovering over Quick
         * And players has enough credit
         * place quick at mouse
         * Display Strings (TYPE, COSTS, UPGRADE, LEVEL)
         */

        //2nd Row: Quick Tower
        if(LoadMap.MAP[mouseY / w][mouseX / w] == 8) {
            if(input.isMousePressed(0) && player.getCredits() >= quickTower.buyingCost &&
                    !sniperPressed && !basicPressed && !bomberPressed) {
                setQuickPressed(true);
                //setQuickPlaced(false);
            }
            if(player.getCredits() < quickTower.buyingCost) {
                g.drawString("Not enough $$$", 715, 595);
            }
            g.drawString("Tower:   Quick", 715, 445);
            g.drawString("Costs:   " + quickTower.buyingCost, 715, 475);
            g.drawString("Upgrade:   " + quickTower.upgradeCost, 715, 505);
            g.drawString("Level:   " + level, 715, 535);
            g.drawString("AttackPower:   " + attackPower, 715, 565);
            quickMarked.draw(11 * w, 3 * w, w, w);

        }
        /**
         * this Method is used to Render Graphics for SiperTower
         * It takes Input from mouse && gets mouseX, mouseY
         * if basicSet == true, then place SniperTower at mousecoordinates
         * If the player has enough credit the if-statement allows the player to buy tower
         * Bought towers can be placed
         * Set tiled ID == 11if sniperIsPlaced
         */
        //SNIPER TOWER
        if(isSniperPressed()) {
            addSniperTower(mouseX, mouseY, g);
            if(player.getCredits() >= sniperTower.buyingCost) {
                addSniperTower(mouseX, mouseY);
                if(input.isMousePressed(0)) {
                    for (int j = 0; j < loadmap.MAP.length; j++) {
                        for (int i = 0; i < loadmap.MAP[0].length; i++) {
                            //Sørger for at museklikket er inden for mappet
                            if(loadmap.MAP[(int) mouseY / w][(int) mouseX / w] == 1) {
                                setSniperPlaced(true);

                                sniperTowers.add(new SniperTower());

                                SniperTower.setSniperTowerX(mouseX / w);
                                SniperTower.setSniperTowerY(mouseY / w);
                                //Erstatter et punkt på 2D-array-mappet

                                loadmap.MAP[mouseY / w][mouseX / w] = 11;

                                player.addCredits(-sniperTower.buyingCost);

                                if(input.isKeyDown(42) || input.isKeyDown(54)) {
                                    setSniperPressed(true);
                                } else {
                                    sniperPressed = false;
                                }

                                if(player.getCredits() < sniperTower.buyingCost) {
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
        /**
         * If mouse is hovering over Sniper
         * And plaers has enough credit
         * place Snipper at mouse
         * Display Strings (TYPE, COSTS, UPGRADE, LEVEL)
         */

        //2nd Row: Sniper Tower
        if(LoadMap.MAP[mouseY / w][mouseX / w] == 10) {
            if(input.isMousePressed(0) && player.getCredits() >= sniperTower.buyingCost &&
                    !quickPressed && !basicPressed && !bomberPressed) {
                setSniperPressed(true);
                //setSniperPlaced(false);
            }
            if(player.getCredits() < sniperTower.buyingCost) {
                g.drawString("Not enough $$$", 715, 595);
            }
            g.drawString("Tower:   Sniper", 715, 445);
            g.drawString("Costs:   " + sniperTower.buyingCost, 715, 475);
            g.drawString("Upgrade:   " + sniperTower.upgradeCost, 715, 505);
            g.drawString("Level:   " + level, 715, 535);
            g.drawString("AttackPower:   " + attackPower, 715, 565);
            sniperMarked.draw(13 * w, 3 * w, w, w);

        }

        //Removes any tower that has been lifted from the menu
        if(input.isMousePressed(1)) {
            setBasicPressed(false);
            setQuickPressed(false);
            setBomberPressed(false);
            setSniperPressed(false);

        }
    }

    /**
     * @param gc
     * @param stateBasedGame
     * @param delta
     * @throws SlickException
     * @throws InterruptedException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int delta)
            throws SlickException, InterruptedException {

    }

    /**
     * @param mouseX
     * @param mouseY
     * @param g
     */
    public void addBasicTower(int mouseX, int mouseY, Graphics g) {
        basicTower.basicClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
        basicTower.radius = new Circle(mouseX - w / 2 + 32, mouseY - w / 2 + 32, 128);
        g.setColor(myAlphaColor);
        g.fill(basicTower.radius);
    }

    /**
     * @param mouseX
     * @param mouseY
     * @param g
     */
    public void addBomberTower(int mouseX, int mouseY, Graphics g) {
        bomberTower.bomberClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
        bomberTower.radius = new Circle(mouseX - w / 2 + 32, mouseY - w / 2 + 32, 200);
        g.setColor(myAlphaColor);
        g.fill(bomberTower.radius);
    }

    /**
     * @param mouseX
     * @param mouseY
     * @param g
     */
    public void addSniperTower(int mouseX, int mouseY, Graphics g) {
        sniperTower.sniperClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
        sniperTower.radius = new Circle(mouseX - w / 2 + 32, mouseY - w / 2 + 32, 250);
        g.setColor(myAlphaColor);
        g.fill(sniperTower.radius);
    }

    /**
     * @param mouseX
     * @param mouseY
     * @param g
     */
    public void addQuickTower(int mouseX, int mouseY, Graphics g) {
        quickTower.quickClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
        quickTower.radius = new Circle(mouseX - w / 2 + 32, mouseY - w / 2 + 32, 100);
        g.setColor(myAlphaColor);
        g.fill(quickTower.radius);

    }

    /**
     * @param mouseX
     * @param mouseY
     */
    public void addBomberTower(int mouseX, int mouseY) {
        bomberClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
    }

    /**
     * @param mouseX
     * @param mouseY
     */
    public void addQuickTower(int mouseX, int mouseY) {
        quickClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
    }

    /**
     * @param mouseX
     * @param mouseY
     */
    public void addSniperTower(int mouseX, int mouseY) {
        sniperClicked.draw(mouseX - w / 2, mouseY - w / 2, w, w);
    }


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

}