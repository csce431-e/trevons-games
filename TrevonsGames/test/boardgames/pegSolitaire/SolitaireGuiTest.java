/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.pegSolitaire;

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
public class SolitaireGuiTest {
    
    public SolitaireGuiTest() {
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
     * Test of init_buttons method, of class SolitaireGui.
     */
    @Test
    public void testInit_buttons() {
        System.out.println("init_buttons");
        SolitaireGui instance = null;
        instance.init_buttons();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessage method, of class SolitaireGui.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        String msg = "";
        SolitaireGui instance = null;
        instance.sendMessage(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of waitForOpponent method, of class SolitaireGui.
     */
    @Test
    public void testWaitForOpponent() {
        System.out.println("waitForOpponent");
        SolitaireGui instance = null;
        instance.waitForOpponent();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of waitForMove method, of class SolitaireGui.
     */
    @Test
    public void testWaitForMove() {
        System.out.println("waitForMove");
        SolitaireGui instance = null;
        instance.waitForMove();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMoveFromString method, of class SolitaireGui.
     */
    @Test
    public void testGetMoveFromString() {
        System.out.println("getMoveFromString");
        String s = "";
        SolitaireGui instance = null;
        SolitaireMove expResult = null;
        SolitaireMove result = instance.getMoveFromString(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of kill_all_children method, of class SolitaireGui.
     */
    @Test
    public void testKill_all_children() throws Exception {
        System.out.println("kill_all_children");
        Thread current_thread = null;
        SolitaireGui instance = null;
        instance.kill_all_children(current_thread);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of game_over method, of class SolitaireGui.
     */
    @Test
    public void testGame_over() {
        System.out.println("game_over");
        SolitaireGui instance = null;
        boolean expResult = false;
        boolean result = instance.game_over();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
