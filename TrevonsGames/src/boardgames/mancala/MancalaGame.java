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
public class MancalaGame {
    int playerTurn;
    boolean gameOver;
    MancalaBoard board;
    
    public MancalaGame(){
        playerTurn = 1;
        gameOver = false;
        board = new MancalaBoard();
    };
    private int changePlayer(int p) {
        int next;
        next = (p == 1) ? 2 : 1;
        return next;
    }
    public void initMancala() {
        Scanner oScan = new Scanner(System.in);
        playerTurn = 1;
        boolean turnOver = false;
        int column_num=3;
        board.prettyPrint();
        while(!gameOver) {
            System.out.println("Player "+playerTurn+"'s turn: ");
            //board.play(playerTurn-1, column_num, playerTurn-1);
            while(!turnOver) {
                column_num = oScan.nextInt();
                turnOver = board.play(playerTurn, column_num, playerTurn);
            }
            board.prettyPrint();
            turnOver = false;
            
            playerTurn = changePlayer(playerTurn);
            gameOver = board.checkForGameOver();
        }
    };
    
    public static void main(String args[]) {
        MancalaGame g = new MancalaGame();
        
        g.initMancala();
    }
}
