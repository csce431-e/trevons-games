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
public class SolitaireGuiPanelTest {
    
    public SolitaireGuiPanelTest() {
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
     * Test of init_buttons method, of class SolitaireGuiPanel.
     */
    @Test
    public void testInit_buttons() {
        System.out.println("init_buttons");
        SolitaireGuiPanel instance = new SolitaireGuiPanel();
        instance.init_buttons();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of kill_all_children method, of class SolitaireGuiPanel.
     */
    @Test
    public void testKill_all_children() throws Exception {
        System.out.println("kill_all_children");
        Thread current_thread = null;
        SolitaireGuiPanel instance = new SolitaireGuiPanel();
        instance.kill_all_children(current_thread);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find_proper_move method, of class SolitaireGuiPanel.
     */
    @Test
    public void testFind_proper_move() {
        System.out.println("find_proper_move");
        SolitaireBoard b_clone = null;
        SolitaireGuiPanel instance = new SolitaireGuiPanel();
        boolean expResult = false;
        boolean result = instance.find_proper_move(b_clone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of game_over method, of class SolitaireGuiPanel.
     */
    @Test
    public void testGame_over() {
        System.out.println("game_over");
        SolitaireGuiPanel instance = new SolitaireGuiPanel();
        boolean expResult = false;
        boolean result = instance.game_over();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}