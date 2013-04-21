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
public class ConnectFourGUIPanelTest {
    
    public ConnectFourGUIPanelTest() {
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
     * Test of sendMessage method, of class ConnectFourGUIPanel.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        String msg = "";
        ConnectFourGUIPanel instance = null;
        instance.sendMessage(msg);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of waitForOpponent_nothost method, of class ConnectFourGUIPanel.
     */
    @Test
    public void testWaitForOpponent_nothost() {
        System.out.println("waitForOpponent_nothost");
        ConnectFourGUIPanel instance = null;
        instance.waitForOpponent_nothost();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of waitForOpponent_host method, of class ConnectFourGUIPanel.
     */
    @Test
    public void testWaitForOpponent_host() {
        System.out.println("waitForOpponent_host");
        ConnectFourGUIPanel instance = null;
        instance.waitForOpponent_host();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of waitForMove method, of class ConnectFourGUIPanel.
     */
    @Test
    public void testWaitForMove() {
        System.out.println("waitForMove");
        ConnectFourGUIPanel instance = null;
        instance.waitForMove();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getMoveFromString method, of class ConnectFourGUIPanel.
     */
    @Test
    public void testGetMoveFromString() {
        System.out.println("getMoveFromString");
        String s = "";
        ConnectFourGUIPanel instance = null;
        int expResult = 0;
        int result = instance.getMoveFromString(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
