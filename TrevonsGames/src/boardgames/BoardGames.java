/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames;
import java.awt.*;
import javax.swing.*;
import boardgames.pegSolitaire.*;

/**
 *
 * @author Trevon
 */
public class BoardGames {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() {
                new Test_GUI().setVisible(true);
            }
        });*/
        MainMenu gui = new MainMenu();
        
        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel textLabel= new JLabel("Label",SwingConstants.CENTER);
        textLabel.setPreferredSize(new Dimension(300,100));
        frame.getContentPane().add(textLabel,BorderLayout.CENTER);
        
        frame.add((gui));
        
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
      
    }
}
