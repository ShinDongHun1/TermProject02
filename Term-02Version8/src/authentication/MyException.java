package authentication;

public class MyException extends Exception{
	MyException(){
		super();
	}
	public MyException(String message){
		super(message);
	}
	
	public String changeKorean() {
		String message=super.getMessage();
		switch (message){
		case "ID does not match format":
			return "���̵��� ������ �ùٸ��� �ʽ��ϴ�.";
		case "Password does not match format":
			return "��й�ȣ�� ������ �ùٸ��� �ʽ��ϴ�.";
		case "Name does not match format":
			return "�̸��� ������ �ùٸ��� �ʽ��ϴ�.";
		case "Phone does not match format":
			return "��ȭ��ȣ�� ������ �ùٸ��� �ʽ��ϴ�.";
		case "Already Exist ID":
			return "�̹� �����ϴ� ���̵��Դϴ�.";
		case "ID does not exist":
			return "���̵� �������� �ʽ��ϴ�.";
		case "Passwords do not match":
			return "��й�ȣ�� ��ġ���� �ʽ��ϴ�.";
			
		case "Fill in the all fields":
			return "�� ĭ�� �ֽ��ϴ�.";
		case "You are already logged in":
			return "�̹� ������ ���̵��Դϴ�.";
		case "Server Closed":
			return "������ �����־��ϴ�.";

		}
		return message;
	}

}
