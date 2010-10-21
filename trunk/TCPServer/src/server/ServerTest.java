/******************************************************
 Compagnie :	Transax Technologies
 Projet :		KeyExchangeServer
 Auteurs : 		Jean-Fran�ois Brais-Villemur, Analyste R�seau
 				Marc-Andr� Laporte, Programmeur Analyste
 Superviseur :	Alain Boucher, CTO
 Classe :		ServerTest.java
 Cr�ation  :	2010-03-08
 Dern. mod : 	2010-03-17
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-03-08 : 	D�but du projet
 
 2010-03-08 : 	D�but des tests au niveau de l'�x�cution
 				
 2010-03-17 :	Modifi� la s�quence d'�x�cution
 *******************************************************/

package server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ServerTest {
	public static void main(String[] args) {
		// Initializes the server with parameters passed (portNumber).
		Server server = new Server("", -1);
		
		// GUI elements.
        WindowListener l = new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                        System.exit(0);
                }
        };

		server.setTitle("Server Program");
        server.addWindowListener(l);
        server.pack();
	    server.setSize(200, 200);
        server.setVisible(true);
		
		// Starts the server with the given IP:Port.
		server.startServer(server.getIP(), server.getPort());
		
		// Sets the server to listen mode.
		server.setListeningState(true);
		
		// Starts accepting connections from client.
		// Any value passed in parameters <= 0 will use the default connections value.
		server.acceptConnections();
	}
}