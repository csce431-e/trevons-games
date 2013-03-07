/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.connectfour;

import java.util.*;

/**
 *
 * @author Jonathan
 */
public class ConnectFourBoard {
    int numColumn;
    int numRow;
    
    ArrayList< ArrayList< Piece> > board;
    //Piece board[][];
    
    public ConnectFourBoard() {
        numColumn = 7;
        numRow = 6;
        //board = new Piece[numRow][numColumn];
        board = new ArrayList<>();
        
        for(int row=0; row<=numRow; row++) {
            ArrayList<Piece> rows = new ArrayList<Piece>();
            for (int col=0; col<=numColumn; col++) {
                Piece piece = new Piece();
                
                rows.add(piece);
            }
            board.add(rows);
        }
    }
    
    
    public int win() {
        
        int winner = 0;
        // Check horizontals
        for(int row =0; row<=numRow; row++){
            for(int col= 0; col<=numColumn; col++) {
                
                if(board.get(row).get(col).pieceColor() > 0 && board.get(row).get(col).pieceColor()==board.get(row).get(col+1).pieceColor() && board.get(row).get(col).pieceColor() == board.get(row).get(col+2).pieceColor() && board.get(row).get(col).pieceColor() == board.get(row).get(col+3).pieceColor()){
                    winner = board.get(row).get(col).pieceColor();
                }
            }
        }
        
        // Check verticals
        for(int col =0; col<=numColumn; col++){
            for(int row= 0; row<=numRow; row++) {
                if(board.get(row).get(col).pieceColor() > 0 && board.get(row).get(col).pieceColor()==board.get(row+1).get(col).pieceColor() && board.get(row).get(col).pieceColor() == board.get(row+2).get(col).pieceColor() && board.get(row).get(col).pieceColor() == board.get(row+3).get(col).pieceColor()){
                    winner = board.get(row).get(col).pieceColor();
                }
            }
        }
        
        // Check positive diagonal
        for(int row=0; row<=numRow-3; row++) {
            for(int col=0; col<=numColumn-3; col++){
                if(board.get(row).get(col).pieceColor() > 0 && board.get(row).get(col).pieceColor()==board.get(row+1).get(col+1).pieceColor() && board.get(row).get(col).pieceColor() == board.get(row+2).get(col+2).pieceColor() && board.get(row).get(col).pieceColor() == board.get(row+3).get(col+3).pieceColor()){
                    winner = board.get(row).get(col).pieceColor();
                }
            }
        }
        
        // Check negative diagonal
        for(int row=numRow-1; row>=3; row--) {
            for(int col=0; col<=numColumn-3; col++) {
                if(board.get(row).get(col).pieceColor() > 0 && board.get(row).get(col).pieceColor()==board.get(row-1).get(col+1).pieceColor() && board.get(row).get(col).pieceColor() == board.get(row-2).get(col+2).pieceColor() && board.get(row).get(col).pieceColor() == board.get(row-3).get(col+3).pieceColor()){
                    winner = board.get(row).get(col).pieceColor();
                }
            }
        }
        
        return winner;
    }

    void printBoard() {
        System.out.println("Start");
        int currentPlace;
        for(int row=numRow-1; row>=0; row--) {
            for(int col=0; col<=numColumn-1; col++) {
                currentPlace = board.get(row).get(col).pieceColor();
                
                switch(currentPlace){
                    case 0:
                        System.out.print("[ ]");
                        break;
                    case 1:
                        System.out.print("[@]");
                        break;
                    case 2:
                        System.out.print("[O]");
                        break;
                }
                
            }
            
            System.out.println("");
        }
        
        System.out.println(" 1  2  3  4  5  6  7 ");
    }
    
    void setPiece(int row, int col, int color) {
        Piece piece = new Piece(color);
        board.get(row).set(col, piece);
    }
}
