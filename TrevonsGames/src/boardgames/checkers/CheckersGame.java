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
    public boolean AI;
    
    
    public void initCheckers()
    {
        AI = false;
        b = new CheckersBoard(this);
        turn = Owner.PLAYER1;
        
        //b.printBoard();
        sendMove();
    }
    
    public void sendMove()//(int x1, int y1, int x2, int y2)
    {
        System.out.println("Enter 0 for player vs player. Enter 1 for player vs AI:");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        
        for(;;)
        {
            if(option == 1 && turn == Owner.PLAYER2)
            {
                sendAIMove();
            }
            else
            {
                turnCompleted = false;
                System.out.println("Current Turn: " + turn.toString());



                System.out.println("Enter m or j to move or jump");
                String choice = sc.next();

                CheckersMove m = new CheckersMove();

                if (choice.equals("m")) {
                    System.out.println("Enter coordinates of piece that will be moved: ");

                    int x1, y1, x2, y2;

                    x1 = sc.nextInt();
                    y1 = sc.nextInt();

                    System.out.println("Enter coordinates of destination: ");

                    x2 = sc.nextInt();
                    y2 = sc.nextInt();


                    m = new CheckersMove(CheckersBoard.board.get(x1).get(y1),
                            CheckersBoard.board.get(x2).get(y2));

                } else if (choice.equals("j")) {
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

                if (b.makeMove(m)) {
                    turnCompleted = true;
                }
                
            }
            if (turnCompleted == true) 
            {
                turn = turn.opposite();
            }
        }
    }
    
    public void sendAIMove()
    {
        ArrayList<CheckersMove> l = b.getAllMoves();
        Iterator<CheckersMove> il = l.iterator();
        CheckersMove m = il.next();
        
        while(il.hasNext() && !b.makeMove(m))
        {
            m = il.next();
        }
    }
    
        

    public static void main(String args[])
    {
        CheckersGame g = new CheckersGame();
        g.initCheckers();
    }
    
}
