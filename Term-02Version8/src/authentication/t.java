package authentication;

public class t {

	public static void main(String[] args) {
		String[] s=new String[5];
		s[0]="1";
		s[1]="1";
		s[2]="1";
		s[3]="1";
		s[4]="1";
		Member m=new Member(s);
		System.out.println(m.getRentalBookNum());
	}
}
