/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames;

import java.util.Vector;
import boardgames.Square;   

/**
 *
 * @author jcrabb
 */
public class Row 
{
    Vector<Square> squares;

	Row()
        {
		squares.setSize(16);
                for(int i=-0;i<squares.size();i++)
                {   
                    squares.elementAt(i).setState('+');
                        //plus acts as empty character
                }
	}
}
