/*
 * 2D Shootout
 * Copyright(c) 2013 Christopher Hittner
 * Written using Game Engine Alpha-G1-Java
 * 
 * All rights to this code and any other classes of the engine or game are the 
 * property of Christopher Hittner.
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author WHS-D11B4W20
 */
public class Map {
    //--------------------------------------------------------------------------
    //Variables
    //--------------------------------------------------------------------------
    private static int map = 0, terrain = 0;
    private static String mapName = " Empty Room ", terrainName = "Simulator";
    private static final int numberOfMaps = 3, numberOfTerrains = 3;
    private static Color ground = new Color(224,224,224);
    //--------------------------------------------------------------------------
    //Obstacle sets
    //--------------------------------------------------------------------------
    private static Obstacle obstacle[];
    private static int numberOfObstacles = 0;
    //--------------------------------------------------------------------------
    //Methods
    //--------------------------------------------------------------------------
    //Sets the map to the one desired
    //@param The desired map
    public static void incrementMap(int a){
        map += a;
        if(map < 0){
            map = numberOfMaps;
        } else if(map > numberOfMaps){
            map = 0;
        }
        if(map == 0){
            mapName = " Empty Room ";
            obstacle = null;
            numberOfObstacles = 0;
        } else if(map == 1){
            mapName = "Four Corners";
            numberOfObstacles = 4;
            obstacle = new Obstacle[numberOfObstacles];
            //Bottom left
            obstacle[0] = new Obstacle(140,505, 50, 25);
            //Top left
            obstacle[1] = new Obstacle(140,125, 50, 25);
            //Bottom right
            obstacle[2] = new Obstacle(635,505, 50, 25);
            //Top right
            obstacle[3] = new Obstacle(635,125, 50, 25);
            //785 wide and 630 wide
        } else if(map == 2){
            mapName = "Center Rock";
            numberOfObstacles = 1;
            obstacle = new Obstacle[numberOfObstacles];
            //Bottom left
            obstacle[0] = new Obstacle(355,320, 120, 70);
            //785 wide and 630 wide
        }
        else if(map == 3){
            mapName = "Stonehenge";
            numberOfObstacles = 12;
            obstacle = new Obstacle[numberOfObstacles];
            //Bottom left
            for(int i = 0; i < numberOfObstacles; i++)
            obstacle[i] = new Obstacle(387 + (int)(200 * Math.cos(Math.toRadians(30.0 * i))),305 + (int)(200 * Math.sin(Math.toRadians(30.0 * i))), 50, 25);
            //785 wide and 630 wide
        }
    }
    
    public static void incrementTerrain(int a){
        terrain += a;
        if(terrain < 0){
            terrain = numberOfTerrains;
        } else if(terrain > numberOfTerrains){
            terrain = 0;
        }
        if(terrain == 0){
            terrainName = "Simulator";
            ground = new Color(224,224,224);
        } else if(terrain == 1){
            terrainName = "   Arctic";
            ground = new Color(216,216,255);
        } else if(terrain == 2){
            terrainName = "   Desert";
            ground = new Color(255,255,128);
        } else if(terrain == 3){
            terrainName = "Grassland";
            ground = new Color(64, 192, 64);
        }
    }
    
    //Returns the name of the map
    //@param The name of the map currently in use
    public static String getMapName(){
        return mapName;
    }
    
    //Returns the name of the terrain
    //@param The name of the terrain currently in use
    public static String getTerrainName(){
        return terrainName;
    }
    
    public static boolean testHit(double x, double y){
        if(map == 0){
            return false;
        } else {
            for(int i = 0; i < numberOfObstacles; i++){
                if(obstacle[i].testHit(x, y)){
                    return true;
                }
            }
        }
        return false;
    }
    public static void drawObstacles(Graphics g, int n){
        for(int i = 0; i < numberOfObstacles; i++){
            if(obstacle[i].getY() == n){
                obstacle[i].drawObstacle(g);
            }
        }
    }
    public static void drawTerrain(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(ground);
        g2.fillRect(0,0,825,630);
    }
    public static void drawMapThumbnail(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(ground);
        g2.fillRect(533, 180, 162, 79);
        for(int i = 0; i < numberOfObstacles; i++){
            obstacle[i].drawObstacleIcon(g);
        }
        g2.setColor(Color.black);
    }
    public static Obstacle[] getObstacleCopy(){
        return obstacle;
    }
    
    
}
