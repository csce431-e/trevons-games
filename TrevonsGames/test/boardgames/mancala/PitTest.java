/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.Mancala;

import boardgames.Mancala.Pebble;
import boardgames.Mancala.Pit;
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
public class PitTest {
    
    public PitTest() {
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
     * Test of add method, of class Pit.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Pebble p = null;
        Pit instance = new Pit();
        instance.add(p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class Pit.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        int i = 0;
        Pit instance = new Pit();
        Pebble expResult = null;
        Pebble result = instance.remove(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class Pit.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int i = 0;
        Pit instance = new Pit();
        Pebble expResult = null;
        Pebble result = instance.get(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class Pit.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        Pit instance = new Pit();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
