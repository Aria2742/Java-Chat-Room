package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/*
 * This class starts and displays the client application
 * Communication between components and sockets is set up in this class as well
 */
public class MainWindow extends JFrame
{
	public static void main(String[] args)
	{
		new MainWindow().setVisible(true);
	}

	// socket for receiving and sending text
	private ClientTextSocket textSock;
	// container for text sending components
	private TextSender textSend;
	// container for text viewer components
	private TextViewer textView;
	
	public MainWindow()
	{
		// setup connection first
		String IP = JOptionPane.showInputDialog("Enter the IP of the server");
		textSock = new ClientTextSocket(IP);
		textSock.start(); // run the socket thread
		// now make the window
		setLayout(new BorderLayout()); // use border layout
		setSize(800,600); // set a window size so window doesn't start at size 0,0
		setBackground(Color.BLACK);
		addWindowListener(new WindowCloseListener()); // add listener to disconnect on window close
		textSend = new TextSender(textSock); // create the text sender
		add(textSend, BorderLayout.PAGE_END); // add it to the bottom of the window
		textView = new TextViewer(textSock, new JTextArea()); // create the text viewer
		add(textView, BorderLayout.CENTER); // add it to the left of the window
	}

	/*
	 * Class to disconnect socket from the server on window close
	 * Does nothing on any other window event
	 */
	private class WindowCloseListener implements WindowListener
	{
		// close server connection on window close
		public void windowClosing(WindowEvent e) {
			textSock.close(); // close the socket
			System.exit(0); // exit this program
		}

		public void windowActivated(WindowEvent e) {}
		public void windowClosed(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
	}
}
