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

public class Obstacle {
    //--------------------------------------------------------------------------
    //Variables
    //--------------------------------------------------------------------------
    private int x1, x2;
    private int y1, y2;
    private int WIDTH, HEIGHT;
    //--------------------------------------------------------------------------
    //Constructor(s)
    //--------------------------------------------------------------------------
    public Obstacle(int a, int b, int c, int d){
        x1 = a;
        y1 = b;
        x2 = x1 + c;
        y2 = y1 + d;
        WIDTH = c;
        HEIGHT = d;
    }
    //--------------------------------------------------------------------------
    //Methods
    //--------------------------------------------------------------------------
    
    //Determines whether or not the obstacle was hit
    //@param The coordinates of the bullet
    public boolean testHit(double x, double y){
        if(x >= x1 && x <= x2 && y >= y1 && y <= y2){
            return true;
        } else {
            return false;
        }
    }

    //Draws the obstacle
    //@param The graphics class
    public void drawObstacle(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //Filling
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x1, y1 - 64, WIDTH, HEIGHT);
        g2.fillRect(x1, y2 - 64, WIDTH, 64);
        
        //Outlines
        g2.setColor(Color.BLACK);
        g2.drawRect(x1, y1 - 64, WIDTH, HEIGHT);
        g2.drawRect(x1 + 1, y1 - 63, WIDTH - 2, HEIGHT - 2);
        
        
        g2.drawRect(x1, y2 - 64, WIDTH, 64);
        g2.drawRect(x1 + 1, y2 - 63, WIDTH - 2, 62);
    }
    
    public void drawObstacleIcon(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(x1/8 + 563, y1/8 + 180, WIDTH/8, HEIGHT/8);
    }
    //Returns the top right corner's y-coordinate
    //@return The y-coordinate of the topmost corner of the obstacle
    public int getY(){
        return y1;
    }
    
    //Returns the x-value of the center of the obstacle
    //@return The center's x-coordinate
    public double getCenterX(){
        return (x1 + x2)/2.0;
    }
    
    //Returns the y-value of the center of the obstacle
    //@return The center's y-coordinate
    public double getCenterY(){
        return (y1 + y2)/2.0;
    }
    
    //Returns the width of the obstacle
    //@return The obsacle width
    public int getWIDTH(){
        return WIDTH;
    }
    
    //Returns the height of the obstacle
    //@return The obstacle height
    public int getHEIGHT(){
        return HEIGHT;
    }
    
}
