package Server;

public class ServerStartup
{
	private static final int TEXT_SERVER_PORT = 65432;
	
	/*
	 * RUN THIS THROUGH COMAND PROMPT TO START THE SERVER
	 */
	public static void main(String[] args)
	{
		try {
			TextMainServer tms = new TextMainServer(TEXT_SERVER_PORT);
			tms.run();
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	
	public static void printError(Exception err) {
		err.printStackTrace(); }
}
