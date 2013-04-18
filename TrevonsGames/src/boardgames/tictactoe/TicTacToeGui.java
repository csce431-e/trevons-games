/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.tictactoe;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author Tom
 */
public class TicTacToeGui extends javax.swing.JFrame {

    /**
     * Creates new form TicTacToeGui
     */
    TicTacToeGame game;
    Integer turnCount;
    ArrayList<JButton> buttons;
    
     //start0 for online play******************************************************************************************************
    boolean isOnline;
    byte [] serverIP;
    public boolean myTurn;
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    public boolean iquit;
    public final JFrame wait_window = new JFrame("Waiting for an opponent");
    //end0 for online play*********************************************************************************************
    
    public TicTacToeGui(boolean online, byte[] ip) {
        initComponents();
        
        game = new TicTacToeGame();
        turnCount = 0;
        
        buttons = new ArrayList<>();
        buttons.add(jButton1);
        buttons.add(jButton2);
        buttons.add(jButton3);
        buttons.add(jButton4);
        buttons.add(jButton5);
        buttons.add(jButton6);
        buttons.add(jButton7);
        buttons.add(jButton8);
        buttons.add(jButton9);
        
        //start1 for online play******************************************************************************************************
        isOnline = online;
        if(isOnline)
        {
            serverIP = ip;
            iquit = false;
            setup_client_socket();
        }
        //end1 for online play*****************************************************************************************
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
            
            out.writeObject("tic");
            System.out.println("waiting for response from server");
            String msg = (String)in.readObject(); //waiting or starting
            System.out.println("readin: "+ msg);

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
                System.out.println("its my turn");
            }
            else
            {
                myTurn = false; 
                System.out.println("its NOT my turn");
            }
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
            JButton but = getMoveFromString(msg);
            make_move(but);

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
    JButton getMoveFromString(String s)
    {
        int xpos = Integer.parseInt(s.substring(0, 1));
        int ypos = Integer.parseInt(s.substring(1, 2));
        
        return get_button(xpos,ypos);
    }
    
    JButton get_button(int x, int y)
    {
        return buttons.get(x + (3*y));
    }
    //end2 for online play********************************************************************************************
    
    Coordinate get_coordinate(JButton but)
    {
        if(but.equals(jButton1))
        {
            return new Coordinate(0,0);
        }
        else if(but.equals(jButton2))
        {
            return new Coordinate(0,1);
        }
        else if(but.equals(jButton3))
        {
            return new Coordinate(0,2);
        }
        else if(but.equals(jButton4))
        {
            return new Coordinate(1,0);
        }
        else if(but.equals(jButton5))
        {
            return new Coordinate(1,1);
        }
        else if(but.equals(jButton6))
        {
            return new Coordinate(1,2);
        }
        else if(but.equals(jButton7))
        {
            return new Coordinate(2,0);
        }
        else if(but.equals(jButton8))
        {
            return new Coordinate(2,1);
        }
        else if(but.equals(jButton9))
        {
            return new Coordinate(2,2);
        }
        return new Coordinate(-1,-1);
    }
    
    boolean check_win(JButton but, int player)
    {
        Coordinate c = get_coordinate(but);
        int xpos = c.x;
        int ypos = c.y;
        int count = 0;
        System.out.println(""+c.x + c.y);
        
        //horizontal - left  ========================================
        while(is_valid(xpos,ypos))
        {
            if(game.board.get(xpos).get(ypos).equals(player))
            {
                xpos--;
                count++;
            }
            else
            {
                break;
            }
        }
        xpos = c.x;
        ypos = c.y;
        count--;
        //horizontal - right
        while(is_valid(xpos,ypos))
        {
            if(game.board.get(xpos).get(ypos).equals(player))
            {
                xpos++;
                count++;
            }
            else
            {
                break;
            }
        }
        if(count >= 3)
        {
            System.err.println("horizontal");
            return true;
        }
            
        //vertical - up  ========================================
        xpos = c.x;
        ypos = c.y;
        count = 0;
        while(is_valid(xpos,ypos))
        {
            if(game.board.get(xpos).get(ypos).equals(player))
            {
                ypos--;
                count++;
            }
            else
            {
                break;
            }
        }
        //vertical - dwn
        xpos = c.x;
        ypos = c.y;
        count--;
        while(is_valid(xpos,ypos))
        {
            if(game.board.get(xpos).get(ypos).equals(player))
            {
                ypos++;;
                count++;
            }
            else
            {
                break;
            }
        }
        if(count >= 3)
        {
            System.err.println("vertical");
            return true;
        }
            
        //upright  ========================================
        xpos = c.x;
        ypos = c.y;
        count = 0;
        while(is_valid(xpos,ypos))
        {
            if(game.board.get(xpos).get(ypos).equals(player))
            {
                xpos++;
                ypos--;
                count++;
            }
            else
            {
                break;
            }
        }
        //downleft
        xpos = c.x;
        ypos = c.y;
        count--;
        while(is_valid(xpos,ypos))
        {
            if(game.board.get(xpos).get(ypos).equals(player))
            {
                xpos--;
                ypos++;
                count++;
            }
            else
            {
                break;
            }
        }
        if(count >= 3)
        {
            System.err.println("upright");
            return true;
        }
            
        //upleft  ========================================
        xpos = c.x;
        ypos = c.y;
         count = 0;
        while(is_valid(xpos,ypos))
        {
            if(game.board.get(xpos).get(ypos).equals(player))
            {
                xpos--;
                ypos--;
                count++;
            }
            else
            {
                break;
            }
        }
        //downright
        xpos = c.x;
        ypos = c.y;
        count--;
        while(is_valid(xpos,ypos))
        {
            if(game.board.get(xpos).get(ypos).equals(player))
            {
                xpos++;
                ypos++;
                count++;
            }
            else
            {
                break;
            }
        }
        if(count >= 3)
        {
            System.err.println("downright");
            return true;
        }
        
        return false;
    }
    
    boolean is_valid(int x, int y)
    {
        if(x < 3 && x >= 0 && y<3 && y>= 0)
        {
            return true;
        }
        return false;
    }
    
    void disable_buttons()
    {
        for(int i = 0; i<buttons.size(); i++)
        {
            buttons.get(i).setEnabled(false);
        }
    }
    
    void make_move(JButton but)
    {
        
        if((isOnline && myTurn) || (!isOnline))
        {
            Coordinate c = get_coordinate(but);
            game.make_move(c.x, c.y, turnCount%2);
            if(turnCount%2 == 0)
            {
                but.setText("O");
                game.board.get(c.x).set(c.y, 0);
            }
            else
            {
                but.setText("X");
                game.board.get(c.x).set(c.y, 1);
            }
            but.setEnabled(false);
            //System.out.println(game.board.toString());

            if(check_win(but, turnCount%2))
            {
                this.setTitle("WIN!!!");
                disable_buttons();
                //this.dispose();
            }

            turnCount++;
        }
        else if(!myTurn)
        {
            System.out.println("Waiting for opponent to make a move");
            
        }
        else
        {
            System.out.println("Game is over");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clicked(evt);
            }
        });

        jButton2.setText("1");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clicked(evt);
            }
        });

        jButton3.setText("1");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clicked(evt);
            }
        });

        jButton4.setText("1");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clicked(evt);
            }
        });

        jButton5.setText("1");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clicked(evt);
            }
        });

        jButton6.setText("1");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clicked(evt);
            }
        });

        jButton7.setText("1");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clicked(evt);
            }
        });

        jButton8.setText("1");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clicked(evt);
            }
        });

        jButton9.setText("1");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clicked(evt);
            }
        });

        jButton10.setText("Quit");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quit_clicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_clicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_clicked
        // TODO add your handling code here:
        
        JButton but = (JButton)evt.getSource();
        
        make_move(but);
    }//GEN-LAST:event_button_clicked

    private void quit_clicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quit_clicked
        // TODO add your handling code here:
        if(isOnline)
        {
            sendMessage("quit");
        }
        this.dispose();
    }//GEN-LAST:event_quit_clicked

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    // End of variables declaration//GEN-END:variables
}
