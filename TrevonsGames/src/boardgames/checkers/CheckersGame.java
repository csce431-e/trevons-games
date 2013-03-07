/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.checkers;

import java.util.*;
/**
 *
 * @author Trevon
 */
public class CheckersGame {
    
    public CheckersBoard b;
    public Owner turn;
    public boolean turnCompleted;
    
    public void initCheckers()
    {
        b = new CheckersBoard(this);
        turn = Owner.PLAYER1;
        
        //b.printBoard();
        sendMove();
    }
    
    public void sendMove()//(int x1, int y1, int x2, int y2)
    {
        
        for(;;)
        {
            turnCompleted = false;
            System.out.println("Current Turn: " + turn.toString());

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter m or j to move or jump");
            String choice = sc.next();
            
            CheckersMove m = new CheckersMove();
            
            if (choice.equals("m")) 
            {
                System.out.println("Enter coordinates of piece that will be moved: ");

                int x1, y1, x2, y2;

                x1 = sc.nextInt();
                y1 = sc.nextInt();

                System.out.println("Enter coordinates of destination: ");

                x2 = sc.nextInt();
                y2 = sc.nextInt();

                
                m = new CheckersMove(CheckersBoard.board.get(x1).get(y1),
                    CheckersBoard.board.get(x2).get(y2));
                
            } 
            else if (choice.equals("j")) 
            {
                System.out.println("Enter coordinates of piece that will be moved: ");

                int x1, y1, x2, y2, x3, y3;

                x1 = sc.nextInt();
                y1 = sc.nextInt();

                System.out.println("Enter coordinates of jumped piece: ");

                x2 = sc.nextInt();
                y2 = sc.nextInt();

                System.out.println("Enter coordinates of destination: ");

                x3 = sc.nextInt();
                y3 = sc.nextInt();
                
                //CheckersJump j;
                m = new CheckersJump(CheckersBoard.board.get(x1).get(y1), 
                        CheckersBoard.board.get(x2).get(y2), CheckersBoard.board.get(x3).get(y3));
                
                //m = (CheckersMove)j;
                
            }
            
            if (b.makeMove(m)) 
            {
                turnCompleted = true;
            }
            if(turnCompleted == true)
            {
                turn = turn.opposite();
            }
        }
    }

    public static void main(String args[])
    {
        CheckersGame g = new CheckersGame();
        g.initCheckers();
    }
    
}
