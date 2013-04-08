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
     * Test of printBoard method, of class CheckersBoard.
     */
    @Test
    public void testPrintBoard()
    {
        System.out.println("printBoard");
        CheckersBoard instance = null;
        instance.printBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllMoves method, of class CheckersBoard.
     */
    @Test
    public void testGetAllMoves()
    {
        System.out.println("getAllMoves");
        CheckersBoard instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getAllMoves();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJumpMoves method, of class CheckersBoard.
     */
    @Test
    public void testGetJumpMoves()
    {
        System.out.println("getJumpMoves");
        CheckersBoard instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getJumpMoves();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNormalMoves method, of class CheckersBoard.
     */
    @Test
    public void testGetNormalMoves()
    {
        System.out.println("getNormalMoves");
        CheckersBoard instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getNormalMoves();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeMove method, of class CheckersBoard.
     */
    @Test
    public void testMakeMove()
    {
        System.out.println("makeMove");
        CheckersMove m = null;
        CheckersBoard instance = null;
        boolean expResult = false;
        boolean result = instance.makeMove(m);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCell method, of class CheckersBoard.
     */
    @Test
    public void testGetCell()
    {
        System.out.println("getCell");
        int x = 0;
        int y = 0;
        CheckersBoard instance = null;
        CheckersCell expResult = null;
        CheckersCell result = instance.getCell(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isGameOver method, of class CheckersBoard.
     */
    @Test
    public void testIsGameOver()
    {
        System.out.println("isGameOver");
        CheckersBoard instance = null;
        Owner expResult = null;
        Owner result = instance.isGameOver();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of jump method, of class CheckersBoard.
     */
    @Test
    public void testJump()
    {
        System.out.println("jump");
        CheckersCell source = null;
        CheckersCell dest = null;
        CheckersBoard instance = null;
        boolean expResult = false;
        boolean result = instance.jump(source, dest);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
