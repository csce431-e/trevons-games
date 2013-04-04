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
    Socket connection1, connection2;
    private String line, message;
    
    ObjectOutputStream out1, out2;
    ObjectInputStream in1, in2;
    
    Slave(Socket s1, Socket s2)
    {
        connection1 = s1;
        connection2 = s2;
    }
    
    void sendMessage(String msg, boolean one)
    {
        try
        {
			if(one)
			{
				out1.writeObject(msg);
				out1.flush();
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
            out1 = new ObjectOutputStream(connection1.getOutputStream());
            out1.flush();
			out2 = new ObjectOutputStream(connection2.getOutputStream());
            out2.flush();
            in1 = new ObjectInputStream(connection1.getInputStream());
            in2 = new ObjectInputStream(connection2.getInputStream());
            sendMessage("Connection successful",true);
            sendMessage("Connection successful",false);
            
            while(true)
            {
                try
                {
                    System.out.println("Waiting for move");
                    message = "nothing";

					message = (String)in1.readObject();		//receiving from in1, so send to out2
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
                in1.close();
                in2.close();
                out1.close();
                out2.close();
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
            ServerSocket providerSocket, providerSocket2;
            Socket connection1, connection2;
            String message;
            int port_number = 2004;
            
            while(true)
            {
                providerSocket = new ServerSocket(port_number++);
                System.out.println("Waiting for first connection");
                connection1 = providerSocket.accept();
                System.out.println("Connection received from " + connection1.getInetAddress().getHostName());
                
                providerSocket2 = new ServerSocket(port_number++);
                System.out.println("Waiting for second connection");
                connection2 = providerSocket2.accept();
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

/*
try it without threads (just comment out the 3 thread lines at the end and see if it forms the connection w/ both




*/
