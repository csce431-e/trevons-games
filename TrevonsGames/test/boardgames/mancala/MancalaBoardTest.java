/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.Mancala;

import boardgames.Mancala.MancalaBoard;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class MancalaBoardTest {
    
    public MancalaBoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of checkForGameOver method, of class MancalaBoard.
     */
    @Test
    public void testCheckForGameOver() {
        System.out.println("checkForGameOver");
        int playerTurn = 0;
        MancalaBoard instance = new MancalaBoard();
        boolean expResult = false;
        boolean result = instance.checkForGameOver(playerTurn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveAllPieces method, of class MancalaBoard.
     */
    @Test
    public void testMoveAllPieces() {
        System.out.println("moveAllPieces");
        int c1 = 0;
        int r1 = 0;
        int c2 = 0;
        int r2 = 0;
        MancalaBoard instance = new MancalaBoard();
        instance.moveAllPieces(c1, r1, c2, r2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveOnePiece method, of class MancalaBoard.
     */
    @Test
    public void testMoveOnePiece() {
        System.out.println("moveOnePiece");
        int c1 = 0;
        int r1 = 0;
        int c2 = 0;
        int r2 = 0;
        MancalaBoard instance = new MancalaBoard();
        instance.moveOnePiece(c1, r1, c2, r2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of play method, of class MancalaBoard.
     */
    @Test
    public void testPlay() {
        System.out.println("play");
        int p = 0;
        int c = 0;
        int r = 0;
        MancalaBoard instance = new MancalaBoard();
        int expResult = 0;
        int result = instance.play(p, c, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWinner method, of class MancalaBoard.
     */
    @Test
    public void testGetWinner() {
        System.out.println("getWinner");
        MancalaBoard instance = new MancalaBoard();
        int expResult = 0;
        int result = instance.getWinner();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of prettyPrint method, of class MancalaBoard.
     */
    @Test
    public void testPrettyPrint() {
        System.out.println("prettyPrint");
        MancalaBoard instance = new MancalaBoard();
        instance.prettyPrint();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
