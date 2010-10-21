/******************************************************
 Projet :		TCPServer
 Auteurs :		Claude Bouchard
 				Jean-François Brais-Villemur
 				Gabriel Desmarais
 Codes perm. :	BOUC12018902
 				BRAJ14088901
 				DESG24078908
 Classe :		Server.java			 
 Création  :	2010-10-21
 Dern. mod : 	2010-10-21
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-10-21 : 	Début du travail pratique
 *******************************************************/

package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Server extends JFrame implements Runnable {
	private final static String DEFAULT_IP = "127.0.0.1";
	private final static int DEFAULT_PORT = 10000;

	// GUI
	private JLabel text, textClient;
	private JPanel panel;
	
	private boolean listening = false;
	
	private String receivedText = null;
	private String IP = null;
	private int port;
	
	private ServerSocket serverSocket;
	private Socket clientSocket;

	private BufferedReader in = null;
		
	private Thread thread;

	// Constructor that permits the specification of the port number.
	/**
	 * @param aIP
	 * @param aPort
	 */
	public Server(String aIP, int aPort) {
		super();
		
		if (!aIP.equals(""))
			IP = aIP;
		else
			IP = DEFAULT_IP;
		
		if (aPort <= 0)
			port = DEFAULT_PORT;
		else
			port = aPort;
		
		thread = new Thread(this);
		// Starts the server with the given IP:Port.
		startServer(getIP(), getPort());
		
		text = new JLabel("Text received from client:");
	    textClient = new JLabel("");

	    panel = new JPanel();
	    panel.setLayout(new BorderLayout());
	    panel.setBackground(Color.white);
	    getContentPane().add(panel);
	    panel.add("North", text);
	    panel.add("Center", textClient);
	}

	// Returns the IP address of the server.
	/**
	 * @return
	 */
	public String getIP() {
		return IP;
	}

	// Returns the port number on which the server is running.
	/**
	 * @return
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * @param state
	 */
	public void setListeningState(boolean state){
		listening = state;
	}
	
	/**
	 * @return
	 */
	public boolean getListeningState(){
		return listening;
	}
	
	// Creates a SocketServer at the specified IP:Port.
	/**
	 * @param serverIP
	 * @param serverPort
	 */
	public void startServer(String serverIP, int serverPort) {		
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			System.out.println("Couldn't listen on port : " + serverPort);
			System.exit(-1);
		}
		
		// Starts the thread that will accept connections.
		start();
	}
	
	// Accepts the number of connections specified in parameters.
	// If the int passed is <= to 0, default value will be used.
	/**
	 * 
	 */
	public void acceptConnections() {
		System.out.println("Accepting clients..");
		try {
			clientSocket = serverSocket.accept();
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Accept clients until limit is reached.
		while (listening)
		{				
			try {
				receivedText = in.readLine();
				textClient.setText(receivedText);
			} catch (IOException e) {
				setListeningState(false);
				closeConnections();
				e.printStackTrace();
			} 
		}	
	}

	public void closeConnections() {
		try {
			if (in != null || clientSocket != null) {
				in.close();
				clientSocket.close();
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private void start() {
		thread.start();
	}
	
	@Override
	public void run() {
		// Sets the server to listen mode.
		setListeningState(true);
		
		// Starts accepting connections from client.
		acceptConnections();
	}
}