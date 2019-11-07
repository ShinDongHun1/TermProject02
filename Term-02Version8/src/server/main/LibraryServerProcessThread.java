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
	private List<PrintWriter> clients = null; // 접속한 고객들 -> 공유경제를 위해 만들어둠
	private Map<String, String> clients_info=new HashMap<>();//아이디, 아이피로 회원 엮음
	private String id=null;
	BufferedReader br;
	PrintWriter pw;
	
	public LibraryServerProcessThread(Socket socket,  List<PrintWriter> clients ,  Map<String, String> clients_info) {//생성자
		this.client_socket = socket;
		this.clients=clients;
		this.clients_info=clients_info;
	}

	public void run() {

		try {
			br = new BufferedReader(new InputStreamReader(client_socket.getInputStream(),StandardCharsets.UTF_8));// 읽기
			pw=new PrintWriter(new OutputStreamWriter(client_socket.getOutputStream(), StandardCharsets.UTF_8));
			
			//들어옴 -> 서버 목록에 기록
			
			while (true) {
				String request = br.readLine();//요청 목록: 1. 책 검색[Search Book], 2. 책 대여[Rental Book], 3. 책 반납, 4.다른 회원 검색(다른 회원이 가진 책들)->메세지 보내기로 연결,

				
				if (request == null) {
					// 연결 끊김.
					System.out.println("연걀 종료");
					doQuit(pw,id);
					break;
				}
				String[] tokens=request.split(":");
				if(tokens[0].equals(REQUEST[0])) {//아이디 아이피 받음 LogIn:id:ip
					addClient(tokens[1],tokens[2],pw);

				}
				else if(tokens[0].equals(REQUEST[1])) {//책 검색-회원이 할거 SearchBook:title-ddd:auther-kkks
					SearchBook(tokens, pw);
				}
				else if(tokens[0].equals(REQUEST[2])) {//책  대여  Rental Book : ID : Registration_Num
					rentalBook(tokens[1],tokens[2]);
				}
				else if(tokens[0].equals(REQUEST[3])) {//책  반납 Return Book : ID : Registration_Num
					returnBook(tokens[1],tokens[2]);
				}
				else if(tokens[0].equals(REQUEST[4])) {//회원 검색
					
				}
				else if(tokens[0].equals(REQUEST[5])) {//종료
					doQuit(pw,id);
				}
			
				
			}

		} catch (IOException e) {
			//서버 나감.->서버 로그에 기록
			//System.out.println(clients_info.size());

		e.printStackTrace();
		} 
	}

	private void doQuit(PrintWriter writer,String id) {
		removeClient(writer, id);
	}

	private void removeClient(PrintWriter writer, String id) {
		synchronized (clients) {//동기화 기법, 다중 접근 제한.	
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
		synchronized (clients) {//동기화 기법, 다중 접근 제한.

			List<Book> searchResult=null;
			if(info.length==1) {//검색 정보 없음-모든 책 검색
				searchResult=Book_Info_Manager.getAllBookFromDB();
			}
			else if(info.length==2) {//검색 정보 1개
				searchResult=Book_Info_Manager.searchBook(info[1]);
			}else if(info.length==3) {//검색 정보 2개

				searchResult=Book_Info_Manager.searchBook(info[1],info[2]);
				
			}else if(info.length==4) {//검색 정보 3개
				searchResult=Book_Info_Manager.searchBook(info[1],info[2],info[3]);

			}else if(info.length==5) {//검색 정보 4개

				searchResult=Book_Info_Manager.searchBook(info[1], info[2], info[3], info[4]);
			}else if(info.length==6) {//검색 정보 5개

				searchResult=Book_Info_Manager.searchBook(info[1], info[2], info[3], info[4],info[5]);
			}else {//이외
				pw.println("이상한거 입력한 거 같아여");
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
			if(!rentalBook.canRental()||mem.getRentalBookNum()>=10) {//빌릴 수 없다면(이미 빌린 사람이 있다면)
				//빌릴 수 없을때 어케하누 ㅋㅋㅋ...
			}else {
				mem.addRentalBook();
				rentalBook.setId(id);//빌린사람 아이디
				rentalBook.setLocation(null);//위치 존재X
				rentalBook.setRemainingPeriod(30);//30일
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
				rentalBook.setId(null);//빌린사람 아이디
				rentalBook.setLocation(null);//위치 존재 ->알아서 처리.. 어케하지..ㅋㅋ
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