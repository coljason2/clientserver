package com.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import frame.ClientFrame;

public class client extends Thread
{

    private Socket connect;
    private DataOutputStream output=null;
    private String inputMessage=null;
    private Scanner scan=new Scanner(System.in);
    private Receive receive;

    public client(ClientFrame clientFrame) throws UnknownHostException,
	    IOException
    {
	connect=new Socket("127.0.0.1",5566);
	receive=new Receive(connect,null);
	output=new DataOutputStream(connect.getOutputStream());
    }

    @Override
    public void run()
    {
	try
	{
	    receive.start();
	    do
	    {
		// System.out.print("Client:");
		inputMessage=scan.nextLine();
		output.writeUTF(inputMessage);
		output.flush();
	    }
	    while (!inputMessage.trim().equals("exit"));
	}
	catch (IOException e)
	{
	    interrupt();
	    e.printStackTrace();
	}
    }
}
