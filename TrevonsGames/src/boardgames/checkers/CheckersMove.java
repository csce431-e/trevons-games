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
    CheckersCell middle;
    CheckersCell dest;
    boolean jump;
    
    public CheckersMove(CheckersCell src, CheckersCell d)
    {
        source = src;
        dest = d;
        jump = false;
    }
    
    public CheckersMove(CheckersCell src, CheckersCell mid, CheckersCell d)
    {
        source = src;
        middle = mid;
        dest = d;
        jump = true;
    }
    @Override
    public String toString()
    {
        String s = source.x + "," + source.y;
        String d = dest.x + "," + dest.y;
        return s + " to " + d;
    }
}
