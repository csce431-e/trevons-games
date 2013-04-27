/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.checkers;

import java.util.*;
import java.math.*;
/**
 *
 * @author Trevon
 */
public class CheckersGame {
    
    public CheckersBoard b;
    public Owner turn;
    public boolean turnCompleted;
    public boolean AI;
    //public boolean gui;
    public Owner winner;
    String status;
    
   
  /*  public CheckersGame()
    {
        
        initCheckers(false);
        sendMove();
    }*/
    
    public void initCheckers()
    {
        status = "";
        AI = false;
        b = new CheckersBoard();
        b.initGame(this);
        turn = Owner.PLAYER1;
        
        //b.printBoard();
        //sendMove();
    }
    
    public void initCheckers(boolean gui)
    {
        status = "";
        AI = false;
        b = new CheckersBoard();
        b.initGame(this);
        turn = Owner.PLAYER1;  
        if(!gui)
        {
            sendMove();
        }
    }
    
    public boolean checkGameOver()
    {
        winner = b.isGameOver();
        if(winner != Owner.EMPTY)
        {
            endGame();
            return true;
        }
         return false;
    }
    
    public void setStatus(String s)
    {
        status = s; 
        System.out.println(s);
    }
    
    public String readStatus()
    {
        return status;
    }
    
    public void sendMove()//(int x1, int y1, int x2, int y2)
    {
        System.out.println("=-_-= Checkers =-_-=");
        System.out.println("0 Player vs Player");
        System.out.println("1 Player vs AI");
        System.out.println("=_=-_-=-_==_-=-_-=_=");
        System.out.print("Enter choice: ");
        
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        
        b.printBoard();
        for(;;)
        {
  
            if(checkGameOver())
            {
                break;
            }
            
            System.out.println("Current Turn: " + turn.toString());
            turnCompleted = false;
            
            if(option == 1 && turn == Owner.PLAYER2)
            {
                AI = true;
                sendAIMove();
                if(!b.anotherJump)
                {
                    turnCompleted = true;
                }
            }
            else
            {
                CheckersMove m = requestMove();

                if (b.makeMove(m) && !b.anotherJump) 
                {
                    turnCompleted = true;
                }
            }
            if (turnCompleted == true) 
            {
                turn = turn.opposite();
            }
        }
    }
     
    public CheckersMove requestMove()
    {
        if(AI && turn == Owner.PLAYER2)
        {
            sendAIMove();
        }
        /*
        System.out.println("Enter m or j to move or jump");
        Scanner sc = new Scanner(System.in);
        String choice = sc.next();
        */
        Scanner sc = new Scanner(System.in);
        CheckersMove m = new CheckersMove();

        //if (choice.equals("m")) {

        int x1 = -1;
        int x2 = -1;
        int y1 = -1;
        int y2 = -1;

        System.out.println("Enter coordinates of piece that will be moved: ");
        while(!sc.hasNextInt())
        {
            sc.next();
        }
        x1 = sc.nextInt();
        
        while(!sc.hasNextInt())
        {
            sc.next();
        }
        y1 = sc.nextInt();  
        
        System.out.println("Enter coordinates of destination: ");
        while(!sc.hasNextInt())
        {
            sc.next();
        }
        x2 = sc.nextInt();
        
        while(!sc.hasNextInt())
        {
            sc.next();
        }
        y2 = sc.nextInt(); 
      
        if(!isInBounds(x1,y1) || !isInBounds(x2,y2))
        {
            return new CheckersMove(b.board.get(0).get(0),
                    b.board.get(0).get(0),b);
        }

        if(Math.abs(x2-x1) > 1)
        {
            CheckersCell s = b.board.get(x1).get(y1);
            CheckersCell d = b.board.get(x2).get(y2);
            m = new CheckersJump(s, s.getMid(s,d),d,b);
        }
        else 
        {
            m = new CheckersMove(b.board.get(x1).get(y1),
                b.board.get(x2).get(y2),b);
        }


        /*} else if (choice.equals("j")) {
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
        }*/
        return m;
                    //m = (CheckersMove)j;
    }
    public CheckersMove sendAIMove()
    {
       
        ArrayList<CheckersMove> l = b.getAllMoves();
  
        Random generator = new Random();
        CheckersMove m = new CheckersMove();
        boolean moveMade = false;
        if(!l.isEmpty())
        {
            while(!moveMade)
            {
                int index = generator.nextInt(l.size());
                m = l.get(index);
                moveMade = b.makeMove(m);
            }
        }
        return m;
        /*Iterator<CheckersMove> il = l.iterator();
        CheckersMove m = il.next();
        
        boolean moveMade = b.makeMove(m);
        
        while(!moveMade && il.hasNext())
        {
            m = il.next();
            moveMade = b.makeMove(m);
        }*/
    }
    
    public static void main(String args[])
    {
        CheckersGame g = new CheckersGame();
        g.initCheckers();
        g.sendMove();
    }
    
    public void endGame()
    {
        setStatus("Game Over.\nCongratulations,\n " + winner + " wins!");
    }
    
    public boolean equals(CheckersGame g)
    {
        return true;
    }
    
    public boolean isInBounds(int x, int y)
    {
        if(x <0 || x > CheckersBoard.BOARDSIZE)
        {
            return false;
        }
        if(y <0 || y > CheckersBoard.BOARDSIZE)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}

