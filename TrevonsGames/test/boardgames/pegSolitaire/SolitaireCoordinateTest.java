/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.pegSolitaire;

import java.util.ArrayList;
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
public class SolitaireCoordinateTest {
    
    public SolitaireCoordinateTest() {
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
     * Test of getTwoSpacesAway method, of class SolitaireCoordinate.
     */
    @Test
    public void testGetTwoSpacesAway() {
        System.out.println("getTwoSpacesAway");
        SolitaireBoard b = null;
        SolitaireCoordinate instance = new SolitaireCoordinate();
        ArrayList expResult = null;
        ArrayList result = instance.getTwoSpacesAway(b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJumpsSrc method, of class SolitaireCoordinate.
     */
    @Test
    public void testGetJumpsSrc() {
        System.out.println("getJumpsSrc");
        SolitaireBoard b = null;
        SolitaireCoordinate instance = new SolitaireCoordinate();
        ArrayList expResult = null;
        ArrayList result = instance.getJumpsSrc(b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJumpsDest method, of class SolitaireCoordinate.
     */
    @Test
    public void testGetJumpsDest() {
        System.out.println("getJumpsDest");
        SolitaireBoard b = null;
        SolitaireCoordinate instance = new SolitaireCoordinate();
        ArrayList expResult = null;
        ArrayList result = instance.getJumpsDest(b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of max method, of class SolitaireCoordinate.
     */
    @Test
    public void testMax() {
        System.out.println("max");
        int a = 0;
        int b = 0;
        SolitaireCoordinate instance = new SolitaireCoordinate();
        int expResult = 0;
        int result = instance.max(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of min method, of class SolitaireCoordinate.
     */
    @Test
    public void testMin() {
        System.out.println("min");
        int a = 0;
        int b = 0;
        SolitaireCoordinate instance = new SolitaireCoordinate();
        int expResult = 0;
        int result = instance.min(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJumpType method, of class SolitaireCoordinate.
     */
    @Test
    public void testGetJumpType() {
        System.out.println("getJumpType");
        SolitaireCoordinate dest = null;
        SolitaireCoordinate instance = new SolitaireCoordinate();
        int expResult = 0;
        int result = instance.getJumpType(dest);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of canJump method, of class SolitaireCoordinate.
     */
    @Test
    public void testCanJump() {
        System.out.println("canJump");
        SolitaireBoard b = null;
        SolitaireCoordinate dest = null;
        SolitaireCoordinate instance = new SolitaireCoordinate();
        SolitaireCoordinate expResult = null;
        SolitaireCoordinate result = instance.canJump(b, dest);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class SolitaireCoordinate.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SolitaireCoordinate instance = new SolitaireCoordinate();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}