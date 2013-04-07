/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.pegSolitaire;

import java.util.ArrayList;
import java.util.Arrays;
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
        SolitaireBoard b = new SolitaireBoard();
        int x = 0;
        int y = 0;
        SolitaireCoordinate instance = new SolitaireCoordinate(x,y,true,true);
        ArrayList<SolitaireCoordinate> expResult = new ArrayList();
        SolitaireCoordinate [] list = {new SolitaireCoordinate(x,y-2,true,true),
                                        new SolitaireCoordinate(x-2,y,true,true),
                                        new SolitaireCoordinate(x+2,y,true,true),
                                        new SolitaireCoordinate(x,y+2,true,true)};
        expResult.addAll(Arrays.asList(list));
        
        ArrayList<SolitaireCoordinate> result = instance.getTwoSpacesAway(b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getJumpsSrc method, of class SolitaireCoordinate.
     */
    @Test
    public void testGetJumpsSrc() {
        System.out.println("getJumpsSrc");
        SolitaireBoard b = new SolitaireBoard();
        SolitaireCoordinate dest = new SolitaireCoordinate(0,0,false,true);
        SolitaireCoordinate src = new SolitaireCoordinate(0,-2,true,true);
        
        ArrayList<SolitaireMove> expResult = null;
        ArrayList<SolitaireMove> result = src.getJumpsSrc(b);
        
        
        
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
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
        //fail("The test case is a prototype.");
    }

    /**
     * Test of max method, of class SolitaireCoordinate.
     */
    @Test
    public void testMax() {
        System.out.println("max");
        int a = 0;
        int b = 5;
        SolitaireCoordinate instance = new SolitaireCoordinate();
        int expResult = 5;
        int result = instance.max(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of min method, of class SolitaireCoordinate.
     */
    @Test
    public void testMin() {
        System.out.println("min");
        int a = 0;
        int b = 5;
        SolitaireCoordinate instance = new SolitaireCoordinate();
        int expResult = 0;
        int result = instance.min(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getJumpType method, of class SolitaireCoordinate.
     */
    @Test
    public void testGetJumpType() {
        System.out.println("getJumpType");
        SolitaireCoordinate dest = new SolitaireCoordinate(0,0,false,true);
        SolitaireCoordinate src = new SolitaireCoordinate(0,-2,true,true);
        
        int expResult = 2; //up
        int result = src.getJumpType(dest);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of canJump method, of class SolitaireCoordinate.
     */
    @Test
    public void testCanJump() {
        System.out.println("canJump");
        SolitaireBoard b = new SolitaireBoard();
        SolitaireCoordinate dest = new SolitaireCoordinate(0,0,false,true);
        SolitaireCoordinate src = new SolitaireCoordinate(0,-2,true,true);
        
        //SolitaireCoordinate expResult = new SolitaireCoordinate(0,-1,true,true);
        SolitaireCoordinate result = src.canJump(b, dest);
        
        assertTrue(result.x == 0 && result.y == -1 && result.filled == true && result.onBoard == true);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
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
        //fail("The test case is a prototype.");
    }
}
