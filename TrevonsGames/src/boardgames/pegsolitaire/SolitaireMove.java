/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.pegSolitaire;

import java.util.Objects;

/**
 *
 * @author Tom
 */
public class SolitaireMove
{
    public SolitaireCoordinate src;
    public SolitaireCoordinate dest;
    public SolitaireCoordinate middle;

    public SolitaireMove(SolitaireCoordinate s, SolitaireCoordinate d, SolitaireCoordinate m)
    {
        src = s;
        dest = d;
        middle = m;
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
            if (!(other instanceof SolitaireMove))
            {
                return false;
            }
            SolitaireMove otherMyClass = (SolitaireMove)other;
            
            if(otherMyClass.src.equals(src) && otherMyClass.dest.equals(dest) && otherMyClass.middle.equals(middle))
            {
                return true;
            }
            return false;
        }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.src);
        hash = 83 * hash + Objects.hashCode(this.dest);
        hash = 83 * hash + Objects.hashCode(this.middle);
        return hash;
    }
    
    
    public String toString()
    {
        String source;
        String destination;
        String mid;
        
        source = Integer.toString(src.x+20) + Integer.toString(src.y+20);
        destination = Integer.toString(dest.x+20) + Integer.toString(dest.y+20);
        mid = Integer.toString(middle.x+20) + Integer.toString(middle.y+20);
        
        return source + destination + mid;
    }
}
