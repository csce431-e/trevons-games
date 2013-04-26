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
import javax.swing.*;
import javax.swing.text.DefaultCaret;


class Slave implements Runnable
{
    Socket connection1, connection2;
    private String message;
    JTextArea server_info_terminal;
    
    ObjectOutputStream out1,out2;
    ObjectInputStream in1, in2;
    
    Slave(Socket s, Socket s2, ObjectOutputStream o1, ObjectInputStream i1, ObjectOutputStream o2, ObjectInputStream i2, JTextArea text_area)
    {
        connection1 = s;
        connection2 = s2;
        out1 = o1;
        in1 = i1;
        out2 = o2;
        in2 = i2;
        server_info_terminal = text_area;
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
            //sendMessage("Connection successful",true);
            sendMessage("Connection successful",false);
            
            while(true)
            {
                try
                {
                    System.out.println("Waiting for move");
                    server_info_terminal.setText(server_info_terminal.getText()+"Waiting for move\n");
                    message = "nothing";

                    message = (String)in1.readObject();		//receiving from in1, so send to out2
                    
                    System.out.println("Message received: "+message);
                    server_info_terminal.setText(server_info_terminal.getText()+"Message received: "+message+"\n");
                    
                    sendMessage(message, false);
                    
                    System.out.println("Message sent: "+message);
                    server_info_terminal.setText(server_info_terminal.getText()+"Message sent: "+message+"\n");
                    //check here for if message was quit
                    if(message.equals("quit"))
                    {
                        server_info_terminal.setText(server_info_terminal.getText()+"One player has quit\n");
                        break;
                    }

                    message = (String)in2.readObject();
                    
                    System.out.println("Message received: "+message);
                    server_info_terminal.setText(server_info_terminal.getText()+"Message received: "+message+"\n");
                    
                    sendMessage(message, true);
                    
                    System.out.println("Message sent: "+message);
                    server_info_terminal.setText(server_info_terminal.getText()+"Message sent: "+message+"\n");
                    //check here again if message was quit
                    if(message.equals("quit"))
                    {
                        server_info_terminal.setText(server_info_terminal.getText()+"Other player has quit\n");
                        break;
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
                System.err.println("thread dying");
                server_info_terminal.setText(server_info_terminal.getText()+"Thread Dying\n");
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
        ArrayList<Thread> threads = new ArrayList<>();
        JTextArea server_info_terminal;
        
        Server_Handler(MainMenu menu)
        {
            menu.removeAll();
            
            server_info_terminal = new JTextArea("Starting Server...");
            
            //set the autoscroll to update automatically
            DefaultCaret caret = (DefaultCaret)server_info_terminal.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            
            JScrollPane scroll_pane = new JScrollPane(server_info_terminal);
            scroll_pane.setSize(393, 378);
            menu.add(scroll_pane);
            menu.paintAll(menu.getGraphics());
        }
        
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
                    System.out.println("\n------------------------------------------------------------");
                    server_info_terminal.setText(server_info_terminal.getText() + "\n------------------------------------------------------------\n");
                    System.out.println("\nWaiting for connection #"+ count.toString());
                    server_info_terminal.setText(server_info_terminal.getText() + "\nWaiting for connection #"+ count.toString()+"\n");
                    
                    connection = serverSocket.accept();
                    
                    System.out.println("Connection received from " + connection.getInetAddress().getHostName()+"\n");
                    server_info_terminal.setText(server_info_terminal.getText() + "Connection received from " + connection.getInetAddress().getHostName()+"\n\n");
                    
                    //remove any threads that have ended since last accept
                    for(int i = 0; i<threads.size(); i++)
                    {
                        if(!threads.get(i).isAlive())
                        {
                            threads.remove(i);
                            i--;
                        }
                    }
                    System.out.println("# of threads currently: " + threads.size()+"\n");
                    server_info_terminal.setText(server_info_terminal.getText() + "# of threads currently: " + threads.size()+"\n\n");
                    
                    ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                    //wait for name of the game trying to connect
                    String message = (String)in.readObject();
                    boolean found = false;
                    
                    System.out.println("Searching for host");
                    server_info_terminal.setText(server_info_terminal.getText() + "Searching for host\n");
                    
                    for(int i = 0; i<waiting_hosts.size(); i++)
                    {
                        if(message.equals(waiting_hosts.get(i).game_name))
                        {
                           System.out.println("found waiting host");
                           server_info_terminal.setText(server_info_terminal.getText() + "found waiting host\n");
                           
                           waiting_hosts.get(i).out_stream.writeObject("didyouquit");
                           String check_quit = (String)waiting_hosts.get(i).in_stream.readObject();
                           if(check_quit.equals("yes"))
                           {
                               //deleting host
                                System.out.println("found waiting host to delete");
                                server_info_terminal.setText(server_info_terminal.getText() + "found waiting host to delete\n");
                                
                                waiting_hosts.remove(i);
                                break;
                           }
                           else
                           {
                                found = true;
                                out.writeObject("starting"); //tell second player starting
                                out.flush();
                                waiting_hosts.get(i).out_stream.writeObject("starting"); //tell original player starting
                                waiting_hosts.get(i).out_stream.flush();

                                Thread t = new Thread(new Slave(connection, waiting_hosts.get(i).socket,waiting_hosts.get(i).out_stream,waiting_hosts.get(i).in_stream,out,in,server_info_terminal));
                                waiting_hosts.remove(i);
                                threads.add(t);
                                t.start();
                                break;
                           }
                        }
                    }
                    if(found == false)
                    {
                       System.out.println("No host found, creating host");
                       server_info_terminal.setText(server_info_terminal.getText() + "No host found, creating host\n");
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
    public void start(MainMenu menu)
    {
        Thread t = new Thread(new Server_Handler(menu));
        t.start();
    }
}

/*




*/
