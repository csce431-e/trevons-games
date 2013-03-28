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
        int turnOver = 0;
        int row_num=3;
        int winner_num=0;
        board.prettyPrint();
        while(!gameOver) {
            System.out.println("Player "+playerTurn+"'s turn: ");
            //board.play(playerTurn-1, column_num, playerTurn-1);
            while(turnOver != 1) {
                row_num = oScan.nextInt();
                //System.out.println(board.play(playerTurn, playerTurn-1, row_num));
                turnOver = board.play(playerTurn, playerTurn-1, row_num);
                board.prettyPrint();
                if(turnOver == -1) {
                    System.out.println("That is not a valid move! Pick another row: ");
                }
                else if (turnOver == 0) {
                    System.out.println("You have another turn! Pick another row: ");
                }
                
            }
            board.prettyPrint();
            turnOver = 0;
            
            playerTurn = changePlayer(playerTurn);
            gameOver = board.checkForGameOver(playerTurn);
        }
        winner_num = board.getWinner();
        if(winner_num == 0) {
            System.out.println("It's a tie!");
        }
        else {
            System.out.print("Game Over! Player "+board.getWinner()+" is the winner!");
        }
        
    };
    
    public static void main(String args[]) {
        MancalaGame g = new MancalaGame();
        
        g.initMancala();
    }
}
