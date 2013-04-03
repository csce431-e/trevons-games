/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package boardgames.BattleShip;

import java.util.*;
/**
 *
 * @author Mike
 */
public class BattleShip {

    Scanner in = new Scanner(System.in); //Scanner object for basic IO in pre-GUI interface
    
    final int BOARD_W = 10;
    final int BOARD_H = 10;
    ArrayList<Ship> ships = new ArrayList<Ship>();
    
    String[][] gameBoard = new String[BOARD_W][BOARD_H];
    /*
     * game board tiles:
     *      "o" -> ocean
     *      posative number -> index in ships vector of the ship in that location (no hit)
     *      negative number -> index in ships vector of the ship in that location (hit)
     */
    
    public BattleShip() {
       //nothing to construct at this time
    }
    
     /**
     * Ships:
     * 1 length 2
     * 2 length 3
     * 1 length 4
     * 1 length 5
     */
    public void init(){
        //set up game board to have only "ocean" tiles
        for(int x=0; x<BOARD_W; x++){
            for(int y = 0; y<BOARD_H; y++){
                gameBoard[x][y]="o";
            }
    }
        
        //add all the ships from largest to smallest
        ships.add( new Ship(5,"Battleship"));
        ships.add( new Ship(4,"Cruiser"));
        ships.add( new Ship(3,"Submarine"));
        ships.add( new Ship(3,"Destroyer"));
        ships.add( new Ship(2,"PT Boat"));
        
        //create a method to place ships so when we move to GUI we only have to rewrite the method
        //placeShips();
        
        
    }
    
    public int placeShips(Ship.point p, int shipNo, String orientation){
        //this method will be remade for GUI version
        int x=p.x, y=p.y;
        //String orientation = "NORTH";
        int i = shipNo;
        //for(int i=0; i<ships.size();i++){
            System.out.println(i + " " + ships.size());
            System.out.println("Ship: " + ships.get(i).name() + 
                               ". Size: " + ships.get(i).size());
            
            System.out.println("X coordinate: \n" + x);
            //x=in.nextInt();
            System.out.println("Y coordinate: \n" + y);
            //y=in.nextInt();
            System.out.println("Orientation: \n" + orientation);
            //orientation=in.next();
            
            //System.out.println(orientation);
            
            //make sure ships are in bounds
            String locationOK = checkShipInBounds(i, x, y, orientation);
            if(locationOK != "OK"){
                System.out.println("Cannot place ship at that location because " + locationOK + '.');
                return -1;
            }
            else{
                switch(orientation.charAt(0)){
                    case 'N':
                        System.out.println("i = " + i);
                        for(int o=0;o<ships.get(i).size();o++){
                            if(!gameBoard[x][y+o].equals("o")){
                                System.out.println("Collision!  A boat is already in one of the locations");return -1;

                                //break;
                            }
                        }
                        ships.get(i).placeShip(new Ship.point(x,y), orientation);
                        for(int o=0;o<ships.get(i).size();o++){
                            if(gameBoard[x][y+o].equals("o")){

                                gameBoard[x][y+o] = i + "";
                            }
                        }

                        break;

                    case 'E':
                        for(int o=0;o<ships.get(i).size();o++){
                            if(!gameBoard[x+o][y].equals("o")){
                                System.out.println("Collision! " + gameBoard[x+o][y] +  " A boat is already in one of the locations " + x + " " + y); return -1;

                                //break;
                            }
                        }
                        ships.get(i).placeShip(new Ship.point(x,y), orientation);
                        for(int o=0;o<ships.get(i).size();o++){
                            if(gameBoard[x+o][y].equals("o")){

                                gameBoard[x+o][y] = i + "";
                            }
                        }


                        break;

                    case 'S':
                        for(int o=0;o<ships.get(i).size();o++){
                            if(!gameBoard[x][y-o].equals("o")){
                                System.out.println("Collision!  A boat is already in one of the locations"); return -1;

                                //break;
                            }
                        }
                        ships.get(i).placeShip(new Ship.point(x,y), orientation);
                        for(int o=0;o<ships.get(i).size();o++){
                            if(gameBoard[x][y-o].equals("o")){

                                gameBoard[x][y-o] = i + "";
                            }
                        }

                        break;

                    case 'W':
                        for(int o=0;o<ships.get(i).size();o++){
                            if(!gameBoard[x-o][y].equals("o")){
                                System.out.println("Collision!  A boat is already in one of the locations"); return -1;

                                //break;
                            }
                        }
                        ships.get(i).placeShip(new Ship.point(x,y), orientation);
                        for(int o=0;o<ships.get(i).size();o++){
                            if(gameBoard[x-o][y].equals("o")){

                                gameBoard[x-o][y] = i + "";
                            }
                        }

                        break;
                    default:
                        System.out.println("Error");
                        return -1;
                        //break;

                }
            }
            
        //}
        return 0;
    }
    
    public String checkShipInBounds(int shipIndex, int x, int y, String orientation){
        //make sure orientation is an accepted direction
        if(!orientation.equals("NORTH")  && !orientation.equals("EAST") && !orientation.equals("SOUTH") && !orientation.equals("WEST")){ return "incorrect location string " + orientation;}
        
        //make sure the ship in fully on the board
        if(orientation.equals("NORTH")){
            if( ships.get(shipIndex).size()+y > BOARD_H ) return "OOB NORTH " + ships.get(shipIndex).size()+y;
        }
        if(orientation.equals("EAST")){
            if( ships.get(shipIndex).size()+x > BOARD_W ) return "OOB EAST " + ships.get(shipIndex).size()+x;
        }
        if(orientation.equals("SOUTH")){
            if( y-ships.get(shipIndex).size()+1 < 0 ) return "OOB SOUTH " + (y-ships.get(shipIndex).size());
        }
        if(orientation.equals("WEST")){
            if( x-ships.get(shipIndex).size()+1 < 0 ) return "OOB WEST " +  (y-ships.get(shipIndex).size());
        }
        
        return "OK";
    }
    
    public void showBoard(){
        for(int y=0; y<BOARD_H; y++){
            for(int x=0;x<BOARD_W;x++){
                System.out.print(gameBoard[x][BOARD_H -(y+1)] + " ");
            
            }
            System.out.println();
        }
        
    }
    
    public String[][] getBoard(){
        return gameBoard;
    }
    
    public String attackSpot(int x, int y){
        //y=BOARD_H - y -1; //compensate for the board being the way it is NEVERMIND
        
        String boatHit = gameBoard[x][y];
        
        
        if(boatHit.equals("o")){gameBoard[x][y] = "M"; return "MISS";}
        else if(Character.isDigit(boatHit.charAt(0))){
            try{
                gameBoard[x][y] = "X";
                Ship s = ships.get(Integer.parseInt(boatHit));
                int hitLoc=0;
                switch(s.getOrientation().charAt(0)){
                    case 'N':
                        hitLoc = y-s.point().getY();
                        break;
                    case 'E':
                        hitLoc = x-s.point().getX();
                        break;
                    case 'S':
                        hitLoc = s.point().getY()-y;
                        break;
                    case 'W':
                        hitLoc = s.point().getX()-x;
                        break;
                }
                
                return s.damage(hitLoc);
            }catch(Ship.NotInShipException e){
                System.out.println(e.what());
            }
            
        }
        return "X";
    }
    
    public boolean checkWin(){
        
        for(int i=0; i<ships.size();i++){
            if(ships.get(i).sunk() == false){
                return false;
            }
        }
        return true;
    }
    
    public void PlayGame(){
        init();
        //placeShips();
        showBoard();
        int game =1;
        int x, y;
        while(game==1){
            System.out.println("X coordinate: ");
            x=in.nextInt();
            System.out.println("Y coordinate: ");
            y=in.nextInt();
            
            String attack = attackSpot(x,y);
            System.out.println(attack);
            showBoard();
            if(attack.contains("Destroyed")){
                //updateShips(); //will write this method later.  used to show remaining ships
            }
            if(checkWin()){
                System.out.println("All of your ships have been sunk!  YOU LOSE");
                game=0;
            }
        }
        
        
    }
}