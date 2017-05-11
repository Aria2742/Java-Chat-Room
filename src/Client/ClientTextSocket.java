package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * @author Nathan Augsburger
 * 
 * This class manages text communications with the server. This class is responsible for 
 * sending text messages to the server and relaying received messages to the text viewer 
 * component.
 */
public class ClientTextSocket extends Thread
{
	/** The port number of the text server. This MUST be the same as the server's!
	 * <p>
	 * TO DO: make this a part of a class specifically for constants.
	 */
	private static final int TEXT_SERVER_PORT = 65432;
	/** Sentinel variable for the main loop of this thread. When set to false, the socket will close after receiving the next message. */
	private boolean open;
	/** State of the connection. Set to true if and only if the socket successfully connects to the server. Otherwise, this is false. */
	private boolean valid;
	/** Socket to connect to the main server. */
	private Socket socket;
	/** DataInputStream to read data sent from the server to this socket. */
	private DataInputStream dIn;
	/** DataOutputStream to send data from this socket to the server. */
	private DataOutputStream dOut;
	/** TextViewer component used in the main window. All text received from the server is passed here. */
	private TextViewer textView;
	/** Username appended to the beginning of every message sent from this socket. */
	protected String username;
	

	/**
	 * Initialize the socket by attempting to connect to the server and creating data streams. 
	 * This constructor also sets {@link Client.ClientTextSocket#open} and  {@link Client.ClientTextSocket#valid} 
	 * to true unless an error occurs in connecting to the server or creating the data streams. 
	 * If no errors occur, this constructor will also ask for a username.
	 * @param addr The IP address to connect to
	 */
	public ClientTextSocket(String addr)
	{
		valid = true; // start with assuming socket is valid
		try {
			socket = new Socket(addr, TEXT_SERVER_PORT); // make socket
			dIn = new DataInputStream(socket.getInputStream()); // get input stream
			dOut = new DataOutputStream(socket.getOutputStream()); // get output stream
			getUserName();
		} catch(Exception err) {
			valid = false; // something went wrong, so socket is not valid connection
			ErrorDisplay.showError(err);
		}
		open = valid;
	}

	/**
	 * Asks the user for a username and sets {@link Client.ClientTextSocket#open} to that string.
	 */
	private void getUserName()
	{
		boolean valid = false;
		do {
		try {
				username = JOptionPane.showInputDialog("Enter a username");
				username += ": ";
				valid = true;
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, "Please enter a username");
			}
		} while(!valid);
	}
	
	/**
	 * Sets {@link Client.ClientTextSocket#textView}.
	 * @param tv The textViewer object to print messages to
	 */
	public void setViewer(TextViewer tv) {
		textView = tv; }
	
	/**
	 * Main loop to get messages from the server and relay them to the textViewer.
	 */
	public void run()
	{
		while(open) {
			try {
				String m = dIn.readUTF(); // get messages from the server
				textView.addText(m + "\n"); // add a new line to the end of the message and print it
			} catch (Exception e) {
				ErrorDisplay.showError(e);
				open = false;
			}
		}
		// out of loop now, so close socket
		try {
			socket.close();
		} catch(Exception err) {
			ErrorDisplay.showError(err);
		}
	}

	/**
	 * Sends a message to the server.
	 * @param s The string to be sent
	 */
	public void sendMessage(String s)
	{
		try {
			dOut.writeUTF(s); // write message
			dOut.flush(); // push message out
		} catch (Exception err) {
			ErrorDisplay.showError(err);
		}
	}
	
	/**
	 * Shuts down the server by sending a disconnect message and setting {@link Client.ClientTextSocket#open} to false.
	 */
	public void close() {
		open = false; // tell thread to stop loop
		sendMessage("disconnect"); // send message to close connection
	}
	
	/**
	 * Returns {@link Client.ClientTextSocket#valid}. This is true if and only if the 
	 * connection to the server was successfully established. Otherwise, this returns false.
	 * @return The value of {@link Client.ClientTextSocket#valid}
	 */
	public boolean isValid() {
		return valid; }
}
