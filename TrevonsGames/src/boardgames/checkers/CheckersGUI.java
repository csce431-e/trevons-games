/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.checkers;

import boardgames.pegSolitaire.SolitaireCoordinate;
import boardgames.pegSolitaire.SolitaireMove;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 *
 * @author Trevon
 */
public class CheckersGUI extends javax.swing.JFrame {

    /**
     * Creates new form CheckersGUI
     */
    
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
    
    CheckersGame game;
    int BOARDSIZE;
    
    CheckersCell source;
    CheckersCell destination;
    CheckersMove currentMove;
    StatusUpdate statusThread;
    
    //boolean gameStarted;
    boolean firstSelection;
    boolean disableButtons;
    boolean showMoves;
    //Owner turn;
    
    ArrayList<ArrayList<JButton>> guiBoard;
    
     public void playGame() {
         
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CheckersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CheckersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CheckersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CheckersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                new CheckersGUI().setVisible(true);
            }
        });*/
        
    }
    
     public CheckersGUI(boolean online, byte[] ip)
    {
        initComponents();
        game = new CheckersGame();
        game.initCheckers(true);
        guiBoard = new ArrayList<ArrayList<JButton>>();
        
        BOARDSIZE = 8;
        firstSelection = true;
        game.turn = Owner.PLAYER1;
        game.turnCompleted = false;
        game.AI = false;
        disableButtons = true;
        //gameStarted = false;
        showMoves = false;
        //startButton.requestFocus();
        init_buttons();
        updateBoard();
        
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
     
    public CheckersGUI(boolean isAIGame)
    {
        initComponents();
        game = new CheckersGame();
        game.initCheckers(true);
        guiBoard = new ArrayList<ArrayList<JButton>>();
        
        BOARDSIZE = 8;
        firstSelection = true;
        game.turn = Owner.PLAYER1;
        game.turnCompleted = false;
        game.AI = isAIGame;
        disableButtons = false;
        //gameStarted = false;
        showMoves = false;
        //startButton.requestFocus();
        //game.setStatus("Current Turn: " + game.turn.toString());
        init_buttons();
        updateBoard();
    }
    
    //sets up guiBoard
    public void init_buttons()
    {
        
        for(int i=0;i<BOARDSIZE;i++)
        {
            guiBoard.add(new ArrayList<JButton>());
        }
        
        guiBoard.get(0).add(jButton1);
        guiBoard.get(0).add(jButton2);
        guiBoard.get(0).add(jButton3);
        guiBoard.get(0).add(jButton4);
        guiBoard.get(0).add(jButton5);
        guiBoard.get(0).add(jButton6);
        guiBoard.get(0).add(jButton7);
        guiBoard.get(0).add(jButton8);
        
        guiBoard.get(1).add(jButton9);
        guiBoard.get(1).add(jButton10);
        guiBoard.get(1).add(jButton11);
        guiBoard.get(1).add(jButton12);
        guiBoard.get(1).add(jButton13);
        guiBoard.get(1).add(jButton14);
        guiBoard.get(1).add(jButton15);
        guiBoard.get(1).add(jButton16);
        
        guiBoard.get(2).add(jButton17);
        guiBoard.get(2).add(jButton18);
        guiBoard.get(2).add(jButton19);
        guiBoard.get(2).add(jButton20);
        guiBoard.get(2).add(jButton21);
        guiBoard.get(2).add(jButton22);
        guiBoard.get(2).add(jButton23);
        guiBoard.get(2).add(jButton24);
        
        guiBoard.get(3).add(jButton25);
        guiBoard.get(3).add(jButton26);
        guiBoard.get(3).add(jButton27);
        guiBoard.get(3).add(jButton28);
        guiBoard.get(3).add(jButton29);
        guiBoard.get(3).add(jButton30);
        guiBoard.get(3).add(jButton31);
        guiBoard.get(3).add(jButton32);
       
        guiBoard.get(4).add(jButton33);
        guiBoard.get(4).add(jButton34);
        guiBoard.get(4).add(jButton35);
        guiBoard.get(4).add(jButton36);
        guiBoard.get(4).add(jButton37);
        guiBoard.get(4).add(jButton38);
        guiBoard.get(4).add(jButton39);
        guiBoard.get(4).add(jButton40);
        
        guiBoard.get(5).add(jButton41);
        guiBoard.get(5).add(jButton42);
        guiBoard.get(5).add(jButton43);
        guiBoard.get(5).add(jButton44);
        guiBoard.get(5).add(jButton45);
        guiBoard.get(5).add(jButton46);
        guiBoard.get(5).add(jButton47);
        guiBoard.get(5).add(jButton48);
        
        guiBoard.get(6).add(jButton49);
        guiBoard.get(6).add(jButton50);
        guiBoard.get(6).add(jButton51);
        guiBoard.get(6).add(jButton52);
        guiBoard.get(6).add(jButton53);
        guiBoard.get(6).add(jButton54);
        guiBoard.get(6).add(jButton55);
        guiBoard.get(6).add(jButton56);
        
        guiBoard.get(7).add(jButton57);
        guiBoard.get(7).add(jButton58);
        guiBoard.get(7).add(jButton59);
        guiBoard.get(7).add(jButton60);
        guiBoard.get(7).add(jButton61);
        guiBoard.get(7).add(jButton62);
        guiBoard.get(7).add(jButton63);
        guiBoard.get(7).add(jButton64);
        
        addActionListenerToButtons();
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
            
            out.writeObject("checkers");
            System.out.println("waiting for response from server");
            String msg = (String)in.readObject(); //waiting or starting
            System.out.println("readin: "+ msg);

            if(msg.equals("waiting"))
            {
                game.turn = Owner.PLAYER1;
                
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

                disableButtons = false;
                myTurn = true;
                System.out.println("its my turn");
                game.setStatus("its my turn");
                //statusTextArea.setText("its my turn");
            }
            else
            {
                game.turn = Owner.PLAYER1;
                disableButtons = true;
                myTurn = false; 
                System.out.println("its NOT my turn");
                game.setStatus("its NOT my turn");
                //statusTextArea.setText("its NOT my turn");
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
            
            disableButtons = true;
            //removeActionListenerFromButtons();
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
            
            repaint();
            CheckersMove otherPlayerMove = getMoveFromString(msg);
            //game.b.loadPlayerPieces();
            game.b.makeMove(otherPlayerMove);
            game.setStatus("");
            //addActionListenerToButtons();
            
            game.turn = game.turn.opposite();
            updateBoard();
            repaint();
            
            myTurn = true;
            disableButtons = false;
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
    CheckersMove getMoveFromString(String s)
    {
        int srcx = Integer.parseInt(s.substring(0, 1));
        int srcy = Integer.parseInt(s.substring(1, 2));
        int destx = Integer.parseInt(s.substring(2, 3));
        int desty = Integer.parseInt(s.substring(3, 4));

        int midx;
        int midy;
        
        CheckersCell source = game.b.board.get(srcx).get(srcy);
        CheckersCell mid;
        CheckersCell dest = game.b.board.get(destx).get(desty);
        
        CheckersMove otherPlayerMove = new CheckersMove(source,dest,game.b);
        if(s.length() > 4)
        {
            midx = destx;
            midy = desty;
                    
            destx = Integer.parseInt(s.substring(4, 5));
            desty = Integer.parseInt(s.substring(5, 6));
            
            mid = game.b.board.get(midx).get(midy);
            dest = game.b.board.get(destx).get(desty);
                      
            otherPlayerMove = new CheckersJump(source,mid,dest,game.b);
        }
        
        return otherPlayerMove;
    }
    //end2 for online play******************************************************************************************************
    
    
    void addActionListenerToButtons()
    {
        //Start button
        /*startButton.addActionListener(new java.awt.event.ActionListener()
                {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        startHandler(evt);
                    }
                });*/
        //ShowMoves button
        showMovesButton.addActionListener(new java.awt.event.ActionListener()
                {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        showMovesHandler(evt);
                    }
                });
        
        quitButton.addActionListener(new java.awt.event.ActionListener()
                {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        quit_buttonClicked(evt);
                    }
                });
        
        //CheckersBoard buttons
        for(ArrayList<JButton> row: guiBoard)
        {
            for(JButton j: row)
            {
                j.addActionListener(new java.awt.event.ActionListener() 
                {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        actionHandler(evt);
                    }
                });
            }
        }
    }
    
    void removeActionListenerFromButtons()
    {
        //Start button
        //startButton.removeActionListener(startButton.getActionListeners()[0]);
        //ShowMoves button
        showMovesButton.removeActionListener(showMovesButton.getActionListeners()[0]);
        
        //CheckersBoard buttons
        for(ArrayList<JButton> row: guiBoard)
        {
            for(JButton j: row)
            {
                j.removeActionListener(j.getActionListeners()[0]);
            }
        }
    }
    
    public class StatusUpdate implements Runnable
    {
        
        volatile boolean finished = false;
        
        public void run()
        {
            String s = "";
            while(!finished)
            {
                String temp = game.status;
                if(!s.equals(temp))
                {
                    s = temp;
                    statusTextArea.setText(s);
                }
            }
                
        }
        
    }
    
   /* public class DelayPaintTask extends TimerTask
    {
        public void run()
        {
            redrawBoard();
            
        }
    }*/
    
    
    
    void updateBoard()
    {
        if(statusThread == null)
        {
            statusThread = new StatusUpdate();
            (new Thread(statusThread)).start();
        }
        
        for(int i=0;i<BOARDSIZE;i++)
        {
            for(int j=0;j<BOARDSIZE;j++)
            {
                CheckersCell c = game.b.board.get(i).get(j);
                Owner o = c.getOwner(); 
                JButton b = guiBoard.get(i).get(j);
                if(o == Owner.PLAYER1)
                {
                    b.setBackground(Color.BLACK);
                }
                else if(o == Owner.PLAYER2)
                {
                     b.setBackground(Color.RED);
                }
                else if(o == Owner.EMPTY)
                {
                    b.setBackground(Color.LIGHT_GRAY);
                }
                
                if(c.isKing())
                {
                    b.setForeground(Color.WHITE);
                    b.setText("K");
                }
                
                if(!c.isKing())
                {
                    b.setText(null);
                }
                    
            }
        }
        
        setTurnButton();
        //repaint();
        if(game.checkGameOver())
        {
            disableButtons = true;
            statusThread.finished = true;
        }
    }
    
    public void showAvailableMoves(JButton b)
    {
        updateBoard();
        CheckersCell c = getCellFromButton(b);
        if(c.getOwner() == game.turn)
        {
            ArrayList<CheckersJump> allJumps = game.b.getJumpMoves();
            ArrayList<CheckersMove> moves = c.getMoves();
            ArrayList<CheckersJump> jumps = c.getJumps();

            if(allJumps.isEmpty())
            {
                for(int i=0;i<moves.size();i++)
                {
                    JButton button = getButtonFromCell(moves.get(i).dest);
                    button.setBackground(Color.blue);
                }
            }
            else
            {
                for(int i=0;i<jumps.size();i++)
                {
                    JButton button = getButtonFromCell(jumps.get(i).dest);
                    button.setBackground(Color.blue);
                }
            }
        }
    }
    
    JButton getButtonFromCell(CheckersCell c)
    {
        for(int i=0;i<BOARDSIZE; i++)
        {
            for(int j=0;j<BOARDSIZE;j++)
            {
               if(c.equals(game.b.board.get(i).get(j)))
               {
                   return guiBoard.get(i).get(j);
               }                    
            }
        }
        return new JButton();
    }
    
    CheckersCell getCellFromButton(JButton b)
    {
        for(int i=0;i<BOARDSIZE; i++)
        {
            for(int j=0;j<BOARDSIZE;j++)
            {
               if(b.equals(guiBoard.get(i).get(j)))
               {
                   return game.b.board.get(i).get(j);
               }                    
            }
        }
        return new CheckersCell(-1,-1);
    }
    
    private void showMovesHandler(java.awt.event.ActionEvent evt)
    {
        showMoves ^= true;
        updateBoard();
    }
    
    /*private void startHandler(java.awt.event.ActionEvent evt)
    {
        if(!isOnline)
        {
            disableButtons = false;
        }
        //statusThread = new StatusUpdate();
        if(game.turn == Owner.PLAYER1)
        {
            jButton65.setBackground(Color.BLACK);
        }
        else if(game.turn == Owner.PLAYER2)
        {
            jButton65.setBackground(Color.RED);
        }
        //(new Thread(statusThread)).start();
    }*/
    
    private void actionHandler(java.awt.event.ActionEvent evt)
    {
        JButton b = (JButton) evt.getSource();
        CheckersCell c = getCellFromButton(b);
        
        if(!disableButtons && !game.checkGameOver())
        {
            //if jumps is > 0 then 
            if(!firstSelection && c.getOwner() == source.getOwner())
            {
                firstSelection = true;
            }
            //allow double jumps without a reclick of jumping piece
            if(game.b.anotherJump)
            {
                source = destination;
                firstSelection = false;
            }

            if(firstSelection && c.getOwner() != Owner.EMPTY)
            {
                if(showMoves)
                {
                    showAvailableMoves(b);
                }
                source = c;
                firstSelection = false;
            }
            else if(!firstSelection)
            {
                destination = getCellFromButton(b);
                if(Math.abs(source.x-destination.x) > 1)
                {
                    currentMove = new CheckersJump(source,
                            source.getMid(source,destination),destination,game.b);
                }
                else
                {
                    currentMove = new CheckersMove(source, destination,game.b);
                }

                if(game.b.makeMove(currentMove) && !game.b.anotherJump)
                {
                    game.turnCompleted = true;
                    
                    switchTurns(currentMove);
                    if(game.AI && game.turn == Owner.PLAYER2)
                    {
                        do
                        {
                            CheckersMove aiMove = game.sendAIMove();
                            if(!game.b.anotherJump)
                            {
                                game.turnCompleted = true;
                                switchTurns(aiMove);
                            }
                        }while(game.b.anotherJump);
                    }
                    //game.setStatus("Current Turn: " + game.turn.toString());
                    /*try {
                        Thread.sleep(0);
                        //game.turnCompleted = false;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CheckersGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                }
                updateBoard();
                if(game.b.anotherJump)
                {
                    if(showMoves)
                    {
                        showAvailableMoves(getButtonFromCell(destination));
                    }
                }
                firstSelection = true;
            }
        }
    }
    
    public void setTurnButton()
    {
        //game.setStatus("Current Turn: " + game.turn.toString());
        if(game.turn == Owner.PLAYER1)
        {
            jButton65.setBackground(Color.BLACK);
        }
        else if(game.turn == Owner.PLAYER2)
        {
            jButton65.setBackground(Color.RED);
        }
    }
  
    
    public void switchTurns(CheckersMove moveToSend)
    {
        if(game.turnCompleted)
        {
            game.setStatus("");
            game.turn = game.turn.opposite();
            //game.setStatus("Current Turn: " + game.turn.toString());
            updateBoard();
            //game.b.storePlayerPieces();
            //game.b.clearPlayerPieces();
            game.turnCompleted = false;
            //start3 for online play******************************************************************************************************
            if(isOnline)
            {
               myTurn = false;
               disableButtons = true;
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
    
    //start4 for online play******************************************************************************************************
    private void quit_buttonClicked(java.awt.event.ActionEvent evt) {                                    
        // TODO add your handling code here:
        
        //send message to other player that you quit (it should sit in their queue if it is currently their turn and theyll get it when they make a move
        if(isOnline)
        {
            sendMessage("quit");
        }
        
        game.b.clearPlayerPieces();
        
        this.dispose();
    }                                   
    //end4 for online play******************************************************************************************************
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
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
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        jButton62 = new javax.swing.JButton();
        jButton63 = new javax.swing.JButton();
        jButton64 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        statusTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        showMovesButton = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jButton65 = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton2.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton3.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton4.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton5.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton6.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton7.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton8.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton9.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton10.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton11.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton12.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton13.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton14.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton15.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton16.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton17.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton18.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton19.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton20.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton21.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton22.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton23.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton24.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton25.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton26.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton27.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton28.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton29.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton30.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton31.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton32.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton33.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton34.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton35.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton36.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton37.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton38.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton39.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton40.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton41.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton42.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton43.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton44.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton45.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton46.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton47.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton48.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton49.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton51.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton50.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton52.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton53.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton55.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton54.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton56.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton57.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton58.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton59.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton60.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton61.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton62.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton63.setPreferredSize(new java.awt.Dimension(50, 50));

        jButton64.setPreferredSize(new java.awt.Dimension(50, 50));

        statusTextArea.setColumns(20);
        statusTextArea.setLineWrap(true);
        statusTextArea.setRows(2);
        statusTextArea.setName(""); // NOI18N
        jScrollPane2.setViewportView(statusTextArea);

        jLabel1.setText("Status");

        showMovesButton.setText("Show Moves");
        showMovesButton.setMaximumSize(new java.awt.Dimension(97, 23));

        jLabel2.setText("Turn");

        jButton65.setPreferredSize(new java.awt.Dimension(33, 33));

        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                quitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton65, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                            .addComponent(quitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(showMovesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(showMovesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jButton65, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(quitButton)))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_quitButtonActionPerformed
    {//GEN-HEADEREND:event_quitButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        System.out.println("hey");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CheckersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CheckersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CheckersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CheckersGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                new CheckersGUI().setVisible(true);
            }
        });*/
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
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton65;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton quitButton;
    private javax.swing.JToggleButton showMovesButton;
    private javax.swing.JTextArea statusTextArea;
    // End of variables declaration//GEN-END:variables
}
