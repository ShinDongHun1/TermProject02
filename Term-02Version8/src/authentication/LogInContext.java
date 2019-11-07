package authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import database.MemberLogManager;

public class LogInContext {
	private Scanner sc = new Scanner(System.in);
	private FormException fe = new FormException();


	public boolean SignUp(String Name, String Phone, String ID, String Password) throws MyException {// ȸ������
	
		if(fe.NameFormCheck(Name)&&fe.phoneFormCheck(Phone)&&fe.IDFormCheck(ID)&&fe.passwordFormCheck(Password)) {//���Ŀ� �´ٸ�
			Member getMember;
			getMember = MemberLogManager.getMemberInfoFromDB(ID);// �ߺ��˻����ִ� �޼ҵ��� ���ϰ��� ��й�ȣ�̴�, ���� �ߺ��Ǹ� null����
			if (getMember != null) { // �ߺ��ȴٸ�
				throw new MyException("Already Exist ID");
			}else {
			MemberLogManager.insertMember(ID, Password, Name, Phone, 0, 0, 0);
			// ȸ������ �Ϸ�
			return true;
			}
		}
		return false;
		
	}

	public boolean SignOut(String ID, String Password) throws MyException {
		Member getMember;
		getMember = MemberLogManager.getMemberInfoFromDB(ID);
			if (getMember == null) {// ���̵� �������� �ʴ´ٸ� null�� ��ȯ
				throw new MyException("ID does not exist");
			}
		// ���� ���̵�� �����ϴ� ��Ȳ
	
			if (Password.equals(getMember.getPassword())) {
				MemberLogManager.deleateMember(ID);
				return true;//���� ����
			}else {
			throw new MyException("Passwords do not match");
				}
			
			
	}

	public boolean logIn(String ID, String PW) throws MyException {
		Member getMember;
		getMember = MemberLogManager.getMemberInfoFromDB(ID);

		if (getMember == null) {// �������� �ʴ� ���̵���
			throw new MyException("ID does not exist");
		}
		if (PW.equals(getMember.getPassword())) {
			//����

			if(!getMember.getIs_connected()) {//����Ǿ� ������ true, �ƴϸ� false
				
				return true;//�α��� ����
			}
			else {
				throw new MyException("You are already logged in");
			}
		} else {//��й�ȣ�� �ٸ��ٸ�
			throw new MyException("Passwords do not match");
		}

	}
	

	public void logOut() {

	}

}
