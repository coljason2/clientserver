package com.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import frame.ClientFrame;

public class Receive extends Thread
{
    private Socket connect;
    private DataInputStream input=null;
    private ClientFrame frame;
    String getMessage;

    public Receive(Socket c, ClientFrame clientFrame) throws IOException
    {
	frame=clientFrame;
	connect=c;
	input=new DataInputStream(connect.getInputStream());
    }

    @Override
    public void run()
    {
	try
	{
	    getMessage=input.readUTF();
	    showMessage(getMessage);
	    while (true)
	    {
		getMessage=input.readUTF();
		while (getMessage!=null)
		{
		    showMessage(getMessage);
		    getMessage=input.readUTF();
		}
	    }
	}
	catch (Exception e)
	{
	    showMessage(e.getMessage());
	}
    }

    private void showMessage(String m)
    {
	frame.textArea.append(m+"\n");
    }
}
