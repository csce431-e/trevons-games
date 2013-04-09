/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.BattleShip;

import boardgames.BattleShip.Ship.NotInShipException;
import boardgames.BattleShip.Ship.point;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mike
 */
public class ShipTest {
    
    public ShipTest() {
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
     * Test of size method, of class Ship.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        Ship instance = new Ship();
        int expResult = 3;
        int result = instance.size();
        assertEquals(expResult, result);
        
        Ship instance2 = new Ship(5);
        expResult = 5;
        result = instance2.size();
        assertEquals(expResult, result);
        
        Ship instance3 = new Ship(2, "Carrier");
        expResult = 2;
        result = instance3.size();
        assertEquals(expResult, result);
        
        Ship instance4 = new Ship(10);
        expResult = 10;
        result = instance4.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of name method, of class Ship.
     */
    @Test
    public void testName() {
        System.out.println("name");
        Ship instance = new Ship();
        String expResult = "Ship";
        String result = instance.name();
        assertEquals(expResult, result);
        
        Ship instance2 = new Ship(5,"BattleShip");
        expResult = "BattleShip";
        result = instance2.name();
        assertEquals(expResult, result);
        
        Ship instance3 = new Ship(5,"Carrier");
        expResult = "Carrier";
        result = instance3.name();
        assertEquals(expResult, result);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of point method, of class Ship.
     */
    @Test
    public void testPoint() {
        System.out.println("point");
        Ship instance = new Ship();
        
        Ship.point p = new Ship.point(0,0);
        instance.placeShip(p, "NORTH");
        
        point expResult = p;
        point result = instance.point();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of placeShip method, of class Ship.
     */
    @Test
    public void testPlaceShip() {
        System.out.println("placeShip");
        point p = new Ship.point(0,0);
        String orient = "NORTH";
        Ship instance = new Ship();
        instance.placeShip(p, orient);
        
        instance = new Ship();
        orient = "SOUTH";
        instance.placeShip(p, orient);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getX method, of class Ship.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Ship instance = new Ship();
        int expResult = 0;
        int result = instance.getX();
        assertEquals(expResult, result);
        
        instance = new Ship(2);
        instance.placeShip(new Ship.point(5,5), "NORTH");
        expResult = 5;
        result = instance.getX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getY method, of class Ship.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Ship instance = new Ship(1);
        int expResult = 0;
        int result = instance.getY();
        assertEquals(expResult, result);
        
        instance = new Ship(2);
        instance.placeShip(new Ship.point(5,5), "NORTH");
        expResult = 5;
        result = instance.getY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getOrientation method, of class Ship.
     */
    @Test
    public void testGetOrientation() {
        System.out.println("getOrientation");
        Ship instance = new Ship();
        String expResult = "";
        String result = instance.getOrientation();
        assertEquals(expResult, result);
        
        instance.placeShip(new Ship.point(0,0), "NORTH");
        expResult = "NORTH";
        result = instance.getOrientation();
         assertEquals(expResult, result);
         
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of sunk method, of class Ship.
     */
    @Test
    public void testSunk() {
        System.out.println("sunk");
        Ship instance = new Ship();
        boolean expResult = false;
        boolean result = instance.sunk();
        assertEquals(expResult, result);
        
        
        try {
            instance.damage(0);
            instance.damage(1);
            instance.damage(2);
        } catch (NotInShipException ex) {
            Logger.getLogger(ShipTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        expResult = true;
        result = instance.sunk();
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of damage method, of class Ship.
     */
    @Test
    public void testDamage() throws Exception {
        System.out.println("damage");
        int location = 0;
        Ship instance = new Ship();
        String expResult = "Damaged Ship";
        String result = instance.damage(location);
        assertEquals(expResult, result);
        
        instance.damage(1);
        expResult = "Destroyed Ship";
        result = instance.damage(2);
        assertEquals(expResult, result);
        
        try{
            instance.damage(3);
        }catch (NotInShipException ex) {
            return;
        }
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
