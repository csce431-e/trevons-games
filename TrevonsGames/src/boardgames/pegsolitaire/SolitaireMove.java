/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.pegSolitaire;

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
