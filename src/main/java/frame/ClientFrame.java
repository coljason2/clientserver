package frame;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.client.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends JFrame
{

    /**
     * 
     */
    private static final long serialVersionUID=8140887891083273026L;
    public JTextArea textArea=new JTextArea();
    final JTextArea textinput=new JTextArea();
    private Socket connect;
    private DataOutputStream output=null;
    private String inputMessage=null;
    private Receive receive;

    public ClientFrame()
    {

	setResizable(false);
	setTitle("Client");
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100,100,513,298);
	setLocationRelativeTo(null);
	getContentPane().setLayout(null);

	JScrollPane scrollPane=new JScrollPane();
	scrollPane.setBounds(14,13,479,182);
	getContentPane().add(scrollPane);
	textArea.setEditable(false);

	scrollPane.setViewportView(textArea);

	JButton btnSend=new JButton("send");
	btnSend.setBounds(14,207,101,29);
	getContentPane().add(btnSend);
	JScrollPane scrollPane_1=new JScrollPane();
	scrollPane_1.setBounds(129,208,364,47);
	getContentPane().add(scrollPane_1);
	scrollPane_1.setViewportView(textinput);
	setVisible(true);

	try
	{
	    connect=new Socket("127.0.0.1",5566);
	    receive=new Receive(connect,ClientFrame.this);
	    output=new DataOutputStream(connect.getOutputStream());
	    receive.start();
	}
	catch (Exception e)
	{
	    showMessage(e.getMessage()+"請重新啟動");
	}

	btnSend.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {

		try
		{
		    inputMessage=textinput.getText();
		    textinput.setText("");
		    output.writeUTF(inputMessage);
		    output.flush();
		}
		catch (IOException e)
		{
		    showMessage(e.getMessage());
		}

	    }
	});
	addWindowListener(new WindowAdapter()
	{
	    @Override
	    public void windowClosed(WindowEvent arg0)
	    {
		try
		{
		    connect.close();
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
	    }
	});
    }

    public void showMessage(String m)
    {
	textArea.append(m+"\n");
    }
}
