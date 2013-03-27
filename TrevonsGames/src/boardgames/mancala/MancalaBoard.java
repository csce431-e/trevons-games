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
    
    public boolean checkForGameOver() {
        boolean gameOver = false;
        return gameOver;
    }
    
    public void moveAllPieces(int c1, int r1, int c2, int r2) {
        while(board[c1][r1].size()>0){
            board[c2][r2].add(board[c1][r1].remove(0));
        }
    }
    
    public void moveOnePiece(int c1, int r1, int c2, int r2) {
            board[c2][r2].add(board[c1][r1].remove(0));
    }
    
    public boolean play(int p, int c, int r) {
        boolean validMove = true;
        if(p == c) {
            int i=r+1;
            int j = c;
            while(board[c][r].size() > 0) {
                moveOnePiece(c,r,i,j);
            }
        }
        else {
            validMove = false;
        }
        return validMove;
    }
    
    public void prettyPrint() {
        String edge = "------";
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
