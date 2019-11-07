package authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import database.MemberLogManager;

public class LogInContext {
	private Scanner sc = new Scanner(System.in);
	private FormException fe = new FormException();


	public boolean SignUp(String Name, String Phone, String ID, String Password) throws MyException {// 회원가입
	
		if(fe.NameFormCheck(Name)&&fe.phoneFormCheck(Phone)&&fe.IDFormCheck(ID)&&fe.passwordFormCheck(Password)) {//형식에 맞다면
			Member getMember;
			getMember = MemberLogManager.getMemberInfoFromDB(ID);// 중복검사해주는 메소드의 리턴값은 비밀번호이다, 만약 중복되면 null값임
			if (getMember != null) { // 중복된다면
				throw new MyException("Already Exist ID");
			}else {
			MemberLogManager.insertMember(ID, Password, Name, Phone, 0, 0, 0);
			// 회원가입 완료
			return true;
			}
		}
		return false;
		
	}

	public boolean SignOut(String ID, String Password) throws MyException {
		Member getMember;
		getMember = MemberLogManager.getMemberInfoFromDB(ID);
			if (getMember == null) {// 아이디가 존재하지 않는다면 null을 반환
				throw new MyException("ID does not exist");
			}
		// 지울 아이디는 존재하는 상황
	
			if (Password.equals(getMember.getPassword())) {
				MemberLogManager.deleateMember(ID);
				return true;//제거 성공
			}else {
			throw new MyException("Passwords do not match");
				}
			
			
	}

	public boolean logIn(String ID, String PW) throws MyException {
		Member getMember;
		getMember = MemberLogManager.getMemberInfoFromDB(ID);

		if (getMember == null) {// 존재하지 않는 아이디라면
			throw new MyException("ID does not exist");
		}
		if (PW.equals(getMember.getPassword())) {
			//성공

			if(!getMember.getIs_connected()) {//연결되어 있으면 true, 아니면 false
				
				return true;//로그인 성공
			}
			else {
				throw new MyException("You are already logged in");
			}
		} else {//비밀번호가 다르다면
			throw new MyException("Passwords do not match");
		}

	}
	

	public void logOut() {

	}

}
