/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.BattleShip;

/**
 *
 * @author Mike
 */
public class Ship {
    /*  Each ship will have a size and optionally a name (like carrier or something)
    *   The status array will determine if a ship has been damaged, with 1 being a damaged spot
    *   This can be used to show different models for damaged ships later.
    *   orientation is the direction that the ship is facing, NORTH, EAST, SOUTH, WEST
    *   location is the initial point of a ship.  A ship with location (0,0) and orientation NORTH will
    *   be a ship pointing up from (0,0) and ending at (0,size)
    */
    private int size;
    private String name;
    private point location;
    private String orientation;
    private boolean sunk;
    int[] status;
    
    //error class just in case we magically hit a ship outside its size
    public class NotInShipException extends Exception{
       String out;
        public NotInShipException(String ship){
            out = (ship + " has registered a hit outside the bounds of the ship.");
         }   
            public String what(){return out;}
        
    };
    
    static class point{
        Integer x, y;
        
        public point(){
            x=0;
            y=0;
        }
        
        public point(int xCoord, int yCoord){
            x=xCoord;
            y=yCoord;
        }
        
        int getX(){return x;}
        
        int getY(){return y;}
    }
    
    public Ship(){
        size = 3;
        name = "Ship";
        status=new int[size];
        sunk=false;
        location=new point();
        orientation = "";
    }
    
    public Ship(int s){
        size = s;
        name = "Ship";
        status=new int[size];
        sunk=false;
        location= new point();
        orientation = "";
    }
    
    public Ship(int s, String n){
        size = s;
        name = n;
        status=new int[size];
        sunk=false;
        location=new point();
        orientation = "";
    }
    
    public int size(){ return size;}
    
    public String name(){return name;}
    
    public point point(){return location;}
    
    public void placeShip(point p, String orient){
        location=p;
        orientation=orient;
    }
    
    public int getX(){ return location.x;}
    
    public int getY(){ return location.y;}
    
    public String getOrientation(){ return orientation;}
    
    public boolean sunk(){return sunk;}
    
    public String damage(int location) throws NotInShipException{
        if(location < size){
            status[location] = 1;
        }
        else{ throw new NotInShipException(name);} //if we get a locaton outside of the ship's size, throw error
        
        for(int i=0; i<size;i++){
                if(status[i]!=1){ return "Damaged " + name;}
        }
        sunk=true;
        return "Destroyed " + name;
        
    }
}
