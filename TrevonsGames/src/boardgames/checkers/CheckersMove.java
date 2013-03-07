/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.checkers;

/**
 *
 * @author Trevon
 */
public class CheckersMove {
    
    CheckersCell source;
    CheckersCell dest;
    
    static boolean jump;
    
    public CheckersMove()
    {
        jump = false;
    }
    
    public CheckersMove(CheckersCell src, CheckersCell d)
    {
        source = src;
        dest = d;
        jump = false;
    }
    
    public boolean updateBoard(CheckersBoard b)
    {
        CheckersCell src = b.board.get(source.x).get(source.y);
        CheckersCell d = b.board.get(dest.x).get(dest.y);
        
        if(isValidMove())
        {
            Owner o = src.getOwner();
            src.setOwner(Owner.EMPTY);
            d.setOwner(o);
            o.pieces.remove(src);
            o.pieces.add(d);
            
            System.out.println("Move: " + this.toString());
            return true;
        }
        
        return false;
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
        
        Owner o = source.getOwner();
        
        if (o == Owner.EMPTY) 
        {
            return false;
        }
        else if(o == Owner.PLAYER1)
        {
            if(dest.x >= source.x)
            {
                return false;
            }
        }
        else if(o == Owner.PLAYER2)
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
    public String toString()
    {
        String s = source.x + "," + source.y;
        String d = dest.x + "," + dest.y;
        return s + " to " + d;
    }
}
