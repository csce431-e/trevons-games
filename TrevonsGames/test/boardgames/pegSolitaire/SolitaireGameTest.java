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
public class SolitaireGameTest {
    
    public SolitaireGameTest() {
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
     * Test of initGame method, of class SolitaireGame.
     */
    @Test
    public void testInitGame() {
        System.out.println("initGame");
        SolitaireGame instance = new SolitaireGame();
        instance.initGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMove method, of class SolitaireGame.
     */
    @Test
    public void testSendMove() {
        System.out.println("sendMove");
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        SolitaireGame instance = new SolitaireGame();
        instance.sendMove(x1, y1, x2, y2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}