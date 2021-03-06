package exception;

import java.util.regex.Pattern;

public class FormException {
	
	public static boolean IDFormCheck(String id) throws MyException {//아이디는 오로지 영어와 숫자로만
		if(Pattern.matches("^[a-zA-Z0-9]*$", id)&&id.length()!=0) {
			return true;//형식에 맞다면
		}
		throw new MyException("영어와 숫자만 사용 가능합니다.");
	}
	
	public static boolean passwordFormCheck(String pw) throws MyException{//비밀번호는 영어, 숫자 ,(!@#$%^&*())의 특수문자가 허용.
		if(Pattern.matches("^[a-zA-Z0-9!@#$%^&*()]*$", pw)&&pw.length()!=0)
			return true;
		
		throw new MyException("영어, 숫자, 특수문자만 사용하세요.");
	}
	
	public static boolean NameFormCheck(String name) throws MyException{//이름은 한글과 영어만 가능.
		if(Pattern.matches("^[a-zA-Z가-힣]*$", name)&&name.length()!=0)
			return true;

		throw new MyException("한글과 영어만 사용하세요.");
	}
	
	public static boolean phoneFormCheck(String phone) throws MyException {//핸드폰번호 형식은 010-000(0)-0000
		if(Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", phone)&&phone.length()!=0)
			return true;
		
		throw new MyException("번호를 확인해주세요.");
	}
	public static boolean emailFormCheck(String email) throws MyException {//email은 영어와 숫자만
		if(Pattern.matches("^[a-zA-Z0-9]*$", email)&&email.length()!=0)
			return true;
		
		throw new MyException("이메일 주소를 확인해주세요.");
	}
}
