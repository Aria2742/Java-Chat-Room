package Client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * @author Nathan Augsburger
 * 
 * This class is the container that holds the text area and button to send text to the chat
 * This class interfaces with the client text socket to send out messages
 * Once initialized, just add this to the main window
 */
public class TextSender extends Container
{
	/** The weight given to the text field inside the GridBagLayout. */
	private final double textFieldWeight = 1.0;
	/** The ClientTextSocket to pass messages to for sending a message to the server. */
	private ClientTextSocket textSock;
	/** TextField to type messages in. */
	private JTextField textArea;
	/** Button for sending messages. The text inside the {@link Client.TextSender#textArea} is sent when this is pressed. */
	private JButton send;
	
	/**
	 * Initializes all the instance variables and places all the components inside this container.
	 * @param cts The ClientTextServer to pass messages to
	 */
	public TextSender(ClientTextSocket cts)
	{
		textSock = cts;
		// prepare the layout
		setLayout(new GridBagLayout()); // use GridBag layout
		GridBagConstraints gbc = new GridBagConstraints(); // constraints for elemnts added to window
		// create text field
		textArea = new JTextField();
		gbc.weightx = textFieldWeight;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0; // text field in left spot
		gbc.gridy = 0;
		add(textArea, gbc);
		// create button
		send = new JButton("send");
		send.addActionListener(new ButtonListener());
		gbc.weightx = 1.0 - textFieldWeight;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1; // button to the right of text field
		gbc.gridy = 0;
		add(send, gbc);
	}

	/**
	 * This class is the ActionListener added onto the {@link Client.TextSender#send} button. 
	 * Messages are sent only if the string inside {@link Client.TextSender#textArea} is not 
	 * an empty string. After sending the message, {@link Client.TextSender#textArea} is set 
	 * to contain an empty string.
	 */
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			String m = textArea.getText(); // get text field text
			if(m.length() > 0) { // prevent empty messages from being sent
				textSock.sendMessage(textSock.username + m); } // send message
			textArea.setText(""); // clear text field
		}
	}
}
