/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.connectfour;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonathan
 */
public class ConnectFourBoardTest {
    
    public ConnectFourBoardTest() {
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
     * Test of win method, of class ConnectFourBoard.
     */
    @Test
    public void testWin() {
        System.out.println("win");
        ConnectFourBoard instance = new ConnectFourBoard();
        int expResult = 0;
        int result = instance.win();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of printBoard method, of class ConnectFourBoard.
     */
    @Test
    public void testPrintBoard() {
        System.out.println("printBoard");
        ConnectFourBoard instance = new ConnectFourBoard();
        instance.printBoard();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTurn method, of class ConnectFourBoard.
     */
    @Test
    public void testGetTurn() {
        System.out.println("getTurn");
        ConnectFourBoard instance = new ConnectFourBoard();
        int expResult = 1;
        int result = instance.getTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getColor method, of class ConnectFourBoard.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        int row = 0;
        int column = 0;
        ConnectFourBoard instance = new ConnectFourBoard();
        int expResult = 0;
        int result = instance.getColor(row, column);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of rowHeight method, of class ConnectFourBoard.
     */
    @Test
    public void testRowHeight() {
        System.out.println("rowHeight");
        int column = 0;
        ConnectFourBoard instance = new ConnectFourBoard();
        int expResult = 0;
        int result = instance.rowHeight(column);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of move method, of class ConnectFourBoard.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        int turn = 0;
        int column = 0;
        ConnectFourBoard instance = new ConnectFourBoard();
        boolean expResult = false;
        boolean result = instance.move(turn, column);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkFull method, of class ConnectFourBoard.
     */
    @Test
    public void testCheckFull() {
        System.out.println("checkFull");
        ConnectFourBoard instance = new ConnectFourBoard();
        boolean expResult = false;
        boolean result = instance.checkFull();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setPiece method, of class ConnectFourBoard.
     */
    @Test
    public void testSetPiece() {
        System.out.println("setPiece");
        int row = 0;
        int col = 0;
        int color = 0;
        ConnectFourBoard instance = new ConnectFourBoard();
        instance.setPiece(row, col, color);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
