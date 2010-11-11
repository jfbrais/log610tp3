/******************************************************
 Projet :		TCPClient
 Auteurs :		Claude Bouchard
 				Jean-François Brais-Villemur
 				Gabriel Desmarais
 Codes perm. :	BOUC12018902
 				BRAJ14088901
 				DESG24078908
 Classe :		Client.java			 
 Création  :	2010-10-21
 Dern. mod : 	2010-10-21
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-10-21 : 	Début du travail pratique
 *******************************************************/

package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Client extends JFrame implements ActionListener {
	private Socket socket;
	private PrintWriter out;
	
	private String ip, port;
	
	private JLabel text;
	private JButton button;
	private JPanel panel;
	private JTextField textField;

	public Client(String ip, String port) {
		this.ip = ip;
		this.port = port;
		
		text = new JLabel("Text to send to server:");
	    textField = new JTextField(20);
	    button = new JButton("Send !");
	    button.addActionListener(this);

	    panel = new JPanel();
	    panel.setLayout(new BorderLayout());
	    panel.setBackground(Color.white);
	    getContentPane().add(panel);
	    panel.add("North", text);
	    panel.add("Center", textField);
	    panel.add("South", button);
	    panel.setSize(200, 200);
	}
	
	public void connect() {
		try {
			socket = new Socket(ip, Integer.parseInt(port));
			
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendText(String t) {
		out.println(t);
	}
	
	public void closeConnections() {
		try {
			if (out != null && socket != null) {
				out.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			String text = textField.getText();
	        sendText(text);
	        textField.setText(new String(""));
		}
	}
}