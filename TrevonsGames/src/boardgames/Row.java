/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames;

import boardgames.Square;   
import java.util.ArrayList;

/**
 *
 * @author jcrabb
 */
public class Row 
{
    ArrayList<Square> squares = new ArrayList();

	public Row()
        {
		for(int i=0;i<16;++i)
                {
                    squares.add(i,new Square());
                }
	}
        
        
}
