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
		boolean �뿩���ɼ�;
		String id;
		String location;
		int �����Ⱓ;
		System.out.print("��Ϲ�ȣ�� �Է��ϼ���: ");
		ResNum=sc.nextInt();
		sc.nextLine();
		System.out.print("������ �Է��ϼ���: ");
		Title=sc.nextLine();
		System.out.print("�۰��� �Է��ϼ���: ");
		auther=sc.nextLine();
		System.out.print("���ǻ縦 �Է��ϼ���: ");
		publisher=sc.nextLine();
		System.out.print("�帣�� �Է��ϼ���: ");
		gerne=sc.nextLine();

		System.out.print("������� ���̵� �Է��ϼ���: ");
		id=sc.nextLine();
		System.out.print("��ġ�� �Է��ϼ���: ");
		location=sc.nextLine();
		System.out.print("�����Ⱓ�� �Է��ϼ���: ");
		�����Ⱓ=sc.nextInt();
		
		Book_Info_Manager.insertBookInfo(ResNum,Title,auther,publisher,gerne,true,id,location,�����Ⱓ);
	}
}
