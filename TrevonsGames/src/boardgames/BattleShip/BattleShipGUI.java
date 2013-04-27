/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames.BattleShip;

import boardgames.pegSolitaire.SolitaireCoordinate;
import boardgames.pegSolitaire.SolitaireMove;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Mike
 */
public class BattleShipGUI extends javax.swing.JFrame {
    
    //private boolean myTurn=true;
    private boolean turnSwap = false;
    private boolean p1PlacedShips=false;
    private boolean p2PlacedShips=false;
    private boolean placeShips=true;
    private boolean game = true;
    static int currentShip=0;
    BattleShip p1, p2;
    String send = "";
    
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
    
    ArrayList<ArrayList<JButton>> p1button;
    ArrayList<ArrayList<JButton>> p2button;
    
    
    
    
    public BattleShipGUI() {
        initComponents();
        p1button = new ArrayList<ArrayList<JButton>>();
        p2button = new ArrayList<ArrayList<JButton>>();
        
        p1 = new BattleShip();
        p2 = new BattleShip();
        
        for(int x=0;x<10;x++){
            p1button.add(new ArrayList<JButton>());
            p2button.add(new ArrayList<JButton>());
        }
        
        init();
        isOnline = false;
    }
    
    public BattleShipGUI(boolean online, byte[] ip){
        initComponents();
        p1button = new ArrayList<ArrayList<JButton>>();
        p2button = new ArrayList<ArrayList<JButton>>();
        
        p1 = new BattleShip();
        p2 = new BattleShip();
        
        for(int x=0;x<10;x++){
            p1button.add(new ArrayList<JButton>());
            p2button.add(new ArrayList<JButton>());
        }
        
        
        
        //start1 for online play******************************************************************************************************
        isOnline = online;
        if(!online) myTurn=true;
        else myTurn=false;
        if(isOnline)
        {
            serverIP = ip;
            iquit = false;
            setup_client_socket();
        }
        //end1 for online play******************************************************************************************************
        init();
        this.setVisible(true);
        //myTurn=myTurn;
    }
    
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
            
            out.writeObject("battle");
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
            if(placeShips){
                System.out.println("Waiting for move");

                    //msg = (String)in.readObject();
                    //IIS IIS IIS IIS IIS
                    System.out.println("Message received: "+msg);
                    if(msg.equals("Connection successful")){
                        waitForMove();
                    }
                    else if(msg.equals("quit"))
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
                    else{
                        int x = Integer.parseInt(msg.substring(0,1));
                        int y = Integer.parseInt(msg.substring(1,2));
                        String orientation = msg.substring(2,3);
                        switch(orientation.charAt(0)){
                            case 'N': orientation = "NORTH"; break;
                            case 'E': orientation = "EAST"; break;
                            case 'S': orientation = "SOUTH"; break;
                            case 'W': orientation = "WEST"; break;
                        }
                        p2.placeShips(new Ship.point(x,y),currentShip,orientation);
                        currentShip++;

                        x = Integer.parseInt(msg.substring(4,5));
                        y = Integer.parseInt(msg.substring(5,6));
                        orientation = msg.substring(6,7);
                        switch(orientation.charAt(0)){
                            case 'N': orientation = "NORTH"; break;
                            case 'E': orientation = "EAST"; break;
                            case 'S': orientation = "SOUTH"; break;
                            case 'W': orientation = "WEST"; break;
                        }
                        p2.placeShips(new Ship.point(x,y),currentShip,orientation);
                        currentShip++;

                        x = Integer.parseInt(msg.substring(8,9));
                        y = Integer.parseInt(msg.substring(9,10));
                        orientation = msg.substring(10,11);
                        switch(orientation.charAt(0)){
                            case 'N': orientation = "NORTH"; break;
                            case 'E': orientation = "EAST"; break;
                            case 'S': orientation = "SOUTH"; break;
                            case 'W': orientation = "WEST"; break;
                        }
                        p2.placeShips(new Ship.point(x,y),currentShip,orientation);
                        currentShip++;

                        x = Integer.parseInt(msg.substring(12,13));
                        y = Integer.parseInt(msg.substring(13,14));
                        orientation = msg.substring(14,15);
                        switch(orientation.charAt(0)){
                            case 'N': orientation = "NORTH"; break;
                            case 'E': orientation = "EAST"; break;
                            case 'S': orientation = "SOUTH"; break;
                            case 'W': orientation = "WEST"; break;
                        }
                        p2.placeShips(new Ship.point(x,y),currentShip,orientation);
                        currentShip++;

                        x = Integer.parseInt(msg.substring(16,17));
                        y = Integer.parseInt(msg.substring(17,18));
                        orientation = msg.substring(18,19);
                        switch(orientation.charAt(0)){
                            case 'N': orientation = "NORTH"; break;
                            case 'E': orientation = "EAST"; break;
                            case 'S': orientation = "SOUTH"; break;
                            case 'W': orientation = "WEST"; break;
                        }
                        p2.placeShips(new Ship.point(x,y),currentShip,orientation);
                        currentShip++;
                        
                    }
                
                p2PlacedShips=true;
                currentShip=0;
                myTurn=true;
                }
                else{
                Ship.point move = getMoveFromString(msg);            
                p1.attackSpot(move.x, move.y);
                updateP1Board();
                myTurn = true; 
                }
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
    Ship.point getMoveFromString(String s)
    {
        int x = Integer.parseInt(s.substring(0, 1));
        int y = Integer.parseInt(s.substring(1, 2));
        
        return new Ship.point(x,y);
    }
    //end2 for online play******************************************************************************************************
    
    //don't question this, just hope it works
    public void init(){
        
        p1.init();
        p2.init();
        
        jLabel1.setText("Player 1 place ship 1");
        jLabel2.setText(" ");
        
        p1button.get(9).add(jButton1);
        p1button.get(9).add(jButton2);
        p1button.get(9).add(jButton3);
        p1button.get(9).add(jButton4);
        p1button.get(9).add(jButton5);
        p1button.get(9).add(jButton6);
        p1button.get(9).add(jButton7);
        p1button.get(9).add(jButton8);
        p1button.get(9).add(jButton9);
        p1button.get(9).add(jButton10);
        
        p1button.get(8).add(jButton11);
        p1button.get(8).add(jButton12);
        p1button.get(8).add(jButton13);
        p1button.get(8).add(jButton14);
        p1button.get(8).add(jButton15);
        p1button.get(8).add(jButton16);
        p1button.get(8).add(jButton17);
        p1button.get(8).add(jButton18);
        p1button.get(8).add(jButton19);
        p1button.get(8).add(jButton20);
        
        p1button.get(7).add(jButton21);
        p1button.get(7).add(jButton22);
        p1button.get(7).add(jButton23);
        p1button.get(7).add(jButton24);
        p1button.get(7).add(jButton25);
        p1button.get(7).add(jButton26);
        p1button.get(7).add(jButton27);
        p1button.get(7).add(jButton28);
        p1button.get(7).add(jButton29);
        p1button.get(7).add(jButton30);
        
        p1button.get(6).add(jButton31);
        p1button.get(6).add(jButton32);
        p1button.get(6).add(jButton33);
        p1button.get(6).add(jButton34);
        p1button.get(6).add(jButton35);
        p1button.get(6).add(jButton36);
        p1button.get(6).add(jButton37);
        p1button.get(6).add(jButton38);
        p1button.get(6).add(jButton39);
        p1button.get(6).add(jButton40);
        
        p1button.get(5).add(jButton41);
        p1button.get(5).add(jButton42);
        p1button.get(5).add(jButton43);
        p1button.get(5).add(jButton44);
        p1button.get(5).add(jButton45);
        p1button.get(5).add(jButton46);
        p1button.get(5).add(jButton47);
        p1button.get(5).add(jButton48);
        p1button.get(5).add(jButton49);
        p1button.get(5).add(jButton50);
        
        p1button.get(4).add(jButton51);
        p1button.get(4).add(jButton52);
        p1button.get(4).add(jButton53);
        p1button.get(4).add(jButton54);
        p1button.get(4).add(jButton55);
        p1button.get(4).add(jButton56);
        p1button.get(4).add(jButton57);
        p1button.get(4).add(jButton58);
        p1button.get(4).add(jButton59);
        p1button.get(4).add(jButton60);
        
        p1button.get(3).add(jButton61);
        p1button.get(3).add(jButton62);
        p1button.get(3).add(jButton63);
        p1button.get(3).add(jButton64);
        p1button.get(3).add(jButton65);
        p1button.get(3).add(jButton66);
        p1button.get(3).add(jButton67);
        p1button.get(3).add(jButton68);
        p1button.get(3).add(jButton69);
        p1button.get(3).add(jButton70);
        
        p1button.get(2).add(jButton71);
        p1button.get(2).add(jButton72);
        p1button.get(2).add(jButton73);
        p1button.get(2).add(jButton74);
        p1button.get(2).add(jButton75);
        p1button.get(2).add(jButton76);
        p1button.get(2).add(jButton77);
        p1button.get(2).add(jButton78);
        p1button.get(2).add(jButton79);
        p1button.get(2).add(jButton80);
        
        p1button.get(1).add(jButton81);
        p1button.get(1).add(jButton82);
        p1button.get(1).add(jButton83);
        p1button.get(1).add(jButton84);
        p1button.get(1).add(jButton85);
        p1button.get(1).add(jButton86);
        p1button.get(1).add(jButton87);
        p1button.get(1).add(jButton88);
        p1button.get(1).add(jButton89);
        p1button.get(1).add(jButton90);
        
        p1button.get(0).add(jButton91);
        p1button.get(0).add(jButton92);
        p1button.get(0).add(jButton93);
        p1button.get(0).add(jButton94);
        p1button.get(0).add(jButton95);
        p1button.get(0).add(jButton96);
        p1button.get(0).add(jButton97);
        p1button.get(0).add(jButton98);
        p1button.get(0).add(jButton99);
        p1button.get(0).add(jButton100);
        
        p2button.get(9).add(jButton101);
        p2button.get(9).add(jButton102);
        p2button.get(9).add(jButton103);
        p2button.get(9).add(jButton104);
        p2button.get(9).add(jButton105);
        p2button.get(9).add(jButton106);
        p2button.get(9).add(jButton107);
        p2button.get(9).add(jButton108);
        p2button.get(9).add(jButton109);
        p2button.get(9).add(jButton110);
        
        p2button.get(8).add(jButton111);
        p2button.get(8).add(jButton112);
        p2button.get(8).add(jButton113);
        p2button.get(8).add(jButton114);
        p2button.get(8).add(jButton115);
        p2button.get(8).add(jButton116);
        p2button.get(8).add(jButton117);
        p2button.get(8).add(jButton118);
        p2button.get(8).add(jButton119);
        p2button.get(8).add(jButton120);
        
        p2button.get(7).add(jButton121);
        p2button.get(7).add(jButton122);
        p2button.get(7).add(jButton123);
        p2button.get(7).add(jButton124);
        p2button.get(7).add(jButton125);
        p2button.get(7).add(jButton126);
        p2button.get(7).add(jButton127);
        p2button.get(7).add(jButton128);
        p2button.get(7).add(jButton129);
        p2button.get(7).add(jButton130);
        
        p2button.get(6).add(jButton131);
        p2button.get(6).add(jButton132);
        p2button.get(6).add(jButton133);
        p2button.get(6).add(jButton134);
        p2button.get(6).add(jButton135);
        p2button.get(6).add(jButton136);
        p2button.get(6).add(jButton137);
        p2button.get(6).add(jButton138);
        p2button.get(6).add(jButton139);
        p2button.get(6).add(jButton140);
        
        p2button.get(5).add(jButton141);
        p2button.get(5).add(jButton142);
        p2button.get(5).add(jButton143);
        p2button.get(5).add(jButton144);
        p2button.get(5).add(jButton145);
        p2button.get(5).add(jButton146);
        p2button.get(5).add(jButton147);
        p2button.get(5).add(jButton148);
        p2button.get(5).add(jButton149);
        p2button.get(5).add(jButton150);
        
        p2button.get(4).add(jButton151);
        p2button.get(4).add(jButton152);
        p2button.get(4).add(jButton153);
        p2button.get(4).add(jButton154);
        p2button.get(4).add(jButton155);
        p2button.get(4).add(jButton156);
        p2button.get(4).add(jButton157);
        p2button.get(4).add(jButton158);
        p2button.get(4).add(jButton159);
        p2button.get(4).add(jButton160);
        
        p2button.get(3).add(jButton161);
        p2button.get(3).add(jButton162);
        p2button.get(3).add(jButton163);
        p2button.get(3).add(jButton164);
        p2button.get(3).add(jButton165);
        p2button.get(3).add(jButton166);
        p2button.get(3).add(jButton167);
        p2button.get(3).add(jButton168);
        p2button.get(3).add(jButton169);
        p2button.get(3).add(jButton170);
        
        p2button.get(2).add(jButton171);
        p2button.get(2).add(jButton172);
        p2button.get(2).add(jButton173);
        p2button.get(2).add(jButton174);
        p2button.get(2).add(jButton175);
        p2button.get(2).add(jButton176);
        p2button.get(2).add(jButton177);
        p2button.get(2).add(jButton178);
        p2button.get(2).add(jButton179);
        p2button.get(2).add(jButton180);
        
        p2button.get(1).add(jButton181);
        p2button.get(1).add(jButton182);
        p2button.get(1).add(jButton183);
        p2button.get(1).add(jButton184);
        p2button.get(1).add(jButton185);
        p2button.get(1).add(jButton186);
        p2button.get(1).add(jButton187);
        p2button.get(1).add(jButton188);
        p2button.get(1).add(jButton189);
        p2button.get(1).add(jButton190);
        
        p2button.get(0).add(jButton191);
        p2button.get(0).add(jButton192);
        p2button.get(0).add(jButton193);
        p2button.get(0).add(jButton194);
        p2button.get(0).add(jButton195);
        p2button.get(0).add(jButton196);
        p2button.get(0).add(jButton197);
        p2button.get(0).add(jButton198);
        p2button.get(0).add(jButton199);
        p2button.get(0).add(jButton200);
        
        
        disableP2();
        updateEmptyBoard();     
    }
    
    Ship.point getCoordsp1(JButton b){
        for(int y=0;y<10;y++){
            for(int x=0; x<10;x++){
                if(p1button.get(y).get(x).equals(b)){
                    return new Ship.point(x,y);
                }
            }
        }
        
        return new Ship.point(-1,-1);
    }
    
    Ship.point getCoordsp2(JButton b){
        for(int y=0;y<10;y++){
            for(int x=0; x<10;x++){
                if(p2button.get(y).get(x).equals(b)){
                    return new Ship.point(x,y);
                }
            }
        }
        
        return new Ship.point(-1,-1);
    }
    
    void updateP1Board(){
        //p1.showBoard();
        //p2.showBoard();
        String[][] gameBoardp1 = p1.getBoard();
        String[][] gameBoardp2 = p2.getBoard();
        for(int y=0;y<p1.BOARD_H;y++){
            for(int x=0; x<p1.BOARD_W;x++){
                //player 1 will see all of player 1's ships
                p1button.get(y).get(x).setBackground(Color.blue);
                if(gameBoardp1[x][y].equals("M")){
                    p1button.get(y).get(x).setBackground(Color.CYAN);
                }
                else if(gameBoardp1[x][y].equals("X")){
                    p1button.get(y).get(x).setBackground(Color.red);
                }
                else if(Character.isDigit(gameBoardp1[x][y].charAt(0))){
                    p1button.get(y).get(x).setBackground(Color.DARK_GRAY);
                }
                
                //player 1 will only see hits made to player 2's ships
                p2button.get(y).get(x).setBackground(Color.blue);
                if(gameBoardp2[x][y].equals("M")){
                    p2button.get(y).get(x).setBackground(Color.CYAN);
                }
                else if(gameBoardp2[x][y].equals("X")){
                    p2button.get(y).get(x).setBackground(Color.red);
                }
            }
        }
    }
    
    void updateP2Board(){
        //p1.showBoard();
        //p2.showBoard();
        String[][] gameBoardp1 = p1.getBoard();
        String[][] gameBoardp2 = p2.getBoard();
        for(int y=0;y<p1.BOARD_H;y++){
            for(int x=0; x<p1.BOARD_W;x++){
                //player 2 will only see hits made to player 1's ships
                p1button.get(y).get(x).setBackground(Color.blue);
                if(gameBoardp1[x][y].equals("M")){
                    p1button.get(y).get(x).setBackground(Color.CYAN);
                }
                else if(gameBoardp1[x][y].equals("X")){
                    p1button.get(y).get(x).setBackground(Color.red);
                }
                
                //player 2 will see all of player 2's ships
                p2button.get(y).get(x).setBackground(Color.blue);
                if(gameBoardp2[x][y].equals("M")){
                    p2button.get(y).get(x).setBackground(Color.CYAN);
                }
                else if(gameBoardp2[x][y].equals("X")){
                    p2button.get(y).get(x).setBackground(Color.red);
                }
                else if(Character.isDigit(gameBoardp2[x][y].charAt(0))){
                    p2button.get(y).get(x).setBackground(Color.DARK_GRAY);
                }
            
            }
        }
    }

    void updateEmptyBoard(){
        for(int y=0;y<p1.BOARD_H;y++){
            for(int x=0; x<p1.BOARD_W;x++){
                //make the whole board blue so that people can't cheat between turns
                p1button.get(y).get(x).setBackground(Color.blue);
                p2button.get(y).get(x).setBackground(Color.blue);
            }
        }
    }
    
    void disableP1(){
         for(int y=0;y<p1.BOARD_H;y++){
            for(int x=0; x<p1.BOARD_W;x++){
                p1button.get(y).get(x).removeActionListener(p1button.get(y).get(x).getActionListeners()[0]);
            }
        }
    }
    
    void disableP2(){
         for(int y=0;y<p1.BOARD_H;y++){
            for(int x=0; x<p1.BOARD_W;x++){
                p2button.get(y).get(x).removeActionListener(p2button.get(y).get(x).getActionListeners()[0]);
            }
        }
    }
    
    void enableP1(){
         for(int y=0;y<p1.BOARD_H;y++){
            for(int x=0; x<p1.BOARD_W;x++){
                p1button.get(y).get(x).addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        p2ActionHandler(evt);
                    }
                });
            }
        }
    }
    
    void enableP2(){
         for(int y=0;y<p1.BOARD_H;y++){
            for(int x=0; x<p1.BOARD_W;x++){
                p2button.get(y).get(x).addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        p2ActionHandler(evt);
                    }
                });
            }
        }
    }
         
    void playerChange(){
        turnSwap=true;
        jDialog1.setVisible(true);
        updateEmptyBoard();
    }
         
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jDialog1 = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jButton201 = new javax.swing.JButton();
        p1Panel = new javax.swing.JPanel();
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
        jButton50 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        jButton61 = new javax.swing.JButton();
        jButton62 = new javax.swing.JButton();
        jButton63 = new javax.swing.JButton();
        jButton64 = new javax.swing.JButton();
        jButton65 = new javax.swing.JButton();
        jButton66 = new javax.swing.JButton();
        jButton67 = new javax.swing.JButton();
        jButton68 = new javax.swing.JButton();
        jButton69 = new javax.swing.JButton();
        jButton70 = new javax.swing.JButton();
        jButton71 = new javax.swing.JButton();
        jButton72 = new javax.swing.JButton();
        jButton73 = new javax.swing.JButton();
        jButton74 = new javax.swing.JButton();
        jButton75 = new javax.swing.JButton();
        jButton76 = new javax.swing.JButton();
        jButton77 = new javax.swing.JButton();
        jButton78 = new javax.swing.JButton();
        jButton79 = new javax.swing.JButton();
        jButton80 = new javax.swing.JButton();
        jButton81 = new javax.swing.JButton();
        jButton82 = new javax.swing.JButton();
        jButton83 = new javax.swing.JButton();
        jButton84 = new javax.swing.JButton();
        jButton85 = new javax.swing.JButton();
        jButton86 = new javax.swing.JButton();
        jButton87 = new javax.swing.JButton();
        jButton88 = new javax.swing.JButton();
        jButton89 = new javax.swing.JButton();
        jButton90 = new javax.swing.JButton();
        jButton91 = new javax.swing.JButton();
        jButton92 = new javax.swing.JButton();
        jButton93 = new javax.swing.JButton();
        jButton94 = new javax.swing.JButton();
        jButton95 = new javax.swing.JButton();
        jButton96 = new javax.swing.JButton();
        jButton97 = new javax.swing.JButton();
        jButton98 = new javax.swing.JButton();
        jButton99 = new javax.swing.JButton();
        jButton100 = new javax.swing.JButton();
        p2Panel = new javax.swing.JPanel();
        jButton101 = new javax.swing.JButton();
        jButton102 = new javax.swing.JButton();
        jButton103 = new javax.swing.JButton();
        jButton104 = new javax.swing.JButton();
        jButton105 = new javax.swing.JButton();
        jButton106 = new javax.swing.JButton();
        jButton107 = new javax.swing.JButton();
        jButton108 = new javax.swing.JButton();
        jButton109 = new javax.swing.JButton();
        jButton110 = new javax.swing.JButton();
        jButton111 = new javax.swing.JButton();
        jButton112 = new javax.swing.JButton();
        jButton113 = new javax.swing.JButton();
        jButton114 = new javax.swing.JButton();
        jButton115 = new javax.swing.JButton();
        jButton116 = new javax.swing.JButton();
        jButton117 = new javax.swing.JButton();
        jButton118 = new javax.swing.JButton();
        jButton119 = new javax.swing.JButton();
        jButton120 = new javax.swing.JButton();
        jButton121 = new javax.swing.JButton();
        jButton122 = new javax.swing.JButton();
        jButton123 = new javax.swing.JButton();
        jButton124 = new javax.swing.JButton();
        jButton125 = new javax.swing.JButton();
        jButton126 = new javax.swing.JButton();
        jButton127 = new javax.swing.JButton();
        jButton128 = new javax.swing.JButton();
        jButton129 = new javax.swing.JButton();
        jButton130 = new javax.swing.JButton();
        jButton131 = new javax.swing.JButton();
        jButton132 = new javax.swing.JButton();
        jButton133 = new javax.swing.JButton();
        jButton134 = new javax.swing.JButton();
        jButton135 = new javax.swing.JButton();
        jButton136 = new javax.swing.JButton();
        jButton137 = new javax.swing.JButton();
        jButton138 = new javax.swing.JButton();
        jButton139 = new javax.swing.JButton();
        jButton140 = new javax.swing.JButton();
        jButton141 = new javax.swing.JButton();
        jButton142 = new javax.swing.JButton();
        jButton143 = new javax.swing.JButton();
        jButton144 = new javax.swing.JButton();
        jButton145 = new javax.swing.JButton();
        jButton146 = new javax.swing.JButton();
        jButton147 = new javax.swing.JButton();
        jButton148 = new javax.swing.JButton();
        jButton149 = new javax.swing.JButton();
        jButton150 = new javax.swing.JButton();
        jButton151 = new javax.swing.JButton();
        jButton152 = new javax.swing.JButton();
        jButton153 = new javax.swing.JButton();
        jButton154 = new javax.swing.JButton();
        jButton155 = new javax.swing.JButton();
        jButton156 = new javax.swing.JButton();
        jButton157 = new javax.swing.JButton();
        jButton158 = new javax.swing.JButton();
        jButton159 = new javax.swing.JButton();
        jButton160 = new javax.swing.JButton();
        jButton161 = new javax.swing.JButton();
        jButton162 = new javax.swing.JButton();
        jButton163 = new javax.swing.JButton();
        jButton164 = new javax.swing.JButton();
        jButton165 = new javax.swing.JButton();
        jButton166 = new javax.swing.JButton();
        jButton167 = new javax.swing.JButton();
        jButton168 = new javax.swing.JButton();
        jButton169 = new javax.swing.JButton();
        jButton170 = new javax.swing.JButton();
        jButton171 = new javax.swing.JButton();
        jButton172 = new javax.swing.JButton();
        jButton173 = new javax.swing.JButton();
        jButton174 = new javax.swing.JButton();
        jButton175 = new javax.swing.JButton();
        jButton176 = new javax.swing.JButton();
        jButton177 = new javax.swing.JButton();
        jButton178 = new javax.swing.JButton();
        jButton179 = new javax.swing.JButton();
        jButton180 = new javax.swing.JButton();
        jButton181 = new javax.swing.JButton();
        jButton182 = new javax.swing.JButton();
        jButton183 = new javax.swing.JButton();
        jButton184 = new javax.swing.JButton();
        jButton185 = new javax.swing.JButton();
        jButton186 = new javax.swing.JButton();
        jButton187 = new javax.swing.JButton();
        jButton188 = new javax.swing.JButton();
        jButton189 = new javax.swing.JButton();
        jButton190 = new javax.swing.JButton();
        jButton191 = new javax.swing.JButton();
        jButton192 = new javax.swing.JButton();
        jButton193 = new javax.swing.JButton();
        jButton194 = new javax.swing.JButton();
        jButton195 = new javax.swing.JButton();
        jButton196 = new javax.swing.JButton();
        jButton197 = new javax.swing.JButton();
        jButton198 = new javax.swing.JButton();
        jButton199 = new javax.swing.JButton();
        jButton200 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jDialog1.setAlwaysOnTop(true);
        jDialog1.setMinimumSize(new java.awt.Dimension(400, 400));
        jDialog1.setPreferredSize(new java.awt.Dimension(400, 400));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText(" Switching turns");
        jLabel3.setPreferredSize(new java.awt.Dimension(125, 25));

        jButton201.setLabel("OK");
        jButton201.setPreferredSize(new java.awt.Dimension(100, 50));
        jButton201.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton201ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jButton201, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton201, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        p1Panel.setBackground(new java.awt.Color(51, 204, 255));
        p1Panel.setLayout(new java.awt.GridLayout(10, 10, 10, 10));

        jButton1.setText(" ");
        jButton1.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p1ActionHandler(evt);
            }
        });
        p1Panel.add(jButton1);

        jButton2.setText(" ");
        jButton2.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton2);

        jButton3.setText(" ");
        jButton3.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton3);

        jButton4.setText(" ");
        jButton4.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton4);

        jButton5.setText(" ");
        jButton5.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton5);

        jButton6.setText(" ");
        jButton6.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton6);

        jButton7.setText(" ");
        jButton7.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton7);

        jButton8.setText(" ");
        jButton8.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton8);

        jButton9.setText(" ");
        jButton9.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton9);

        jButton10.setText(" ");
        jButton10.setPreferredSize(new java.awt.Dimension(50, 50));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton10);

        jButton11.setText(" ");
        jButton11.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton11);

        jButton12.setText(" ");
        jButton12.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton12);

        jButton13.setText(" ");
        jButton13.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton13);

        jButton14.setText(" ");
        jButton14.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton14);

        jButton15.setText(" ");
        jButton15.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton15);

        jButton16.setText(" ");
        jButton16.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton16);

        jButton17.setText(" ");
        jButton17.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton17);

        jButton18.setText(" ");
        jButton18.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton18);

        jButton19.setText(" ");
        jButton19.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton19);

        jButton20.setText(" ");
        jButton20.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton20);

        jButton21.setText(" ");
        jButton21.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton21);

        jButton22.setText(" ");
        jButton22.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton22);

        jButton23.setText(" ");
        jButton23.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton23);

        jButton24.setText(" ");
        jButton24.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton24);

        jButton25.setText(" ");
        jButton25.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton25);

        jButton26.setText(" ");
        jButton26.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton26);

        jButton27.setText(" ");
        jButton27.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton27);

        jButton28.setText(" ");
        jButton28.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton28);

        jButton29.setText(" ");
        jButton29.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton29);

        jButton30.setText(" ");
        jButton30.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton30);

        jButton31.setText(" ");
        jButton31.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton31);

        jButton32.setText(" ");
        jButton32.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton32);

        jButton33.setText(" ");
        jButton33.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton33);

        jButton34.setText(" ");
        jButton34.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton34);

        jButton35.setText(" ");
        jButton35.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton35);

        jButton36.setText(" ");
        jButton36.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton36);

        jButton37.setText(" ");
        jButton37.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton37);

        jButton38.setText(" ");
        jButton38.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton38);

        jButton39.setText(" ");
        jButton39.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton39);

        jButton40.setText(" ");
        jButton40.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton40);

        jButton41.setText(" ");
        jButton41.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton41);

        jButton42.setText(" ");
        jButton42.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton42);

        jButton43.setText(" ");
        jButton43.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton43ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton43);

        jButton44.setText(" ");
        jButton44.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton44);

        jButton45.setText(" ");
        jButton45.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton45);

        jButton46.setText(" ");
        jButton46.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton46);

        jButton47.setText(" ");
        jButton47.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton47ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton47);

        jButton48.setText(" ");
        jButton48.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton48);

        jButton49.setText(" ");
        jButton49.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton49);

        jButton50.setText(" ");
        jButton50.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton50);

        jButton51.setText(" ");
        jButton51.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton51);

        jButton52.setText(" ");
        jButton52.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton52ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton52);

        jButton53.setText(" ");
        jButton53.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton53ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton53);

        jButton54.setText(" ");
        jButton54.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton54);

        jButton55.setText(" ");
        jButton55.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton55);

        jButton56.setText(" ");
        jButton56.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton56);

        jButton57.setText(" ");
        jButton57.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton57);

        jButton58.setText(" ");
        jButton58.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton58);

        jButton59.setText(" ");
        jButton59.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton59);

        jButton60.setText(" ");
        jButton60.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton60);

        jButton61.setText(" ");
        jButton61.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton61);

        jButton62.setText(" ");
        jButton62.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton62ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton62);

        jButton63.setText(" ");
        jButton63.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton63ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton63);

        jButton64.setText(" ");
        jButton64.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton64ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton64);

        jButton65.setText(" ");
        jButton65.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton65ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton65);

        jButton66.setText(" ");
        jButton66.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton66ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton66);

        jButton67.setText(" ");
        jButton67.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton67ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton67);

        jButton68.setText(" ");
        jButton68.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton68ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton68);

        jButton69.setText(" ");
        jButton69.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton69ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton69);

        jButton70.setText(" ");
        jButton70.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton70ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton70);

        jButton71.setText(" ");
        jButton71.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton71ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton71);

        jButton72.setText(" ");
        jButton72.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton72ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton72);

        jButton73.setText(" ");
        jButton73.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton73ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton73);

        jButton74.setText(" ");
        jButton74.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton74ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton74);

        jButton75.setText(" ");
        jButton75.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton75ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton75);

        jButton76.setText(" ");
        jButton76.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton76ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton76);

        jButton77.setText(" ");
        jButton77.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton77ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton77);

        jButton78.setText(" ");
        jButton78.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton78ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton78);

        jButton79.setText(" ");
        jButton79.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton79ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton79);

        jButton80.setText(" ");
        jButton80.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton80ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton80);

        jButton81.setText(" ");
        jButton81.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton81ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton81);

        jButton82.setText(" ");
        jButton82.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton82ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton82);

        jButton83.setText(" ");
        jButton83.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton83ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton83);

        jButton84.setText(" ");
        jButton84.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton84ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton84);

        jButton85.setText(" ");
        jButton85.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton85ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton85);

        jButton86.setText(" ");
        jButton86.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton86ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton86);

        jButton87.setText(" ");
        jButton87.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton87.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton87ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton87);

        jButton88.setText(" ");
        jButton88.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton88ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton88);

        jButton89.setText(" ");
        jButton89.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton89.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton89ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton89);

        jButton90.setText(" ");
        jButton90.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton90ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton90);

        jButton91.setText(" ");
        jButton91.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton91.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton91ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton91);

        jButton92.setText(" ");
        jButton92.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton92.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton92ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton92);

        jButton93.setText(" ");
        jButton93.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton93.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton93ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton93);

        jButton94.setText(" ");
        jButton94.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton94.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton94ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton94);

        jButton95.setText(" ");
        jButton95.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton95.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton95ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton95);

        jButton96.setText(" ");
        jButton96.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton96.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton96ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton96);

        jButton97.setText(" ");
        jButton97.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton97.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton97ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton97);

        jButton98.setText(" ");
        jButton98.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton98.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton98ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton98);

        jButton99.setText(" ");
        jButton99.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton99ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton99);

        jButton100.setText(" ");
        jButton100.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton100ActionPerformed(evt);
            }
        });
        p1Panel.add(jButton100);

        p2Panel.setBackground(new java.awt.Color(51, 204, 255));
        p2Panel.setLayout(new java.awt.GridLayout(10, 10, 10, 10));

        jButton101.setText(" ");
        jButton101.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p2ActionHandler(evt);
            }
        });
        p2Panel.add(jButton101);

        jButton102.setText(" ");
        jButton102.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton102.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton102ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton102);

        jButton103.setText(" ");
        jButton103.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton103.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton103ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton103);

        jButton104.setText(" ");
        jButton104.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton104.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton104ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton104);

        jButton105.setText(" ");
        jButton105.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton105.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton105ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton105);

        jButton106.setText(" ");
        jButton106.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton106.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton106ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton106);

        jButton107.setText(" ");
        jButton107.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton107.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton107ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton107);

        jButton108.setText(" ");
        jButton108.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton108.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton108ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton108);

        jButton109.setText(" ");
        jButton109.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton109.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton109ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton109);

        jButton110.setText(" ");
        jButton110.setPreferredSize(new java.awt.Dimension(50, 50));
        jButton110.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton110ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton110);

        jButton111.setText(" ");
        jButton111.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton111ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton111);

        jButton112.setText(" ");
        jButton112.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton112ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton112);

        jButton113.setText(" ");
        jButton113.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton113.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton113ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton113);

        jButton114.setText(" ");
        jButton114.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton114.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton114ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton114);

        jButton115.setText(" ");
        jButton115.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton115.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton115ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton115);

        jButton116.setText(" ");
        jButton116.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton116.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton116ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton116);

        jButton117.setText(" ");
        jButton117.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton117.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton117ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton117);

        jButton118.setText(" ");
        jButton118.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton118.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton118ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton118);

        jButton119.setText(" ");
        jButton119.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton119.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton119ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton119);

        jButton120.setText(" ");
        jButton120.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton120.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton120ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton120);

        jButton121.setText(" ");
        jButton121.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton121.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton121ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton121);

        jButton122.setText(" ");
        jButton122.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton122.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton122ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton122);

        jButton123.setText(" ");
        jButton123.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton123.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton123ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton123);

        jButton124.setText(" ");
        jButton124.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton124.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton124ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton124);

        jButton125.setText(" ");
        jButton125.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton125.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton125ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton125);

        jButton126.setText(" ");
        jButton126.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton126.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton126ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton126);

        jButton127.setText(" ");
        jButton127.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton127.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton127ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton127);

        jButton128.setText(" ");
        jButton128.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton128.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton128ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton128);

        jButton129.setText(" ");
        jButton129.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton129.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton129ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton129);

        jButton130.setText(" ");
        jButton130.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton130.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton130ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton130);

        jButton131.setText(" ");
        jButton131.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton131.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton131ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton131);

        jButton132.setText(" ");
        jButton132.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton132.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton132ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton132);

        jButton133.setText(" ");
        jButton133.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton133.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton133ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton133);

        jButton134.setText(" ");
        jButton134.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton134.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton134ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton134);

        jButton135.setText(" ");
        jButton135.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton135.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton135ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton135);

        jButton136.setText(" ");
        jButton136.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton136.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton136ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton136);

        jButton137.setText(" ");
        jButton137.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton137.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton137ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton137);

        jButton138.setText(" ");
        jButton138.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton138.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton138ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton138);

        jButton139.setText(" ");
        jButton139.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton139.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton139ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton139);

        jButton140.setText(" ");
        jButton140.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton140.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton140ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton140);

        jButton141.setText(" ");
        jButton141.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton141.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton141ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton141);

        jButton142.setText(" ");
        jButton142.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton142.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton142ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton142);

        jButton143.setText(" ");
        jButton143.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton143.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton143ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton143);

        jButton144.setText(" ");
        jButton144.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton144.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton144ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton144);

        jButton145.setText(" ");
        jButton145.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton145.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton145ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton145);

        jButton146.setText(" ");
        jButton146.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton146.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton146ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton146);

        jButton147.setText(" ");
        jButton147.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton147.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton147ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton147);

        jButton148.setText(" ");
        jButton148.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton148.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton148ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton148);

        jButton149.setText(" ");
        jButton149.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton149.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton149ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton149);

        jButton150.setText(" ");
        jButton150.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton150.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton150ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton150);

        jButton151.setText(" ");
        jButton151.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton151.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton151ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton151);

        jButton152.setText(" ");
        jButton152.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton152.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton152ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton152);

        jButton153.setText(" ");
        jButton153.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton153.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton153ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton153);

        jButton154.setText(" ");
        jButton154.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton154.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton154ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton154);

        jButton155.setText(" ");
        jButton155.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton155.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton155ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton155);

        jButton156.setText(" ");
        jButton156.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton156.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton156ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton156);

        jButton157.setText(" ");
        jButton157.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton157.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton157ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton157);

        jButton158.setText(" ");
        jButton158.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton158.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton158ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton158);

        jButton159.setText(" ");
        jButton159.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton159.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton159ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton159);

        jButton160.setText(" ");
        jButton160.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton160.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton160ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton160);

        jButton161.setText(" ");
        jButton161.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton161.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton161ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton161);

        jButton162.setText(" ");
        jButton162.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton162.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton162ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton162);

        jButton163.setText(" ");
        jButton163.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton163.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton163ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton163);

        jButton164.setText(" ");
        jButton164.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton164.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton164ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton164);

        jButton165.setText(" ");
        jButton165.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton165.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton165ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton165);

        jButton166.setText(" ");
        jButton166.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton166.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton166ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton166);

        jButton167.setText(" ");
        jButton167.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton167.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton167ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton167);

        jButton168.setText(" ");
        jButton168.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton168.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton168ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton168);

        jButton169.setText(" ");
        jButton169.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton169.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton169ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton169);

        jButton170.setText(" ");
        jButton170.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton170.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton170ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton170);

        jButton171.setText(" ");
        jButton171.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton171.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton171ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton171);

        jButton172.setText(" ");
        jButton172.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton172.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton172ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton172);

        jButton173.setText(" ");
        jButton173.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton173.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton173ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton173);

        jButton174.setText(" ");
        jButton174.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton174.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton174ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton174);

        jButton175.setText(" ");
        jButton175.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton175.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton175ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton175);

        jButton176.setText(" ");
        jButton176.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton176.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton176ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton176);

        jButton177.setText(" ");
        jButton177.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton177.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton177ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton177);

        jButton178.setText(" ");
        jButton178.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton178.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton178ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton178);

        jButton179.setText(" ");
        jButton179.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton179.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton179ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton179);

        jButton180.setText(" ");
        jButton180.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton180.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton180ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton180);

        jButton181.setText(" ");
        jButton181.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton181.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton181ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton181);

        jButton182.setText(" ");
        jButton182.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton182.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton182ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton182);

        jButton183.setText(" ");
        jButton183.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton183.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton183ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton183);

        jButton184.setText(" ");
        jButton184.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton184.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton184ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton184);

        jButton185.setText(" ");
        jButton185.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton185.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton185ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton185);

        jButton186.setText(" ");
        jButton186.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton186.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton186ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton186);

        jButton187.setText(" ");
        jButton187.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton187.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton187ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton187);

        jButton188.setText(" ");
        jButton188.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton188.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton188ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton188);

        jButton189.setText(" ");
        jButton189.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton189.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton189ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton189);

        jButton190.setText(" ");
        jButton190.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton190.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton190ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton190);

        jButton191.setText(" ");
        jButton191.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton191.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton191ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton191);

        jButton192.setText(" ");
        jButton192.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton192.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton192ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton192);

        jButton193.setText(" ");
        jButton193.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton193.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton193ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton193);

        jButton194.setText(" ");
        jButton194.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton194.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton194ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton194);

        jButton195.setText(" ");
        jButton195.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton195.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton195ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton195);

        jButton196.setText(" ");
        jButton196.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton196.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton196ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton196);

        jButton197.setText(" ");
        jButton197.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton197.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton197ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton197);

        jButton198.setText(" ");
        jButton198.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton198.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton198ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton198);

        jButton199.setText(" ");
        jButton199.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton199.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton199ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton199);

        jButton200.setText(" ");
        jButton200.setPreferredSize(new java.awt.Dimension(35, 35));
        jButton200.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton200ActionPerformed(evt);
            }
        });
        p2Panel.add(jButton200);

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("North");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("East");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("West");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("South");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(p1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton4)
                            .addComponent(jRadioButton2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(p2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(p1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(p2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton4)
                        .addGap(230, 230, 230))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void p2ActionHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p2ActionHandler
        // TODO add your handling code here:
        //HANDLE ACTIONS
        if(!game || turnSwap){return;}
        
        JButton b = (JButton)evt.getSource();
        String orientation="NORTH";
        if(jRadioButton1.isSelected()){
            orientation="NORTH";
        }
        if(jRadioButton2.isSelected()){
            orientation="EAST";
        }
        if(jRadioButton3.isSelected()){
            orientation="WEST";
        }
        if(jRadioButton4.isSelected()){
            orientation="SOUTH";
        }
        
        Ship.point p;
        if((myTurn && placeShips) || (!myTurn && !placeShips)){
            p = getCoordsp1(b);
            System.out.println(p.x + " " + p.y);
        }
        else if((!myTurn && placeShips) || (myTurn && !placeShips) || isOnline){
            p=getCoordsp2(b);
            System.out.println(p.x + " " + p.y);
        }else{
            p=getCoordsp1(b);
        }
         
        if(placeShips && game ){
            if(myTurn && !isOnline){
                if(currentShip +2 < 6){
                    jLabel1.setText("Player 1 place ship " + (currentShip+2));
                    jLabel2.setText("");
                }
                else{
                    jLabel1.setText("Player 1 finished placing ships");
                    jLabel2.setText("Player 2 place Ship 1");
                }
                
                if(p1.placeShips(p, currentShip, orientation) != -1){
                    updateP1Board();
                    currentShip++;
                    System.out.println("player 1 placed ship " + currentShip + " at location " + p.x + ' ' + p.y);
                    if(currentShip==5){
                        myTurn=false;
                        currentShip=0;
                        p1PlacedShips=true;
                        jRadioButton1.setSelected(true);
                        jRadioButton2.setSelected(false);
                        jRadioButton3.setSelected(false);
                        jRadioButton4.setSelected(false);
                        
                        enableP2();
                        disableP1();
                        playerChange();
                        //updateP2Board();
                    }
                }
                else{
                    System.out.print("Error placing ship");
                }
            }
            else if(myTurn && isOnline){
                //send = "";
                if(p1.placeShips(p, currentShip, orientation) != -1){
                    updateP1Board();
                    currentShip++;
                    send += p.x.toString() + p.y.toString() + orientation.charAt(0) + " ";
                    System.out.println("player 1 placed ship " + currentShip + " at location " + p.x + ' ' + p.y);
                    if(currentShip==5){
                        System.out.println(send);
                        sendMessage(send);
                        currentShip=0;
                        p1PlacedShips = true;
                        if(!p2PlacedShips){
                            waitForMove();
                            myTurn=true;
                        }
                        else{
                            myTurn=false;
                        }
                        enableP2();
                        //myTurn=true;
                    }
                }
            }
            else if(!isOnline && !myTurn){
                jLabel1.setText("");
                if(currentShip+2<6){
                    jLabel2.setText("Player 2 place ship " + (currentShip+2));
                    jLabel1.setText("");
                }
                else{
                    jLabel1.setText("Player 2 finished placing ships");
                    jLabel2.setText("Player 1 make move");
                }
                if(p2.placeShips(p, currentShip,orientation)!=-1){ 
                    currentShip++;
                    System.out.println("player 2 placed ship " + currentShip + " at location " + p.x + ' ' + p.y);
                    updateP2Board();

                    if(currentShip==5){
                        myTurn=true;
                        currentShip=0;
                        p2PlacedShips=true;
                        
                        playerChange();
                        //updateP1Board();
                    }
                }
                else{
                    System.out.println("Error placing ship");
                }
            }
             if(p1PlacedShips && p2PlacedShips){
                    placeShips=false;
                    jRadioButton1.setVisible(false);
                    jRadioButton2.setVisible(false);
                    jRadioButton3.setVisible(false);
                    jRadioButton4.setVisible(false);
                    //if(!myTurn)waitForMove();
             }
            
        }// SETUP DONE
        else{
            if(myTurn){
                jLabel2.setText("Player 1 attacked spot " + p.x + ' ' + p.y);
                jLabel1.setText("Player 2 make move");
                if(!p2.attackSpot(p.x,p.y).equals("X")){
                    if(!isOnline) enableP1();
                    if(p2.checkWin()){
                        System.out.println("Player 1 wins!");
                        jLabel1.setText("Player 1 wins!");
                        jLabel2.setText("Player 1 wins!");
                        game=false;
                        disableP1();
                    }
                    myTurn=false;
                    if(isOnline){
                        sendMessage(p.x.toString() + p.y.toString());
                        waitForMove();
                        disableP1();
                    }
                    else{
                        playerChange();
                        //updateP2Board();
                        disableP2();
                    }
                    
                }
                else{
                    jLabel2.setText("Spot " + p.x + ' ' + p.y + " has already been attacked.  Pick another.");
                }
                
            }
            else{
                jLabel1.setText("Player 2 attacked spot " + p.x + ' ' + p.y);
                jLabel2.setText("Player 1 make move");
                if(!p1.attackSpot(p.x,p.y).equals("X")){
                    enableP2();
                    if(p1.checkWin()){
                        System.out.println("Player 2 wins!");
                        jLabel1.setText("Player 2 wins!");
                        jLabel2.setText("Player 2 wins!");
                        game=false;
                        disableP2();
                    }
                    myTurn=true;
                    playerChange();
                    disableP1();
                    //updateP1Board();
                }
                else{
                    jLabel1.setText("Spot " + p.x + ' ' + p.y + " has already been attacked.  Pick another.");
                }
            }
        }
        
    }//GEN-LAST:event_p2ActionHandler

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        p1ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton42ActionPerformed

    private void jButton43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton43ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton43ActionPerformed

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton44ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton45ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton47ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton47ActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton49ActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton51ActionPerformed

    private void jButton52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton52ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton52ActionPerformed

    private void jButton53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton53ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton53ActionPerformed

    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton54ActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton55ActionPerformed

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton56ActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton59ActionPerformed

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton61ActionPerformed

    private void jButton62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton62ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton62ActionPerformed

    private void jButton63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton63ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton63ActionPerformed

    private void jButton64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton64ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton64ActionPerformed

    private void jButton65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton65ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton65ActionPerformed

    private void jButton66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton66ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton66ActionPerformed

    private void jButton67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton67ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton67ActionPerformed

    private void jButton68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton68ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton68ActionPerformed

    private void jButton69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton69ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton69ActionPerformed

    private void jButton70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton70ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton70ActionPerformed

    private void jButton71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton71ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton71ActionPerformed

    private void jButton72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton72ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton72ActionPerformed

    private void jButton73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton73ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton73ActionPerformed

    private void jButton74ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton74ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton74ActionPerformed

    private void jButton75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton75ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton75ActionPerformed

    private void jButton76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton76ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton76ActionPerformed

    private void jButton77ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton77ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton77ActionPerformed

    private void jButton78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton78ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton78ActionPerformed

    private void jButton79ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton79ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton79ActionPerformed

    private void jButton80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton80ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton80ActionPerformed

    private void jButton81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton81ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton81ActionPerformed

    private void jButton82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton82ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton82ActionPerformed

    private void jButton83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton83ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton83ActionPerformed

    private void jButton84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton84ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton84ActionPerformed

    private void jButton85ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton85ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton85ActionPerformed

    private void jButton86ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton86ActionPerformed
        p1ActionHandler(evt); // TODO add your handling code here:
    }//GEN-LAST:event_jButton86ActionPerformed

    private void jButton87ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton87ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton87ActionPerformed

    private void jButton88ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton88ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton88ActionPerformed

    private void jButton89ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton89ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton89ActionPerformed

    private void jButton90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton90ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton90ActionPerformed

    private void jButton91ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton91ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton91ActionPerformed

    private void jButton92ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton92ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton92ActionPerformed

    private void jButton93ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton93ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton93ActionPerformed

    private void jButton94ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton94ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton94ActionPerformed

    private void jButton95ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton95ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton95ActionPerformed

    private void jButton96ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton96ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton96ActionPerformed

    private void jButton97ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton97ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton97ActionPerformed

    private void jButton98ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton98ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton98ActionPerformed

    private void jButton99ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton99ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton99ActionPerformed

    private void jButton100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton100ActionPerformed
        p1ActionHandler(evt);         // TODO add your handling code here:
    }//GEN-LAST:event_jButton100ActionPerformed

    private void p1ActionHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p1ActionHandler
        p2ActionHandler(evt);// TODO add your handling code here:
    }//GEN-LAST:event_p1ActionHandler

    private void jButton102ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton102ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton102ActionPerformed

    private void jButton103ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton103ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton103ActionPerformed

    private void jButton104ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton104ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton104ActionPerformed

    private void jButton105ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton105ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton105ActionPerformed

    private void jButton106ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton106ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton106ActionPerformed

    private void jButton107ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton107ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton107ActionPerformed

    private void jButton108ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton108ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton108ActionPerformed

    private void jButton109ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton109ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton109ActionPerformed

    private void jButton110ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton110ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton110ActionPerformed

    private void jButton111ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton111ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton111ActionPerformed

    private void jButton112ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton112ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton112ActionPerformed

    private void jButton113ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton113ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton113ActionPerformed

    private void jButton114ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton114ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton114ActionPerformed

    private void jButton115ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton115ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton115ActionPerformed

    private void jButton116ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton116ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton116ActionPerformed

    private void jButton117ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton117ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton117ActionPerformed

    private void jButton118ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton118ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton118ActionPerformed

    private void jButton119ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton119ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton119ActionPerformed

    private void jButton121ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton121ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton121ActionPerformed

    private void jButton120ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton120ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton120ActionPerformed

    private void jButton122ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton122ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton122ActionPerformed

    private void jButton123ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton123ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton123ActionPerformed

    private void jButton124ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton124ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton124ActionPerformed

    private void jButton125ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton125ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton125ActionPerformed

    private void jButton126ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton126ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton126ActionPerformed

    private void jButton127ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton127ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton127ActionPerformed

    private void jButton128ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton128ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton128ActionPerformed

    private void jButton129ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton129ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton129ActionPerformed

    private void jButton131ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton131ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton131ActionPerformed

    private void jButton130ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton130ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton130ActionPerformed

    private void jButton132ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton132ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton132ActionPerformed

    private void jButton133ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton133ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton133ActionPerformed

    private void jButton134ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton134ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton134ActionPerformed

    private void jButton135ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton135ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton135ActionPerformed

    private void jButton136ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton136ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton136ActionPerformed

    private void jButton137ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton137ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton137ActionPerformed

    private void jButton138ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton138ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton138ActionPerformed

    private void jButton141ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton141ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton141ActionPerformed

    private void jButton140ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton140ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton140ActionPerformed

    private void jButton139ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton139ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton139ActionPerformed

    private void jButton142ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton142ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton142ActionPerformed

    private void jButton143ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton143ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton143ActionPerformed

    private void jButton144ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton144ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton144ActionPerformed

    private void jButton145ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton145ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton145ActionPerformed

    private void jButton146ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton146ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton146ActionPerformed

    private void jButton147ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton147ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton147ActionPerformed

    private void jButton148ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton148ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton148ActionPerformed

    private void jButton151ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton151ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton151ActionPerformed

    private void jButton150ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton150ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton150ActionPerformed

    private void jButton149ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton149ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton149ActionPerformed

    private void jButton152ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton152ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton152ActionPerformed

    private void jButton153ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton153ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton153ActionPerformed

    private void jButton154ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton154ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton154ActionPerformed

    private void jButton155ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton155ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton155ActionPerformed

    private void jButton156ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton156ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton156ActionPerformed

    private void jButton157ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton157ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton157ActionPerformed

    private void jButton158ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton158ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton158ActionPerformed

    private void jButton159ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton159ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton159ActionPerformed

    private void jButton160ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton160ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton160ActionPerformed

    private void jButton161ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton161ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton161ActionPerformed

    private void jButton162ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton162ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton162ActionPerformed

    private void jButton163ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton163ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton163ActionPerformed

    private void jButton164ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton164ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton164ActionPerformed

    private void jButton165ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton165ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton165ActionPerformed

    private void jButton166ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton166ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton166ActionPerformed

    private void jButton167ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton167ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton167ActionPerformed

    private void jButton168ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton168ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton168ActionPerformed

    private void jButton169ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton169ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton169ActionPerformed

    private void jButton170ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton170ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton170ActionPerformed

    private void jButton171ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton171ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton171ActionPerformed

    private void jButton172ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton172ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton172ActionPerformed

    private void jButton173ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton173ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton173ActionPerformed

    private void jButton174ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton174ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton174ActionPerformed

    private void jButton175ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton175ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton175ActionPerformed

    private void jButton176ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton176ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton176ActionPerformed

    private void jButton177ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton177ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton177ActionPerformed

    private void jButton178ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton178ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton178ActionPerformed

    private void jButton179ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton179ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton179ActionPerformed

    private void jButton180ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton180ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton180ActionPerformed

    private void jButton181ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton181ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton181ActionPerformed

    private void jButton182ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton182ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton182ActionPerformed

    private void jButton183ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton183ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton183ActionPerformed

    private void jButton184ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton184ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton184ActionPerformed

    private void jButton185ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton185ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton185ActionPerformed

    private void jButton186ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton186ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton186ActionPerformed

    private void jButton187ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton187ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton187ActionPerformed

    private void jButton188ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton188ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton188ActionPerformed

    private void jButton189ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton189ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton189ActionPerformed

    private void jButton190ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton190ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton190ActionPerformed

    private void jButton191ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton191ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton191ActionPerformed

    private void jButton192ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton192ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton192ActionPerformed

    private void jButton193ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton193ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton193ActionPerformed

    private void jButton194ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton194ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton194ActionPerformed

    private void jButton195ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton195ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton195ActionPerformed

    private void jButton196ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton196ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton196ActionPerformed

    private void jButton197ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton197ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton197ActionPerformed

    private void jButton198ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton198ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton198ActionPerformed

    private void jButton199ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton199ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton199ActionPerformed

    private void jButton200ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton200ActionPerformed
        p2ActionHandler(evt);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton200ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
    jRadioButton2.setSelected(false);  
    jRadioButton3.setSelected(false);  
    jRadioButton4.setSelected(false);  
    // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
    jRadioButton1.setSelected(false);  
    jRadioButton3.setSelected(false);  
    jRadioButton4.setSelected(false);         // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
    jRadioButton1.setSelected(false);  
    jRadioButton2.setSelected(false);  
    jRadioButton4.setSelected(false);         // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
    jRadioButton1.setSelected(false);  
    jRadioButton2.setSelected(false);  
    jRadioButton3.setSelected(false);         // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jButton201ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton201ActionPerformed
        turnSwap =false;
        jDialog1.setVisible(false);
        if(myTurn){
            updateP1Board();
        }else{
            updateP2Board();
        }
    }//GEN-LAST:event_jButton201ActionPerformed
  
    /**
     * @param args the command line arguments
     */
    public void playGame() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BattleShipGUI().setVisible(true);
            }
        });
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton100;
    private javax.swing.JButton jButton101;
    private javax.swing.JButton jButton102;
    private javax.swing.JButton jButton103;
    private javax.swing.JButton jButton104;
    private javax.swing.JButton jButton105;
    private javax.swing.JButton jButton106;
    private javax.swing.JButton jButton107;
    private javax.swing.JButton jButton108;
    private javax.swing.JButton jButton109;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton110;
    private javax.swing.JButton jButton111;
    private javax.swing.JButton jButton112;
    private javax.swing.JButton jButton113;
    private javax.swing.JButton jButton114;
    private javax.swing.JButton jButton115;
    private javax.swing.JButton jButton116;
    private javax.swing.JButton jButton117;
    private javax.swing.JButton jButton118;
    private javax.swing.JButton jButton119;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton120;
    private javax.swing.JButton jButton121;
    private javax.swing.JButton jButton122;
    private javax.swing.JButton jButton123;
    private javax.swing.JButton jButton124;
    private javax.swing.JButton jButton125;
    private javax.swing.JButton jButton126;
    private javax.swing.JButton jButton127;
    private javax.swing.JButton jButton128;
    private javax.swing.JButton jButton129;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton130;
    private javax.swing.JButton jButton131;
    private javax.swing.JButton jButton132;
    private javax.swing.JButton jButton133;
    private javax.swing.JButton jButton134;
    private javax.swing.JButton jButton135;
    private javax.swing.JButton jButton136;
    private javax.swing.JButton jButton137;
    private javax.swing.JButton jButton138;
    private javax.swing.JButton jButton139;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton140;
    private javax.swing.JButton jButton141;
    private javax.swing.JButton jButton142;
    private javax.swing.JButton jButton143;
    private javax.swing.JButton jButton144;
    private javax.swing.JButton jButton145;
    private javax.swing.JButton jButton146;
    private javax.swing.JButton jButton147;
    private javax.swing.JButton jButton148;
    private javax.swing.JButton jButton149;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton150;
    private javax.swing.JButton jButton151;
    private javax.swing.JButton jButton152;
    private javax.swing.JButton jButton153;
    private javax.swing.JButton jButton154;
    private javax.swing.JButton jButton155;
    private javax.swing.JButton jButton156;
    private javax.swing.JButton jButton157;
    private javax.swing.JButton jButton158;
    private javax.swing.JButton jButton159;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton160;
    private javax.swing.JButton jButton161;
    private javax.swing.JButton jButton162;
    private javax.swing.JButton jButton163;
    private javax.swing.JButton jButton164;
    private javax.swing.JButton jButton165;
    private javax.swing.JButton jButton166;
    private javax.swing.JButton jButton167;
    private javax.swing.JButton jButton168;
    private javax.swing.JButton jButton169;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton170;
    private javax.swing.JButton jButton171;
    private javax.swing.JButton jButton172;
    private javax.swing.JButton jButton173;
    private javax.swing.JButton jButton174;
    private javax.swing.JButton jButton175;
    private javax.swing.JButton jButton176;
    private javax.swing.JButton jButton177;
    private javax.swing.JButton jButton178;
    private javax.swing.JButton jButton179;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton180;
    private javax.swing.JButton jButton181;
    private javax.swing.JButton jButton182;
    private javax.swing.JButton jButton183;
    private javax.swing.JButton jButton184;
    private javax.swing.JButton jButton185;
    private javax.swing.JButton jButton186;
    private javax.swing.JButton jButton187;
    private javax.swing.JButton jButton188;
    private javax.swing.JButton jButton189;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton190;
    private javax.swing.JButton jButton191;
    private javax.swing.JButton jButton192;
    private javax.swing.JButton jButton193;
    private javax.swing.JButton jButton194;
    private javax.swing.JButton jButton195;
    private javax.swing.JButton jButton196;
    private javax.swing.JButton jButton197;
    private javax.swing.JButton jButton198;
    private javax.swing.JButton jButton199;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton200;
    private javax.swing.JButton jButton201;
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
    private javax.swing.JButton jButton66;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton68;
    private javax.swing.JButton jButton69;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton70;
    private javax.swing.JButton jButton71;
    private javax.swing.JButton jButton72;
    private javax.swing.JButton jButton73;
    private javax.swing.JButton jButton74;
    private javax.swing.JButton jButton75;
    private javax.swing.JButton jButton76;
    private javax.swing.JButton jButton77;
    private javax.swing.JButton jButton78;
    private javax.swing.JButton jButton79;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton80;
    private javax.swing.JButton jButton81;
    private javax.swing.JButton jButton82;
    private javax.swing.JButton jButton83;
    private javax.swing.JButton jButton84;
    private javax.swing.JButton jButton85;
    private javax.swing.JButton jButton86;
    private javax.swing.JButton jButton87;
    private javax.swing.JButton jButton88;
    private javax.swing.JButton jButton89;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton90;
    private javax.swing.JButton jButton91;
    private javax.swing.JButton jButton92;
    private javax.swing.JButton jButton93;
    private javax.swing.JButton jButton94;
    private javax.swing.JButton jButton95;
    private javax.swing.JButton jButton96;
    private javax.swing.JButton jButton97;
    private javax.swing.JButton jButton98;
    private javax.swing.JButton jButton99;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JPanel p1Panel;
    private javax.swing.JPanel p2Panel;
    // End of variables declaration//GEN-END:variables
}
