package Client;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame implements WindowListener
{
	public static void main(String[] args)
	{
		new MainWindow().setVisible(true);
	}
	
	private ClientTextSocket textSock;
	private TextSender textSend;
	
	public MainWindow()
	{
		String IP = JOptionPane.showInputDialog("Enter the IP of the server");
		textSock = new ClientTextSocket(IP);
		textSend = new TextSender(textSock);
		add(textSend);
	}

	// close server connection on window close
	public void windowClosing(WindowEvent e) {
		textSock.close(); }
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}
