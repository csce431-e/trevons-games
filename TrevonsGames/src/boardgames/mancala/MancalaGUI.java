/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.Mancala;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JButton;

/**
 *
 * @author Admin
 */
public class MancalaGUI extends javax.swing.JFrame {

    JButton buttons [][];
    int cols;
    int rows;
    int playerTurn;
    int turnOver;
    int winnerNum;
    boolean gameOver;
    boolean AIGame;
    MancalaBoard board;
    /**
     * Creates new form MancalaGUI
     */
    public MancalaGUI(boolean isAIGame) {
        AIGame = isAIGame;
        cols = 2;
        rows = 7;
        initComponents();
        initButtons();
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        playerTurn = 1;
        winnerNum = 0;
        gameOver = false;
        board = new MancalaBoard();
        
        turnOver = 0;

        disableButtons();
        updateBoard();
    }
    
    public MancalaGUI(boolean online, byte[] ip) {
        cols = 2;
        rows = 7;
        initComponents();
        initButtons();
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        playerTurn = 1;
        winnerNum = 0;
        gameOver = false;
        board = new MancalaBoard();
        
        turnOver = 0;

        disableButtons();
        updateBoard();
    }
    
    public void setAI(boolean isAIGame) {
        AIGame = isAIGame;
    }
    
    public void start() {
        if(AIGame) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MancalaGUI(true).setVisible(true);
            }
        });
        }
        else {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MancalaGUI(false).setVisible(true);
            }
        });
        }
    }
    
     private void changePlayer(int p) {
        int next;
        int validMove=0;
        next = (p == 1) ? 2 : 1;
        for(int i = 1; i< rows; i++) {
            buttons[playerTurn-1][i].setEnabled(false);
        }
        playerTurn = next;
        System.out.println(AIGame +""+playerTurn);
        if(AIGame && playerTurn ==2) {
             try {
                 jTextField1.setText("Computer's Turn");
                 jTextField2.setText("Computer's Turn");
                 AIPlay();
             } catch (InterruptedException ex) {
                 Logger.getLogger(MancalaGUI.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }
     
     public void AIPlay() throws InterruptedException { 
         int validMove = 0;
         checkForEnd();
            if(gameOver == true) {
                endGame();
            }
            else {
                while (validMove == 0) {
                    validMove = board.AIPlay();
                }
                changePlayer(playerTurn);
                Thread.sleep(1000);
                updateBoard();
            }
     }
     
    public void checkForEnd() {
        if(board.checkForGameOver(playerTurn)) {
            gameOver = true;
        }
    }
    
    public void endGame() {
            gameOver = true;
            disableButtons();
            jTextField1.setText("Game Over");
            if(board.getWinner() == 1) {
                jTextField2.setText("Player 1 wins!");
            }
            else if (board.getWinner() == 2) {
                jTextField2.setText("Player 2 wins!");
            }
            else
                jTextField2.setText("It's a tie!");
    }
    
    public void updateBoard() {
        if(board.checkForGameOver(playerTurn)) {
            endGame();
        }
        else {
            for(int i = 1; i< rows; i++) {
                buttons[playerTurn-1][i].setEnabled(true);
            }
        }
        for(int i = 0; i< cols; i++) {
            for(int j = 0; j< rows; j++) {
                buttons[i][j].setText(""+board.getNumPiecesInPit(i, j));
            }
        }
        if(playerTurn == 1 && !gameOver) {
            jTextField1.setText("Player 1's Turn");
            jTextField2.setText("Player 1's Turn");
        }
        else if(playerTurn == 2 && !gameOver) {
            jTextField1.setText("Player 2's Turn");
            jTextField2.setText("Player 2's Turn");
        }
    }
    
    private void disableButtons() {
        for(int i = 0; i< cols; i++) {
            for (int j =0; j<rows; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
    
    private void initButtons() {
        buttons = new JButton[cols][rows];
        buttons[0][0] = jButton00;
        buttons[0][1] = jButton01;
        buttons[0][2] = jButton02;
        buttons[0][3] = jButton03;
        buttons[0][4] = jButton04;
        buttons[0][5] = jButton05;
        buttons[0][6] = jButton06;
        buttons[1][0] = jButton10;
        buttons[1][1] = jButton11;
        buttons[1][2] = jButton12;
        buttons[1][3] = jButton13;
        buttons[1][4] = jButton14;
        buttons[1][5] = jButton15;
        buttons[1][6] = jButton16;    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton00 = new javax.swing.JButton();
        jButton01 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton02 = new javax.swing.JButton();
        jButton03 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton04 = new javax.swing.JButton();
        jButton05 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton06 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton00ActionPerformed(evt);
            }
        });

        jButton01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton01ActionPerformed(evt);
            }
        });

        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton02ActionPerformed(evt);
            }
        });

        jButton03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton03ActionPerformed(evt);
            }
        });

        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton04ActionPerformed(evt);
            }
        });

        jButton05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton05ActionPerformed(evt);
            }
        });

        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton06ActionPerformed(evt);
            }
        });

        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton01, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton02, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton03, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton04, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton06, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton05, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton00, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton06, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton05, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton04, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton03, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton02, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton01, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton00, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton00ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 0);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton00ActionPerformed

    private void jButton01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton01ActionPerformed
        // TODO add your handling code here:
        System.out.println(AIGame);
        turnOver = board.play(playerTurn, playerTurn-1, 1);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton01ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 6);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 5);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton02ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 2);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton02ActionPerformed

    private void jButton03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton03ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 3);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton03ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 4);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 3);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton04ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 4);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton04ActionPerformed

    private void jButton05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton05ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 5);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton05ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 2);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 1);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton06ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 6);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton06ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        turnOver = board.play(playerTurn, playerTurn-1, 0);
        if(turnOver == 1) {
            changePlayer(playerTurn);
        }
        updateBoard();
    }//GEN-LAST:event_jButton10ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton00;
    private javax.swing.JButton jButton01;
    private javax.swing.JButton jButton02;
    private javax.swing.JButton jButton03;
    private javax.swing.JButton jButton04;
    private javax.swing.JButton jButton05;
    private javax.swing.JButton jButton06;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
