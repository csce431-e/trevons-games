/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.connectfour;

import java.util.Scanner;

/**
 *
 * @author Jonathan
 */
public class ConnectFourGame {
    
    // Used to keep track of what color each player is playing as
    int playerColor = 1, opponentColor =2;
    int colOne, colTwo, colThr, colFor, colFiv, colSix, colSev;
    ConnectFourBoard b;
    boolean moveComplete = false, gameOver = false;
    
    public void cfGameStart() {
        b = new ConnectFourBoard();
        
        for(int row=0; row<b.numRow; row++) {
            for (int col=0; col<b.numColumn; col++) {
                b.setPiece(row, col, 0);
            }
        }
        
        b.printBoard();
        
        int turn = 1;
        int winner = 0;
        while(!gameOver) {
            moveComplete = b.move(turn, 0);
            
            b.printBoard();
            
            if (moveComplete){
                turn++;
            }
            
            winner = b.win();
            
            System.out.println("Winner value is " + winner);
            
            if(winner==1) {
                System.out.println("Game Over! Black won!");
                gameOver = true;
            }
            if(winner==2) {
                System.out.println("Game over! Red won!");
                gameOver = true;
            }
            
            if(b.checkFull()) {
                System.out.println("Game over! No moves left! It's a tie!");
                gameOver = true;
            }
        }
    }
}
