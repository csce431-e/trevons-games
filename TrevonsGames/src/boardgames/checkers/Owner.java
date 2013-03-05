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
public enum Owner {
 
    PLAYER1, PLAYER2, EMPTY;   
        
    public ArrayList<CheckersCell> pieces;
    
    Owner()
    {
        pieces = new ArrayList();
    }
   
    @Override
    public String toString()
    {
        if(this==PLAYER1)
        {
            return "PLAYER1 - BLACK";
        }
        else if(this==PLAYER2)
        {
            return "PLAYER2 - RED";
        }
        else
        {
            return "";
        }
    }
}
