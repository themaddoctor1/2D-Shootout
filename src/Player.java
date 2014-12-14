/*
 * 2D Shootout
 * Copyright(c) 2013 Christopher Hittner
 * Written using Game Engine Alpha-G1-Java
 * 
 * All rights to this code and any other classes of the engine or game are the 
 * property of Christopher Hittner.
*/

import java.awt.event.KeyEvent;

public class Player {
    //Location variable
    double moveX = 0, moveY = 0, centerX, centerY;
    int gunTimer = 0, spawnTimer = -1;
    int health = 100;
    int playerNumber;
    int kills = 0;
    boolean shooting = false;
    double shotX1, shotX2, shotY1, shotY2;
    double x = 100, y = 100;
    //Location for the facing directing
    double directionX, directionY = 0;
    static boolean ad = false;
    boolean serving;
    //Statistic variables
    int shotsFired = 0, hits = 0, accuracy;
    static int clipSize = 10;
    int roundsInMagazine = 10;
    static int firingRate = 5, reloadTime = 260;
    //Aesthetic variables
    int walkCycle = 0;
    boolean targetHit = true;
    //Physics variables
    static double bulletSpeed = 1, walkSpeed = 1;
    double bulletX, bulletY;
    //Noises
    Audio shoot, clipOut, clipIn, pullReceiver;
    public Player(int a) throws Exception{
        playerNumber = a;
        if(playerNumber == 1){
            x = 32;
            centerX = 48;
            
            y = 72;
            centerY = 104;
            
            directionX = 1;
        } else if(playerNumber == 2){
            x = 761;
            centerX = 777;
            
            y = 534;
            centerY = 566;
            
            directionX = -1;
        }
        shoot = new Audio("bang");
        clipOut = new Audio("click1");
        clipIn = new Audio("click2");
        pullReceiver = new Audio("click3");
    }
    public void move(){
        if(gunTimer > 0){
            //Timing for player's weapon
            gunTimer--;
        }
        if(gunTimer < 1 && roundsInMagazine < 1){
            roundsInMagazine = clipSize;
        }
        if(spawnTimer < 2){
            x += moveX / walkSpeed;
            y += moveY / walkSpeed;
        }
        centerX = x + 16;
        centerY = y + 32;
        //Walk cycle
        if(moveX != 0 || moveY != 0){
            walkCycle++;
            walkCycle %= 360;
        } else {
            walkCycle = 0;
        }
        //This method moves the player based on the set velocity on the X and Y axis.
        //It also resets the centerX and centerY values to their proper values.
    }
    public void testHit(){
        //This method can be used to detect collisions. It can be modified for
        //one's preferences and needs. In this, I used it to detect whether the
        //player was trying to move the square out of the applet and make sure
        //it doesn't happen.
        if(x < 0){
            x = 0;
        } else if(centerX > 809){
            x = 793;
        }
        //This test tests the player's X coordinate
        //
        if(y < 40){
            y = 40;
        } else if(centerY > 597){
            y = 566;
        }
        //This test tests the player's Y coordinate
        if(Main.One.health > 0 && Main.Two.health > 0){
            if(Main.One.x - Main.Two.x < 28 && Main.One.x - Main.Two.x > 0 && Math.abs(Main.One.y - Main.Two.y) < 14){
                Main.One.x += 1;
                Main.Two.x -= 1;
            } else if(Main.Two.x - Main.One.x < 28 && Main.Two.x - Main.One.x >= 0 && Math.abs(Main.Two.y - Main.One.y) < 14){
                Main.One.x -= 1;
                Main.Two.x += 1;
            } else if(Main.One.y - Main.Two.y < 16 && Main.One.y - Main.Two.y >= 0 && Math.abs(Main.One.x - Main.Two.x) < 26){
                Main.One.y += 1;
                Main.Two.y -= 1;
            } else if(Main.Two.y - Main.One.y < 16 && Main.Two.y - Main.One.y >= 0 && Math.abs(Main.Two.x - Main.One.x) < 26){
                Main.One.y -= 1;
                Main.Two.y += 1;
            }
        }
        
        //Tests collisions with obstacles
        if(Map.testHit(centerX, centerY + 32)){
            y -= 1.5;
        } else if(Map.testHit(centerX, centerY + 6)){
            y += 1.5;
        } else if(Map.testHit(centerX - 14, centerY + 19)){
            x += 1.5;
        } else if(Map.testHit(centerX + 14, centerY + 19)){
            x -= 1.5;
        }
        
        //These two if-else statements create a barrier that forbids the player
        //from leaving the space between (0,0) and (250,300). The x and y variables,
        //as well as the centerX and centerY values may have different numbers,
        //but it is done that way for collision detection that keeps the player on
        //the board. If you aren't convinced, you can look at the background size
        //(see Main.paint() to find that) and enable resizing of the applet
        //(found in Main.main()) to see.
        
    }
    public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_A)
		Main.One.moveX = 0;
	    if(e.getKeyCode() == KeyEvent.VK_D)
		Main.One.moveX = 0;
            if(e.getKeyCode() == KeyEvent.VK_S)
		Main.One.moveY = 0;
            if(e.getKeyCode() == KeyEvent.VK_W)
		Main.One.moveY = 0;
        
            if(e.getKeyCode() == KeyEvent.VK_SPACE)
		Main.One.shooting = false;
            if(!Main.againstComputer){
            if(e.getKeyCode() == KeyEvent.VK_LEFT)
		Main.Two.moveX = 0;
	    if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		Main.Two.moveX = 0;
            if(e.getKeyCode() == KeyEvent.VK_DOWN)
		Main.Two.moveY = 0;
            if(e.getKeyCode() == KeyEvent.VK_UP)
		Main.Two.moveY = 0;
            
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
		Main.Two.shooting = false;
            } else {
                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                    Main.One.moveX = 0;
                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                    Main.One.moveX = 0;
                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                    Main.One.moveY = 0;
                if(e.getKeyCode() == KeyEvent.VK_UP)
                    Main.One.moveY = 0;

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    Main.One.shooting = false;
            }
            //This method will stop the player's movement on a single axis if a
            //key is released. I did this to fix a bug where releasing a key would
            //stop movement on both the X and Y axis.
    }

    public void keyPressed(KeyEvent e) {
        //Player one's controls
        if(e.getKeyCode() == KeyEvent.VK_A){
            Main.One.moveX = -Math.sqrt(2);
        }
        if(e.getKeyCode() == KeyEvent.VK_D){
            Main.One.moveX = Math.sqrt(2);
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            Main.One.moveY = Math.sqrt(2);
        }
        if(e.getKeyCode() == KeyEvent.VK_W){
            Main.One.moveY = -Math.sqrt(2);
        }
        
        if(Math.abs(Main.One.moveX) == Math.abs(Main.One.moveY)){
            Main.One.moveX /= Math.sqrt(2);
            Main.One.moveY /= Math.sqrt(2);
        }
        
        //Player one shoot command
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(Main.One.gunTimer == 0 && Main.One.health > 0){
                Main.One.shooting = true;
            }
            //Shoot player one's weapon
        }
        
        //Player two's controls
        if(Main.againstComputer == false){
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                Main.Two.moveX = -Math.sqrt(2);
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                Main.Two.moveX = Math.sqrt(2);
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                Main.Two.moveY = Math.sqrt(2);
            }
            if(e.getKeyCode() == KeyEvent.VK_UP){
                Main.Two.moveY = -Math.sqrt(2);
            }
            if(Math.abs(Main.Two.moveX) == Math.abs(Main.Two.moveY)){
                Main.Two.moveX /= Math.sqrt(2);
                Main.Two.moveY /= Math.sqrt(2);
            }

            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                if(Main.Two.gunTimer == 0 && Main.Two.health > 0){
                    Main.Two.shooting = true;
                }
                //Shoot player two's weapon
            }
        } else {
            //Since player two is AI controlled, his/her controls will be used
            //by player one
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                Main.One.moveX = -Math.sqrt(2);
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                Main.One.moveX = Math.sqrt(2);
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                Main.One.moveY = Math.sqrt(2);
            }
            if(e.getKeyCode() == KeyEvent.VK_UP){
                Main.One.moveY = -Math.sqrt(2);
            }
            if(Math.abs(Main.One.moveX) == Math.abs(Main.One.moveY)){
                Main.One.moveX /= Math.sqrt(2);
                Main.One.moveY /= Math.sqrt(2);
            }

            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                if(Main.One.gunTimer == 0 && Main.One.health > 0){
                    Main.One.shooting = true;
                }
                //Shoot player two's weapon
            }
        }
        
        
        //Player one aiming code
        if(Main.One.moveX > 0 && Main.One.moveY > 0){
            //Down right
            Main.One.directionX = 1;
            Main.One.directionY = 1;
        } else if(Main.One.moveX < 0 && Main.One.moveY > 0){
            //Down left
            Main.One.directionX = -1;
            Main.One.directionY = 1;
        } else if(Main.One.moveX > 0 && Main.One.moveY < 0){
            //Up right
            Main.One.directionX = 1;
            Main.One.directionY = -1;
        } else if(Main.One.moveX < 0 && Main.One.moveY < 0){
            //Up left
            Main.One.directionX = -1;
            Main.One.directionY = -1;
        } else if(Main.One.moveY == 0 && Main.One.moveX > 0){
            //Right
            Main.One.directionX = Math.sqrt(2);
            Main.One.directionY = 0;
        } else if(Main.One.moveY == 0 && Main.One.moveX < 0){
            //Left
            Main.One.directionX = -Math.sqrt(2);
            Main.One.directionY = 0;
        } else if(Main.One.moveX == 0 && Main.One.moveY < 0){
            //Up
            Main.One.directionX = 0;
            Main.One.directionY = -Math.sqrt(2);
        } else if(Main.One.moveX == 0 && Main.One.moveY > 0){
            //Down
            Main.One.directionX = 0;
            Main.One.directionY = Math.sqrt(2);
        } else if(Main.One.moveX == 0 && Main.One.moveY == 0){
        }
        
        
        //Player two aiming code
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
        
        
        if(e.getKeyCode() == KeyEvent.VK_P){
            if(Main.gameStatus.equals("playing")){
		Main.paused = true;
            }
        }//This method contains player controls. The example that I made will
        //change the speed that the player is moving at on the X and Y axis.
    }
    public static void shootOne() throws Exception{
        
        Main.One.shotX1 = Main.One.shotX2;
        Main.One.shotY1 = Main.One.shotY2;
        if(Main.One.gunTimer < 1){
            if(Main.volume)
                Main.One.shoot.playSound();
            Main.One.shotX1 = Main.One.centerX  - (12 * Main.One.directionY);
            Main.One.shotY1 = Main.One.centerY - 15;
            Main.One.shotsFired ++;
            Main.One.gunTimer = 90/firingRate*5;
            Main.One.bulletX = Main.One.directionX;
            Main.One.bulletY = Main.One.directionY;
            if(clipSize > 0){
                Main.One.roundsInMagazine--;
            }
            if(Main.One.roundsInMagazine < 1 && clipSize > 0){
                Main.One.gunTimer += reloadTime + 25;
            }
        }
        double x = Main.One.shotX1;
        double y = Main.One.shotY1;
        boolean hit = false;
        int i = 0;
        while(hit == false && i < 20){
            x += 3 * (1/bulletSpeed) * Main.One.bulletX/Math.sqrt(1 + Math.abs(Main.One.directionY));
            y += 3 * (1/bulletSpeed) * Main.One.bulletY/Math.sqrt(1 + Math.abs(Main.One.directionX));
            if(x < 0 || x > 825){
                hit = true;
            } else if (y < 40 || y > 630){
                hit = true;
            } else if(Math.abs(Main.Two.centerX - x) < 14 && Math.abs(Main.Two.centerY - y) < 32 && Main.Two.health > 0){
                if(!(Main.One.y > Main.Two.y && Main.One.directionY >= 0 && Math.abs(Main.Two.x - Main.One.x) < 22)){
                    hit = true;
                    Main.One.hits ++;
                    
                    Main.Two.health -= 10 + Main.generator.nextInt(5) - 2;
                    //Player one was shot!
                }
            } else if(Map.testHit(x, y + 47)){
                hit = true;
            }
            
            
            i++;
            
            Main.One.targetHit = hit;
        }
        Main.One.shotX2 = x;
        Main.One.shotY2 = y;
        if(Main.One.targetHit){
            Main.One.accuracy = (100 * Main.One.hits/Main.One.shotsFired);
        }
    }
    public static void shootTwo() throws Exception{
        Main.Two.shotX1 = Main.Two.shotX2;
        Main.Two.shotY1 = Main.Two.shotY2;
        if(Main.Two.gunTimer < 1){
            if(Main.volume)
                Main.Two.shoot.playSound();
            Main.Two.shotX1 = Main.Two.centerX  - (12 * Main.Two.directionY);
            Main.Two.shotY1 = Main.Two.centerY - 15;
            Main.Two.shotsFired ++;
            Main.Two.gunTimer = 90/firingRate*5;
            Main.Two.bulletX = Main.Two.directionX;
            Main.Two.bulletY = Main.Two.directionY;
            
            if(clipSize > 0){
                Main.Two.roundsInMagazine--;
            }
            if(Main.Two.roundsInMagazine < 1 && clipSize > 0){
                Main.Two.gunTimer += reloadTime + 25;
            }
        }
        double x = Main.Two.shotX1;
        double y = Main.Two.shotY1;
        boolean hit = false;
        int i = 0;
        while(hit == false && i < 20){
            x += 3 * (1/bulletSpeed) * Main.Two.bulletX/Math.sqrt(1 + Math.abs(Main.Two.directionY));
            y += 3 * (1/bulletSpeed) * Main.Two.bulletY/Math.sqrt(1 + Math.abs(Main.Two.directionX));
            if(x < 0 || x > 825){
                hit = true;
            } else if (y < 40 || y > 630){
                hit = true;
            } else if(Math.abs(Main.One.centerX - x) < 14 && Math.abs(Main.One.centerY - y) < 32 && Main.One.health > 0){
                if(!(Main.Two.y > Main.One.y && Main.Two.directionY >= 0 && Math.abs(Main.One.x - Main.Two.x) < 22)){
                    hit = true;
                    Main.Two.hits ++;
                    Main.One.health -=  10 + Main.generator.nextInt(5) - 2;
                    //Player one was shot!
                }
            } else if(Map.testHit(x, y + 47)){
                hit = true;
            }
            Main.Two.targetHit = hit;
            i++;
            
            
        }
        Main.Two.shotX2 = x;
        Main.Two.shotY2 = y;
        
        if(Main.Two.targetHit){
            Main.Two.accuracy = (100 * Main.Two.hits/Main.Two.shotsFired);
        }
    }
    public static boolean Aimbot(){
        double x = Main.Two.centerX - (12 * Main.Two.directionY);
        double y = Main.Two.centerY - 15;
        boolean hit = false;
        while(hit == false){
            x += 5 * Main.Two.directionX/Math.sqrt(1 + Math.abs(Main.Two.directionY));
            y += 5 * Main.Two.directionY/Math.sqrt(1 + Math.abs(Main.Two.directionX));
            if(x < 0 || x > 825){
                return false;
            } else if (y < 40 || y > 630){
                return false;
            } else if(Math.abs(Main.One.centerX - x) < 14 && Math.abs(Main.One.centerY - y) < 32 && Main.One.health > 0){
                if(!(Main.Two.y > Main.One.y && Main.Two.directionY >= 0 && Math.abs(Main.One.x - Main.Two.x) < 22)){
                    return true;
                }
            } else if(Map.testHit(x, y + 47)){
                return false;
            }
            
        }
        return false;
    }
    public void spawn(){
        if(health < 1 && spawnTimer == -1){
            spawnTimer = 50 * Main.timeMultiplier;
            if(playerNumber == 1){
                Main.Two.kills += 1;
            } else if(playerNumber == 2){
                Main.One.kills += 1;
            }
        }
        if(spawnTimer > -1){
            spawnTimer--;
            if(spawnTimer == 0){
                health = 100;
                if(playerNumber == 1){
                    if(Main.Two.centerX > 413){
                        x = 32;
                        centerX = 48;
                    } else {
                        x = 761;
                        centerX = 777;
                    }
                    if(Main.Two.centerY > 335){
                        y = 72;
                        centerY = 104;
                    } else {
                        y = 534;
                        centerY = 566;
                    }
                } else if(playerNumber == 2){
                    if(Main.One.centerX > 413){
                        x = 32;
                        centerX = 48;
                    } else {
                        x = 761;
                        centerX = 777;
                    }
                    if(Main.One.centerY > 335){
                        y = 72;
                        centerY = 104;
                    } else {
                        y = 534;
                        centerY = 566;
                    }
                }
                roundsInMagazine = clipSize;
            }
        }
    }

    
}
