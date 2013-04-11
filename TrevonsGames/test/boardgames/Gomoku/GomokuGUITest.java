/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.Gomoku;

import boardgames.Gomoku.GomokuGUI.point;
import javax.swing.JButton;
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
public class GomokuGUITest {
    
    public GomokuGUITest() {
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
     * Test of updateBoard method, of class GomokuGUI.
     */
    @Test
    public void testUpdateBoard() {
        System.out.println("updateBoard");
        GomokuGUI instance = new GomokuGUI();
        instance.updateBoard();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getMove method, of class GomokuGUI.
     */
    @Test
    public void testGetMove() {
        System.out.println("getMove");
        JButton b = null;
        GomokuGUI instance = new GomokuGUI();
        point expResult = null;
        point result = instance.getMove(b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of quitGame method, of class GomokuGUI.
     */
    @Test
    public void testQuitGame() {
        System.out.println("quitGame");
        GomokuGUI instance = new GomokuGUI();
        instance.quitGame();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of playGame method, of class GomokuGUI.
     */
    @Test
    public void testPlayGame() {
        System.out.println("playGame");
        GomokuGUI instance = new GomokuGUI();
        instance.playGame();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
