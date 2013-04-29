/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.Mancala;

import java.util.Objects;

/**
 *
 * @author Tom
 */
public class MancalaMove
{
    public int player;
    public int row;
    public int column;

    public MancalaMove(int p,int c, int r)
    {
        player = p;
        column = c;
        row = r;
    }
    
    @Override
        public boolean equals(Object other){
            if (other == null) 
            {
                return false;
            }
            if (other == this) 
            {
                return true;
            }
            if (!(other instanceof MancalaMove))
            {
                return false;
            }
            MancalaMove otherMyClass = (MancalaMove)other;
            
            if(otherMyClass.row==row && otherMyClass.column==column)
            {
                return true;
            }
            return false;
        }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.player);
        hash = 83 * hash + Objects.hashCode(this.column);
        hash = 83 * hash + Objects.hashCode(this.row);
        return hash;
    }
    
    
    public String toString()
    {
        String spla;
        String scol;
        String srow;
        
        spla = Integer.toString(player);
        srow = Integer.toString(column);
        scol = Integer.toString(row);
        return spla+srow+scol;
    }
}
