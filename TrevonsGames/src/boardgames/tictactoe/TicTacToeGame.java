/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.tictactoe;
import java.util.*;
/**
 *
 * @author Tom
 */
public class TicTacToeGame 
{
    ArrayList<ArrayList<Integer>> board;
    static int BOARDSIZE = 3;
    
    TicTacToeGame()
    {
        board = new ArrayList<>();
        
        set_up_board();
    }
    
    TicTacToeGame(TicTacToeGame g)
    {
        board = new ArrayList<>();
        set_up_board();
        
        for(int i = 0; i< BOARDSIZE; i++)
        {
            for(int j = 0; j<BOARDSIZE; j++)
            {
                board.get(i).set(j,g.board.get(i).get(j));
            }   
        }
    }
            
    
    final void set_up_board()
    {
        for(int i = 0; i< BOARDSIZE; i++)
        {
            ArrayList<Integer> temp = new ArrayList<>();
            for(int j = 0; j<BOARDSIZE; j++)
            {
                temp.add(-1);   
            }
            board.add(temp);
        }
    }
    
    void make_move(int y, int x, int player)
    {
        board.get(y).set(x, player);
    }
    
    void unmake_move(int y, int x)
    {
        board.get(y).set(x, -1);
    }
    
    @Override
    public String toString()
    {
        String s = "";
        for(int i = 0; i< BOARDSIZE; i++)
        {
            for(int j = 0; j<BOARDSIZE; j++)
            {
                s += board.get(i).get(j);
            }
            s+="\n";
        }
        return s;
    }
}
