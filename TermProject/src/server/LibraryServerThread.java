package server;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import authentication.LogInContext;
import authentication.User;
import book.Book;
import database.DB_BOOK;
import database.DB_USER;
import exception.MyException;

public class LibraryServerThread extends Thread{
	private Map<String, String> client_id_ip;
	List<PrintWriter> listUser =null;
	private Socket client;
	BufferedReader br=null;
	PrintWriter pw=null;
	private String id=null;
	private String method=null;
	LibraryServerThread(Socket client, Map<String, String> client_id_ip, List<PrintWriter> listUser){
		this.client=client;
		this.client_id_ip=client_id_ip;
		this.listUser=listUser;
	}
	
	
	@Override
	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream(),StandardCharsets.UTF_8));
			pw=new PrintWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8));
			
			while(true) {
				try {
					String request=br.readLine();
					if(request==null) {
						break;
					}
					String [] request_tokens=request.split(":");// [ex] LOGIN:ID:IP
				
					if(request_tokens[0].equals(ServerRequest.SIGN_UP.getRequest())) {//SignUp:ID:PW:Name:Phone:Email,Address
						method="SignUp";
						SignUp(request_tokens[1],request_tokens[2],request_tokens[3],request_tokens[4],request_tokens[5],request_tokens[6]);
					
					}else if(request_tokens[0].equals(ServerRequest.LOG_IN.getRequest())) {//LogIn:ID:PW
						method="LogIn";
						LogIn(request_tokens[1], request_tokens[2], pw);
					}else if(request_tokens[0].equals(ServerRequest.SIGN_OUT.getRequest())) {//SignOut:ID:PW
						SignOut(request_tokens[1], request_tokens[2],pw);
					
					}else if(request_tokens[0].equals(ServerRequest.LOG_OUT.getRequest())) {//LogOut:ID
						LogOut(request_tokens[1],pw);
					
					}else if(request_tokens[0].equals(ServerRequest.MODIFY_USER_DATA.getRequest())) {//ModifyUserData:ID:PW:NAME:PHONE:EMAIL:ADDRESS  !���̵�� ���� �Ұ�!
						ModifyUserData(request_tokens[1],request_tokens[2],request_tokens[3],request_tokens[4],request_tokens[5],request_tokens[6]);
					
					}else if(request_tokens[0].equals(ServerRequest.SEARCH_BOOK.getRequest())) {//SearchBook:����-~~:�۰�-~~
						SearchBook(request_tokens, pw);
					}else if(request_tokens[0].equals(ServerRequest.ADD_BOOK_DATA.getRequest())) {//AddBookData:����:�۰�:������:, �̰� �Ǹ� ����ȭ�鿡 ���� ���� å�� ������, BroadCast�� ���� ����ִ� �������� ������!!
						AddBookData(request_tokens);
					}else if(request_tokens[0].equals(ServerRequest.MODIFY_BOOK_DATA.getRequest())) {//ModifyBookData:å����:�۰�:������
						modifyBookData(request_tokens);
					}else if(request_tokens[0].equals(ServerRequest.DELETE_BOOK_DATA.getRequest())) {//DeleteBookData:å��ȣ
						deleteBookData(request_tokens[1]);
					}else if(request_tokens[0].equals(ServerRequest.PURCHASE_BOOK.getRequest())) {//PurchaseBook:�� å ����:�Ĵ»��Id:��»��ID
					
					}else if(request_tokens[0].equals(ServerRequest.RENTAL_BOOK.getRequest())) {}

				}catch(MyException e) {
					pw.println(method+":"+e.getMessage());
					pw.flush();
				}
			}
		}catch(IOException e) {
		
			if(e.getMessage().equals("Connection reset")) {
				System.out.println(id+"���� �ý����� �����ϼ̽��ϴ�.");
				LogOut(id,pw);
			}else if(e.getMessage().equals("socket closed")){
				
			}else{
				e.printStackTrace();
				LogOut(id,pw);
			}
		
		}
	}
	
	private synchronized void SignUp(String id, String password, String name, String phone, String Email, String Address) throws MyException {
		if(LogInContext.SignUp(name, phone, id, password,Email,Address)) {//���̵�,��й�ȣ,�̸�,��ȭ��ȣ,��ü��,���� å ��, ���Ῡ��(�α��ο���[0�̸� ����X, 1�̸� ����O])
			pw.println("SignUp:����:");
			pw.flush();
		}
	}
	
	private synchronized void LogIn(String id, String password, PrintWriter cleint) throws MyException {
		if(LogInContext.LogIn(id,password)) {//�α��� �����ϸ� ���� ����
			DB_USER.userLogIn(id);
			pw.println("LogIn:����:"+DB_USER.getUser(id).toString());
			pw.flush();
			client_id_ip.put(id, client.getInetAddress().toString());
			listUser.add(cleint);
			this.id=id;
			storeUserLog("LogIn");
		}
	}
	private synchronized void SignOut(String id, String password, PrintWriter client) throws MyException {
		if(LogInContext.SignOut(id, password)) {
			client_id_ip.remove(id);
			listUser.remove(client);
			storeUserLog("LogOut");
			pw.close();
		}
	}
	private synchronized void LogOut(String id, PrintWriter client){
		LogInContext.LogOut(id);
		client_id_ip.remove(id);
		listUser.remove(client);
		System.out.println(id+"���� �α׾ƿ� �Ǿ����ϴ�.");
		storeUserLog("LogOut");
		pw.close();
	}
	private synchronized void ModifyUserData(String id, String changePw, String changeName, String changePhone,String changeEmail, String changeAddress) {//�α��� �� ���¿��� �ٲٴ� �ű� ������ ������ �̻����� �� ����
		User old=DB_USER.getUser(id);
		String []changeData= {id, changePw, changeName, changePhone,changeEmail,changeAddress,old.isLend_OK()+"",old.is_connected()+""};
		User changeMem=new User(changeData);
		DB_USER.modifyUser(changeMem);
	}
	
	private synchronized void SearchBook(String[] info,PrintWriter cleint) {

			List<Book> searchResult=null;
			if(info.length==1) {//�˻� ���� ����-��� å �˻�
				searchResult=DB_BOOK.getAllBook();
			}
			else if(info.length==2) {//�˻� ���� 1��
				searchResult=DB_BOOK.searchBook(info[1]);
			}else if(info.length==3) {//�˻� ���� 2��

				searchResult=DB_BOOK.searchBook(info[1],info[2]);
				
			}else if(info.length==4) {//�˻� ���� 3��
				searchResult=DB_BOOK.searchBook(info[1],info[2],info[3]);

			}else if(info.length==5) {//�˻� ���� 4��

				searchResult=DB_BOOK.searchBook(info[1], info[2], info[3], info[4]);
			}else if(info.length==6) {//�˻� ���� 5��

				searchResult=DB_BOOK.searchBook(info[1], info[2], info[3], info[4],info[5]);
			}else {//�̿�
				pw.println("�̻��Ѱ� �Է��� �� ���ƿ�");
				return;
			}
			String result="";
			if(searchResult.size()==0) {
				cleint.println(result);
				cleint.flush();
			}else {
				result="";
				Iterator e=searchResult.iterator();
				while(e.hasNext()) {
					result+=e.next()+"\r\n";
					}
				
				cleint.println(result);
				cleint.flush();
		
		}
	}
	private synchronized void AddBookData(String[] bookData) {//�α��� �� ���¿��� �ٲٴ� �ű� ������ ������ �̻����� �� ����
		
		DB_BOOK.insertBook(Integer.parseInt(bookData[1]),bookData[2],bookData[3],bookData[4],bookData[5],bookData[6],Integer.parseInt(bookData[7]),Integer.parseInt(bookData[8]),Integer.parseInt(bookData[9]),Boolean.parseBoolean(bookData[10]),bookData[11]);
		String[] newBookData=new String[11];
		for(int i=0; i<11; i++) {
			newBookData[i]=bookData[i+1];
		}
		Book newBook=new Book(newBookData);
		broadcast(newBook);
	}
	private void broadcast(Book addBook) {
		synchronized(listUser) {//��� ����鿡�� �޼��� ���
			for(PrintWriter writer : listUser) {
				writer.println(addBook);
				writer.flush();
			}
		}
		
	}
	
	private synchronized void modifyBookData(String[] bookData) {
		DB_BOOK.changeBook(Integer.parseInt(bookData[1]),bookData[2],bookData[3],bookData[4],bookData[5],bookData[6],Integer.parseInt(bookData[7]),Integer.parseInt(bookData[8]),Integer.parseInt(bookData[9]),Boolean.parseBoolean(bookData[10]),bookData[11]);
		
	}
	private synchronized void deleteBookData(String Book_Number) {
		DB_BOOK.deleateBook(Integer.parseInt(Book_Number));
	}
	
	private void storeUserLog(String status) {
		SimpleDateFormat format_day = new SimpleDateFormat ( "yyyy-MM-dd");			
		SimpleDateFormat format_clock = new SimpleDateFormat ( "HH:mm:ss");		
		Calendar time = Calendar.getInstance();	       
		String day = format_day.format(time.getTime());
		String clock = format_clock.format(time.getTime());
		PrintWriter pw=null;
		try {
			if(status.equals("LogIn")) {
				pw=new PrintWriter(new FileWriter("src/server/userlog/"+day+"UserLogIn.txt"),true);
				StringBuffer stb= new StringBuffer(day+", "+clock);
				stb.append("  [User ID: "+this.id+"]  [User IP: "+this.client_id_ip.get(id)+"], LogIn");
				pw.println(stb);
				pw.close();
			}else if(status.equals("LogOut")){
				pw=new PrintWriter(new FileWriter("src/server/userlog/"+day+"UserLogOut.txt"),true);
				StringBuffer stb= new StringBuffer(day+", "+clock);
				stb.append("  [User ID: "+this.id+"]  [User IP: "+this.client_id_ip.get(id)+"], LogOut");
				pw.println(stb);
				pw.close();
			}

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}