package server.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class LibraryServer {
	public static final int PORT = 5000;

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		List<PrintWriter> clients= new ArrayList<PrintWriter>();
		Map<String, String> clients_info=new HashMap<>();//���̵�, �����Ƿ� ȸ�� ����

		try {
			// 1. ���� ���� ����
			serverSocket = new ServerSocket();

			// 2. ���ε�
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			System.out.println("���� ��ٸ�");
			// 3. ��û ���
			while (true) {
				Socket socket = serverSocket.accept();// ��û�� ������
				new LibraryServerProcessThread(socket, clients, clients_info).start();
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
