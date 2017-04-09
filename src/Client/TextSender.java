package Client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

public class TextSender extends Container
{
	// modify to change how space is shared between button and text field
	private final double textFieldWeight = 1.0;
	// used to send messages to the server
	private ClientTextSocket textSock;
	// typing area
	private JTextField textArea;
	// push to send message
	private JButton send;
	
	/*
	 * Initialize stuff
	 */
	public TextSender(ClientTextSocket s)
	{
		textSock = s;
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

	/*
	 * Listens for user to press "send" button
	 * Sends the message to the server and resets the text field
	 */
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			String m = textArea.getText(); // get text field text
			if(m.length() > 0) { // prevent empty messages from being sent
				textSock.sendMessage(m); } // send message
			textArea.setText(""); // clear text field
		}
	}
}
