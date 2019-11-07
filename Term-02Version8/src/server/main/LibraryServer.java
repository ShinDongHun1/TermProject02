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
		Map<String, String> clients_info=new HashMap<>();//아이디, 아이피로 회원 엮음

		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			System.out.println("연결 기다림");
			// 3. 요청 대기
			while (true) {
				Socket socket = serverSocket.accept();// 요청이 들어오면
				new LibraryServerProcessThread(socket, clients, clients_info).start();
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
