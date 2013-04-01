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
public class ServerPanelTest {
    
    public ServerPanelTest() {
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
     * Test of sendMessage method, of class ServerPanel.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        String msg = "";
        ServerPanel instance = new ServerPanel();
        instance.sendMessage(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of waitForMove method, of class ServerPanel.
     */
    @Test
    public void testWaitForMove() {
        System.out.println("waitForMove");
        ServerPanel instance = new ServerPanel();
        instance.waitForMove();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMoveFromString method, of class ServerPanel.
     */
    @Test
    public void testGetMoveFromString() {
        System.out.println("getMoveFromString");
        String s = "";
        ServerPanel instance = new ServerPanel();
        SolitaireMove expResult = null;
        SolitaireMove result = instance.getMoveFromString(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of game_over method, of class ServerPanel.
     */
    @Test
    public void testGame_over() {
        System.out.println("game_over");
        ServerPanel instance = new ServerPanel();
        boolean expResult = false;
        boolean result = instance.game_over();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}