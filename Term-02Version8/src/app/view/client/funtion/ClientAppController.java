package app.view.client.funtion;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import app.view.client.data.dataStore;
import authentication.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ClientAppController {

	private Member client;
	
	public static final String SERVER_IP="10.3.166.25";
	public static final int SERVER_PORT=5000;

	@FXML
	private Button btn_move_search_book;
	@FXML
	private Button btn_move_rental_book;
	@FXML
	private Button btn_move_return_book;
	@FXML
	private Button btn_move_search_member;
	@FXML
	private Button btn_end;
	


	
	@FXML
	private void initialize() {
		this.client=dataStore.client;
		Socket socket=new Socket();
		try {
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			dataStore.socket=socket;
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
	           //socket�� ��� ��ũ���� �����ϴ� ��Ұ� �ִ�? ����?
	            String request="LogIn:"+client.getID()+":"+findMyIP()+"\r\n";
	            pw.println(request);
	         
		} catch (ConnectException e) {
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("���� �����ڳ�");
			//e.printStackTrace();
			System.exit(0);
		}catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	private static String findMyIP() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			return ip;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	
	
	public void moveSearchBook(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("BookSearch.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Book Search");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // �ٸ�â���� �Ѿ��
	}

	public void moveRentalBook(ActionEvent event) {

	}

	public void moveReturnBook(ActionEvent event) {

	}

	public void moveSearchMember(ActionEvent event) {

	}

	public static void closeConnect() {
		PrintWriter pw;
		try {
			pw=new PrintWriter(new OutputStreamWriter(dataStore.socket.getOutputStream(), StandardCharsets.UTF_8),true);
			String request="Close:\r\n";
			pw.println(request);
			System.exit(0);//SystemŬ������ ���� ����
		
		}catch(IOException e1) {
			e1.printStackTrace();
		}finally {
			dataStore.socket=null;
			dataStore.client=null;
		}
	}
	
	public void close(ActionEvent event) {
		Stage stage=(Stage) btn_end.getScene().getWindow();
	 	stage.close();
	}
	


}
