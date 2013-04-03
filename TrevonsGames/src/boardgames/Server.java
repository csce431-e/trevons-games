/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames;

/**
 *
 * @author Tom
 */

import boardgames.pegSolitaire.ClientPanel;
import boardgames.pegSolitaire.SolitaireMove;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class Slave implements Runnable
{
    Socket connection1, connection2;
    private String line, message;
    
    ObjectOutputStream out;
    ObjectInputStream in;
    
    Slave(Socket s1, Socket s2)
    {
        connection1 = s1;
        connection2 = s2;
    }
    
    void sendMessage(String msg)
    {
        try
        {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
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
            out = new ObjectOutputStream(connection1.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection1.getInputStream());
            sendMessage("Connection successful");
            
            while(true)
            {
                try
                {
                    System.out.println("Waiting for move");
                    message = "nothing";
                    while(message.equals("nothing"))
                    {
                        System.out.println("Message still nothing");
                        message = (String)in.readObject();
                        System.out.println("Message now: "+message);
                        sendMessage(message);

                    }
                    System.out.println(message);
                }
                catch(ClassNotFoundException classNot)
                {
                    System.err.println("data received in unknown format");
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
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
                connection1.close();
                connection2.close();
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
    public static void main(String args[])
    {
        try
        {
            ServerSocket providerSocket;
            Socket connection1, connection2;
            String message;
            int port_number = 2004;
            
            while(true)
            {
                providerSocket = new ServerSocket(port_number++);
                System.out.println("Waiting for first connection");
                connection1 = providerSocket.accept();
                System.out.println("Connection received from " + connection1.getInetAddress().getHostName());
                
                providerSocket = new ServerSocket(port_number++);
                System.out.println("Waiting for second connection");
                connection2 = providerSocket.accept();
                System.out.println("Connection received from " + connection2.getInetAddress().getHostName());
                
                //now pass both players to the slave
                Slave slave= new Slave(connection1, connection2);
                Thread t = new Thread(slave);
                t.start();
                
            }
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
    
}
