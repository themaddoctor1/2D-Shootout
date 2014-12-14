/*
 * 2D Shootout
 * Copyright(c) 2013 Christopher Hittner
 * Written using Game Engine Alpha-G1-Java
 * 
 * All rights to this code and any other classes of the engine or game are the 
 * property of Christopher Hittner.
*/

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import javax.swing.JFrame;


public class Main extends Applet implements MouseListener, MouseMotionListener{
    static String gameStatus = "startup", pointVictor, gameVictor, setVictor;
    Image image;
    Graphics graphics;
    static Main game = new Main();
    //Creates players
    static Player One;
    static Player Two;
    //Game stuff
    static int gameWinner = 0;
    static Random generator = new Random();
    static int time = 0, timeMultiplier = 4;
    //Menu stuff
    //--------------------------------------------------------------------------
    //Button detectors
    //--------------------------------------------------------------------------
    //Main Menu
    //---------
    //Main menu start button
    static Button Main_Start = new Button(353,101,100,24,"preGame","running");
    //Main menu help button
    static Button Main_Help = new Button(353,101,140,24,"help","running");
    //Main menu quit button
    static Button Main_Quit = new Button(353,101,180,24,"exiting","running");
    //---------
    //Help Page
    //---------
    //Help screen return button
    static Button Help_Return = new Button(288,240,378,28,"running","help");
    //-----------
    //Paused Page
    //-----------
    //Game pause resume button
    static Button Pause_Resume = new Button(343,140,150,28,"playing","paused");
    //Game pause help button
    static Button Pause_Help = new Button(343,140,190,28,"help","paused");
    //Game pause quit button
    static Button Pause_Quit = new Button(343,140,230,28,"postGame","paused");
    //--------------
    //Post-game menu
    //--------------
    //Return to main  
    static Button Postgame_Return = new Button(293, 234, 290, 28,"running","postGame");
    //--------------
    //Pre-game menu
    //--------------
    static Button Pregame_Return = new Button(275, 117, 470, 28,"running","preGame");
    static Button Pregame_Start = new Button(455, 117, 470, 28,"playing","preGame");
    
    static Button lessTime = new Button(185, 10, 182, 10,"preGame","preGame");
    static Button moreTime = new Button(290, 10, 182, 10,"preGame","preGame");
    
    static Button lessKills = new Button(185, 10, 202, 10,"preGame","preGame");
    static Button moreKills = new Button(270, 10, 202, 10,"preGame","preGame");
    
    static Button lessBullets = new Button(185, 10, 222, 10,"preGame","preGame");
    static Button moreBullets = new Button(215, 10, 222, 10,"preGame","preGame");
    
    static Button lessShooting = new Button(185, 10, 242, 10,"preGame","preGame");
    static Button moreShooting = new Button(220, 10, 242, 10,"preGame","preGame");
    
    static Button fastReload = new Button(185, 10, 262, 10,"preGame","preGame");
    static Button slowReload = new Button(235, 10, 262, 10,"preGame","preGame");
    
    static Button fastBullet = new Button(185, 10, 282, 10,"preGame","preGame");
    static Button slowBullet = new Button(235, 10, 282, 10,"preGame","preGame");
    
    static Button fastWalk = new Button(185, 10, 302, 10,"preGame","preGame");
    static Button slowWalk = new Button(235, 10, 302, 10,"preGame","preGame");
    
    static Button fastTime = new Button(235, 10, 322, 10,"preGame","preGame");
    static Button slowTime = new Button(185, 10, 322, 10,"preGame","preGame");
    
    static Button chooseHuman = new Button(240, 160, 360, 80,"preGame","preGame");
    static Button chooseAI = new Button(450, 160, 360, 80,"preGame","preGame");
    
    static Button EasyAI = new Button(610, 80, 360, 20,"preGame","preGame");
    static Button NormalAI = new Button(610, 80, 380, 20,"preGame","preGame");
    static Button HardAI = new Button(610, 80, 400, 20,"preGame","preGame");
    static Button InsaneAI = new Button(610, 80, 420, 20,"preGame","preGame");
    
    static Button mapUp = new Button(694, 10, 260, 20,"preGame","preGame");
    static Button mapDown = new Button(524, 10, 260, 20,"preGame","preGame");
    
    static Button terrainUp = new Button(694, 10, 280, 20,"preGame","preGame");
    static Button terrainDown = new Button(524, 10, 280, 20,"preGame","preGame");
    
    //---------------
    //In-game buttons
    //---------------
    static Button toggleVolume = new Button(697, 20, 0, 20,"playing","playing");
    static Button pauseGame = new Button(697, 20, 20, 20,"playing","playing");
    
    
    static boolean paused = false;
    
    static boolean overPlayButton = false, overHelpButton = false, overHelpReturnButton = false;
    static boolean overQuitButton = false, overPauseResumeButton = false, overPauseHelpButton = false , overPauseQuitButton = false;
    static boolean overPostGameReturnButton = false;
    static boolean overPreGameReturnButton = false, overPreGameStartButton = false;
    static boolean VsAISelected = false, VsHumanSelected = false;
    static boolean easySelected = false, normalSelected = false, hardSelected = false, insaneSelected = false;
    //--------------------------------------------------------------------------
    //Game managers
    //--------------------------------------------------------------------------
    //Time limit in minutes and the score limit
    static int timeLimit = 10, scoreLimit = 20;
    //Sets whether playing against the computer
    static boolean againstComputer = false;
    static int AIDifficulty = 1, reactionTime = -1;
    //Volume controller
    static boolean volume = true;
    public static void main(String[] args) throws Exception{
	JFrame frame = new JFrame("2D Shooter");
	frame.add(game);
	frame.setSize(831, 658);
	frame.setVisible(true);
        frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creates and sets up the applet and makes it 825 pixels wide and 
        //630 pixels high. I added six pixels to the width and twenty eight to 
        //the height because the last four pixels of the game get cut off by the
        //rim of the game
        if(One == null)
            One = new Player(1);
        if(Two == null)
            Two = new Player(2);
        
        //Main menu music
        Audio theme = new Audio("Hitman");
        boolean themePlaying = false;
        
        Thread.sleep(6000);
        //This six second pause here (6000 milliseconds) is just so that the
        //splash screen can play.
        
        gameStatus = "running";
        while(!gameStatus.equals("exiting")){
            if(!themePlaying && volume){
                theme.playSound();
                themePlaying = true;
            }
            if(!volume){
                theme.stopSound();
            }
            resetBoard();
            //gameStatus = "playing";
            printScreen();
            time = 60000 * timeLimit;
            while(gameStatus.equals("playing") || gameStatus.equals("paused")){
                if(themePlaying){
                    theme.stopSound();
                    themePlaying = false;
                }
                //Movements
                One.move();
                Two.move();
                //Collision tests
                One.testHit();
                Two.testHit();
                //Spawning
                One.spawn();
                Two.spawn();
                //Shooting
                if(againstComputer){
                    AIController();
                }
                if(One.targetHit && One.gunTimer < 1 && One.shooting && One.health > 0){
                    One.targetHit = false;
                } if(Two.targetHit && Two.gunTimer < 1 && Two.shooting && Two.health > 0){
                    Two.targetHit = false;
                }
                if(((One.shooting == true && One.gunTimer == 0 && One.spawnTimer < 1)  || !One.targetHit)){
                    Player.shootOne();
                }
                if(((Two.shooting == true && Two.gunTimer == 0 && Two.spawnTimer < 1) || !Two.targetHit)){
                    Player.shootTwo();
                }
                printScreen();
                Thread.sleep((int)(16/timeMultiplier));
                time -= 16/timeMultiplier;
                gameStatus = checkScore();
                //This code does the following in this order:
                //Moves player, then tests for impacts, then prints the screen, and
                //then waits ten milliseconds.
                while(paused){
                    printScreen();
                }
                if(volume){
                    if(One.roundsInMagazine < 1 && One.health > 0){
                        if(One.gunTimer == Player.reloadTime + (90/Player.firingRate*5) - 25){
                            One.clipOut.playSound();
                        } else if(One.gunTimer == (90/Player.firingRate*5) + (int)(0.15 * Player.firingRate)){
                            One.clipIn.playSound();
                        } else if(One.gunTimer == 45){
                            One.pullReceiver.playSound();
                        }
                    }
                    if(Two.roundsInMagazine < 1  && Two.health > 0){
                        if(Two.gunTimer == Player.reloadTime + (90/Player.firingRate*5) - 25){
                            Two.clipOut.playSound();
                        } else if(Two.gunTimer == (90/Player.firingRate*5) + (int)(0.15 * Player.firingRate)){
                            Two.clipIn.playSound();
                        } else if(Two.gunTimer == 45){
                            Two.pullReceiver.playSound();
                        }

                    }
                } else {
                    One.clipOut.stopSound();
                    One.clipIn.stopSound();
                    One.pullReceiver.stopSound();
                    Two.clipOut.stopSound();
                    Two.clipIn.stopSound();
                    Two.pullReceiver.stopSound();
                }
            }
            while(gameStatus.equals("help") || gameStatus.equals("postGame")|| gameStatus.equals("preGame")){
                printScreen();
                if(!volume){
                    theme.stopSound();
                }
            }
            //These two lines of code display the exit splash screen and wait six seconds
            //before continuing to the system.exit() code, which will terminate the program.
            //In case you're wondering, a 1 instead of a 0 would represent the program
            //closing due to a problem.
        }
        //Remove this if you want
        //printScreen();
        //Thread.sleep(6000);
        System.exit(0);
    }
    //AI Controller
    public static void AIController(){
        //Horizontal walking code
        if(One.x - Two.x > 14){
            Two.moveX = Math.sqrt(2);
        } else if(One.x - Two.x < -14){
            Two.moveX = -Math.sqrt(2);
        } else {
            Two.moveX = 0;
        }
        //Vertical walking code
        if(Main.Two.y > Main.One.y && Main.Two.directionY >= 0 && Math.abs(Main.One.x - Main.Two.x) < 22){
            //Exception for vertical controls that will help AI aim at the player if
            //right underneath the player
            Two.moveY = -1;
        } else if(One.y - Two.y > 14){
            Two.moveY = Math.sqrt(2);
        } else if(One.y - Two.y < -14 - (Math.pow(1.4, AIDifficulty))){
            Two.moveY = -Math.sqrt(2);
        } else {
            Two.moveY = 0;
        }
        
        //Shooting controls
        if(Player.Aimbot()){
            if(reactionTime == -1){
                reactionTime = (3-AIDifficulty)*(generator.nextInt(25) + 30);
            } else if(reactionTime > 0){
                reactionTime--;
            }
        } else {
            reactionTime = -1;
        }
        
        
        if(Math.abs(Two.moveX) == Math.abs(Two.moveY)){
            Two.moveX /= Math.sqrt(2);
            Two.moveY /= Math.sqrt(2);
        }
        if(Two.gunTimer == 0 && Two.spawnTimer < 1 && reactionTime == 0 && againstComputer && Two.targetHit){
            Two.shooting = Player.Aimbot();
        } else {
            Two.shooting = false;
        }
        
        
        //Aiming code
        if(Main.Two.moveX > 0 && Main.Two.moveY > 0){
            //Down right
            Main.Two.directionX = 1;
            Main.Two.directionY = 1;
        } else if(Main.Two.moveX < 0 && Main.Two.moveY > 0){
            //Down left
            Main.Two.directionX = -1;
            Main.Two.directionY = 1;
        } else if(Main.Two.moveX > 0 && Main.Two.moveY < 0){
            //Up right
            Main.Two.directionX = 1;
            Main.Two.directionY = -1;
        } else if(Main.Two.moveX < 0 && Main.Two.moveY < 0){
            //Up left
            Main.Two.directionX = -1;
            Main.Two.directionY = -1;
        } else if(Main.Two.moveY == 0 && Main.Two.moveX > 0){
            //Right
            Main.Two.directionX = Math.sqrt(2);
            Main.Two.directionY = 0;
        } else if(Main.Two.moveY == 0 && Main.Two.moveX < 0){
            //Left
            Main.Two.directionX = -Math.sqrt(2);
            Main.Two.directionY = 0;
        } else if(Main.Two.moveX == 0 && Main.Two.moveY < 0){
            //Up
            Main.Two.directionX = 0;
            Main.Two.directionY = -Math.sqrt(2);
        } else if(Main.Two.moveX == 0 && Main.Two.moveY > 0){
            //Down
            Main.Two.directionX = 0;
            Main.Two.directionY = Math.sqrt(2);
        } else if(Main.Two.moveX == 0 && Main.Two.moveY == 0){
        }
    }
    
    
    //Game managers
    public static String checkScore() {
        if(One.kills >= scoreLimit && Two.kills >= scoreLimit){
            gameWinner = 0;
        } else if(One.kills >= scoreLimit && Two.kills < scoreLimit){
            gameWinner = 1;
        } else if(One.kills < scoreLimit && Two.kills >= scoreLimit){
            gameWinner = 2;
        } else if(time <= 0){
            if(One.kills > Two.kills){
                gameWinner = 1;
            } else if(One.kills < Two.kills){
                gameWinner = 2;
            } else {
                gameWinner = 0;
            }
        }else {
            //Still going
            return "playing";
        }
        return "postGame";
    }
    public static void resetBoard(){
        //Player one reset
        One.x = 32;
        One.centerX = 48;
        One.y = 72;
        One.centerY = 104;
        One.directionX = 1;
        One.health = 100;
        One.kills = 0;
        One.shooting = false;
        One.shotsFired = 0;
        One.hits = 0;
        One.roundsInMagazine = Player.clipSize;
        One.spawnTimer = 0;
        One.gunTimer = 0;
        
        //Player two reset
        Two.x = 761;
        Two.centerX = 777;
        Two.y = 534;
        Two.centerY = 566;
        Two.directionX = -1;
        Two.health = 100;
        Two.kills = 0;
        Two.shooting = false;
        Two.shotsFired = 0;
        Two.hits = 0;
        Two.roundsInMagazine = Player.clipSize;
        Two.spawnTimer = 0;
        Two.gunTimer = 0;
        
        //Misc
        
    }
    //Graphics stuff
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            graphics = image.getGraphics();
        }
        graphics.setColor(getBackground());
        graphics.fillRect(0,  0,  this.getWidth(),  this.getHeight());
        graphics.setColor(getForeground());
        paint(graphics);
        g.drawImage(image, 0, 0, this);
    }
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //Fonts
        Font font = new Font("Courier New",Font.PLAIN,15);
        Font bigFont = new Font("Courier New",Font.PLAIN,40);
        Font mediumFont = new Font("Courier New",Font.PLAIN,20);
        //Colors
        Color colorOne = new Color(64,0,0);
        Color colorTwo = new Color(0,0,64);
        g2.setFont(font);
        //Version Number
        String versionNumber = "Version 0.7.0";
        if(!gameStatus.equals("playing")){
            g2.drawString(versionNumber,703,625);
        }
        if(gameStatus.equals("startup")){
            g2.setFont(bigFont);
            g2.setColor(Color.black);
            g2.fillRect(0, 0, 825, 630);
            g2.setColor(Color.white);
            g2.drawString("2D Shootout", 280, 120);
            g2.setFont(font);
            g2.drawString("Written by", 370, 165); 
            g2.drawString("Christopher Hittner", 325, 190);
            g2.setFont(bigFont);
            //This if statement is used to display the startup splash screen. For multiple
            //screens, some extra modification may be neded, such as a change
            //in the gameStatus variable and a duplicate of this if statement
            //with the next screen.
            return;
        } else if(gameStatus.equals("help")){
            g2.setFont(bigFont);
            g2.drawString("How To Play", 280, 120);
            g2.setFont(mediumFont);
            g2.drawString("Player One",350,175);
            g2.setFont(font);
            g2.drawString("WASD to move", 353,200);
            g2.drawString("Space to shoot", 343,220);
            g2.setFont(mediumFont);
            g2.drawString("Player Two",350,250);
            g2.setFont(font);
            g2.drawString("Arrow keys to move", 327,275);
            g2.drawString("Enter to shoot", 343,295);
            g2.setFont(mediumFont);
            g2.drawString("P to Pause",350,325);
            if(paused){
                if(!overHelpReturnButton){
                    g2.setColor(Color.GRAY);
                    g2.fillRect(288, 378, 234, 28);
                    g2.setColor(Color.BLACK);
                    g2.drawString("  Return to Game  ",295,398);
                } else {
                    g2.setColor(Color.BLACK);
                    g2.fillRect(288, 378, 234, 28);
                    g2.setColor(Color.WHITE);
                    g2.drawString("  Return to Game  ",295,398);
                }
            } else {
                if(!overHelpReturnButton){
                    g2.setColor(Color.GRAY);
                    g2.fillRect(288, 378, 234, 28);
                    g2.setColor(Color.BLACK);
                    g2.drawString("Return to Main Menu",290,398);
                } else {
                    g2.setColor(Color.BLACK);
                    g2.fillRect(288, 378, 234, 28);
                    g2.setColor(Color.WHITE);
                    g2.drawString("Return to Main Menu",290,398);
                }
            }
            return;
        } else if(gameStatus.equals("running")){
            
            g2.setFont(bigFont);
            g2.setColor(Color.BLACK);
            g2.drawString("2D Shootout", 280, 70);
            //Play game button
            if(!overPlayButton){
                g2.setColor(Color.GRAY);
                g2.fillRect(353, 100, 111, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.BLACK);
                g2.drawString("Play Game",355,120);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillRect(353, 100, 111, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.WHITE);
                g2.drawString("Play Game",355,120);
            }
            
            //Help button
            if(!overHelpButton){
                g2.setColor(Color.GRAY);
                g2.fillRect(353, 140, 111, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.BLACK);
                g2.drawString("Help",385,160);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillRect(353, 140, 111, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.WHITE);
                g2.drawString("Help",385,160);
            }
            
            //Quit button
            if(!overQuitButton){
                g2.setColor(Color.GRAY);
                g2.fillRect(353, 180, 111, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.BLACK);
                g2.drawString("Quit",380,200);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillRect(353, 180, 111, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.WHITE);
                g2.drawString("Quit",380,200);
            }
            
            
            g2.setFont(font);
            g2.drawString(versionNumber,703,625);
            
            return;
        } else if(gameStatus.equals("postGame")){
            //Decorative frame for the pause menu
            g2.setColor(Color.WHITE);
            g2.fillRect(40,88,745,450);
            g2.setFont(bigFont);
            g2.setColor(Color.BLACK);
            g2.drawRect(40,88,745,450);
            g2.drawRect(41,89,743,448);
            
            //Title
            g2.drawString("Post Game", 300, 120);
            g2.setFont(mediumFont);
            if(gameWinner != 0){
                g2.drawString("Player " + gameWinner + " wins!", 313, 150);
            } else {
                g2.drawString("It is a tie!", 330, 150);
            }
            g2.drawString("Player 1", 230, 200);
            g2.drawString("Player 2", 500, 200);
            g2.setFont(font);
            
            //Background
            
            for(int i = 207; i <= 282; i += 15){
                g2.setColor(Color.RED);
                g2.drawLine(150, i, 352, i);
                g2.setColor(Color.BLUE);
                g2.drawLine(474, i, 675, i);
                g2.setColor(Color.BLACK);
                g2.drawLine(354, i, 473, i);
            }
            g2.setColor(Color.BLACK);
            g2.drawLine(353, 207, 353, 282);
            g2.drawLine(473, 207, 473, 282);
            
            g2.drawString("Shots Fired", 362, 218);
            g2.drawString("Hits", 400, 233);
            g2.drawString("Accuracy", 376, 248);
            g2.drawString("Kills", 390, 263);
            g2.drawString("Deaths", 386, 278);
            
            //Player one's post game stats
            g2.drawString("" + One.shotsFired, 315, 218);
            g2.drawString("" + One.hits, 315, 233);
            g2.drawString(One.accuracy + "%", 315, 248);
            g2.drawString("" + One.kills, 315, 263);
            g2.drawString("" + Two.kills, 315, 278);

            //Player two's post game Stats
            g2.drawString("" + Two.shotsFired, 500, 218);
            g2.drawString("" + Two.hits, 500, 233);
            g2.drawString(Two.accuracy + "%", 500, 248);
            g2.drawString("" + Two.kills, 500, 263);
            g2.drawString("" + One.kills, 500, 278);
            
            //Return to main button
            if(!overPostGameReturnButton){
                g2.setColor(Color.GRAY);
                g2.fillRect(293, 290, 234, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.BLACK);
                g2.drawString("Return to Main Menu",293,310);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillRect(293, 290, 234, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.WHITE);
                g2.drawString("Return to Main Menu",293,310);
            }
            return;
        } else if(gameStatus.equals("preGame")){
            //Decorative frame for the pause menu
            g2.setColor(Color.WHITE);
            g2.fillRect(40,88,745,450);
            g2.setFont(bigFont);
            g2.setColor(Color.BLACK);
            g2.drawRect(40,88,745,450);
            g2.drawRect(41,89,743,448);
            
            //Title
            g2.setFont(bigFont);
            g2.drawString("Setup Game", 300, 120);
            
            
            //----------
            //Game Rules
            //----------
            //Title
            g2.setFont(mediumFont);
            g2.drawString("Game Rules", 100, 170);
            //Time limit
            g2.setFont(font);
            g2.drawString("Match Length: " + timeLimit + " minutes", 70, 190);
            g2.fillRect(185, 182, 10, 10);
            g2.fillRect(290, 182, 10, 10);
            g2.setColor(Color.white);
            g2.drawString("-", 185, 192);
            g2.drawString("+", 290, 192);
            g2.setColor(Color.black);
            
            g2.drawString(" Score Limit: " + scoreLimit + " kills", 70, 210);
            g2.fillRect(185, 202, 10, 10);
            g2.fillRect(270, 202, 10, 10);
            g2.setColor(Color.white);
            g2.drawString("-", 185, 212);
            g2.drawString("+", 270, 212);
            g2.setColor(Color.black);
            
            if(Player.clipSize > 0){
                g2.drawString("   Clip Size: " + Player.clipSize, 70, 230);
            } else {
                g2.drawString("   Clip Size: o", 70, 230);
                g2.drawString("o",201, 230);
            }
            g2.fillRect(185, 222, 10, 10);
            g2.fillRect(215, 222, 10, 10);
            g2.setColor(Color.white);
            g2.drawString("-", 185, 232);
            g2.drawString("+", 215, 232);
            g2.setColor(Color.black);
            
            g2.drawString(" Firing Rate: " + (Player.firingRate/5), 70, 250);
            g2.fillRect(185, 242, 10, 10);
            g2.fillRect(220, 242, 10, 10);
            g2.setColor(Color.white);
            g2.drawString("-", 185, 252);
            g2.drawString("+", 220, 252);
            g2.setColor(Color.black);
            
            //Reload time
            if(Player.reloadTime > 270){
                g2.drawString("Reload Speed: 1/" + (Player.reloadTime/260) + "x", 70, 270);
            } else {
                g2.drawString("Reload Speed: " + (260/Player.reloadTime) + "x", 70, 270);
            }
            g2.fillRect(185, 262, 10, 10);
            g2.fillRect(235, 262, 10, 10);
            g2.setColor(Color.white);
            g2.drawString("-", 185, 272);
            g2.drawString("+", 235, 272);
            g2.setColor(Color.black);
            
            //Bullet speed
            if(Player.bulletSpeed > 1){
                g2.drawString("Bullet Speed: 1/" + (int)(Player.bulletSpeed/1) + "x", 70, 290);
            } else {
                g2.drawString("Bullet Speed: " + (int)(1/Player.bulletSpeed) + "x", 70, 290);
            }
            g2.fillRect(185, 282, 10, 10);
            g2.fillRect(235, 282, 10, 10);
            g2.setColor(Color.white);
            g2.drawString("-", 185, 292);
            g2.drawString("+", 235, 292);
            g2.setColor(Color.black);
            
            //Walk speed
            if(Player.walkSpeed > 1){
                g2.drawString("  Walk Speed: 1/" + (int)(Player.walkSpeed/1) + "x", 70, 310);
            } else {
                g2.drawString("  Walk Speed: " + (int)(1.0/Player.walkSpeed) + "x", 70, 310);
            }
            g2.fillRect(185, 302, 10, 10);
            g2.fillRect(235, 302, 10, 10);
            g2.setColor(Color.white);
            g2.drawString("-", 185, 312);
            g2.drawString("+", 235, 312);
            g2.setColor(Color.black);
            
            //Time speed
            if(timeMultiplier < 4){
                g2.drawString("  Time Speed: 1/" + (4/timeMultiplier) + "x", 70, 330);
            } else {
                g2.drawString("  Time Speed: " + (timeMultiplier/4) + "x", 70, 330);
            }
            g2.fillRect(185, 322, 10, 10);
            g2.fillRect(235, 322, 10, 10);
            g2.setColor(Color.white);
            g2.drawString("-", 185, 332);
            g2.drawString("+", 235, 332);
            g2.setColor(Color.black);
            
            //-----------
            //Versus type
            //-----------
            //Title
            g2.setFont(mediumFont);
            g2.drawString("Choose Opponent", 330, 340);
            //Plaver Vs. Player (PVP) Selector
            g2.drawRect(240, 360, 160, 80);
            g2.drawRect(241, 361, 158, 78);
            if(!againstComputer){
                g2.setFont(new Font("Courier New",Font.BOLD,20));
                g2.drawRect(244, 364, 152, 72);
                g2.drawRect(245, 365, 150, 70);
            } else {
                g2.setFont(mediumFont);
            }
            if(VsHumanSelected){
                g2.drawRect(242, 362, 156, 76);
                g2.drawRect(243, 363, 154, 74);
            }
            g2.drawString("VS. HUMAN",265, 410);
            
            //Plaver vs. Computer (PVC) Selector
            g2.drawRect(450, 360, 160, 80);
            g2.drawRect(451, 361, 158, 78);
            if(againstComputer){
                g2.setFont(new Font("Courier New",Font.BOLD,20));
                g2.drawRect(454, 364, 152, 72);
                g2.drawRect(455, 365, 150, 70);
            } else {
                g2.setFont(mediumFont);
            }
            if(VsAISelected){
                g2.drawRect(452, 362, 156, 76);
                g2.drawRect(453, 363, 154, 74);
            }
            g2.drawString("VS. COMPUTER",460, 410);
            g2.setFont(mediumFont);
            //---------------------
            //AI Difficulty buttons
            //---------------------
            if(againstComputer || VsAISelected){
                //Easy
                g2.setColor(Color.green);
                g2.fillRect(610, 360, 80, 20);
                g2.setColor(Color.black);
                g2.drawRect(610, 360, 80, 20);
                g2.drawRect(611, 361, 78, 18);
                if(AIDifficulty == 0){
                    g2.setFont(new Font("Courier New",Font.BOLD,15));
                    g2.drawRect(612, 362, 76, 16);
                } else {
                    g2.setFont(new Font("Courier New",Font.PLAIN,15));
                }
                if(easySelected){
                    g2.fillRect(680, 360, 10, 20);
                }
                g2.drawString("Easy", 615, 374);

                //Normal
                g2.setColor(Color.yellow);
                g2.fillRect(610, 380, 80, 20);
                g2.setColor(Color.black);
                g2.drawRect(610, 380, 80, 20);
                g2.drawRect(611, 381, 78, 18);
                if(AIDifficulty == 1){
                    g2.setFont(new Font("Courier New",Font.BOLD,15));
                    g2.drawRect(612, 382, 76, 16);
                } else {
                    g2.setFont(new Font("Courier New",Font.PLAIN,15));
                }
                if(normalSelected){
                    g2.fillRect(680, 380, 10, 20);
                }
                g2.drawString("Normal", 615, 394);

                //Hard
                g2.setColor(Color.orange);
                g2.fillRect(610, 400, 80, 20);
                g2.setColor(Color.black);
                g2.drawRect(610, 400, 80, 20);
                g2.drawRect(611, 401, 78, 18);
                if(AIDifficulty == 2){
                    g2.setFont(new Font("Courier New",Font.BOLD,15));
                    g2.drawRect(612, 362, 76, 16);
                } else {
                    g2.setFont(new Font("Courier New",Font.PLAIN,15));
                }
                if(hardSelected){
                    g2.fillRect(680, 400, 10, 20);
                }
                g2.drawString("Hard", 615, 414);

                //Insane
                g2.setColor(Color.red);
                g2.fillRect(610, 420, 80, 20);
                g2.setColor(Color.black);
                g2.drawRect(610, 420, 80, 20);
                g2.drawRect(611, 421, 78, 18);
                if(AIDifficulty == 3){
                    g2.setFont(new Font("Courier New",Font.BOLD,15));
                    g2.drawRect(612, 422, 76, 16);
                } else {
                    g2.setFont(new Font("Courier New",Font.PLAIN,15));
                }
                if(insaneSelected){
                    g2.fillRect(680, 420, 10, 20);
                }
                g2.drawString("Insane", 615, 434);
            }
            
            //--------------
            //Arena selector
            //--------------
            g2.setFont(mediumFont);
            g2.drawString("Choose Map", 550, 170);
            g2.setFont(font);
            
            //Map thumbnail
            Map.drawMapThumbnail(g);
            g2.drawRect(532, 179, 164, 81);
            g2.drawRect(533, 180, 162, 79);
            
            
            
            //Selector buttons for map name
            g2.fillRect(524, 179, 10, 122);
            g2.fillRect(694, 179, 10, 122);
            
            //Arrows
            g2.setColor(Color.WHITE);
            g2.drawString("<",525,275);
            g2.drawString(">",695,275);
            g2.drawString("<",525,295);
            g2.drawString(">",695,295);
            g2.setColor(Color.BLACK);
            
            //Map Name
            g2.drawString("Map: " + Map.getMapName(), 540, 275);
            
            //Terrain
            g2.setFont(new Font("Courier New",Font.PLAIN,14));
            g2.drawString("Terrain: " + Map.getTerrainName(), 540, 295);
            
            
            
            
            //Buttons
            if(!overPreGameReturnButton){
                g2.setColor(Color.GRAY);
                g2.fillRect(275, 470, 117, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.BLACK);
                g2.drawString("Cancel",290,490);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillRect(275, 470, 117, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.WHITE);
                g2.drawString("Cancel",290,490);
            }
            
            if(!overPreGameStartButton){
                g2.setColor(Color.GRAY);
                g2.fillRect(455, 470, 117, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.BLACK);
                g2.drawString("Fight!",475,490);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillRect(455, 470, 117, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.WHITE);
                g2.drawString("Fight!",475,490);
            }
            
            return;
        }
        //Draws the background
        Map.drawTerrain(g);
        //----------------------------------------------------------------------
        //Draws players and the obstacles
        //----------------------------------------------------------------------
        for(int i = 40; i <=825; i++){
            if((int)(One.shotY2 + 32) == i){
                drawGunshot(One, g);
            }
            if((int)(Two.shotY2 + 32) == i){
                drawGunshot(Two, g);
            }
            if((int)(One.y + 32) == i){
                drawPlayer(g, One, colorOne);
            }
            if((int)(Two.y + 32) == i){
                drawPlayer(g, Two, colorTwo);
            }
            Map.drawObstacles(g, i);
        }
        //----------------------------------------------------------------------
        //Draws scoreboard
        //----------------------------------------------------------------------
        g2.setFont(font);
        //Background
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, 825, 40);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 280, 40);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 19, 280, 2);
        g2.drawRect(0, 0, 280, 39);
        g2.drawRect(1, 1, 278, 37);
        
        //Player one score
        g2.setColor(Color.BLACK);
        g2.drawString("Player One", 5, 14);
        g2.fillRect(99, 4, 102, 12);
        g2.setColor(Color.RED);
        g2.fillRect(100,5,(int)(One.kills * 100.0/scoreLimit),10);
        g2.setColor(Color.BLACK);
        g2.drawString(One.kills + " kills", 208, 14);
        
        
        //Player two score
        g2.setColor(Color.BLACK);
        g2.drawString("Player Two", 5, 34);
        g2.fillRect(99, 24, 102, 12);
        g2.setColor(Color.BLUE);
        g2.fillRect(100,25,(int)(Two.kills * 100.0/scoreLimit),10);
        g2.setColor(Color.BLACK);
        g2.drawString(Two.kills + " kills", 208, 34);
        
        //Clock
        //Temporary timer assignment
        time += 1000;
        String clockValue = time/60000 + ":";
        if(time - (60000*(time/60000)) < 10000){
            clockValue += "0";
        }
        clockValue += ((time - (60000*(time/60000)))/1000);
        g2.setColor(Color.WHITE);
        g2.fillRect(697, 0, 128, 40);
        g2.setColor(Color.BLACK);
        g2.drawRect(715, 0, 110, 39);
        g2.drawRect(716, 1, 108, 37);
        g2.drawString("Time: " + clockValue, 720, 26);
        //Reassignment of clock
        time -= 1000;
        
        //Volume button
        g2.drawRect(697, 0, 20, 20);
        g2.drawRect(698, 1, 18, 18);
        //Top bar of note
        g2.fillRect(703, 4, 11, 2);
        //Left arm of note
        g2.fillRect(703,4,2,12);
        g2.fillOval(700,12,5,5);
        //Right arm of note
        g2.fillRect(712,4,2,12);
        g2.fillOval(709,12,5,5);
        if(!volume){
            g2.drawLine(697,0,717,20);
            g2.drawLine(717,0,697,20);
        }
        
        //Pause button
        g2.drawRect(697, 19, 20, 20);
        g2.drawRect(698, 20, 18, 18);
        g2.fillRect(701,24,4,12);
        g2.fillRect(709,24,4,12);
        
        //Debug
        
        if(paused){
            //Decorative frame for the pause menu
            g2.setColor(Color.WHITE);
            g2.fillRect(40,88,745,450);
            g2.setFont(bigFont);
            g2.setColor(Color.BLACK);
            g2.drawRect(40,88,745,450);
            g2.drawRect(41,89,743,448);
            g2.drawString("Game Paused", 280, 120);
            //Play game button
            if(!overPauseResumeButton){
                g2.setColor(Color.GRAY);
                g2.fillRect(343, 150, 131, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.BLACK);
                g2.drawString("Resume Game",343,170);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillRect(343, 150, 131, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.WHITE);
                g2.drawString("Resume Game",343,170);
            }
            
            //Play game button
            if(!overPauseHelpButton){
                g2.setColor(Color.GRAY);
                g2.fillRect(343, 190, 131, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.BLACK);
                g2.drawString("   Help   ",348,210);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillRect(343, 190, 131, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.WHITE);
                g2.drawString("   Help   ",348,210);
            }
            
            //Help button
            if(!overPauseQuitButton){
                g2.setColor(Color.GRAY);
                g2.fillRect(343, 230, 131, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.BLACK);
                g2.drawString("Exit Game",355,250);
            } else {
                g2.setColor(Color.BLACK);
                g2.fillRect(343, 230, 131, 28);
                g2.setFont(mediumFont);
                g2.setColor(Color.WHITE);
                g2.drawString("Exit Game",355,250);
            }
            
        }
    }
    public void drawGunshot(Player p, Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        //----------------------------------------------------------------------
        //Draws gunshots
        //----------------------------------------------------------------------
        if(!p.targetHit){
            g2.setColor(Color.orange);
            g2.drawLine((int)(p.shotX1 + 0.5), (int)(p.shotY1 + 0.5),(int) (p.shotX2 + 0.5),(int) (p.shotY2 + 0.5));
            g2.setColor(Color.black);
            g2.fillRect((int)(p.shotX2 + 0.5), (int)(p.shotY2 - 0.5), 2, 2);
            
        }
    }
    public void drawPlayer(Graphics g, Player p, Color c){
        Graphics2D g2 = (Graphics2D) g;
        //----------------------------------------------------------------------
        //Player's body
        //----------------------------------------------------------------------
        if(p.spawnTimer < 1 || ((int)(p.spawnTimer/10)) % 2 == 0){
            g2.setColor(c);
            //Body
            g2.fillRoundRect((int)(p.x + 0.5) + 6, (int)(p.y + 0.5) + 12, 20, 32, 8, 8);
            //Legs
            if(p.moveX == 0 && Math.abs(p.moveY) > 0){
                g2.fillRoundRect((int)(p.x + 0.5) + 8, (int)(p.y + 0.5) +  39, 7, (int) (26 + 8 * p.directionY * Math.sin(p.walkCycle/25)), 8, 8);
                g2.fillRoundRect((int)(p.x + 0.5) + 17,(int)(p.y + 0.5) +  39, 7, (int) (26 - 8 * p.directionY * Math.sin(p.walkCycle/25)), 8, 8);
            } else if(p.moveY != 0 || Math.abs(p.moveX) != 0){
                //Left
                g2.fillOval((int)(p.x + 0.5) + 8, (int)(p.y + 0.5) +  39, 7, 7);
                for(int i = 12; i < 18; i++){
                    g2.drawLine((int)((p.x + 0.5) + i - 2 * Math.sqrt(2) * p.directionY), (int)(p.y + 0.5) +  43, (int)((p.x + 0.5) + i + 8 * p.directionX * Math.sin(0.04 * p.walkCycle)), (int)((p.y + 0.5) +  61 - 2 * Math.abs(Math.sin(0.04 * p.walkCycle))));
                }
                g2.fillOval((int)((p.x + 0.5) + 12 + 8 * p.directionX * Math.sin(0.04 * p.walkCycle)), (int)((p.y + 0.5) +  57), 7, 7);
                
                //Right
                g2.fillOval((int)(p.x + 0.5) + 17, (int)(p.y + 0.5) +  39, 7, 7);
                for(int i = 13; i < 19; i++){
                    g2.drawLine((int)((p.x + 0.5) + i + 2 * Math.sqrt(2) * p.directionY), (int)(p.y + 0.5) +  43, (int)((p.x + 0.5) + i - 8 * p.directionX * Math.sin(0.04 * p.walkCycle)), (int)((p.y + 0.5) +  61 - 2 * Math.abs(Math.sin(0.04 * p.walkCycle))));
                }
                g2.fillOval((int)((p.x + 0.5) + 13 - 8 * p.directionX * Math.sin(0.04 * p.walkCycle)), (int)((p.y + 0.5) +  57), 7, 7);
            } else {
                if(Math.abs(p.directionX) == Math.abs(p.directionY)){
                    g2.fillRoundRect((int)((p.x + 0.5) + 10), (int)(p.y + 0.5) +  39, 7, 26, 8, 8);
                    g2.fillRoundRect((int)((p.x + 0.5) + 15),(int)(p.y + 0.5) +  39, 7, 26, 8, 8); 
                } else {
                    g2.fillRoundRect((int)((p.x + 0.5) + 8 + 4 * Math.abs(p.directionX)), (int)(p.y + 0.5) +  39, 7, 26, 8, 8);
                    g2.fillRoundRect((int)((p.x + 0.5) + 17 - 4 * Math.abs(p.directionX)),(int)(p.y + 0.5) +  39, 7, 26, 8, 8); 
                }
            }
            //Shoulder
            g2.fillOval((int)(p.centerX - 3 - (8 * p.directionY)), (int)(p.centerY - 15.5), 6, 6);
            //Arm
            //Y
            for(int i = 1; i <= 6; i ++){
                g2.drawLine((int)(p.centerX - (8 * p.directionY)), (int)(p.centerY - 15.5) + i, (int)(p.centerX - (8 * p.directionY) + (10*p.directionX)), (int)(p.centerY - 15.5 + (8 * p.directionY) + i));
            }
            //X
            for(int i = -2; i <= 3; i ++){
                g2.drawLine((int)(p.centerX - (8 * p.directionY) + i - 0.5), (int)(p.centerY - 12.5), (int)(p.centerX - (8 * p.directionY) + (10*p.directionX) + i - 0.5), (int)(p.centerY - 12.5 + (8 * p.directionY)));
            }
            //Hand
            if(p.playerNumber == 1){
                g2.setColor(Color.red);
            } else {
                g2.setColor(Color.blue);
            }
            g2.fillOval((int)(p.centerX - 3 - (8 * p.directionY) + 11 * p.directionX), (int)(p.centerY - 15.5 + 10 * p.directionY), 6, 6);
            g2.setColor(c);
            //Head
            if(p.playerNumber == 1){
                g2.setColor(Color.red);
            } else {
                g2.setColor(Color.blue);
            }
            g2.fillOval((int)(p.x + 0.5) + 10, (int)(p.y + 0.5), 12, 12);
            g2.setColor(c);
            
        }
        
        //----------------------------------------------------------------------
        //Health bar
        //----------------------------------------------------------------------
        if(p.spawnTimer < 1 || p.spawnTimer > 190){
            g2.setColor(Color.BLACK);
            g2.fillRect((int)(p.centerX - 21),(int)(p.centerY - 49),42,10);
            g2.setColor(Color.darkGray);
            g2.fillRect((int)(p.centerX - 20),(int)(p.centerY - 48),40,8);
            if(p.health > 50){
                g2.setColor(Color.green); 
            } else if(p.health > 25){
                g2.setColor(Color.yellow); 
            } else {
                g2.setColor(Color.red); 
            }
            g2.fillRect((int)(p.centerX - 20),(int)(p.centerY - 48),(int)(p.health * 0.4),8);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Courier New",Font.PLAIN,10));
            if(Player.clipSize > 0){
                g2.drawString(p.roundsInMagazine + "/" + Player.clipSize, (int)(p.centerX - 14.5), (int)(p.centerY - 56.5));
            } else {
                g2.drawString("o", (int)(p.centerX - 6.5), (int)(p.centerY - 56.5));
                g2.drawString("o", (int)(p.centerX - 2.5), (int)(p.centerY - 56.5));
            }
        }
        
    }
    public static void printScreen() throws InterruptedException{
        game.repaint();
    }
    public Main(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
		One.keyReleased(e);
            }
            @Override
            public void keyPressed(KeyEvent e) {
		One.keyPressed(e);
            }
        });
        
        addMouseListener(this);
        addMouseMotionListener(this);
        

        setFocusable(true);
    }

    //Use this for reference:
    //http://www.javakode.com/applets/04-mouseInput/
    @Override
    public void mouseClicked(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();
        if(gameStatus.equals("running")){
            //----------------------------------------------------
            gameStatus = Main_Start.MouseClick(x,y);
            if(Main_Start.MouseOver(x,y)){
                return;
            }
            //----------------------------------------------------
            gameStatus = Main_Help.MouseClick(x,y);
            if(Main_Help.MouseOver(x,y)){
                return;
            }
            //----------------------------------------------------
            gameStatus = Main_Quit.MouseClick(x,y);
            if(Main_Quit.MouseOver(x,y)){
                return;
            }
            //----------------------------------------------------
        } else if(gameStatus.equals("help")){
            gameStatus = Help_Return.MouseClick(x,y);
            if(gameStatus.equals("running") && paused){
                gameStatus = "playing";
            }
        }else if(paused){
            gameStatus = Pause_Resume.MouseClick(x,y);
            if(gameStatus.equals("paused")){
                gameStatus = "playing";
                paused = true;
            } else {
                paused = false;
                return;
            }
            gameStatus = Pause_Help.MouseClick(x,y);
            if(gameStatus.equals("paused")){
                gameStatus = "playing";
                paused = true;
            } else {
                return;
            }
            gameStatus = Pause_Quit.MouseClick(x,y);
            if(gameStatus.equals("paused")){
                gameStatus = "playing";
                paused = true;
            } else {
                paused = false;
                if(One.kills > Two.kills){
                    gameWinner = 1;
                } else if(One.kills < Two.kills){
                    gameWinner = 2;
                } else {
                    gameWinner = 0;
                }
                return;
            }
        } else if(gameStatus.equals("postGame")){
            gameStatus = Postgame_Return.MouseClick(x,y);
        } else if(gameStatus.equals("preGame")){
            gameStatus = Pregame_Return.MouseClick(x,y);
            if(gameStatus != "preGame"){
                return;
            }
            gameStatus = Pregame_Start.MouseClick(x,y);
            if(gameStatus != "preGame"){
                return;
            }
            
            if(moreTime.MouseOver(x, y) && timeLimit < 60){
                timeLimit++;
            } else if(lessTime.MouseOver(x, y) && timeLimit > 1){
                timeLimit--;
            }
            
            if(moreKills.MouseOver(x, y) && scoreLimit < 60){
                scoreLimit++;
            } else if(lessKills.MouseOver(x, y) && scoreLimit > 1){
                scoreLimit--;
            }
            
            if(moreBullets.MouseOver(x, y) && Player.clipSize <= 30){
                Player.clipSize++;
                if(Player.clipSize > 10 && Player.clipSize <= 30){
                    while(Player.clipSize%2 != 0 && Player.clipSize%3 != 0 && Player.clipSize%5 != 0){
                        Player.clipSize++;
                    }
                } else if(Player.clipSize > 30){
                    Player.clipSize = 0;
                }
            } else if(lessBullets.MouseOver(x, y) && Player.clipSize >= 0){
                Player.clipSize--;
                if(Player.clipSize > 10 && Player.clipSize >= 0){
                    while(Player.clipSize%2 != 0 && Player.clipSize%3 != 0 && Player.clipSize%5 != 0){
                        Player.clipSize--;
                    }
                } else if(Player.clipSize < 0){
                    Player.clipSize = 30;
                }
            }
            
            if(moreShooting.MouseOver(x, y) && Player.firingRate < 50){
                Player.firingRate++;
                if(Player.firingRate > 5 && Player.firingRate <= 50){
                    while(Player.firingRate%5 != 0){
                        Player.firingRate++;
                    }
                } else if(Player.firingRate > 50){
                    Player.firingRate = 50;
                }
            } else if(lessShooting.MouseOver(x, y) && Player.firingRate > 5){
                Player.firingRate--;
                if(Player.firingRate > 5){
                    while(Player.firingRate%5 != 0 && Player.firingRate > 2){
                        Player.firingRate--;
                    }
                }
            }
            
            if(slowReload.MouseOver(x, y)){
                if(Player.reloadTime > 65){
                    Player.reloadTime/=2;
                }
            } else if(fastReload.MouseOver(x, y)){
                if(Player.reloadTime < 1040){
                    Player.reloadTime*=2;
                }
            }
            
            if(slowBullet.MouseOver(x, y)){
                if(Player.bulletSpeed > 0.125){
                    Player.bulletSpeed/=2;
                }
            } else if(fastBullet.MouseOver(x, y)){
                if(Player.bulletSpeed < 8){
                    Player.bulletSpeed*=2;
                }
            }
            
            if(slowWalk.MouseOver(x, y)){
                if(Player.walkSpeed > 0.25){
                    Player.walkSpeed/=2.0;
                }
            } else if(fastWalk.MouseOver(x, y)){
                if(Player.walkSpeed < 4){
                    Player.walkSpeed*=2.0;
                }
            }
            
            if(slowTime.MouseOver(x, y)){
                if(timeMultiplier > 1){
                    timeMultiplier/=2.0;
                }
            } else if(fastTime.MouseOver(x, y)){
                if(timeMultiplier < 16){
                    timeMultiplier*=2.0;
                }
            }
            
            if(chooseHuman.MouseOver(x, y)){
                againstComputer = false;
            }
            if(chooseAI.MouseOver(x, y)){
                againstComputer = true;
            }
            if(againstComputer){
                if(EasyAI.MouseOver(x, y)){
                    AIDifficulty = 0;
                } else if(NormalAI.MouseOver(x, y)){
                    AIDifficulty = 1;
                } else if(HardAI.MouseOver(x, y)){
                    AIDifficulty = 2;
                } else if(InsaneAI.MouseOver(x, y)){
                    AIDifficulty = 3;
                }
            }
            if(mapUp.MouseOver(x, y)){
                Map.incrementMap(1);
            }
            if(mapDown.MouseOver(x, y)){
                Map.incrementMap(-1);
            }
            
            if(terrainUp.MouseOver(x, y)){
                Map.incrementTerrain(1);
            }
            if(terrainDown.MouseOver(x, y)){
                Map.incrementTerrain(-1);
            }
        } else if(gameStatus.equals("playing")){
            if(toggleVolume.MouseOver(x, y)){
                volume = !volume;
            }
            if(!paused && pauseGame.MouseOver(x, y)){
                paused = true;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();
        if(gameStatus.equals("running")){
            overPlayButton = Main_Start.MouseOver(x,y);
            overHelpButton = Main_Help.MouseOver(x,y);
            overQuitButton = Main_Quit.MouseOver(x,y);
        } else if(gameStatus.equals("help")){
            overHelpReturnButton = Help_Return.MouseOver(x,y);
        } else if(paused){
            overPauseResumeButton = Pause_Resume.MouseOver(x,y);
            overPauseHelpButton = Pause_Help.MouseOver(x,y);
            overPauseQuitButton = Pause_Quit.MouseOver(x,y);
        } else if(gameStatus.equals("postGame")){
            overPostGameReturnButton = Postgame_Return.MouseOver(x,y);
        } else if(gameStatus.equals("preGame")){
            overPreGameReturnButton = Pregame_Return.MouseOver(x,y);
            overPreGameStartButton = Pregame_Start.MouseOver(x,y);
            VsAISelected = chooseAI.MouseOver(x, y);
            VsHumanSelected = chooseHuman.MouseOver(x, y);
            easySelected = EasyAI.MouseOver(x, y);
            normalSelected = NormalAI.MouseOver(x, y);
            hardSelected = HardAI.MouseOver(x, y);
            insaneSelected = InsaneAI.MouseOver(x, y);
        }
        
    }

}
