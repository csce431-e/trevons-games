/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.mancala;

import boardgames.Mancala.MancalaGUI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class MancalaGUITest {
    
    public MancalaGUITest() {
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
     * Test of setAI method, of class MancalaGUI.
     */
    @Test
    public void testSetAI() {
        System.out.println("setAI");
        boolean isAIGame = false;
        MancalaGUI instance = null;
        instance.setAI(isAIGame);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class MancalaGUI.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        MancalaGUI instance = null;
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of AIPlay method, of class MancalaGUI.
     */
    @Test
    public void testAIPlay() throws Exception {
        System.out.println("AIPlay");
        MancalaGUI instance = null;
        instance.AIPlay();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkForEnd method, of class MancalaGUI.
     */
    @Test
    public void testCheckForEnd() {
        System.out.println("checkForEnd");
        MancalaGUI instance = null;
        instance.checkForEnd();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of endGame method, of class MancalaGUI.
     */
    @Test
    public void testEndGame() {
        System.out.println("endGame");
        MancalaGUI instance = null;
        instance.endGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateBoard method, of class MancalaGUI.
     */
    @Test
    public void testUpdateBoard() {
        System.out.println("updateBoard");
        MancalaGUI instance = null;
        instance.updateBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
