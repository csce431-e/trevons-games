/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.Gomoku;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jcrabb
 */
public class GomokuTest {
    
    public GomokuTest() {
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
     * Test of consoleDisplay method, of class Gomoku.
     */
    @Test
    public void testConsoleDisplay() {
        System.out.println("consoleDisplay");
        Gomoku instance = new Gomoku();
        instance.consoleDisplay();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkMove method, of class Gomoku.
     */
    @Test
    public void testCheckMove() {
        System.out.println("checkMove");
        int row = 0;
        int col = 0;
        char clr = ' ';
        Gomoku instance = new Gomoku();
        boolean expResult = false;
        boolean result = instance.checkMove(row, col, clr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkWin method, of class Gomoku.
     */
    @Test
    public void testCheckWin() {
        System.out.println("checkWin");
        int row = 0;
        int col = 0;
        char clr = ' ';
        Gomoku instance = new Gomoku();
        boolean expResult = false;
        boolean result = instance.checkWin(row, col, clr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of undo method, of class Gomoku.
     */
    @Test
    public void testUndo() {
        System.out.println("undo");
        Gomoku instance = new Gomoku();
        instance.undo();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of restart method, of class Gomoku.
     */
    @Test
    public void testRestart() {
        System.out.println("restart");
        Gomoku instance = new Gomoku();
        instance.restart();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getMove method, of class Gomoku.
     */
    @Test
    public void testGetMove() {
        System.out.println("getMove");
        Gomoku instance = new Gomoku();
        instance.getMove();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of incTC method, of class Gomoku.
     */
    @Test
    public void testIncTC() {
        System.out.println("incTC");
        Gomoku instance = new Gomoku();
        instance.incTC();
        // TODO review the generated test code and remove the default call to fail.
       //fail("The test case is a prototype.");
    }

    /**
     * Test of setTC method, of class Gomoku.
     */
    @Test
    public void testSetTC() {
        System.out.println("setTC");
        int x = 0;
        Gomoku instance = new Gomoku();
        instance.setTC(x);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
