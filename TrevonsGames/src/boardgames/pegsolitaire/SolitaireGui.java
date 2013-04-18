/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.pegSolitaire;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author Tom
 */
public class SolitaireGui extends javax.swing.JFrame {

    /**
     * Creates new form SolitaireGui
     */

    
    int BOARDSIZE;
    SolitaireBoard b;
    int state;
    boolean game_is_over;
    boolean disable_buttons;
    JButton firstChoice;
    JButton middleButton;
    ArrayList<ArrayList<JButton>> buts;
    Integer move_counter;
    
    ArrayList<JButton> column1;
    ArrayList<JButton> column2;
    ArrayList<JButton> column3;
    ArrayList<JButton> column4;
    ArrayList<JButton> column5;
    ArrayList<JButton> column6;
    ArrayList<JButton> column7;

    int x1;
    int x2;

    int y1;
    int y2;
    
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
    
    public SolitaireGui(boolean online, byte[] ip) {
        initComponents();
        
        b = new SolitaireBoard();
        state = 0;
        game_is_over = false;
        disable_buttons = false;
        firstChoice = new JButton();
        middleButton = new JButton();
        buts = new ArrayList<>();
        myTurn = false;
        BOARDSIZE = 7;
        move_counter = 0;
        x1 = 0;
        x2 = 0;
        y1 = 0;
        y2 = 0;
        init_buttons();
        
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
    
    final void init_buttons()
    {
        column1 = new ArrayList<>();
        column2 = new ArrayList<>();
        column3 = new ArrayList<>();
        column4 = new ArrayList<>();
        column5 = new ArrayList<>();
        column6 = new ArrayList<>();
        column7 = new ArrayList<>();
        
        JButton offBoard = new JButton(); //used to represent the buttons that would be on the screen if it were a full board
        offBoard.setName("offBoard");
        
        column1.add(offBoard);
        column1.add(offBoard);
        column1.add(jButton1);
        column1.add(jButton3);
        column1.add(jButton2);
        column1.add(offBoard);
        column1.add(offBoard);
        buts.add(column1);
        
        column2.add(offBoard);
        column2.add(offBoard);
        column2.add(jButton4);
        column2.add(jButton5);
        column2.add(jButton9);
        column2.add(offBoard);
        column2.add(offBoard);
        buts.add(column2);
        
        column3.add(jButton13);
        column3.add(jButton12);
        column3.add(jButton8);
        column3.add(jButton7);
        column3.add(jButton6);
        column3.add(jButton10);
        column3.add(jButton11);
        buts.add(column3);
        
        column4.add(jButton15);
        column4.add(jButton16);
        column4.add(jButton17);
        column4.add(jButton19);
        column4.add(jButton14);
        column4.add(jButton25);
        column4.add(jButton26);
        buts.add(column4);
        
        column5.add(jButton30);
        column5.add(jButton28);
        column5.add(jButton18);
        column5.add(jButton20);
        column5.add(jButton23);
        column5.add(jButton27);
        column5.add(jButton29);
        buts.add(column5);
        
        column6.add(offBoard);
        column6.add(offBoard);
        column6.add(jButton21);
        column6.add(jButton22);
        column6.add(jButton24);
        column6.add(offBoard);
        column6.add(offBoard);
        buts.add(column6);
        
        column7.add(offBoard);
        column7.add(offBoard);
        column7.add(jButton31);
        column7.add(jButton32);
        column7.add(jButton33);
        column7.add(offBoard);
        column7.add(offBoard);
        buts.add(column7);
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
            
            out.writeObject("sol");
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
            SolitaireMove otherPlayerMove = getMoveFromString(msg);
            b.make_move(otherPlayerMove);

            firstChoice = buts.get((-otherPlayerMove.src.y)+3).get((otherPlayerMove.src.x)+3);
            middleButton = buts.get((-otherPlayerMove.middle.y)+3).get((otherPlayerMove.middle.x)+3);
            JButton clicked = buts.get((-otherPlayerMove.dest.y)+3).get((otherPlayerMove.dest.x)+3);
            apply_move_to_graphics(clicked);
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
    SolitaireMove getMoveFromString(String s)
    {
        int srcx = Integer.parseInt(s.substring(0, 2));
        int srcy = Integer.parseInt(s.substring(2, 4));
        int destx = Integer.parseInt(s.substring(4, 6));
        int desty = Integer.parseInt(s.substring(6, 8));
        int midx = Integer.parseInt(s.substring(8, 10));
        int midy = Integer.parseInt(s.substring(10, 12));
        //handle negative numbers
        srcx-=20;
        srcy-=20;
        destx-=20;
        desty-=20;
        midx-=20;
        midy-=20;
        
        //break the string into the 3 coordinates
        SolitaireCoordinate src = new SolitaireCoordinate(srcx,srcy,true,true);
        SolitaireCoordinate dest = new SolitaireCoordinate(destx,desty,true,true);
        SolitaireCoordinate mid = new SolitaireCoordinate(midx,midy,true,true);
        //set the parts of a local move to the 3 coordinates
        SolitaireMove otherPlayerMove = new SolitaireMove(src,dest,mid);
        return otherPlayerMove;
    }
    //end2 for online play******************************************************************************************************
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        moves_textbox = new javax.swing.JTextArea();
        quit_button = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton19.setBackground(new java.awt.Color(255, 255, 255));
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton20.setBackground(new java.awt.Color(0, 0, 255));
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(0, 0, 255));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(0, 0, 255));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(0, 0, 255));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(0, 0, 255));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton17.setBackground(new java.awt.Color(0, 0, 255));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(0, 0, 255));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(0, 0, 255));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(0, 0, 255));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton30.setBackground(new java.awt.Color(0, 0, 255));
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(0, 0, 255));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(0, 0, 255));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 0, 255));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 0, 255));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton22.setBackground(new java.awt.Color(0, 0, 255));
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 0, 255));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton23.setBackground(new java.awt.Color(0, 0, 255));
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 0, 255));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton24.setBackground(new java.awt.Color(0, 0, 255));
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton25.setBackground(new java.awt.Color(0, 0, 255));
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(0, 0, 255));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton26.setBackground(new java.awt.Color(0, 0, 255));
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton27.setBackground(new java.awt.Color(0, 0, 255));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 0, 255));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton31.setBackground(new java.awt.Color(0, 0, 255));
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton28.setBackground(new java.awt.Color(0, 0, 255));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 255));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton32.setBackground(new java.awt.Color(0, 0, 255));
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton29.setBackground(new java.awt.Color(0, 0, 255));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 0, 255));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton34.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton34.setText("Hint");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHint_Clicked(evt);
            }
        });

        jButton33.setBackground(new java.awt.Color(0, 0, 255));
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jButton21.setBackground(new java.awt.Color(0, 0, 255));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        moves_textbox.setColumns(20);
        moves_textbox.setRows(5);
        moves_textbox.setText("Number of Moves: 0");
        jScrollPane1.setViewportView(moves_textbox);

        quit_button.setText("Quit");
        quit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quit_buttonClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addComponent(quit_button, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(quit_button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonHint_Clicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHint_Clicked
        // TODO add your handling code here:
        final ArrayList<SolitaireMove> moves = b.get_all_possible_moves();

        for(int i = 0; i<moves.size(); i++)
        {
            //JButton middleButton = buts.get((-moves.get(i).middle.y)+3).get((moves.get(i).middle.x)+3);
            JButton src = buts.get((-moves.get(i).src.y)+3).get((moves.get(i).src.x)+3);
            //JButton dest = buts.get((-moves.get(i).dest.y)+3).get((moves.get(i).dest.x)+3);

            src.setBackground(Color.red);
            //need a way to go through and reset these to blue afterward
        }

        java.util.Timer timer = new java.util.Timer("blink buttons");

        class MyTask extends TimerTask
        {
            private int times = 0;
            @Override
            public void run()
            {
                disable_buttons = true;
                if (times < 6)
                {
                    if(times%2 == 0)
                    {
                        for(int i = 0; i<moves.size(); i++)
                        {
                            JButton src = buts.get((-moves.get(i).src.y)+3).get((moves.get(i).src.x)+3);
                            src.setBackground(Color.red);
                        }
                    }
                    else
                    {
                        for(int i = 0; i<moves.size(); i++)
                        {
                            JButton src = buts.get((-moves.get(i).src.y)+3).get((moves.get(i).src.x)+3);
                            src.setBackground(Color.blue);
                        }
                    }
                    times++;
                }
                else
                {
                    System.out.println("Timer stops now...");
                    disable_buttons = false;
                    System.out.println("Buttons re-enabled");
                    this.cancel();  //stops the timer
                }
            }
        }

        timer.schedule(new MyTask(), 0, 300);

    }//GEN-LAST:event_jButtonHint_Clicked

    
    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActionPerformed
        // TODO add your handling code here:
        if(!game_is_over && !disable_buttons && (isOnline && myTurn) || (!isOnline))
        {
            JButton clicked = (JButton)evt.getSource();

            if (state == 0)
            {
                x1 = getX(clicked);
                y1 = getY(clicked);
                SolitaireCoordinate c = b.getCoordinate(x1, y1);
                if(c.filled)
                {
                    state = 1;
                    (clicked).setBackground(Color.GREEN);  //set selected to green
                    firstChoice = clicked;
                }
                else
                {
                    System.out.println("Invalid Source: no peg at "+x1+", "+y1);
                }
            }
            else
            {
                if (clicked.equals(firstChoice))
                {
                    clicked.setBackground(Color.BLUE);  //set selected to darkblue
                    state = 0;
                }
                else
                {
                    state = 0;
                    x2 = getX(clicked);
                    y2 = getY(clicked);
                        //System.out.println("x1"+x1+" y1"+y1+" x2"+x2+" y2"+y2);
                    SolitaireCoordinate c1 = b.getCoordinate(x1, y1);
                    SolitaireCoordinate c2 = b.getCoordinate(x2, y2);
                    if (c1 != null && c2 != null)
                    {
                        //input was good, proceed to make the move if possible
                        SolitaireCoordinate moveMade = b.move(c1, c2);

                        if (moveMade == null)
                        {
                            //print error text that move was bad (bad input)
                            System.out.println("Invalid Jump Attempted");
                            firstChoice.setBackground(Color.BLUE);
                        }
                        else
                        {
                            middleButton = buts.get((-moveMade.y)+3).get((moveMade.x)+3);
                            apply_move_to_graphics(clicked);
                            this.paintAll(this.getGraphics());  //repaints the current JFrame and it's components
                            move_counter++;
                            moves_textbox.setText("Number of Moves: "+move_counter.toString());
                            
                            //start3 for online play******************************************************************************************************
                            if(isOnline)
                            {
                               myTurn = false;
                               SolitaireMove moveToSend = new SolitaireMove(c1, c2, moveMade);
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
                        }
                    }
                }
            }
        
            if(game_over())
            {
                game_is_over = true;
            }
        }
        else if(!myTurn)
        {
            System.out.println("Waiting for opponent to make a move");
            
        }
        else if(disable_buttons)
        {
            System.out.println("Buttons Disabled while hint is flashing");
        }
        else
        {
            System.out.println("Game is over");
        }
    }//GEN-LAST:event_jButtonActionPerformed

    //start4 for online play******************************************************************************************************
    private void quit_buttonClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quit_buttonClicked
        // TODO add your handling code here:
        
        //send message to other player that you quit (it should sit in their queue if it is currently their turn and theyll get it when they make a move
        if(isOnline)
        {
            sendMessage("quit");
        }
        this.dispose();
    }//GEN-LAST:event_quit_buttonClicked
    //end4 for online play******************************************************************************************************
    
    private int getX(JButton r)
    {
        for (int i = 0; i < buts.size(); i++) //columns or x
        {
            for (int j = 0; j < buts.get(i).size(); j++) //rows or y
            {
                if (r.equals(buts.get(i).get(j)))
                {
                    return j-3;
                }
            }
        }
        return -99;
    }

    private int getY(JButton r)
    {
        for (int i = 0; i < buts.size(); i++) //columns or x
        {
            for (int j = 0; j < buts.get(i).size(); j++) //rows or y
            {
                if (r.equals(buts.get(i).get(j)))
                {
                    return -(i-3);
                }
            }
        }
        return -99;
    }
    
    private void apply_move_to_graphics(JButton destination)
    {
        //firstChoice is the source
        //middleButton is the middle
        
        destination.setBackground(Color.BLUE);  //set dest to filled
        firstChoice.setBackground(Color.WHITE);  //set source to empty
        middleButton.setBackground(Color.WHITE);  //set middel to empty
    }
    
    boolean game_over()
    {
        if(b.win())
        {
            System.out.println("WINNER!");
            return true;
        }
        else if(b.lose())
        {
            System.out.println("LOSER!");
            return true;
        }
        
        
        return false;

    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextArea moves_textbox;
    private javax.swing.JButton quit_button;
    // End of variables declaration//GEN-END:variables
}
