package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * @author Nathan Augsburger
 * 
 * This class starts and displays the client application. All components for the application 
 * (such as text, drawing, etc) will each have their own containers that are added into this 
 * class. The window also has a listener to shut down the connections when the window is closed.
 */
public class MainWindow extends JFrame
{
	/**
	 * Starts the application be initializing an instance of this class.
	 * @param args ignored
	 */
	public static void main(String[] args) {
		new MainWindow().setVisible(true); }

	/** Socket that manages text communication with the server .*/
	private ClientTextSocket textSock;
	/** Container for text sending components. */
	private TextSender textSend; 
	/** Container for text viewing components. */
	private TextViewer textView;
	
	/**
	 * Constructs a new JFrame with all the components of the application included. 
	 * Components such as sockets, text, and drawing areas are all initialized and 
	 * added into this JFrame object. The application is initially visible and 800 x 600 
	 * in size. On an error in socket connection, this method returns and closes any open 
	 * resources. NOTE: May need to change window layout when drawing component is added.
	 */
	public MainWindow()
	{
		// setup connection first
		String IP = JOptionPane.showInputDialog("Enter the IP of the server");
		textSock = new ClientTextSocket(IP);
		if(!textSock.isValid()) {
			System.exit(-1); } // exit on error if the text socket is not valid
		textSock.start(); // run the socket thread
		// now do the window settings and whatnot
		setLayout(new BorderLayout()); // use border layout
		setSize(800,600); // set a window size so window doesn't start at size 0,0
		setBackground(Color.BLACK);
		// now add the components
		addWindowListener(new WindowCloseListener()); // add listener to disconnect on window close
		textSend = new TextSender(textSock); // create the text sender
		add(textSend, BorderLayout.PAGE_END); // add it to the bottom of the window
		textView = new TextViewer(textSock, new JTextArea()); // create the text viewer
		add(textView, BorderLayout.CENTER); // add it to the left of the window
	}

	/*
	 * This class closes all open sockets when the window is closed. Nothing occurs 
	 * on any other WindowEvent.
	 */
	private class WindowCloseListener implements WindowListener
	{
		/**
		 * Closes the server connections when the window closes and exits the program.
		 */
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
