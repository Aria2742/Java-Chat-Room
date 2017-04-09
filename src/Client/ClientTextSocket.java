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

	// init
	public ClientTextSocket(String addr)
	{
		open = true;
		try {
			socket = new Socket(addr, TEXT_SERVER_PORT);
			dIn = new DataInputStream(socket.getInputStream());
			dOut = new DataOutputStream(socket.getOutputStream());
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
				String m = dIn.readUTF(); // send received messages to text management
				System.out.println(m);
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
			dOut.writeUTF(s);
			dOut.flush();
		} catch (Exception err) {
			ErrorDisplay.showError(err);
		}
	}
	
	public void close() {
		open = false;
		sendMessage(""); // send message to close connection
	}
}
