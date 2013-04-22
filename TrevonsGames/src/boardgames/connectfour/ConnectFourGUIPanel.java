/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.connectfour;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author Jonathan
 */
public class ConnectFourGUIPanel extends javax.swing.JFrame {

    /**
     * Creates new form ConnectFourGUIPanel
     */

    ConnectFourBoard cfBoard;
    int state=0;
    int winner=0;
    int column;
    int rowHeight;
    boolean gameIsOver=false;
    boolean disableButtons=false;
    ArrayList<JButton> columns;
    
    //start0 for online play******************************************************************************************************
    boolean isOnline;
    byte [] serverIP;
    public boolean myTurn;
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    public boolean iquit;
    public final JFrame wait_window = new JFrame("Waiting for an opponent");
    //end0 for online play******************************************************************************************************
    
    
    public ConnectFourGUIPanel(boolean online, byte[] ip) {
        initComponents();
        
        this.setSize(550, 400);
        
        columns = new ArrayList<>();
        
        cfBoard = new ConnectFourBoard();
        
        columns.add(col1);
        columns.add(col2);
        columns.add(col3);
        columns.add(col4);
        columns.add(col5);
        columns.add(col6);
        columns.add(col7);
        
        //start1 for online play******************************************************************************************************
        isOnline = online;
        if(isOnline)
        {
            serverIP = ip;
            iquit = false;
            setup_client_socket();
        }
        //end1 for online play******************************************************************************************************
        
        //System.out.println("Please select column to place piece player @: ");
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
            
            out.writeObject("confour");
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
            
            Integer otherPlayerMove = getMoveFromString(msg);
            cfBoard.move(cfBoard.getTurn(), otherPlayerMove);

            System.out.println("Turn number: " + cfBoard.getTurn());
            
            updateBoard(otherPlayerMove, cfBoard.getTurn()+1);
            
            winner = cfBoard.win();
            if(winner==1) {
                winnerButton.setBackground(Color.BLACK);
                jTextField1.setText("Game Over! Black won!");
                System.out.println("Game Over! Black won!");
                gameIsOver = disableButtons = true;
            }
            if(winner==2) {
                winnerButton.setBackground(Color.RED);
                jTextField1.setText("Game over! Red won!");
                System.out.println("Game over! Red won!");
                gameIsOver = disableButtons = true;
            }
            
            if(cfBoard.checkFull()) {
                jTextField1.setText("Game over! Tie game!");
                System.out.println("Game over! No moves left! It's a tie!");
                gameIsOver = disableButtons = true;
            }
            
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
    int getMoveFromString(String s)
    {
        int otherPlayerMove = Integer.parseInt(s.substring(0, 1));
        
        return otherPlayerMove;
    }
    //end2 for online play******************************************************************************************************
    
    private void updateBoard(int column, int turn) {
        rowHeight = cfBoard.rowHeight(column);
        int color = cfBoard.getColor(rowHeight-1, column-1);
        switch(column) {
            case 1:
                if(rowHeight == 1) {
                    switch(color) {
                        case 1:
                            r1c1.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r1c1.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 2) {
                    switch(color) {
                        case 1:
                            r2c1.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r2c1.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 3) {
                    switch(color) {
                        case 1:
                            r3c1.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r3c1.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 4) {
                    switch(color) {
                        case 1:
                            r4c1.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r4c1.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 5) {
                    switch(color) {
                        case 1:
                            r5c1.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r5c1.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 6) {
                    switch(color) {
                        case 1:
                            r6c1.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r6c1.setBackground(Color.RED);
                            break;
                    }
                }
                break;
            case 2:
                if(rowHeight == 1) {
                    switch(color) {
                        case 1:
                            r1c2.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r1c2.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 2) {
                    switch(color) {
                        case 1:
                            r2c2.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r2c2.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 3) {
                    switch(color) {
                        case 1:
                            r3c2.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r3c2.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 4) {
                    switch(color) {
                        case 1:
                            r4c2.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r4c2.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 5) {
                    switch(color) {
                        case 1:
                            r5c2.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r5c2.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 6) {
                    switch(color) {
                        case 1:
                            r6c2.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r6c2.setBackground(Color.RED);
                            break;
                    }
                }
                break;
            case 3:
                if(rowHeight == 1) {
                    switch(color) {
                        case 1:
                            r1c3.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r1c3.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 2) {
                    switch(color) {
                        case 1:
                            r2c3.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r2c3.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 3) {
                    switch(color) {
                        case 1:
                            r3c3.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r3c3.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 4) {
                    switch(color) {
                        case 1:
                            r4c3.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r4c3.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 5) {
                    switch(color) {
                        case 1:
                            r5c3.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r5c3.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 6) {
                    switch(color) {
                        case 1:
                            r6c3.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r6c3.setBackground(Color.RED);
                            break;
                    }
                }
                break;
            case 4:
                if(rowHeight == 1) {
                    switch(color) {
                        case 1:
                            r1c4.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r1c4.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 2) {
                    switch(color) {
                        case 1:
                            r2c4.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r2c4.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 3) {
                    switch(color) {
                        case 1:
                            r3c4.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r3c4.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 4) {
                    switch(color) {
                        case 1:
                            r4c4.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r4c4.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 5) {
                    switch(color) {
                        case 1:
                            r5c4.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r5c4.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 6) {
                    switch(color) {
                        case 1:
                            r6c4.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r6c4.setBackground(Color.RED);
                            break;
                    }
                }
                break;
            case 5:
                if(rowHeight == 1) {
                    switch(color) {
                        case 1:
                            r1c5.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r1c5.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 2) {
                    switch(color) {
                        case 1:
                            r2c5.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r2c5.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 3) {
                    switch(color) {
                        case 1:
                            r3c5.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r3c5.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 4) {
                    switch(color) {
                        case 1:
                            r4c5.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r4c5.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 5) {
                    switch(color) {
                        case 1:
                            r5c5.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r5c5.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 6) {
                    switch(color) {
                        case 1:
                            r6c5.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r6c5.setBackground(Color.RED);
                            break;
                    }
                }
                break;
            case 6:
                if(rowHeight == 1) {
                    switch(color) {
                        case 1:
                            r1c6.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r1c6.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 2) {
                    switch(color) {
                        case 1:
                            r2c6.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r2c6.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 3) {
                    switch(color) {
                        case 1:
                            r3c6.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r3c6.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 4) {
                    switch(color) {
                        case 1:
                            r4c6.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r4c6.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 5) {
                    switch(color) {
                        case 1:
                            r5c6.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r5c6.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 6) {
                    switch(color) {
                        case 1:
                            r6c6.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r6c6.setBackground(Color.RED);
                            break;
                    }
                }
                break;
            case 7:
            if(rowHeight == 1) {
                    switch(color) {
                        case 1:
                            r1c7.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r1c7.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 2) {
                    switch(color) {
                        case 1:
                            r2c7.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r2c7.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 3) {
                    switch(color) {
                        case 1:
                            r3c7.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r3c7.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 4) {
                    switch(color) {
                        case 1:
                            r4c7.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r4c7.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 5) {
                    switch(color) {
                        case 1:
                            r5c7.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r5c7.setBackground(Color.RED);
                            break;
                    }
                }
                if(rowHeight == 6) {
                    switch(color) {
                        case 1:
                            r6c7.setBackground(Color.BLACK);
                            break;
                        case 2:
                            r6c7.setBackground(Color.RED);
                            break;
                    }
                }
                break;
        }
        
        if (turn%2 ==1) {
            turnButton.setBackground(Color.RED);
            jTextField1.setText("It's Red's turn.");
        }
        if (turn%2 ==0) {
            turnButton.setBackground(Color.BLACK);
            jTextField1.setText("It's Black's turn.");
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

        col1 = new javax.swing.JButton();
        col2 = new javax.swing.JButton();
        col3 = new javax.swing.JButton();
        col4 = new javax.swing.JButton();
        col5 = new javax.swing.JButton();
        col6 = new javax.swing.JButton();
        col7 = new javax.swing.JButton();
        r1c1 = new javax.swing.JButton();
        r2c1 = new javax.swing.JButton();
        r3c1 = new javax.swing.JButton();
        r4c1 = new javax.swing.JButton();
        r5c1 = new javax.swing.JButton();
        r6c1 = new javax.swing.JButton();
        r6c2 = new javax.swing.JButton();
        r6c3 = new javax.swing.JButton();
        r6c4 = new javax.swing.JButton();
        r6c5 = new javax.swing.JButton();
        r6c6 = new javax.swing.JButton();
        r6c7 = new javax.swing.JButton();
        r5c2 = new javax.swing.JButton();
        r5c3 = new javax.swing.JButton();
        r5c4 = new javax.swing.JButton();
        r5c5 = new javax.swing.JButton();
        r5c6 = new javax.swing.JButton();
        r5c7 = new javax.swing.JButton();
        r4c2 = new javax.swing.JButton();
        r4c3 = new javax.swing.JButton();
        r4c4 = new javax.swing.JButton();
        r4c5 = new javax.swing.JButton();
        r4c6 = new javax.swing.JButton();
        r4c7 = new javax.swing.JButton();
        r3c2 = new javax.swing.JButton();
        r3c3 = new javax.swing.JButton();
        r3c4 = new javax.swing.JButton();
        r3c5 = new javax.swing.JButton();
        r3c6 = new javax.swing.JButton();
        r3c7 = new javax.swing.JButton();
        r2c2 = new javax.swing.JButton();
        r2c3 = new javax.swing.JButton();
        r2c4 = new javax.swing.JButton();
        r2c5 = new javax.swing.JButton();
        r2c6 = new javax.swing.JButton();
        r2c7 = new javax.swing.JButton();
        r1c2 = new javax.swing.JButton();
        r1c3 = new javax.swing.JButton();
        r1c4 = new javax.swing.JButton();
        r1c5 = new javax.swing.JButton();
        r1c6 = new javax.swing.JButton();
        r1c7 = new javax.swing.JButton();
        turnText = new javax.swing.JLabel();
        winnerText = new javax.swing.JLabel();
        turnButton = new javax.swing.JButton();
        winnerButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        quitButton = new javax.swing.JButton();

        col1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        col1.setText("1");
        col1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col1ActionPerformed(evt);
            }
        });

        col2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        col2.setText("2");
        col2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col2ActionPerformed(evt);
            }
        });

        col3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        col3.setText("3");
        col3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col3ActionPerformed(evt);
            }
        });

        col4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        col4.setText("4");
        col4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col4ActionPerformed(evt);
            }
        });

        col5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        col5.setText("5");
        col5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col5ActionPerformed(evt);
            }
        });

        col6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        col6.setText("6");
        col6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col6ActionPerformed(evt);
            }
        });

        col7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        col7.setText("7");
        col7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                col7ActionPerformed(evt);
            }
        });

        r1c1.setEnabled(false);

        r2c1.setEnabled(false);

        r3c1.setEnabled(false);

        r4c1.setEnabled(false);

        r5c1.setEnabled(false);

        r6c1.setEnabled(false);

        r6c2.setEnabled(false);

        r6c3.setEnabled(false);

        r6c4.setEnabled(false);

        r6c5.setEnabled(false);

        r6c6.setEnabled(false);

        r6c7.setEnabled(false);

        r5c2.setEnabled(false);

        r5c3.setEnabled(false);

        r5c4.setEnabled(false);

        r5c5.setEnabled(false);

        r5c6.setEnabled(false);

        r5c7.setEnabled(false);

        r4c2.setEnabled(false);

        r4c3.setEnabled(false);

        r4c4.setEnabled(false);

        r4c5.setEnabled(false);

        r4c6.setEnabled(false);

        r4c7.setEnabled(false);

        r3c2.setEnabled(false);

        r3c3.setEnabled(false);

        r3c4.setEnabled(false);

        r3c5.setEnabled(false);

        r3c6.setEnabled(false);

        r3c7.setEnabled(false);

        r2c2.setEnabled(false);

        r2c3.setEnabled(false);

        r2c4.setEnabled(false);

        r2c5.setEnabled(false);

        r2c6.setEnabled(false);

        r2c7.setEnabled(false);

        r1c2.setEnabled(false);

        r1c3.setEnabled(false);

        r1c4.setEnabled(false);

        r1c5.setEnabled(false);

        r1c6.setEnabled(false);

        r1c7.setEnabled(false);

        turnText.setText("Turn:");

        winnerText.setText("Winner:");

        turnButton.setBackground(new java.awt.Color(0, 0, 0));
        turnButton.setEnabled(false);

        winnerButton.setEnabled(false);

        jTextField1.setEditable(false);
        jTextField1.setText("It's Black's turn.");

        quitButton.setText("QUIT");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(r6c1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(r5c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r4c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r3c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r2c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r1c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(col1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(r2c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(r2c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(r2c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(r2c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(r2c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(r2c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(r6c2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(col2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(10, 10, 10)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(r6c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(col3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(r6c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(col4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(r6c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(col5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(r6c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(col6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(r6c7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(col7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(r4c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r4c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r4c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r4c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r4c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r4c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(r5c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r5c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r5c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r5c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r5c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r5c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(r3c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r3c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r3c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r3c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r3c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(r3c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(winnerText)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(winnerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(turnText)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(turnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(r1c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r1c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r1c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r1c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r1c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(r1c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(quitButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(r6c7, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(r6c6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(r6c5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(r5c1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(r5c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(r5c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(r5c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(r5c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(r5c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(r5c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(turnButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(turnText)
                        .addGap(26, 26, 26)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(r4c1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r4c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r4c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r4c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r4c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r4c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r4c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(r3c1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r3c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r3c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r3c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r3c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r3c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r3c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(winnerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(winnerText)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(r2c1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r2c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r2c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r2c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r2c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r2c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r2c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(r1c1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(r1c2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(r1c3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(r1c4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(r1c5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(r1c6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(r1c7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(quitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(col1)
                    .addComponent(col2)
                    .addComponent(col3)
                    .addComponent(col4)
                    .addComponent(col5)
                    .addComponent(col6)
                    .addComponent(col7))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void col1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col1ActionPerformed
        // TODO add your handling code here:
        if(!gameIsOver && !disableButtons && (isOnline && myTurn) || !isOnline) {
            column = 1;
            int turn = cfBoard.getTurn();
            cfBoard.move(turn, column);
            updateBoard(column, turn);
        
            //start3 for online play******************************************************************************************************
            if(isOnline)
            {
                myTurn = false;
                Integer moveToSend = column;
                sendMessage(moveToSend.toString());
                               
                class Waiting_for_replay_thread implements Runnable
                {
                    @Override
                    public void run()
                    {
                        waitForMove();
                    }
                }
                Thread t = new Thread(new Waiting_for_replay_thread());
                t.start();

            }
            //end3 for online play******************************************************************************************************
            
            winner = cfBoard.win();
            if(winner==1) {
                winnerButton.setBackground(Color.BLACK);
                jTextField1.setText("Game Over! Black won!");
                System.out.println("Game Over! Black won!");
                gameIsOver = disableButtons = true;
            }
            if(winner==2) {
                winnerButton.setBackground(Color.RED);
                jTextField1.setText("Game over! Red won!");
                System.out.println("Game over! Red won!");
                gameIsOver = disableButtons = true;
            }
            
            if(cfBoard.checkFull()) {
                jTextField1.setText("Game over! Tie game!");
                System.out.println("Game over! No moves left! It's a tie!");
                gameIsOver = disableButtons = true;
            }
        }
    }//GEN-LAST:event_col1ActionPerformed

    private void col2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col2ActionPerformed
        // TODO add your handling code here:
        if(!gameIsOver && !disableButtons && (isOnline && myTurn) || !isOnline) {
            column = 2;
            int turn = cfBoard.getTurn();
            cfBoard.move(turn, column);
            updateBoard(column, turn);
        
                        //start3 for online play******************************************************************************************************
            if(isOnline)
            {
                myTurn = false;
                Integer moveToSend = column;
                sendMessage(moveToSend.toString());
                               
                class Waiting_for_replay_thread implements Runnable
                {
                    @Override
                    public void run()
                    {
                        waitForMove();
                    }
                }
                Thread t = new Thread(new Waiting_for_replay_thread());
                t.start();

            }
            //end3 for online play******************************************************************************************************
           
            winner = cfBoard.win();
            
            if(winner==1) {
                winnerButton.setBackground(Color.BLACK);
                jTextField1.setText("Game Over! Black won!");
                System.out.println("Game Over! Black won!");
                gameIsOver = disableButtons = true;
            }
            if(winner==2) {
                winnerButton.setBackground(Color.RED);
                jTextField1.setText("Game over! Red won!");
                System.out.println("Game over! Red won!");
                gameIsOver = disableButtons = true;
            }
            
            if(cfBoard.checkFull()) {
                jTextField1.setText("Game over! Tie game!");
                System.out.println("Game over! No moves left! It's a tie!");
                gameIsOver = disableButtons = true;
            }
        }
    }//GEN-LAST:event_col2ActionPerformed

    private void col3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col3ActionPerformed
        // TODO add your handling code here:
        if(!gameIsOver && !disableButtons && (isOnline && myTurn) || !isOnline) {
            column = 3;
            int turn = cfBoard.getTurn();
            cfBoard.move(turn, column);
            updateBoard(column, turn);
        
                        //start3 for online play******************************************************************************************************
            if(isOnline)
            {
                myTurn = false;
                Integer moveToSend = column;
                sendMessage(moveToSend.toString());
                               
                class Waiting_for_replay_thread implements Runnable
                {
                    @Override
                    public void run()
                    {
                        waitForMove();
                    }
                }
                Thread t = new Thread(new Waiting_for_replay_thread());
                t.start();

            }
            //end3 for online play******************************************************************************************************
            
            winner = cfBoard.win();
           
            if(winner==1) {
                winnerButton.setBackground(Color.BLACK);
                jTextField1.setText("Game Over! Black won!");
                System.out.println("Game Over! Black won!");
                gameIsOver = disableButtons = true;
            }
            if(winner==2) {
                winnerButton.setBackground(Color.RED);
                jTextField1.setText("Game over! Red won!");
                System.out.println("Game over! Red won!");
                gameIsOver = disableButtons = true;
            }
            
            if(cfBoard.checkFull()) {
                jTextField1.setText("Game over! Tie game!");
                System.out.println("Game over! No moves left! It's a tie!");
                gameIsOver = disableButtons = true;
            }
        }
    }//GEN-LAST:event_col3ActionPerformed

    private void col4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col4ActionPerformed
        // TODO add your handling code here:
        if(!gameIsOver && !disableButtons && (isOnline && myTurn) || !isOnline) {
            column = 4;
            int turn = cfBoard.getTurn();
            cfBoard.move(turn, column);
            updateBoard(column, turn);
        
                        //start3 for online play******************************************************************************************************
            if(isOnline)
            {
                myTurn = false;
                Integer moveToSend = column;
                sendMessage(moveToSend.toString());
                               
                class Waiting_for_replay_thread implements Runnable
                {
                    @Override
                    public void run()
                    {
                        waitForMove();
                    }
                }
                Thread t = new Thread(new Waiting_for_replay_thread());
                t.start();

            }
            //end3 for online play******************************************************************************************************
                            
            winner = cfBoard.win();
            
            if(winner==1) {
                winnerButton.setBackground(Color.BLACK);
                jTextField1.setText("Game Over! Black won!");
                System.out.println("Game Over! Black won!");
                gameIsOver = disableButtons = true;
            }
            if(winner==2) {
                winnerButton.setBackground(Color.RED);
                jTextField1.setText("Game over! Red won!");
                System.out.println("Game over! Red won!");
                gameIsOver = disableButtons = true;
            }
            
            if(cfBoard.checkFull()) {
                jTextField1.setText("Game over! Tie game!");
                System.out.println("Game over! No moves left! It's a tie!");
                gameIsOver = disableButtons = true;
            }
        }
    }//GEN-LAST:event_col4ActionPerformed

    private void col5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col5ActionPerformed
        // TODO add your handling code here:
        if(!gameIsOver && !disableButtons && (isOnline && myTurn) || !isOnline) {
            column = 5;
            int turn = cfBoard.getTurn();
            cfBoard.move(turn, column);
            updateBoard(column, turn);
        
                        //start3 for online play******************************************************************************************************
            if(isOnline)
            {
                myTurn = false;
                Integer moveToSend = column;
                sendMessage(moveToSend.toString());
                               
                class Waiting_for_replay_thread implements Runnable
                {
                    @Override
                    public void run()
                    {
                        waitForMove();
                    }
                }
                Thread t = new Thread(new Waiting_for_replay_thread());
                t.start();

            }
            //end3 for online play******************************************************************************************************
            
            winner = cfBoard.win();
            
            if(winner==1) {
                winnerButton.setBackground(Color.BLACK);
                jTextField1.setText("Game Over! Black won!");
                System.out.println("Game Over! Black won!");
                gameIsOver = disableButtons = true;
            }
            if(winner==2) {
                winnerButton.setBackground(Color.RED);
                jTextField1.setText("Game over! Red won!");
                System.out.println("Game over! Red won!");
                gameIsOver = disableButtons = true;
            }
            
            if(cfBoard.checkFull()) {
                jTextField1.setText("Game over! Tie game!");
                System.out.println("Game over! No moves left! It's a tie!");
                gameIsOver = disableButtons = true;
            }
        }
    }//GEN-LAST:event_col5ActionPerformed

    private void col6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col6ActionPerformed
        // TODO add your handling code here:
        if(!gameIsOver && !disableButtons && (isOnline && myTurn) || !isOnline) {
            column = 6;
            int turn = cfBoard.getTurn();
            cfBoard.move(turn, column);
            updateBoard(column, turn);
        
                        //start3 for online play******************************************************************************************************
            if(isOnline)
            {
                myTurn = false;
                Integer moveToSend = column;
                sendMessage(moveToSend.toString());
                               
                class Waiting_for_replay_thread implements Runnable
                {
                    @Override
                    public void run()
                    {
                        waitForMove();
                    }
                }
                Thread t = new Thread(new Waiting_for_replay_thread());
                t.start();

            }
            //end3 for online play******************************************************************************************************
        
            winner = cfBoard.win();
            
            
            if(winner==1) {
                winnerButton.setBackground(Color.BLACK);
                jTextField1.setText("Game Over! Black won!");
                System.out.println("Game Over! Black won!");
                gameIsOver = disableButtons = true;
            }
            if(winner==2) {
                winnerButton.setBackground(Color.RED);
                jTextField1.setText("Game over! Red won!");
                System.out.println("Game over! Red won!");
                gameIsOver = disableButtons = true;
            }
            
            if(cfBoard.checkFull()) {
                jTextField1.setText("Game over! Tie game!");
                System.out.println("Game over! No moves left! It's a tie!");
                gameIsOver = disableButtons = true;
            }
        }
    }//GEN-LAST:event_col6ActionPerformed

    private void col7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_col7ActionPerformed
        // TODO add your handling code here:
        if(!gameIsOver && !disableButtons && (isOnline && myTurn) || !isOnline) {
            column = 7;
            int turn = cfBoard.getTurn();
            cfBoard.move(turn, column);
            updateBoard(column, turn);
        
            
            //start3 for online play******************************************************************************************************
            if(isOnline)
            {
                myTurn = false;
                Integer moveToSend = column;
                sendMessage(moveToSend.toString());
                               
                class Waiting_for_replay_thread implements Runnable
                {
                    @Override
                    public void run()
                    {
                        waitForMove();
                    }
                }
                Thread t = new Thread(new Waiting_for_replay_thread());
                t.start();

            }
            //end3 for online play******************************************************************************************************
            
            winner = cfBoard.win();
            
            if(winner==1) {
                winnerButton.setBackground(Color.BLACK);
                jTextField1.setText("Game Over! Black won!");
                System.out.println("Game Over! Black won!");
                gameIsOver = disableButtons = true;
            }
            if(winner==2) {
                winnerButton.setBackground(Color.RED);
                jTextField1.setText("Game over! Red won!");
                System.out.println("Game over! Red won!");
                gameIsOver = disableButtons = true;
            }
            
            if(cfBoard.checkFull()) {
                jTextField1.setText("Game over! Tie game!");
                System.out.println("Game over! No moves left! It's a tie!");
                gameIsOver = disableButtons = true;
            }
        }
    }//GEN-LAST:event_col7ActionPerformed

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        // TODO add your handling code here:
        if(isOnline)
        {
            sendMessage("quit");
        }
        this.dispose();
    }//GEN-LAST:event_quitButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton col1;
    private javax.swing.JButton col2;
    private javax.swing.JButton col3;
    private javax.swing.JButton col4;
    private javax.swing.JButton col5;
    private javax.swing.JButton col6;
    private javax.swing.JButton col7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton r1c1;
    private javax.swing.JButton r1c2;
    private javax.swing.JButton r1c3;
    private javax.swing.JButton r1c4;
    private javax.swing.JButton r1c5;
    private javax.swing.JButton r1c6;
    private javax.swing.JButton r1c7;
    private javax.swing.JButton r2c1;
    private javax.swing.JButton r2c2;
    private javax.swing.JButton r2c3;
    private javax.swing.JButton r2c4;
    private javax.swing.JButton r2c5;
    private javax.swing.JButton r2c6;
    private javax.swing.JButton r2c7;
    private javax.swing.JButton r3c1;
    private javax.swing.JButton r3c2;
    private javax.swing.JButton r3c3;
    private javax.swing.JButton r3c4;
    private javax.swing.JButton r3c5;
    private javax.swing.JButton r3c6;
    private javax.swing.JButton r3c7;
    private javax.swing.JButton r4c1;
    private javax.swing.JButton r4c2;
    private javax.swing.JButton r4c3;
    private javax.swing.JButton r4c4;
    private javax.swing.JButton r4c5;
    private javax.swing.JButton r4c6;
    private javax.swing.JButton r4c7;
    private javax.swing.JButton r5c1;
    private javax.swing.JButton r5c2;
    private javax.swing.JButton r5c3;
    private javax.swing.JButton r5c4;
    private javax.swing.JButton r5c5;
    private javax.swing.JButton r5c6;
    private javax.swing.JButton r5c7;
    private javax.swing.JButton r6c1;
    private javax.swing.JButton r6c2;
    private javax.swing.JButton r6c3;
    private javax.swing.JButton r6c4;
    private javax.swing.JButton r6c5;
    private javax.swing.JButton r6c6;
    private javax.swing.JButton r6c7;
    private javax.swing.JButton turnButton;
    private javax.swing.JLabel turnText;
    private javax.swing.JButton winnerButton;
    private javax.swing.JLabel winnerText;
    // End of variables declaration//GEN-END:variables
}
