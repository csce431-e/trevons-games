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
public class CheckersBoard {
    
    /* 
     * The board alternates between light and dark cells for every row. 
     * The bottom right cell is always light and pieces live on dark squares.
     * The indexing starts at the top and Player1 is at the bottom. 
     */
    public static int BOARDSIZE = 8;
    public static ArrayList< ArrayList<CheckersCell> > board = new ArrayList<>();
    
    
    public CheckersBoard()
    {
        //Create nxn board
        for(int i = 0; i < BOARDSIZE; i++)
        {
            ArrayList<CheckersCell> currentRow = new ArrayList<>();
            for(int j =0; j< BOARDSIZE; j++) 
            {
                currentRow.add(new CheckersCell(Owner.EMPTY,j,i));
            }
            
            board.add(currentRow);
        }
        
        //Place 12 pieces on board for player 1
        for(int i = 0; i<3; i++)
        {
            boolean playerCell;
            
            if(i%2==0)
            {
                playerCell = false;
            }
            else
            {
                playerCell = true;
            }
            
            for(CheckersCell cell: board.get(i))
            {
                if(playerCell) 
                {
                    cell.setOwner(Owner.PLAYER2);
                    Owner.PLAYER2.pieces.add(cell);
                }
                playerCell ^= true;
            }
        }
        
        //Place 12 pieces on board for player 2
        for(int i = 5; i<8; i++)
        {
            boolean playerCell;
            
            if(i%2==0)
            {
                playerCell = false;
            }
            else
            {
                playerCell = true;
            }
            
            for(CheckersCell cell: board.get(i))
            {
                if(playerCell) 
                {
                    cell.setOwner(Owner.PLAYER1);
                    Owner.PLAYER1.pieces.add(cell);
                }
                playerCell ^= true;
            }
        }
        
        printBoard();
    }
    
    public void printBoard()
    {
        System.out.print("  ");
        for(int i=0;i<BOARDSIZE;i++)
        {
            System.out.print(i + " ");
        }
        System.out.print("\n");
        for(ArrayList<CheckersCell> row: board)
        {
            System.out.print(board.indexOf(row) + " ");
            for(CheckersCell cell: row)
            {
                System.out.print(cell.toString() + " ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }
    
    public boolean jump(CheckersMove move)
    {
        CheckersCell s = board.get(move.source.y).get(move.source.x);
        CheckersCell m = board.get(move.middle.y).get(move.middle.x);
        CheckersCell d = board.get(move.dest.y).get(move.dest.x);
        
        Owner opp = s.getOwner();
        s.setOwner(Owner.EMPTY);
        m.setOwner(Owner.EMPTY);
        d.setOwner(opp);
        
        return false;
    }
    
    public boolean makeMove(CheckersMove m)
    {
        if(CheckersCell.isValidMove(m))
        {
            int xDest = m.dest.x;
            int yDest = m.dest.y;
            CheckersCell d = board.get(yDest).get(xDest);
            
            Owner o = m.source.getOwner();
            m.source.setOwner(Owner.EMPTY);
            d.setOwner(o);
            o.pieces.remove(m.source);
            o.pieces.add(d);
            
            System.out.println("Move: " + m.toString());
            printBoard();
            return true;
        }
        
        System.out.println("Invalid Move: " + m.toString());
        return false;
    }
    
    
    public boolean makeMove(CheckersCell source, CheckersCell dest)
    {
        
        CheckersMove m = new CheckersMove(source, dest);
        if(CheckersCell.isValidMove(m))
        {
            Owner o = source.getOwner();
            source.setOwner(Owner.EMPTY);
            dest.setOwner(o);
            o.pieces.remove(m.source);
            o.pieces.add(dest);
            
            System.out.println("Move: " + m.toString());
            printBoard();
            return true;
        }
        System.out.println("Invalid Move: " + m.toString());
        return false;
    }
    
    public boolean jump(CheckersCell source, CheckersCell dest)
    {
        return true;
    }
    
   /* public static void main(String[] args) 
    {
         System.out.println("Start");
         CheckersBoard b = new CheckersBoard();
         CheckersCell src = b.board.get(2).get(1);
         CheckersCell dest = b.board.get(3).get(0);
         b.makeMove(src, dest);
         
         src = b.board.get(3).get(0);
         ArrayList<CheckersMove> moves = src.getMoves();
         b.makeMove(moves.get(0));
         
         //System.out.println(x);
         b.printBoard();
         
         src = b.board.get(5).get(0);
         ArrayList<CheckersMove> j = src.getJumps();
         b.jump(j.get(0));
         
         b.printBoard();
         
    }*/
}
