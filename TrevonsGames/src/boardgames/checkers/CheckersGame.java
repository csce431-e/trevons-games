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
    
    CheckersBoard b;
    Owner turn;
    
    public void initCheckers()
    {
        b = new CheckersBoard();
        turn = Owner.PLAYER1;
        
        b.printBoard();
        sendMove();
    }
    
    public void sendMove()//(int x1, int y1, int x2, int y2)
    {
        
        for(;;)
        {
            System.out.println("Current Turn: " + turn.toString());

            Scanner sc = new Scanner(System.in);

            System.out.println("Enter m or j to move or jump");
            String choice = sc.next();
            if (choice.equals("m")) 
            {
                System.out.println("Enter coordinates of piece that will be moved: ");

                int x1, y1, x2, y2;

                x1 = sc.nextInt();
                y1 = sc.nextInt();

                System.out.println("Enter coordinates of destination: ");

                x2 = sc.nextInt();
                y2 = sc.nextInt();

                //CheckersMove m = new CheckersMove(b.board.get(y1).get(x1), b.board.get(y2).get(x2));
                
                b.makeMove(b.board.get(y1).get(x1),  b.board.get(y2).get(x2));
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

                CheckersMove m = new CheckersMove(b.board.get(y1).get(x1), b.board.get(y2).get(x2), b.board.get(y3).get(x3));
                b.makeMove(m);
            }
        }
        
        
        
        
        
    }

    public static void main(String args[])
    {
        CheckersGame g = new CheckersGame();
        g.initCheckers();
    }
    
}
