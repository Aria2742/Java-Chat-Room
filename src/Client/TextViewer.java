package Client;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * This class is a scrollable text area for displaying text messages received from the server. 
 * The pane scrolls only vertically, and the size of the scroll bar changes based on the amount 
 * of text on the display.
 */
public class TextViewer extends JScrollPane
{
	/** The text area that messages received from the server are written to. */
	JTextArea textArea;
	
	/**
	 * @param sock the ClientTextSocket that will send messages to this
	 * @param ta a JTextField to write messages to
	 */
	public TextViewer(ClientTextSocket sock, JTextArea ta)
	{
		super(ta); // call super constructor
		textArea = ta; // set textArea
		textArea.setEditable(false); // set text area to not be editable
		textArea.setLineWrap(true); // allow line wrap
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // set scroll bar to be vertical
		//setPreferredSize(new Dimension(500,500)); // needed?
		sock.setViewer(this); // pass this to the text socket
	}
	
	/**
	 * Appends a message to the JTextField
	 * @param m the String to append
	 */
	public void addText(String m) {
		textArea.append(m); }
}
