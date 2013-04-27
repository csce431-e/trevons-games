/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.checkers;

/**
 *
 * @author Trevon
 */
public class CheckersJump extends CheckersMove {
    
    CheckersCell middle;
    static boolean jump;
    CheckersBoard b;
    
    public CheckersJump()
    {
        super();
        jump = true;
    }
    
    public CheckersJump(CheckersCell s, CheckersCell m, CheckersCell d, CheckersBoard board)
    {
        source = s;
        middle = m;
        dest = d;
        jump = true;
        b = board;
    }
    
    @Override
    public boolean updateBoard()
    {
        CheckersCell src = b.board.get(source.x).get(source.y);
        CheckersCell mid = b.board.get(middle.x).get(middle.y);
        CheckersCell d = b.board.get(dest.x).get(dest.y);
        
        if(isValidMove() && isValidJump())
        {
            Owner o = src.getOwner();
            src.setOwner(Owner.EMPTY);
            d.setOwner(o);
            mid.setOwner(Owner.EMPTY);
            
            Owner opponent = o.opposite();
            
            o.pieces.remove(src);
            opponent.pieces.remove(mid);
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
            
            mid.setKing(false);
            
            System.out.println("Move: " + this.toString());
            
            b.anotherJump = true;
    
            return true;
        }
       
        return false;
    }
    
    public boolean isValidJump()
    {
        Owner opponent = source.getOwner().opposite();
        
        if(middle.getOwner() != opponent)
        {
            return false;
        }
        if(dest.getOwner() != Owner.EMPTY)
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        CheckersJump j = (CheckersJump)obj;
        if(!super.equals(j))
        {
            return false;
        }
        if(!this.middle.equals(j.middle))
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean equals(CheckersMove m)
    {
        CheckersJump j = (CheckersJump)m;
        if(!super.equals(j))
        {
            return false;
        }
        if(!this.middle.equals(j.middle))
        {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString()
    {
        /*
        String s = source.x + "," + source.y;
        String m = middle.x + "," + middle.y;
        String d = dest.x + "," + dest.y;
        return s + " over " + m + " to " + d;
         */
        return "" + source.x + source.y + middle.x + middle.y + dest.x + dest.y;
    }
}
