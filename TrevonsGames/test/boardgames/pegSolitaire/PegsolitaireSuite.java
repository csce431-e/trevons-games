/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.pegSolitaire;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Tom
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({boardgames.pegSolitaire.SolitaireBoardTest.class, boardgames.pegSolitaire.SolitaireCoordinateTest.class, boardgames.pegSolitaire.SolitaireGuiTest.class})
public class PegsolitaireSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
