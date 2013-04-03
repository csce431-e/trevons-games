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
    
    int playerColor = 1, opponentColor =2;
    int colOne, colTwo, colThr, colFor, colFiv, colSix, colSev;
    boolean moveComplete = false, gameOver = false;
    int gameTurn = 1;
    
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
    
    public int getTurn() {
        return gameTurn;
    }
    
    public int getColor(int row, int column) {
        return board.get(row).get(column).pieceColor();
    }
    
    public int rowHeight(int column) {
        int height = 0;
        switch(column) {
            case 1:
                height = colOne;
                break;
            case 2:
                height = colTwo;
                break;
            case 3:
                height = colThr;
                break;
            case 4:
                height = colFor;
                break;
            case 5:
                height = colFiv;
                break;
            case 6:
                height = colSix;
                break;
            case 7:
                height = colSev;
                break;
        }
        
        return height;
    }
    
    // Select column to place piece, and make the move
    public boolean move(int turn, int column) {
        int colMove = column;
        int turnColor;
        
        String nextPlayer = "@";
        
        moveComplete = false;
        
        if((turn%2) == 0){
            turnColor=opponentColor;
        } else {
            turnColor=playerColor;
        }
        
        switch((turn%2)){
            case 0:
                nextPlayer = "@";
                break;
            case 1:
                nextPlayer = "O";
                break;
        }
        
//        Scanner scanner = new Scanner(System.in);
//        
        System.out.println("Please select column to place piece player " + nextPlayer + ": ");
//        colMove = scanner.nextInt();
        
        switch(colMove){
            case 1:
                if(colOne == 6) {
                    System.out.println("The column is full. Select another.");
                } else {
                    setPiece(colOne, colMove-1, turnColor);
                    colOne++;
                    moveComplete = true;
                    gameTurn++;
                }
                break;
            case 2:
                if(colTwo == 6) {
                    System.out.println("The column is full. Select another.");
                } else {
                    setPiece(colTwo, colMove-1, turnColor);
                    colTwo++;
                    moveComplete = true;
                    gameTurn++;
                }
                break;
            case 3:
                if(colThr == 6) {
                    System.out.println("The column is full. Select another.");
                } else {
                    setPiece(colThr, colMove-1, turnColor);
                    colThr++;
                    moveComplete = true;
                    gameTurn++;
                }
                break;
            case 4:
                if(colFor == 6) {
                    System.out.println("The column is full. Select another.");
                } else {
                    setPiece(colFor, colMove-1, turnColor);
                    colFor++;
                    moveComplete = true;
                    gameTurn++;
                }
                break;
            case 5:
                if(colFiv == 6) {
                    System.out.println("The column is full. Select another.");
                } else {
                    setPiece(colFiv, colMove-1, turnColor);
                    colFiv++;
                    moveComplete = true;
                    gameTurn++;
                }
                break;
            case 6:
                if(colSix == 6) {
                    System.out.println("The column is full. Select another.");
                } else {
                    setPiece(colSix, colMove-1, turnColor);
                    colSix++;
                    moveComplete = true;
                    gameTurn++;
                }
                break;
            case 7:
                if(colSev == 6) {
                    System.out.println("The column is full. Select another.");
                } else {
                    setPiece(colSev, colMove-1, turnColor);
                    colSev++;
                    moveComplete = true;
                    gameTurn++;
                }
                break;
        }
        
        return moveComplete;
    }
    
    public boolean checkFull() {
        if(colOne == 6 && colTwo == 6 && colThr == 6 && colFor == 6 && colFiv == 6 && colSix == 6 && colSev == 6) {
            return true;
        }
        else {
            return false;
        }
    }
    
    void setPiece(int row, int col, int color) {
        Piece piece = new Piece(color);
        board.get(row).set(col, piece);
    }
}
