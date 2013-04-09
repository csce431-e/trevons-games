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
        SolitaireCoordinate [] list = {b.getCoordinate(x,y-2),
                                        b.getCoordinate(x-2,y),
                                        b.getCoordinate(x+2,y),
                                        b.getCoordinate(x,y+2)};
        expResult.addAll(Arrays.asList(list));
        
        ArrayList<SolitaireCoordinate> result = instance.getTwoSpacesAway(b);
        
        for(int i = 0; i<result.size(); i++)
        {
            assertTrue(expResult.contains(result.get(i)));
        }
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
        SolitaireMove move = new SolitaireMove(b.getCoordinate(0, -2), b.getCoordinate(0, 0), b.getCoordinate(0, -1));
        SolitaireCoordinate src = b.getCoordinate(0, -2);
        SolitaireCoordinate src2 = new SolitaireCoordinate(0,-2,true,true);
        //assertEquals(src, src2);
        //ArrayList<SolitaireMove> expResult = null;
        ArrayList<SolitaireMove> result = src.getJumpsSrc(b);
        
        assertTrue(result.contains(move));
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getJumpsDest method, of class SolitaireCoordinate.
     */
    @Test
    public void testGetJumpsDest() {
        System.out.println("getJumpsDest");
        SolitaireBoard b = new SolitaireBoard();
        SolitaireMove move = new SolitaireMove(b.getCoordinate(0, -2), b.getCoordinate(0, 0), b.getCoordinate(0, -1));
        SolitaireCoordinate dest = b.getCoordinate(0, 0);
        
        ArrayList<SolitaireMove> result = dest.getJumpsDest(b);
        
        assertTrue(result.contains(move));
        //assertEquals(expResult, result);
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
        SolitaireBoard b = new SolitaireBoard();
        SolitaireCoordinate instance1 = b.getCoordinate(0, 0);
        SolitaireCoordinate instance2 = b.getCoordinate(1, 1);
        String result1 = instance1.toString();
        String result2 = instance2.toString();
        String expResult1 = "O";
        String expResult2 = "X";
        
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
