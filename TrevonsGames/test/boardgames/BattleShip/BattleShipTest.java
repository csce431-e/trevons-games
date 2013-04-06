/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.BattleShip;

import boardgames.BattleShip.Ship.point;
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
public class BattleShipTest {
    private BattleShip instance;
    
    public BattleShipTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new BattleShip();
        instance.init();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class BattleShip.
    
    @Test
    public void testInit() {
        System.out.println("init");
        BattleShip instance = new BattleShip();
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    } */

    /**
     * Test of placeShips method, of class BattleShip.
     */
    @Test
    public void testPlaceShips() {
        System.out.println("placeShips");
        point p = new Ship.point(0,0);
        int shipNo = 0;
        String orientation = "NORTH";
        //BattleShip instance = new BattleShip();
        int expResult = 0;
        int result = instance.placeShips(p, shipNo, orientation);
        assertEquals(expResult, result);
        
        expResult = -1;
        result = instance.placeShips(p, shipNo, orientation);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkShipInBounds method, of class BattleShip.
     */
    @Test
    public void testCheckShipInBounds() {
        System.out.println("checkShipInBounds pass");
        int shipIndex = 0;
        int x = 0;
        int y = 0;
        String orientation = "NORTH";
        
        String expResult = "OK";
        String result = instance.checkShipInBounds(shipIndex, x, y, orientation);
        assertEquals(expResult, result);
        
        System.out.println("checkShipInBounds fail");
        shipIndex = 0;
        x = 0;
        y = 0;
        orientation = "SOUTH";
        
        expResult = "OOB SOUTH -5";
        result = instance.checkShipInBounds(shipIndex, x, y, orientation);
        assertEquals(expResult, result);
        
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of showBoard method, of class BattleShip. ShowBoard prints to the out stream, no test necessary
     
    @Test
    public void testShowBoard() {
        System.out.println("showBoard");
        //BattleShip instance = new BattleShip();
        instance.showBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of getBoard method, of class BattleShip.
     */
    @Test
    public void testGetBoard() {
        System.out.println("getBoard");
        instance = new BattleShip();
        instance.init();
        String[][] expResult = {{"o","o","o","o","o","o","o","o","o","o"},
                                {"o","o","o","o","o","o","o","o","o","o"},
                                {"o","o","o","o","o","o","o","o","o","o"},
                                {"o","o","o","o","o","o","o","o","o","o"},
                                {"o","o","o","o","o","o","o","o","o","o"},
                                {"o","o","o","o","o","o","o","o","o","o"},
                                {"o","o","o","o","o","o","o","o","o","o"},
                                {"o","o","o","o","o","o","o","o","o","o"},
                                {"o","o","o","o","o","o","o","o","o","o"},
                                {"o","o","o","o","o","o","o","o","o","o"},
                               };
        String[][] result = instance.getBoard();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of attackSpot method, of class BattleShip.
     */
    @Test
    public void testAttackSpot() {
        System.out.println("attackSpot");
        int x = 0;
        int y = 0;
        //BattleShip instance = new BattleShip();
        String expResult = "MISS";
        String result = instance.attackSpot(x, y);
        assertEquals(expResult, result);
        
        instance.placeShips(new Ship.point(0,0), 0, "NORTH");
        expResult="X";
        result = instance.attackSpot(x,y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkWin method, of class BattleShip.
     */
    @Test
    public void testCheckWin() {
        System.out.println("checkWin");
        //BattleShip instance = new BattleShip();
        boolean expResult = false;
        boolean result = instance.checkWin();
        assertEquals(expResult, result);
        
        expResult = true;
        instance.placeShips(new Ship.point(0,0), 0, "NORTH");
        instance.placeShips(new Ship.point(1,0), 1, "NORTH");
        instance.placeShips(new Ship.point(2,0), 2, "NORTH");
        instance.placeShips(new Ship.point(3,0), 3, "NORTH");
        instance.placeShips(new Ship.point(4,0), 4, "NORTH");

        instance.attackSpot(0,0);
        instance.attackSpot(0,1);
        instance.attackSpot(0,2);
        instance.attackSpot(0,3);
        instance.attackSpot(0,4);
        
        instance.attackSpot(1,0);
        instance.attackSpot(1,1);
        instance.attackSpot(1,2);
        instance.attackSpot(1,3);
        
        instance.attackSpot(2,0);
        instance.attackSpot(2,1);
        instance.attackSpot(2,2);
        
        instance.attackSpot(3,0);
        instance.attackSpot(3,1);
        instance.attackSpot(3,2);
        
        instance.attackSpot(4,0);
        instance.attackSpot(4,1);
        
        result = instance.checkWin();
        
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of PlayGame method, of class BattleShip. PlayGame was designed for console input playing of the game
     
    @Test
    public void testPlayGame() {
        System.out.println("PlayGame");
        //BattleShip instance = new BattleShip();
        instance.PlayGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
}
