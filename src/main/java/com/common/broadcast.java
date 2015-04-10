package com.common;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import frame.ServerFrame;

public class broadcast extends Thread
{

    private List<Socket> clients=new ArrayList<Socket>();
    private String BroadMessage=null;
    private int CurrentClients;
    // 傳遞訊息
    private DataOutputStream output=null;

    @Override
    public void run()
    {
	System.out.println("broadcast start ok");
	while (true)
	{
	    if(BroadMessage!=null)
	    {
		try
		{
		    broadcastAll();
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
	    }

	}
    }

    public void setClients(Socket client) throws IOException
    {
	clients.add(client);
    }

    public synchronized void setBroadMessage(String broadMessage)
    {
	BroadMessage=broadMessage;
    }

    private synchronized void broadcastAll() throws IOException
    {
	for(Socket c : clients)
	{
	    output=new DataOutputStream(c.getOutputStream());
	    output.writeUTF(BroadMessage);
	    output.flush();
	    output=null;
	}
	BroadMessage=null;
    }

    public void setCount(int nowNumber)
    {
	CurrentClients=CurrentClients+nowNumber;
    }

    public int getCount()
    {
	return CurrentClients;
    }

    public void remove(Socket client)
    {
	clients.remove(client);
    }
}
