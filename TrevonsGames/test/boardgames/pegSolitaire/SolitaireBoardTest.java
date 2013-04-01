/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.pegSolitaire;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tom
 */
public class SolitaireBoardTest {
    
    public SolitaireBoardTest() {
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
     * Test of win method, of class SolitaireBoard.
     */
    @Test
    public void testWin() {
        System.out.println("win");
        SolitaireBoard instance = new SolitaireBoard();
        boolean expResult = false;
        boolean result = instance.win();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lose method, of class SolitaireBoard.
     */
    @Test
    public void testLose() {
        System.out.println("lose");
        SolitaireBoard instance = new SolitaireBoard();
        boolean expResult = false;
        boolean result = instance.lose();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCoordinate method, of class SolitaireBoard.
     */
    @Test
    public void testGetCoordinate() {
        System.out.println("getCoordinate");
        int x = 0;
        int y = 0;
        SolitaireBoard instance = new SolitaireBoard();
        SolitaireCoordinate expResult = null;
        SolitaireCoordinate result = instance.getCoordinate(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of move method, of class SolitaireBoard.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        SolitaireCoordinate src = null;
        SolitaireCoordinate dest = null;
        SolitaireBoard instance = new SolitaireBoard();
        SolitaireCoordinate expResult = null;
        SolitaireCoordinate result = instance.move(src, dest);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of make_move method, of class SolitaireBoard.
     */
    @Test
    public void testMake_move() {
        System.out.println("make_move");
        SolitaireMove move = null;
        SolitaireBoard instance = new SolitaireBoard();
        SolitaireCoordinate expResult = null;
        SolitaireCoordinate result = instance.make_move(move);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of un_make_move method, of class SolitaireBoard.
     */
    @Test
    public void testUn_make_move() {
        System.out.println("un_make_move");
        SolitaireMove move = null;
        SolitaireBoard instance = new SolitaireBoard();
        instance.un_make_move(move);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get_all_possible_moves method, of class SolitaireBoard.
     */
    @Test
    public void testGet_all_possible_moves() {
        System.out.println("get_all_possible_moves");
        SolitaireBoard instance = new SolitaireBoard();
        ArrayList expResult = null;
        ArrayList result = instance.get_all_possible_moves();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of no_moves_left method, of class SolitaireBoard.
     */
    @Test
    public void testNo_moves_left() {
        System.out.println("no_moves_left");
        SolitaireBoard instance = new SolitaireBoard();
        boolean expResult = false;
        boolean result = instance.no_moves_left();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printBoard method, of class SolitaireBoard.
     */
    @Test
    public void testPrintBoard() {
        System.out.println("printBoard");
        SolitaireBoard instance = new SolitaireBoard();
        instance.printBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}