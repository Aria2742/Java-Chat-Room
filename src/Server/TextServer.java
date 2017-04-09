package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TextServer extends Thread
{
	private boolean open; // determines if socket is to remain open
	private Socket connection; // socket
	private DataInputStream dIn; // input
	private DataOutputStream dOut; // output
	
	// init stuff
	public TextServer(Socket s)
	{
		open = true;
		connection = s;
		try {
			dIn = new DataInputStream(connection.getInputStream());
			dOut = new DataOutputStream(connection.getOutputStream());
		} catch (Exception e) {
			ServerStartup.printError(e);
		}
	}
	
	/*
	 * Infinite loop for grabbing input from clients and sending it to main text manager
	 */
	public void run()
	{
		while(open) {
			try {
				String m = dIn.readUTF(); // get the message
				if(m.length() > 0) { // non-empty message, so user sent it
					TextMainServer.receive(m); // send received messages to text management
				} else { // empty message, so disconnect
					TextMainServer.removeConnection(this);
				}
			} catch (Exception e) {
				if(open) {
					ServerStartup.printError(e); }
			}
		}
	}
	
	/*
	 * Sends a message to the client attached to this socket
	 */
	public void send(String m) {
		try {
			dOut.writeUTF(m); // write the message
			dOut.flush(); // push the message out
		} catch (IOException e) {
			ServerStartup.printError(e);
		}
		
	}
	
	/*
	 * Close the server's connection to the client
	 */
	public void close() {
		open = false; // stop the loop for getting input from client
		try {
			connection.close(); // close the socket
		} catch (IOException e) {
			ServerStartup.printError(e);
		}
	}
}
