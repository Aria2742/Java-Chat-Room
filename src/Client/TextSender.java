package Client;

import java.awt.Button;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextSender extends Container implements ActionListener
{
	private ClientTextSocket textSock;
	private TextField textArea;
	private Button send;
	
	public TextSender(ClientTextSocket s)
	{
		textSock = s;
		setLayout(new GridLayout(1,2));
		textArea = new TextField();
		add(textArea);
		send = new Button("send");
		add(send);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(send)) { // if send pressed
			String m = textArea.getText(); // get text field text
			if(m.length() > 0) { // prevent empty messages
				textSock.sendMessage(m); } // send message
			textArea.setText(""); // clear text field
		}
	}
}
