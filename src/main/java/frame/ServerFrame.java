package frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.ServerSocket;

import com.server.*;

public class ServerFrame extends JFrame
{

    /**
     * 
     */
    private ServerSocket port=null;
    private static final long serialVersionUID=-8754954257337516263L;
    private JPanel contentPane=new JPanel();;
    public JTextArea textArea=new JTextArea();;
    private JTextField textPort=new JTextField();;
    private JLabel lblPort=new JLabel("port:");
    private server server=null;

    public ServerFrame()
    {
	initialize();
	setTitle("Server");
	setResizable(false);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100,100,300,360);

	contentPane.setBorder(new EmptyBorder(5,5,5,5));
	setContentPane(contentPane);
	contentPane.setLayout(null);

	JButton btnStart=new JButton("start");
	btnStart.setBounds(14,13,75,29);
	contentPane.add(btnStart);

	JButton btnStop=new JButton("stop");
	btnStop.setBounds(93,13,75,29);
	contentPane.add(btnStop);

	JScrollPane scrollPane=new JScrollPane();
	scrollPane.setBounds(14,55,266,262);
	contentPane.add(scrollPane);

	scrollPane.setViewportView(textArea);

	textPort.setBounds(216,15,64,25);
	textPort.setText("5566");
	contentPane.add(textPort);
	textPort.setColumns(10);

	lblPort.setBounds(178,18,35,19);
	contentPane.add(lblPort);
	setLocationRelativeTo(null);
	setVisible(true);
	btnStart.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		try
		{
		    server=new server(ServerFrame.this,Integer.parseInt(textPort.getText()));
		    textPort.setEditable(false);
		    server.start();
		}
		catch (Exception e)
		{
		    showMessage(e.getMessage());
		}
	    }
	});
	btnStop.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		try
		{
		    if(server.isAlive())
			showMessage("關閉伺服器");
		    server.interrupt();
		    server.server.close();
		    textPort.setEditable(true);

		}
		catch (Exception e)
		{
		    showMessage("Server已關閉");
		}

	    }
	});
    }

    protected void showMessage(String message)
    {
	textArea.append(message+"\n");

    }

    private void initialize()
    {
	try
	{
	    port=new ServerSocket(7788);
	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog(null,"SERVER已啟動");
	    System.exit(0);
	}

    }
}
