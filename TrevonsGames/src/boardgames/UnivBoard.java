//meant to serve as a universal template that can be used by all similar games
    //if you need to alter size, make a function to do so, and run that function 
    //in your game so as to not affect others


package boardgames;

import java.util.Vector;

/**
 *
 * @author jcrabb
 */
public class UnivBoard 
{
    Vector<Row> rows;

	UnivBoard()
	{
		rows.setSize(16);
	}

	public void play(int row, int col, char s)
        {
            rows.elementAt(row).squares.elementAt(col).setState(s);
        }
        
	public void unplay (int row, int col)
        {
            play(row,col,'+');
                //replaces location with empty state
        }
	public char getSquareState(int row, int col)
        {
            return rows.elementAt(row).squares.elementAt(col).getState();
        }
	public void empty()
        {
            for(int i=0;i<16;i++)
            {
                 for(int i2=0;i2<16;i2++)
                    {
			rows.elementAt(i).squares.elementAt(i2).setState('+');
                    }
            }
        }
        public void changeSize(int row, int col)
        {
            rows.setSize(row);
            for(int i=0;i<rows.size();i++)
            {
                rows.elementAt(i).squares.setSize(col);
            }
            
        }
}
