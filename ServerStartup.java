package Server;

import java.util.Scanner;

/*
 * This class starts up the server (both text and drawing)
 * Only supported command is "shutdown"
 */
public class ServerStartup
{
	private static final int TEXT_SERVER_PORT = 65432; // port for text server

	public static void main(String[] args)
	{
		System.out.println("Starting server");
		try {
			TextMainServer tms = new TextMainServer(TEXT_SERVER_PORT);
			tms.start();
		} catch(Exception err) {
			err.printStackTrace();
		}
		// now wait until command "shutdown" is entered
		Scanner exit = new Scanner(System.in);
		do {
			System.out.println("Enter \"shutdown\" to shutdown the server");
		}while(!exit.nextLine().equalsIgnoreCase("shutdown"));
		// shut down the server
		TextMainServer.shutdown();
		exit.close(); // close the scanner
	}
	
	public static void print(String s) {
		System.out.println(s); }
	
	public static void printError(Exception err) {
		err.printStackTrace(); }
}
