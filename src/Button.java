/*
 * 2D Shootout
 * Copyright(c) 2013 Christopher Hittner
 * Written using Game Engine Alpha-G1-Java
 * 
 * All rights to this code and any other classes of the engine or game are the 
 * property of Christopher Hittner.
*/

public class Button {
    int x1, y1, x2, y2;
    String pass, fail;
    public Button(int a, int b, int c, int d, String e, String f){
        x1 = a;
        x2 = x1 + b;
        y1 = c;
        y2 = y1 + d;
        pass = e;
        fail = f;
    }
    public boolean MouseOver(int x, int y){
        if(x >= x1 && x <= x2 && y >= y1 && y <= y2){
            return true;
        } else {
            return false;
        }
        
    }
    public String MouseClick(int x, int y){
        if(x >= x1 && x <= x2 && y >= y1 && y <= y2){
            return pass;
        } else {
            return fail;
        }
    }
}
