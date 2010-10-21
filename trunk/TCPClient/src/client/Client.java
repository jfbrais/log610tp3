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
	
	JLabel text, clicked;
	JButton button;
	JPanel panel;
	JTextField textField;

	
	public Client() {
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
			socket = new Socket("127.0.0.1", 10000);
			
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
			out.close();
			socket.close();
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
