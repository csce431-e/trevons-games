/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgames;

/**
 *
 * @author Tom
 */

import java.io.*;
import java.net.*;
import java.util.*;


class Slave implements Runnable
{
    Socket connection1, connection2;
    private String message;
    
    ObjectOutputStream out1,out2;
    ObjectInputStream in1, in2;
    
    Slave(Socket s, Socket s2, ObjectOutputStream o1, ObjectInputStream i1, ObjectOutputStream o2, ObjectInputStream i2)
    {
        connection1 = s;
        connection2 = s2;
        out1 = o1;
        in1 = i1;
        out2 = o2;
        in2 = i2;
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
                    ex.printStackTrace();
                }   
            }
        }
        finally
        {
           try
           {
                in1.close();
                out1.close();
                connection1.close();
                in2.close();
                out2.close();
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
    public class Pair
    {
	Socket socket;
	String game_name;
        ObjectOutputStream out_stream;
        ObjectInputStream in_stream;
	Pair(Socket s, String n, ObjectOutputStream o, ObjectInputStream i)
	{
	    socket = s;
	    game_name = n;
            out_stream = o;
            in_stream = i;
	}
    }
    
    class Server_Handler implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                ArrayList<Pair> waiting_hosts = new ArrayList<>();
                Socket connection;
                int port_number = 2008; //back to 2006
                Integer count = 1;
                ServerSocket serverSocket = new ServerSocket(port_number);

                while(true)
                {

                    System.out.println("Waiting for connection #"+ count.toString());
                    connection = serverSocket.accept();
                    System.out.println("Connection received from " + connection.getInetAddress().getHostName());

                    ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                    String message = (String)in.readObject();
                    //System.out.println(message);
                    boolean found = false;
                    System.out.println("Searching for host");
                    for(int i = 0; i<waiting_hosts.size(); i++)
                    {
                        if(message.equals(waiting_hosts.get(i).game_name))
                        {
                            System.out.println("found waiting host");
                           found = true;
                           out.writeObject("starting"); //tell second player starting
                           out.flush();
                           waiting_hosts.get(i).out_stream.writeObject("starting"); //tell original player starting
                           waiting_hosts.get(i).out_stream.flush();

                           Thread t = new Thread(new Slave(connection, waiting_hosts.get(i).socket,waiting_hosts.get(i).out_stream,waiting_hosts.get(i).in_stream,out,in));
                           waiting_hosts.remove(i);
                           t.start();
                        }
                    }
                    if(found == false)
                    {
                        System.out.println("No host found, creating host");
                       waiting_hosts.add(new Pair(connection, message, out, in));
                       //send message from out to the client that says they are waiting for a connection
                       out.writeObject("waiting");
                       out.flush();
                    }
                    count++;
                }
            }
            catch(IOException ioException)
            {
                ioException.printStackTrace();
            }
            catch(ClassNotFoundException e)
            {
                System.out.println("class not found in server main");
            }
            finally
            {
            }
        }
    }
    
    //public static void main(String args[])
    public void start()
    {
        Thread t = new Thread(new Server_Handler());
        t.start();
    }
}

/*




*/
