/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.Mancala;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//import java.net.ConnectException;

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
    
    //start0 for online play******************************************************************************************************
    boolean isOnline;
    byte [] serverIP;
    public boolean myTurn;
    public boolean notMyTurn;
    public int myPlayerNum;
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    public boolean iquit;
    public final JFrame wait_window = new JFrame("Waiting for an opponent");
    //end0 for online play******************************************************************************************************

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

        //disableButtons();
        updateBoard();
    }
    
    public MancalaGUI(boolean online, byte[] ip) {
        cols = 2;
        rows = 7;
        initComponents();
        initButtons();
        
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        playerTurn = 1;
        winnerNum = 0;
        gameOver = false;
        board = new MancalaBoard();
        
        turnOver = 0;
        notMyTurn = false;

        //disableButtons();
        //updateBoard();
        
        //start1 for online play******************************************************************************************************
        isOnline = online;
        if(isOnline)
        {
            serverIP = ip;
            iquit = false;
            setup_client_socket();
        }
        //end1 for online play******************************************************************************************************

        
    }
    
    //start2 for online play******************************************************************************************************
    private void setup_client_socket()
    {
        try
        {
            System.out.println("Setting up client socket");
            final InetAddress addr = InetAddress.getByAddress(serverIP);
            //if you request a socket to a nonexistent addr, then
            requestSocket = new Socket(addr, 2008);
            
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            
            out.writeObject("man");
            System.out.println("waiting for response from server");
            String msg = (String)in.readObject(); //waiting or starting
            System.out.println("reading: "+ msg);

            if(msg.equals("waiting"))
            {
                System.out.println("waiting for \"starting\"");

                //create window

                wait_window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JButton accept = new JButton("CANCEL");

                accept.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        iquit = true;
                        wait_window.dispose();
                    }
                });

                wait_window.add(accept);
                wait_window.setLocation(300, 300);
                wait_window.setSize(400, 200);
                wait_window.setVisible(true);
                wait_window.paintAll(wait_window.getGraphics());
                //window done

                myTurn = true;
                jTextField1.setText("it's my turn");
                myPlayerNum = 1;
                System.out.println("its my turn");
            }
            else
            {
                myTurn = false;
                jTextField1.setText("it's NOT my turn");
                myPlayerNum = 2;
                System.out.println("its NOT my turn");
            }
            updateBoard();
            jTextField2.setText("You are player "+myPlayerNum);
        }
        catch(ConnectException ce)
        {
            System.err.println("Connection timed out - invalid ip most like");
            final JFrame quit_window = new JFrame("Unable to connect to given IP");
            JButton accept = new JButton("OK");
            accept.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    quit_window.dispose();

                }
            });
            quit_window.add(accept);
            quit_window.setLocation(300, 300);
            quit_window.setSize(400, 200);
            quit_window.setVisible(true);
            this.dispose();
        }
        catch(ClassNotFoundException classNot)
        { 
            System.err.println("data received in unknown format"); 
        }
        catch(UnknownHostException unknownHost)
        {
            System.err.println("You are trying to connect to an unknown host!");
            final JFrame quit_window = new JFrame("Unable to connect to given IP - Unknown Host");
            JButton accept = new JButton("OK");
            accept.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    quit_window.dispose();

                }
            });
            quit_window.add(accept);
            quit_window.setLocation(300, 300);
            quit_window.setSize(400, 200);
            quit_window.setVisible(true);
            this.dispose();
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
    
    //copy directly
    void sendMessage(String msg)
    {
        try
        {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    public void waitForOpponent_nothost()
    {
        try
        {
            System.out.println("blocking in nothost");
            String msg = (String)in.readObject();
            System.out.println(msg); //should be connection successful, shold only print when BOTH are connected   
        }
        catch(IOException e)
        {
           //System.out.println("IOexception in waiting for opponent");
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            //System.out.println("class not found in waiting for opponent");
            e.printStackTrace();
        }
    }
    
    public void waitForOpponent_host()
    {
        try
        {
             //****put the quit window here**
            System.out.println("blocking in host didyouquit");
            String msg = (String)in.readObject(); //should be didyouquit
            System.out.println(msg);
            
            if(iquit)
            {
                out.writeObject("yes");
                
                return; //you quit so no reason to continue
            }
            else
            {
                out.writeObject("no");
            }
            
            System.out.println("blocking in host for game start");
            msg = (String)in.readObject();
            System.out.println(msg); //should be connection successful, shold only print when BOTH are connected   
        }
        catch(IOException e)
        {
           //System.out.println("IOexception in waiting for opponent");
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            //System.out.println("class not found in waiting for opponent");
            e.printStackTrace();
        }
    }
    
    //only difference is how to actually make the move received and apply to graphics
    public void waitForMove()
    {
        //wait for your turn, continuously ask for msg from in till you get it
        try
        {
            System.out.println("Waiting for move");
            String msg = (String)in.readObject();
            System.out.println("Message received: "+msg);
            
            if(msg.equals("quit"))
            {
                final JFrame quit_window = new JFrame("Your opponent has quit");
                JButton accept = new JButton("OK");
                accept.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        quit_window.dispose();

                    }
                });
                quit_window.add(accept);
                quit_window.setLocation(300, 300);
                quit_window.setSize(400, 200);
                quit_window.setVisible(true);
                this.dispose();
                return;
            }
            MancalaMove otherPlayerMove = getMoveFromString(msg);
            int notBool = board.play(otherPlayerMove);
            updateBoard();
            if(notBool != 1) {
                waitForAnotherMove();
            }
        
            /*firstChoice = buts.get((-otherPlayerMove.src.y)+3).get((otherPlayerMove.src.x)+3);
            middleButton = buts.get((-otherPlayerMove.middle.y)+3).get((otherPlayerMove.middle.x)+3);
            JButton clicked = buts.get((-otherPlayerMove.dest.y)+3).get((otherPlayerMove.dest.x)+3);
            apply_move_to_graphics(clicked);*/
            myTurn = true;
        }
        catch(ClassNotFoundException classNot)
        {
            System.err.println("data received in unknown format");
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }   
    }
    
    //theirs will have to be completely different: some way to parse a string into any possible move
    MancalaMove getMoveFromString(String s)
    {
        int pfromstring = Integer.parseInt(s.substring(0,1));
        int cfromstring = Integer.parseInt(s.substring(1,2));
        int rfromstring = Integer.parseInt(s.substring(2,3));
        
        //break the string into the 3 coordinates
        /*SolitaireCoordinate src = new SolitaireCoordinate(srcx,srcy,true,true);
        SolitaireCoordinate dest = new SolitaireCoordinate(destx,desty,true,true);
        SolitaireCoordinate mid = new SolitaireCoordinate(midx,midy,true,true);
        //set the parts of a local move to the 3 coordinates
        SolitaireMove otherPlayerMove = new SolitaireMove(src,dest,mid);*/
        return new MancalaMove(pfromstring,cfromstring,rfromstring);//otherPlayerMove;
    }
    //end2 for online play******************************************************************************************************
    

    
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
        /*for(int i = 1; i< rows; i++) {
            buttons[playerTurn-1][i].setEnabled(false);
        }*/
        playerTurn = next;
        System.out.println(AIGame +""+playerTurn);
        if(isOnline) {
            myTurn = false;
        }
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
     
     public void waitForAnotherMove() {
         try
        {
            System.out.println("Waiting for move");
            String msg = (String)in.readObject();
            System.out.println("Message received: "+msg);
            
            if(msg.equals("quit"))
            {
                final JFrame quit_window = new JFrame("Your opponent has quit");
                JButton accept = new JButton("OK");
                accept.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        quit_window.dispose();

                    }
                });
                quit_window.add(accept);
                quit_window.setLocation(300, 300);
                quit_window.setSize(400, 200);
                quit_window.setVisible(true);
                this.dispose();
                return;
            }
            MancalaMove otherPlayerMove = getMoveFromString(msg);
            int notBool = board.play(otherPlayerMove);
            updateBoard();
                myTurn = true;
        }
        catch(ClassNotFoundException classNot)
        {
            System.err.println("data received in unknown format");
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
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
        /*else {
            for(int i = 1; i< rows; i++) {
                buttons[playerTurn-1][i].setEnabled(true);
            }
        }*/
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
        jButton17 = new javax.swing.JButton();

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

        jButton17.setText("Quit");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17quit_clicked(evt);
            }
        });

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton01, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton00, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                    .addComponent(jButton05, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton01, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton00, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        //System.out.println(AIGame);
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 0, 1);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 0, 1);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 0) {
            turnOver = board.play(playerTurn, playerTurn-1, 1);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton01ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 1, 6);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 1, 6);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 1) {
            turnOver = board.play(playerTurn, playerTurn-1, 6);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 1, 5);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 1, 5);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 1) {
            turnOver = board.play(playerTurn, playerTurn-1, 5);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton02ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 0, 2);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 0, 2);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 0) {
            turnOver = board.play(playerTurn, playerTurn-1, 2);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton02ActionPerformed

    private void jButton03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton03ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 0, 3);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 0, 3);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 0) {
            turnOver = board.play(playerTurn, playerTurn-1, 3);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton03ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 1, 4);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 1, 4);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 1) {
            turnOver = board.play(playerTurn, playerTurn-1, 4);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 1, 3);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 1, 3);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 1) {
            turnOver = board.play(playerTurn, playerTurn-1, 3);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton04ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 0, 4);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 0, 4);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 0) {
            turnOver = board.play(playerTurn, playerTurn-1, 4);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton04ActionPerformed

    private void jButton05ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton05ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 0, 5);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 0, 5);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 0) {
            turnOver = board.play(playerTurn, playerTurn-1, 5);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton05ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 1, 2);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 1, 2);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 1) {
            turnOver = board.play(playerTurn, playerTurn-1, 2);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 1, 1);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 1, 1);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 1) {
            turnOver = board.play(playerTurn, playerTurn-1, 1);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton06ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton06ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 0, 6);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 0, 6);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 0) {
            turnOver = board.play(playerTurn, playerTurn-1, 6);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton06ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if(isOnline){
            if(myTurn) {
                turnOver = board.play(myPlayerNum, 1, 0);
                MancalaMove moveToSend = new MancalaMove(myPlayerNum, 1, 0);
                sendMessage(moveToSend.toString());
                if(turnOver == 1) {
                    changePlayer(playerTurn);
                    class Waiting_for_replay_thread implements Runnable {
                        @Override
                        public void run() {
                            waitForMove();
                        }
                    }
                    Thread t = new Thread(new Waiting_for_replay_thread());
                    t.start();
                }
            }
        }
        else if (playerTurn-1 == 1) {
            turnOver = board.play(playerTurn, playerTurn-1, 0);
            if(turnOver == 1) {
                changePlayer(playerTurn);
            }
        }
        updateBoard();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton17quit_clicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17quit_clicked
        // TODO add your handling code here:
        if(isOnline)
        {
            sendMessage("quit");
        }
        this.dispose();
    }//GEN-LAST:event_jButton17quit_clicked

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
    private javax.swing.JButton jButton17;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
