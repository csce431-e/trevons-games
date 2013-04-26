/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.Mancala;

import java.util.*;

/**
 *
 * @author Admin
 */
public class MancalaBoard {
    Pit board[][];
    private int cols;
    private int rows;
    
    public MancalaBoard() {
        cols = 2;
        rows = 7;
        board = new Pit[cols][rows];
        for(int i =0; i< cols; i++) {
            board[i][0] = new Pit();
            for(int j = 1; j<rows; j++) {
                board[i][j] = new Pit(3);
            }
        }
    }
    
    public boolean checkForGameOver(int playerTurn) {
        boolean gameOver;
        int sumOfPlayablePebbles = 0;
        for(int i=1; i<rows; i++) {
            sumOfPlayablePebbles += board[playerTurn-1][i].size();
        }
        return sumOfPlayablePebbles == 0;
    }
    
    public void moveAllPieces(int c1, int r1, int c2, int r2) {
        while(board[c1][r1].size()>0){
            board[c2][r2].add(board[c1][r1].remove(0));
        }
    }
    
    public void moveOnePiece(int c1, int r1, int c2, int r2) {
        if(board[c1][r1].size() > 0) {
            board[c2][r2].add(board[c1][r1].remove(0));
        }
    }
    
    public int play(MancalaMove m) {
        return play(m.player, m.column, m.row);
    }
    
    public int play(int p, int c, int r) {
        int validMove = 0;
        int i=c;
        int j = r+1;
        if(p-1 == c && r < rows && r > 0 && board[c][r].size() > 0) {
            while(board[c][r].size() > 0) {
                if ( j == rows && i == p-1) {
                    i = (i == 0) ? 1 : 0;
                    j = 0;
                }
                else if ( j == rows && ((i == p) || (i == p-2))) {
                    i = (i == 0) ? 1 : 0;
                    j = 1;
                }
                moveOnePiece(c,r,i,j);
                if((board[c][r].size() == 0) && (board[i][j].size() == 1) && (i == p-1)) {
                    int opp = (i == 0) ? 1 : 0;
                    if(board[opp][rows-j].size()>0) {
                        moveAllPieces(opp,rows-j,opp,0);  //stopped here!!!
                        moveOnePiece(i,j,opp,0);
                    }
                }
                j++;
            }
            if ( j == 1 && ((p==1 && i == 1) || (p==2 && i == 0))) {
                validMove = 0;
            }
            //else if ((j-1>0) && (i==p-1 && (board[i][j-1].size() == 1))) {
            //    validMove = 0;
            //}
            else {
                validMove = 1;
            }
        }
        else {
            validMove = -1;
        }
        return validMove;
    }
    
    public int AIPlay() {
        Random generator = new Random();
        int validMove = 0;
        int move = generator.nextInt(6)+1;
        while(board[1][move].size()==0) {
            move = generator.nextInt(6)+1;
        }
        int i=1;
        int j = move+1;
        while(board[1][move].size() > 0) {
                if ( j == rows && i == 1) {
                    i = (i == 0) ? 1 : 0;
                    j = 0;
                }
                else if ( j == rows && (i == 0)) {
                    i = (i == 0) ? 1 : 0;
                    j = 1;
                }
                moveOnePiece(1,move,i,j);
                if((board[1][move].size() == 0) && (board[i][j].size() == 1) && (i == 1)) {
                    int opp = (i == 0) ? 1 : 0;
                    if(board[opp][rows-j].size()>0) {
                        moveAllPieces(opp,rows-j,opp,0);  //stopped here!!!
                        moveOnePiece(i,j,opp,0);
                    }
                }
                j++;
            }
        if ( j == 1 && (i == 1)) {
                validMove = 0;
            }
            else {
                validMove = 1;
            }
        return validMove;
    }
    
    public int getNumPiecesInPit(int i, int j) {
        if (i<cols && j < rows) {
            return board[i][j].size();
        }
        else {
            return 0;
        }
    }
    
    public int getWinner() {
        int winner = 0;
        if(board[0][0].size()>board[1][0].size()) {
            winner = 2;
        }
        else if(board[0][0].size()<board[1][0].size()) {
            winner = 1;
        }
        return winner;
    }
    
    public void prettyPrint() {
        String edge = "---------";
        System.out.println(edge);
        System.out.println("|   "+board[0][0].size()+"   |");
        System.out.println(edge);
        for(int i = 1; i<rows; i++) {
            System.out.println("| "+board[0][i].size()+" | "+board[1][rows-i].size()+" |");
            System.out.println(edge);
        }
        System.out.println("|   "+board[1][0].size()+"   |");
        System.out.println(edge);
    }
}
