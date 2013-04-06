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
     * Test of playConnect method, of class ConnectFourGUIPanel.
     */
    @Test
    public void testPlayConnect() {
        System.out.println("playConnect");
        ConnectFourGUIPanel instance = new ConnectFourGUIPanel();
        instance.playConnect();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
