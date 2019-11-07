package database;

import java.util.Scanner;

public class T {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		String Title;
		int ResNum;
		String auther;
		String publisher;
		String gerne;
		boolean 대여가능성;
		String id;
		String location;
		int 남은기간;
		System.out.print("등록번호를 입력하세요: ");
		ResNum=sc.nextInt();
		sc.nextLine();
		System.out.print("제목을 입력하세요: ");
		Title=sc.nextLine();
		System.out.print("작가를 입력하세요: ");
		auther=sc.nextLine();
		System.out.print("출판사를 입력하세요: ");
		publisher=sc.nextLine();
		System.out.print("장르를 입력하세요: ");
		gerne=sc.nextLine();

		System.out.print("빌린사람 아이디를 입력하세요: ");
		id=sc.nextLine();
		System.out.print("위치를 입력하세요: ");
		location=sc.nextLine();
		System.out.print("남은기간을 입력하세요: ");
		남은기간=sc.nextInt();
		
		Book_Info_Manager.insertBookInfo(ResNum,Title,auther,publisher,gerne,true,id,location,남은기간);
	}
}
