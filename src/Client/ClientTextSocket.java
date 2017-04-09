package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientTextSocket extends Thread
{
	private static final int TEXT_SERVER_PORT = 65432; // text server port number to connect to
	private boolean open; // tells loop when to stop
	private Socket socket; // socket
	private DataInputStream dIn; // input
	private DataOutputStream dOut; // output

	/*
	 * Initialize stuff
	 */
	public ClientTextSocket(String addr)
	{
		open = true; // socket just made, so keep it open
		try {
			socket = new Socket(addr, TEXT_SERVER_PORT); // make socket
			dIn = new DataInputStream(socket.getInputStream()); // get input stream
			dOut = new DataOutputStream(socket.getOutputStream()); // get output stream
		} catch(Exception err) {
			ErrorDisplay.showError(err);
		}
	}

	/*
	 * Infinite loop for getting input from the server
	 */
	public void run()
	{
		while(open) {
			try {
				String m = dIn.readUTF(); // get messages from the serverS
				// message display call here
			} catch (Exception e) {
				ErrorDisplay.showError(e);
			}
		}
		// out of loop now, so close socket
		try {
			socket.close();
		} catch(Exception err) {
			ErrorDisplay.showError(err);
		}
	}

	/*
	 * Send a message to the server
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
	
	public void close() {
		open = false; // tell thread to stop loop
		sendMessage(""); // send message to close connection
	}
}
