/******************************************************
 Projet :		TCPServer
 Auteurs :		Claude Bouchard
 				Jean-François Brais-Villemur
 				Gabriel Desmarais
 Codes perm. :	BOUC12018902
 				BRAJ14088901
 				DESG24078908
 Classe :		ServerTest.java			 
 Création  :	2010-10-21
 Dern. mod : 	2010-10-21
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-10-21 : 	Début du travail pratique
 *******************************************************/

package server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ServerTest {
	public static void main(String[] args) {
		// Initializes the server with default parameters.
		final Server server = new Server("", -1);
		
		// GUI elements.
        WindowListener l = new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                	server.closeConnections();
                    System.exit(0);
                }
        };

		server.setTitle("Server Program");
        server.addWindowListener(l);
        server.pack();
	    server.setSize(200, 200);
        server.setVisible(true);
	}
}