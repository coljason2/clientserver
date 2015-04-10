package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerError;

import com.common.broadcast;

import frame.ServerFrame;

public class server extends Thread
{
    private broadcast broad;
    public ServerSocket server;
    private Socket client;
    private ServerFrame frame;

    public server(ServerFrame s, int port) throws IOException
    {
	frame=s;
	server=new ServerSocket(port);
	broad=new broadcast();
	broad.start();

    }

    @Override
    public void run()
    {
	frame.textArea.append("Start Wait Client to connect \n");
	while (true)
	{
	    try
	    {
		client=server.accept();
		broad.setCount(1);
		broad.setClients(client);
		new ServerManager(client,broad,frame).start();

	    }
	    catch (IOException e)
	    {
		broad.setCount(-1);
		break;
	    }
	}
    }
}
