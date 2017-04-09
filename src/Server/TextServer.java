package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TextServer extends Thread
{
	private boolean open; // determines if socket is to remain open
	private Socket connection; // socket
	private DataInputStream dIn; // input stream
	private DataOutputStream dOut; // output stream
	
	/*
	 * Initialize stuff
	 */
	public TextServer(Socket s)
	{
		open = true; // socket just made, so keep it open
		connection = s; // set the socket
		try {
			dIn = new DataInputStream(connection.getInputStream()); // get input stream
			dOut = new DataOutputStream(connection.getOutputStream()); // get output stream
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
					TextMainServer.removeConnection(this); // remove this from the server's list of connections
					open = false; // break out of loop
				}
			} catch (Exception e) {
			//	if(open) {
				//	ServerStartup.printError(e); }
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
		ServerStartup.print("closing socket");
		open = false; // stop the loop for getting input from client
		try {
			send(""); // tell connected socket that this is closing
			connection.close(); // close the socket
		} catch (IOException e) {
			ServerStartup.printError(e);
		}
	}
}
