package authentication;

import java.util.regex.Pattern;

public class FormException {
	
	public boolean IDFormCheck(String id) throws MyException {//���̵�� ������ ����� ���ڷθ�
		if(Pattern.matches("^[a-zA-Z0-9]*$", id)&&id.length()!=0) {
			return true;//���Ŀ� �´ٸ�
		}
		throw new MyException("ID does not match format");
	}
	
	public boolean passwordFormCheck(String id) throws MyException{//��й�ȣ�� ����, ���� ,(!@#$%^&*())�� Ư�����ڰ� ���.
		if(Pattern.matches("^[a-zA-Z0-9!@#$%^&*()]*$", id)&&id.length()!=0)
			return true;
		
		throw new MyException("Password does not match format");
	}
	
	public boolean NameFormCheck(String id) throws MyException{//�̸��� �ѱ۰� ��� ����.
		if(Pattern.matches("^[a-zA-Z��-�R]*$", id)&&id.length()!=0)
			return true;

		throw new MyException("Name does not match format");
	}
	
	public boolean phoneFormCheck(String id) throws MyException {//�ڵ�����ȣ ������ 000-000(0)-0000
		if(Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", id)&&id.length()!=0)
			return true;
		
		throw new MyException("Phone does not match format");
	}

}
