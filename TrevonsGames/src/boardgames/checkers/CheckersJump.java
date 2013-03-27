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
    
    public CheckersJump()
    {
        super();
        jump = true;
    }
    
    public CheckersJump(CheckersCell s, CheckersCell m, CheckersCell d)
    {
        source = s;
        middle = m;
        dest = d;
        jump = true;
    }
    
    @Override
    public boolean updateBoard(CheckersBoard b)
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
            
            o.pieces.remove(src);
            o.pieces.add(d);
            
            System.out.println("Move: " + this.toString());
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
        String s = source.x + "," + source.y;
        String m = middle.x + "," + middle.y;
        String d = dest.x + "," + dest.y;
        return s + " over " + m + " to " + d;
    }
}
