package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		
		// GUI elements.
        WindowListener l = new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                        System.exit(0);
                }
        };

		client.setTitle("Client Program");
		client.addWindowListener(l);
        client.pack();
        client.setVisible(true);
		
        client.connect();
	}
}