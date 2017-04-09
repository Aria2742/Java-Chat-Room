package Client;

import javax.swing.JOptionPane;

/*
 * Class to display errors client-side
 */
public class ErrorDisplay
{
	public static void showError(Exception err) {
		JOptionPane.showMessageDialog(null, err.getMessage()); }
}
