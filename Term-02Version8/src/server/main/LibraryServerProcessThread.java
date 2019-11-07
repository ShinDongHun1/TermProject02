package server.main;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import authentication.Member;
import book.Book;
import database.Book_Info_Manager;
import database.MemberLogManager;

public class LibraryServerProcessThread extends Thread {
	private final String[] REQUEST= {"LogIn","Search Book","Rental Book","Return Book","Search Member ","Close"};
	private Socket client_socket = null;
	private List<PrintWriter> clients = null; // ������ ���� -> ���������� ���� ������
	private Map<String, String> clients_info=new HashMap<>();//���̵�, �����Ƿ� ȸ�� ����
	private String id=null;
	BufferedReader br;
	PrintWriter pw;
	
	public LibraryServerProcessThread(Socket socket,  List<PrintWriter> clients ,  Map<String, String> clients_info) {//������
		this.client_socket = socket;
		this.clients=clients;
		this.clients_info=clients_info;
	}

	public void run() {

		try {
			br = new BufferedReader(new InputStreamReader(client_socket.getInputStream(),StandardCharsets.UTF_8));// �б�
			pw=new PrintWriter(new OutputStreamWriter(client_socket.getOutputStream(), StandardCharsets.UTF_8));
			
			//���� -> ���� ��Ͽ� ���
			
			while (true) {
				String request = br.readLine();//��û ���: 1. å �˻�[Search Book], 2. å �뿩[Rental Book], 3. å �ݳ�, 4.�ٸ� ȸ�� �˻�(�ٸ� ȸ���� ���� å��)->�޼��� ������� ����,

				
				if (request == null) {
					// ���� ����.
					System.out.println("���� ����");
					doQuit(pw,id);
					break;
				}
				String[] tokens=request.split(":");
				if(tokens[0].equals(REQUEST[0])) {//���̵� ������ ���� LogIn:id:ip
					addClient(tokens[1],tokens[2],pw);

				}
				else if(tokens[0].equals(REQUEST[1])) {//å �˻�-ȸ���� �Ұ� SearchBook:title-ddd:auther-kkks
					SearchBook(tokens, pw);
				}
				else if(tokens[0].equals(REQUEST[2])) {//å  �뿩  Rental Book : ID : Registration_Num
					rentalBook(tokens[1],tokens[2]);
				}
				else if(tokens[0].equals(REQUEST[3])) {//å  �ݳ� Return Book : ID : Registration_Num
					returnBook(tokens[1],tokens[2]);
				}
				else if(tokens[0].equals(REQUEST[4])) {//ȸ�� �˻�
					
				}
				else if(tokens[0].equals(REQUEST[5])) {//����
					doQuit(pw,id);
				}
			
				
			}

		} catch (IOException e) {
			//���� ����.->���� �α׿� ���
			//System.out.println(clients_info.size());

		e.printStackTrace();
		} 
	}

	private void doQuit(PrintWriter writer,String id) {
		removeClient(writer, id);
	}

	private void removeClient(PrintWriter writer, String id) {
		synchronized (clients) {//����ȭ ���, ���� ���� ����.	
			MemberLogManager.memberLogOut(id);
			storeUserLog("LogOut");
			clients.remove(writer);
			clients_info.remove(id);
		}
	}
	private void addClient(String id, String ip, PrintWriter writer) {
		synchronized (clients) {
				clients_info.put(id, ip);
				this.id=id;
				clients.add(writer);
				MemberLogManager.memberLogIn(id);
				storeUserLog("LogIn");
			
		}
		
	}
	
	private void SearchBook(String[] info,PrintWriter pw) {
		synchronized (clients) {//����ȭ ���, ���� ���� ����.

			List<Book> searchResult=null;
			if(info.length==1) {//�˻� ���� ����-��� å �˻�
				searchResult=Book_Info_Manager.getAllBookFromDB();
			}
			else if(info.length==2) {//�˻� ���� 1��
				searchResult=Book_Info_Manager.searchBook(info[1]);
			}else if(info.length==3) {//�˻� ���� 2��

				searchResult=Book_Info_Manager.searchBook(info[1],info[2]);
				
			}else if(info.length==4) {//�˻� ���� 3��
				searchResult=Book_Info_Manager.searchBook(info[1],info[2],info[3]);

			}else if(info.length==5) {//�˻� ���� 4��

				searchResult=Book_Info_Manager.searchBook(info[1], info[2], info[3], info[4]);
			}else if(info.length==6) {//�˻� ���� 5��

				searchResult=Book_Info_Manager.searchBook(info[1], info[2], info[3], info[4],info[5]);
			}else {//�̿�
				pw.println("�̻��Ѱ� �Է��� �� ���ƿ�");
				return;
			}
			String result="";
			if(searchResult.size()==0) {
				pw.println(result);
				pw.flush();
			}else {
				result="";
				Iterator e=searchResult.iterator();
				while(e.hasNext()) {
					result+=e.next()+"\r\n";
					}
				
				pw.println(result);
				pw.flush();
				}

			
		}
	}
	
	
	private void rentalBook(String id ,String Registration_Num) {
		synchronized (clients) {
			Book rentalBook=Book_Info_Manager.searchBook(Integer.parseInt(Registration_Num));
			Member mem=MemberLogManager.getMemberInfoFromDB(id);
			if(!rentalBook.canRental()||mem.getRentalBookNum()>=10) {//���� �� ���ٸ�(�̹� ���� ����� �ִٸ�)
				//���� �� ������ �����ϴ� ������...
			}else {
				mem.addRentalBook();
				rentalBook.setId(id);//������� ���̵�
				rentalBook.setLocation(null);//��ġ ����X
				rentalBook.setRemainingPeriod(30);//30��
				rentalBook.setRentalAvailability(false);//false
				Book_Info_Manager.changeBookInfo(rentalBook);
			}
		}
	}
	
	private void returnBook(String id ,String Registration_Num) {
		synchronized (clients) {
			Book rentalBook=Book_Info_Manager.searchBook(Integer.parseInt(Registration_Num));
			Member mem=MemberLogManager.getMemberInfoFromDB(id);
				mem.returnRentalBook();
				rentalBook.setId(null);//������� ���̵�
				rentalBook.setLocation(null);//��ġ ���� ->�˾Ƽ� ó��.. ��������..����
				rentalBook.setRemainingPeriod(0);
				rentalBook.setRentalAvailability(true);
				Book_Info_Manager.changeBookInfo(rentalBook);
			
		}
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
				pw=new PrintWriter(new FileWriter("src/server/main/userlog/"+day+"UserLogIn.txt"),true);
				StringBuffer stb= new StringBuffer(day+", "+clock);
				stb.append("  [User ID: "+this.id+"]  [User IP: "+this.clients_info.get(id)+"], LogIn");
				pw.println(stb);
				pw.close();
			}else if(status.equals("LogOut")){
				pw=new PrintWriter(new FileWriter("src/server/main/userlog/"+day+"UserLogOut.txt"),true);
				StringBuffer stb= new StringBuffer(day+", "+clock);
				stb.append("  [User ID: "+this.id+"]  [User IP: "+this.clients_info.get(id)+"], LogIn");
				pw.println(stb);
				pw.close();
			}

		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}