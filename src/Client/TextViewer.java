package Client;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/*
 * This class displays text received by the server in a scrollable window
 * Initialize with the client text socket (to attach this to the socket) and a JTextPane
 * Once initialized, simply add to the new window
 */
public class TextViewer extends JScrollPane
{
	JTextArea textArea;
	
	public TextViewer(ClientTextSocket sock, JTextArea ta)
	{
		super(ta); // call super constructor
		textArea = ta; // set textArea
		textArea.setEditable(false); // set text area to not be editable
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // set scroll bar to be vertical
		//setPreferredSize(new Dimension(500,500)); // needed?
		sock.setViewer(this); // pass this to the text socket
	}
	
	/*
	 * Adds text to the text area
	 */
	public void addText(String m) {
		textArea.append(m); }
}
