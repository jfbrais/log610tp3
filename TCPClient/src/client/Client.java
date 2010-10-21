package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket socket;
	
	private DataOutputStream dos;
	private DataInputStream dis;
	
	private byte[] bytes;
	
	public void connect() {
		try {
			socket = new Socket("208.92.18.58", 12345);
			
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			
			bytes = new byte[2];
			
			bytes[0] = 1;
			bytes[1] = 0;
			
			dos.write(bytes);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
