package Client;

import javax.swing.JOptionPane;

/**
 * NEEDS TO BE REPLACED BY ACTUAL ERROR LOGGING
 */
public class ErrorDisplay
{
	public static void showError(Exception err) {
		JOptionPane.showMessageDialog(null, err.getMessage()); }
}
