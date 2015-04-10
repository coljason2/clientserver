package com.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.common.broadcast;

import frame.ServerFrame;

public class ServerManager extends Thread
{
    // 接收Client
    private Socket client;
    private String name;
    // 廣播器
    private broadcast broadcastserver;
    // 收到訊息
    private DataInputStream input=null;
    // 傳遞訊息
    private DataOutputStream output=null;
    // 視窗
    private ServerFrame frame;

    public ServerManager(Socket client2, broadcast broad, ServerFrame frame)
	    throws IOException
    {
	this.frame=frame;
	client=client2;
	name=client.getRemoteSocketAddress().toString().substring(11)+":";
	broadcastserver=broad;
	output=new DataOutputStream(client.getOutputStream());
    }

    @Override
    public void run()
    {
	showMessage("有"+client.getRemoteSocketAddress().toString()+"連線進來");
	System.out.printf("有%s連線進來!\n",client.getRemoteSocketAddress());
	broadcastserver.setBroadMessage(name+"連線進來。");
	try
	{
	    // 單獨給client
	    output.writeUTF("Hi "+name+" 目前連線數："+broadcastserver.getCount());
	    output.flush();
	    input=new DataInputStream(client.getInputStream());
	    while (true)
	    { // 接收client 來的訊息再透過廣播傳給其他client端
	      // 利用一字串來等待UTF
		String message=name+input.readUTF();
		if(message!=null)
		{
		    showMessage(message);
		    broadcastserver.setBroadMessage(message);
		}
	    }
	}
	catch (IOException e)
	{
	    showMessage(name+"離開....");
	    broadcastserver.remove(client);
	    broadcastserver.setCount(-1);
	    broadcastserver.setBroadMessage(name+"離開....");
	}
    }

    private void showMessage(String m)
    {
	frame.textArea.append(m+"\n");
    }
}
