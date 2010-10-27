package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import regex.RegEX;

public class WorkerRunnable implements Runnable {
	private String P_GET = "GET";
	private String P_FILE = "( )(.*)( )";
	
	protected Socket clientSocket = null;
    protected String serverText   = null;
    
    private byte[] buffer = new byte[1024];
        
    private Pattern p;
    private String temp = "";

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
        	System.out.println("New connection >> " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + "\n");
        	
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            
            StringBuffer out = new StringBuffer();
            RegEX regex = new RegEX();
            
            int n = input.read(buffer);
            out.append(new String(buffer, 0, n));
            System.out.println(out.toString() + "\n\n\n\n");
            
            p = Pattern.compile(P_GET,Pattern.CASE_INSENSITIVE);
            temp = regex.findInURL(out.toString(), p);
            System.out.println(temp);
            
            p = Pattern.compile(P_FILE,Pattern.CASE_INSENSITIVE);
            temp = (regex.findInURL(out.toString(), p)).trim();
            System.out.println(temp);
            
            // Send requested file.
            File myFile = new File ("test.html");
            byte [] fileData  = new byte [(int)myFile.length()];
            
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(fileData,0,fileData.length);
            
            output.write(fileData,0,fileData.length);
            output.flush();
            
            //output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " + this.serverText + " - " + time + "").getBytes());
            output.close();
            input.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
