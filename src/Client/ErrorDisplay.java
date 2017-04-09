package Client;

import javax.swing.JOptionPane;

public class ErrorDisplay
{
	public static void showError(Exception err) {
		JOptionPane.showMessageDialog(null, err.getMessage()); }
}
