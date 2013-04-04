/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames;

/**
 *
 * @author Tom
 */

//import boardgames.pegSolitaire.ClientPanel;
//import boardgames.pegSolitaire.SolitaireMove;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class Slave implements Runnable
{
    Socket connection, conn2;
    private String line, message;
    
    ObjectOutputStream out,out2;
    ObjectInputStream in, in2;
    
    Slave(Socket s, Socket s2)
    {
        connection = s;
        conn2 = s2;
    }
    
    void sendMessage(String msg, boolean one)
    {
        try
        {
            if(one)
            {
                    out.writeObject(msg);
                    out.flush();
            }
            else
            {
                    out2.writeObject(msg);
                    out2.flush();
            }
            //System.out.println("server>" + msg);
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
    
    
    @Override
    public void run()
    {
        try
        {
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            
            out2 = new ObjectOutputStream(conn2.getOutputStream());
            out2.flush();
            in2 = new ObjectInputStream(conn2.getInputStream());

            sendMessage("Connection successful",true);
            sendMessage("Connection successful",false);
            
            sendMessage("0",true); //going first
            sendMessage("Your not going first",false);
            
            while(true)
            {
                try
                {
                    System.out.println("Waiting for move");
                    message = "nothing";

                    message = (String)in.readObject();		//receiving from in1, so send to out2
                    System.out.println("Message received: "+message);
                    sendMessage(message, false);
                    System.out.println("Message sent: "+message);

                    message = (String)in2.readObject();
                    System.out.println("Message received: "+message);
                    sendMessage(message, true);
                    System.out.println("Message sent: "+message);
                }
                catch(ClassNotFoundException classNot)
                {
                    System.err.println("data received in unknown format");
                } 
                catch (IOException ex) 
                {
                    //Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
        }
        catch (IOException ioe) 
        {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
        finally
        {
           try
           {
                in.close();
                out.close();
                connection.close();
                in2.close();
                out2.close();
                conn2.close();
           }
            catch(IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }
}

public class Server 
{    
    public static class Pair
    {
        public Thread first;
        public Thread second;
        Pair(Thread f) { first = f;}
        void complete_pair(Thread s) { second = s; }
    }
    
    public static void main(String args[])
    {
        try
        {
            ArrayList<Pair> threads = new ArrayList<>();
            Socket connection, conn2;
            String message;
            int port_number = 2008; //back to 2006
            ServerSocket serverSocket = new ServerSocket(port_number);
            
            while(true)
            {
                
                System.out.println("Waiting for first connection");
                connection = serverSocket.accept();
                System.out.println("Connection received from " + connection.getInetAddress().getHostName());
                
                System.out.println("Waiting for second connection");
                conn2 = serverSocket.accept();
                System.out.println("Connection received from " + conn2.getInetAddress().getHostName());
                
                //now pass both players to the slave
                Slave slave= new Slave(connection,conn2);
                Thread t = new Thread(slave);
                
                //threads.add(new Pair(t));
                t.start();
                
            }
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
        finally
        {
        }
    }
    
}

/*
try it without threads (just comment out the 3 thread lines at the end and see if it forms the connection w/ both




*/
