/******************************************************
 Projet :		TCPClient
 Auteurs :		Claude Bouchard
 				Jean-François Brais-Villemur
 				Gabriel Desmarais
 Codes perm. :	BOUC12018902
 				BRAJ14088901
 				DESG24078908
 Classe :		ClientTest.java			 
 Création  :	2010-10-21
 Dern. mod : 	2010-10-21
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-10-21 : 	Début du travail pratique
 *******************************************************/

package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Client client = new Client();
		
		// GUI elements.
        WindowListener l = new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                	client.closeConnections();
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