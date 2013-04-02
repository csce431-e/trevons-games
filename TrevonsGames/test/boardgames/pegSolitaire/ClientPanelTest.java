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
public class ClientPanelTest {
    
    public ClientPanelTest() {
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
     * Test of sendMessage method, of class ClientPanel.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        String msg = "";
        ClientPanel instance = null;
        instance.sendMessage(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of waitForMove method, of class ClientPanel.
     */
    @Test
    public void testWaitForMove() {
        System.out.println("waitForMove");
        ClientPanel instance = null;
        instance.waitForMove();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMoveFromString method, of class ClientPanel.
     */
    @Test
    public void testGetMoveFromString() {
        System.out.println("getMoveFromString");
        String s = "";
        ClientPanel instance = null;
        SolitaireMove expResult = null;
        SolitaireMove result = instance.getMoveFromString(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of game_over method, of class ClientPanel.
     */
    @Test
    public void testGame_over() {
        System.out.println("game_over");
        ClientPanel instance = null;
        boolean expResult = false;
        boolean result = instance.game_over();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}