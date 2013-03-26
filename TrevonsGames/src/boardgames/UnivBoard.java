//meant to serve as a universal template that can be used by all similar games
    //if you need to alter size, make a function to do so, and run that function 
    //in your game so as to not affect others


package boardgames;

import java.util.ArrayList;

/**
 *
 * @author jcrabb
 */
public class UnivBoard 
{
    ArrayList<Row> rows = new ArrayList();

	public UnivBoard()
	{
		for(int i=0;i<16;++i)
                {
                    rows.add(i,new Row());
                }
	}
        
	public void play(int row, int col, char s)
        {
            rows.get(row).squares.get(col).setState(s);
        }
	public void unplay (int row, int col)
        {
            play(row,col,'+');
                //replaces location with empty state
        }
	public char getSquareState(int row, int col)
        {
            return rows.get(row).squares.get(col).getState();
        }
	public void empty()
        {
            for(int i=0;i<rows.size();i++)
            {
                 for(int j=0;j<rows.size();j++)
                    {
			rows.get(i).squares.get(j).setState('+');
                    }
            }
        }
        /* Made for Vectors, alter if you need to use
        public void changeSize(int row, int col)
        {
            rows.setSize(row);
            for(int i=0;i<rows.size();i++)
            {
                rows.elementAt(i).squares.setSize(col);
            }
            
        }    
        */
}
