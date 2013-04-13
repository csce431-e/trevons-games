/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.checkers;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Trevon
 */
public class CheckersBoardTest {
    
    public CheckersBoardTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of initGame method, of class CheckersBoard.
     */
    @Test
    public void testInitGame()
    {
        System.out.println("initGame");
        CheckersGame g = new CheckersGame();
        g.initCheckers();
        //instance.initGame(g);
        //instance.currentGame = g;
        assertSame(g,g.b.currentGame);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The CheckersGame is not being passed correctly.");
    }

    /**
     * Test of printBoard method, of class CheckersBoard.
     */
    @Test
    public void testPrintBoard()
    {
        System.out.println("printBoard");
        CheckersBoard instance = new CheckersBoard();
        instance.printBoard();
        
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getAllMoves method, of class CheckersBoard.
     */
    @Test
    public void testGetAllMoves()
    {
        System.out.println("getAllMoves");
        CheckersGame g = new CheckersGame();
        g.initCheckers();
        CheckersBoard instance = g.b;
        instance.initGame(g);
        CheckersCell s = instance.board.get(5).get(0);
        CheckersCell d = instance.board.get(4).get(1);
        boolean expResult = true;
        boolean result = false;
        ArrayList<CheckersMove> resultArray = instance.getAllMoves();
        for(int i=0;i<resultArray.size();i++)
        {
            CheckersMove m = resultArray.get(i);
            if(m.source.equals(s) && m.dest.equals(d))
            {
                result = true;
            }
        }
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("GetAllMoves failed");
    }

    /**
     * Test of getJumpMoves method, of class CheckersBoard.
     */
    @Test
    public void testGetJumpMoves()
    {
        System.out.println("getJumpMoves");
        CheckersGame g = new CheckersGame();
        g.initCheckers();
        CheckersBoard instance = g.b;
        CheckersCell s = instance.board.get(5).get(6);
        CheckersCell d = instance.board.get(4).get(7);
        instance.makeMove(new CheckersMove(s,d,instance));
        
        g.turn = Owner.PLAYER2;
        
        CheckersCell s2 = instance.board.get(2).get(5);
        CheckersCell d2 = instance.board.get(3).get(6);
        instance.makeMove(new CheckersMove(s2,d2,instance));
        g.turn = Owner.PLAYER1;
        
        CheckersJump expResult = new CheckersJump(d, d2, s2, instance);
        ArrayList result = instance.getJumpMoves();
        assertEquals(expResult, result.get(0));
        // TODO review the generated test code and remove the default call to fail.
        //fail("TestGetJumpMoves failed");
    }

    /**
     * Test of getNormalMoves method, of class CheckersBoard.
     */
    @Test
    public void testGetNormalMoves()
    {
        System.out.println("getAllMoves");
        CheckersGame g = new CheckersGame();
        g.initCheckers();
        CheckersBoard instance = g.b;
        CheckersCell s = instance.board.get(5).get(0);
        CheckersCell d = instance.board.get(4).get(1);
        boolean expResult = true;
        boolean result = false;
        ArrayList<CheckersMove> resultArray = instance.getNormalMoves();
        for(int i=0;i<resultArray.size();i++)
        {
            CheckersMove m = resultArray.get(i);
            if(m.source.equals(s) && m.dest.equals(d))
            {
                result = true;
            }
        }
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("GetAllMoves failed");
    }

    /**
     * Test of makeMove method, of class CheckersBoard.
     */
    @Test
    public void testMakeMove()
    {
        System.out.println("makeMove");
        System.out.println("getAllMoves");
        CheckersGame g = new CheckersGame();
        g.initCheckers();
        CheckersBoard instance = g.b;
        CheckersCell s = instance.board.get(5).get(0);
        CheckersCell d = instance.board.get(4).get(1);
        CheckersMove m = new CheckersMove(s,d,instance);
      
        CheckersCell expResult = d;
        CheckersCell result = g.b.board.get(4).get(1);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of isGameOver method, of class CheckersBoard.
     */
    @Test
    public void testIsGameOver()
    {
        System.out.println("isGameOver");
        CheckersGame g = new CheckersGame();
        g.initCheckers();
        CheckersBoard instance = g.b;
        Owner expResult = Owner.PLAYER1;
       /* for(int i=0;i<3;i++)
        {
            for(int j=0;j<g.b.BOARDSIZE;j++)
            {
                g.b.board.get(i).get(j).setOwner(Owner.EMPTY);
                
            }
        }*/
        Owner.PLAYER2.pieces.clear();
        
        Owner result = instance.isGameOver();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

   
}
