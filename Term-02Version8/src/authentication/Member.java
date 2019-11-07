package authentication;

import book.Book;

public class Member {
	private static final int RENTAL_COUNT=10;//�ִ� 10�� ���� �� ����.
	private String ID;
	private String password;
	private String name;
	private String phone;
	private int LateFee; //��ü��
	private int rentalBookNum;
	private boolean is_connected;

	
	public Member(String[] MemberInfo) {//�α��ο� �����ߴٸ�, ����� ������ ���� ȸ�� ��ü�� ������ �� ����!
		this.ID=MemberInfo[0];
		this.password=MemberInfo[1];
		this.name=MemberInfo[2];
		this.phone=MemberInfo[3];
		this.LateFee=Integer.parseInt(MemberInfo[4]);
		this.rentalBookNum=Integer.parseInt(MemberInfo[5]);
		if(Integer.parseInt(MemberInfo[6])==0) {//false
			this.is_connected=false;
		}
		else {
			this.is_connected=true;
		}
	}
	
	public void addRentalBook() {
		this.rentalBookNum++;
	}
	public void returnRentalBook() {
		this.rentalBookNum--;
	}
	public int getRentalBookNum() {
		return rentalBookNum;
	}


	public void setRentalBookNum(int rentalBookNum) {
		this.rentalBookNum = rentalBookNum;
	}


	public String toString() {
		StringBuilder sb=new StringBuilder("ȸ������: [");
		sb.append("{ID: "+this.ID+"}  {PW: "+this.password+"}  {�̸�: "+this.name+"}  {��ȭ��ȣ: "+this.phone+"}  {��ü��: "+this.LateFee+"��}"+"{���ӿ���: "+this.is_connected+"} ]");
		return new String(sb);
	}

	public boolean getIs_connected() {
		return is_connected;
	}

	public void setIs_connected(boolean is_connected) {
		this.is_connected = is_connected;
	}

	public String getID() {
		return ID;
	}


	public String getPassword() {
		return password;
	}


	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}


	public int getLateFee() {
		return LateFee;
	}

	
	
}
