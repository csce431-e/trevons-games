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
public class PieceTest {
    
    public PieceTest() {
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
     * Test of setColor method, of class Piece.
     */
    @Test
    public void testSetColor() {
        System.out.println("setColor");
        int col = 0;
        Piece instance = new Piece();
        instance.setColor(col);
        assertEquals(col, instance.pieceColor());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of pieceColor method, of class Piece.
     */
    @Test
    public void testPieceColor() {
        System.out.println("pieceColor");
        Piece instance = new Piece();
        int expResult = 0;
        instance.setColor(expResult);
        int result = instance.pieceColor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
