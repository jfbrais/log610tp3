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
    private byte[] fileData;
    
    private File file;
    private FileInputStream fis;
    private BufferedInputStream bis;
        
    private Pattern p;
    
    private String temp = "";
    private String response = "";
    private String path = "";
    private String CRLF = "\r\n";
    
    private int status;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
        	System.out.println("\nNew connection >> " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + "\n---");
        	
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            
            StringBuffer out = new StringBuffer();
            RegEX regex = new RegEX();
            
            int n = input.read(buffer);
            out.append(new String(buffer, 0, n));
            
            // Vérifier si c'est une requête GET.
            p = Pattern.compile(P_GET,Pattern.CASE_INSENSITIVE);
            temp = regex.findInURL(out.toString(), p);
            
            // Extraire le fichier demandé.
            p = Pattern.compile(P_FILE,Pattern.CASE_INSENSITIVE);
            temp = (regex.findInURL(out.toString(), p)).trim();
            
            path += "doc\\" + temp.substring(1);
        	System.out.println(path);
            
            if (new File(path).exists())
            	status = 200;
            else
            	status = 404;

            switch (status) {
	            case 200:
	            	response = "HTTP/1.1 200 OK" + CRLF;
	            	break;
	            case 404:
	            	response = "HTTP/1.1 404 Not Found" + CRLF;
	            	
	            	path = "doc\\filenotfound.html";
	            	break;
            }
            
            file = new File(path);
            fileData  = new byte[(int)file.length()];
            
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            bis.read(fileData,0,fileData.length);
        	
        	response += "Server: " + serverText + CRLF;
        	response += "Content-Length: " + String.valueOf((int)file.length()) + CRLF;
        	response += "Content-Type: text/html" + CRLF;
        	response += CRLF;
        	
        	System.out.println("\n" + response);
            
            output.write(response.getBytes());
        	output.write(fileData, 0, fileData.length);
        	output.flush();
            
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
