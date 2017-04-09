package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/*
 * This class managers server text communications
 * Run only one instance of this class at a time
 */
public class TextMainServer extends Thread
{
	private int portNum; // port number
	private ServerSocket connector; // socket to receive connections
	private static ArrayList<TextServer> connections; // list of all connections to clients
	private static boolean shutdown; // determines when to shutdown
	
	public TextMainServer(int pn) throws IOException
	{
		shutdown = false; // if new server made, keep it from shutting down immediately
		connections = new ArrayList<TextServer>(); // init
		portNum = pn; // copy port number
		connector = new ServerSocket(portNum); // init ServerSocket
		connector.setSoTimeout(1000);
	}
	
	/*
	 * Runs main loop to wait for incoming connections and store them
	 * Closes all connected ports and ServerSocket on shutdown
	 */
	public void run()
	{
		while(!shutdown) {
			try {
				Socket s = connector.accept(); // wait for connection
				TextServer ts = new TextServer(s); // create class to manage connection
				connections.add(ts); // add to list of connections
				ts.start(); // start connection manager
			} catch (SocketTimeoutException e) {
				// do nothing, too many of these
			} catch (IOException e) {
				ServerStartup.printError(e);
			}
		}
		// close all connections
		for(TextServer ts : connections) {
			ts.close(); } // close each connection to client
		try {
			connector.close(); // close server socket
		} catch (IOException e) {
			ServerStartup.printError(e);
		}
		ServerStartup.print("text server closed");
	}
	
	/*
	 * Remove a client connection from connection list
	 * Called when client disconnects
	 */
	public static void removeConnection(TextServer ts)
	{
		ts.close();
		connections.remove(ts);
	}
	
	/*
	 * Called when a client connection receives a message from associated client
	 * Logs the message and send it back to all clients to display in chat
	 */
	public static void receive(String m) {
		for(TextServer ts : connections) {
			ts.send(m); }
	}
	
	/*
	 * Call this to stop the main loop and shut down the message server
	 */
	public static void shutdown() {
		ServerStartup.print("shutting down text server");
		shutdown = true; }
}
