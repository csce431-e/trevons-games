/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.checkers;

import java.util.ArrayList;

/**
 *
 * @author Trevon
 */
public class CheckersMove {
    
    public static final int TOPOFBOARD = 0;
    public static final int BOTOFBOARD = 7;
    CheckersCell source;
    CheckersCell dest;
    
    static boolean jump;
    //ArrayList< ArrayList< CheckersCell>> b = CheckersBoard.board;
    CheckersBoard b;
    
    public CheckersMove()
    {
        jump = false;
    }
    
    public CheckersMove(CheckersCell src, CheckersCell d, CheckersBoard board)
    {
        source = src;
        dest = d;
        jump = false;
        b = board;
    }
    
    public boolean updateBoard()
    {
        CheckersCell src = b.board.get(source.x).get(source.y);
        CheckersCell d = b.board.get(dest.x).get(dest.y);
        //CheckersCell src = b.getCell(source.x, source.y);
        //CheckersCell d = b.getCell(dest.x, dest.y);
        
        if(isValidMove())
        {
            Owner o = src.getOwner();
            src.setOwner(Owner.EMPTY);
            d.setOwner(o);
            o.pieces.remove(src);
            o.pieces.add(d);
            
            if(o == Owner.PLAYER1)
            {
                if(d.x == TOPOFBOARD)
                {
                    d.setKing(true);
                }
            }
            else if(o == Owner.PLAYER2)
            {
                if(d.x == BOTOFBOARD)
                {
                    d.setKing(true);
                }
            }
            
            if(src.isKing())
            {
                d.setKing(true);
                src.setKing(false);
            }
            
            b.anotherJump = false;
            testBoard();
            System.out.println("Move: " + this.toString());
            return true;
        }
        
        
        
        return false;
    }
    
    public void testBoard()
    {
        for(int i=0;i<CheckersBoard.BOARDSIZE;i++)
        {
            for(int j=0;j<CheckersBoard.BOARDSIZE;j++)
            {
                CheckersCell c = b.board.get(i).get(j);
                if(c.x != i || c.y != j)
                {
                    System.out.println("You changed the board");
                }
            }
        }
    }
    
    //TODO make sure this only allows legal moves
    public boolean isValidMove() {
 
        if (source.x < 0 || source.x > 8) 
        {
            return false;
        }

        if (dest.x < 0 || dest.x > 8) 
        {
            return false;
        }
        
        if (source.y < 0 || source.y > 8) 
        {
            return false;
        }

        if (dest.y < 0 || dest.y > 8) 
        {
            return false;
        }
        
        //CheckersJump needs slightly different test
        if(this instanceof CheckersJump)
        {
             if (dest.x > source.x + 2) 
            {
                return false;
            }
             
            if (dest.x < source.x - 2) 
            {
                return false;
            }
           
        }
        else
        {
            if (dest.y > source.y + 1) 
            {
                return false;
            }

            if (dest.y < source.y - 1) 
            {
                return false;
            }
        }
        
        if(dest.x == source.x)
        {
            return false;
        }
        
        if(dest.y == source.y)
        {
            return false;
        }
        
        Owner o = source.getOwner();
        //CheckersCell src = b.board.get(source.x).get(source.y);
        
        if (o == Owner.EMPTY) 
        {
            return false;
        }
        else if(o == Owner.PLAYER1 && !source.isKing())
        {
            if(dest.x >= source.x)
            {
                return false;
            }
        }
        else if(o == Owner.PLAYER2 && !source.isKing())
        {
            if(dest.x <= source.x)
            {
                return false;
            }
        }
        
        if (dest.getOwner() != Owner.EMPTY) 
        {
            return false;
        }

        return true;
    }
 
    @Override
    public boolean equals(Object obj)
    {
        CheckersMove m = (CheckersMove) obj;
        if(!(m.source.equals(this.source)))
        {
            return false;
        }
        if(!(m.dest.equals(this.dest)))
        {
            return false;
        }         
        
        return true;
    }
    
    public boolean equals(CheckersMove m)
    {
        if(!(m.source.equals(this.source)))
        {
            return false;
        }
        if(!(m.dest.equals(this.dest)))
        {
            return false;
        }         
        
        return true;
    }
    
    @Override
    public String toString()
    {
        String s = source.x + "," + source.y;
        String d = dest.x + "," + dest.y;
        return s + " to " + d;
    }
}
