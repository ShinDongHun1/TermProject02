package server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LibraryClientReceiveThread extends Thread{
	
	private Socket client_socket=null;
	public LibraryClientReceiveThread(Socket client_socket) {
		this.client_socket=client_socket;
	}
	
	
	 public void run(){
	            try {
	                BufferedReader br = new BufferedReader(new InputStreamReader(client_socket.getInputStream(), StandardCharsets.UTF_8));
	                //socket�� ����� �Է½�Ʈ���� br�� ����. ���� �̷�����?
	                while(true) {
	                    String msg = br.readLine();

	                    //��ɼ���
	                }
	            }  catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	 

		
	    
}
