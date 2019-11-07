package book;

public class Book {
	
	private 		int 				registrationNum; // ��Ϲ�ȣ, ��ϵ� ������ 1�� ������Ŵ.
	private 		String 			title;						//����
	private		String 			auther;					//�۰�
	private 		String 			publisher;				//���ǻ�
	private 		String 			genre;						//�帣
	private		boolean 		rentalAvailability;//�뿩 ����, �����ϸ� true, �̹� �뿩���� ���¶�� false.
	private 		String			id;							//å ������ ��� ���̵�
	private 		String			location;					//��ġ
	private 		int  				remainingPeriod; // ���� �Ⱓ, �뿩���ΰ� true�϶��� ���� X
	

	public Book(String[] bookInfo) {
		this.registrationNum=Integer.parseInt(bookInfo[0]);
		this.title=bookInfo[1];
		this.auther=bookInfo[2];
		this.publisher=bookInfo[3];
		this.genre=bookInfo[4];
		
		if(bookInfo[5].equals("1")) {//true
			bookInfo[5]="true";
		}else {
			bookInfo[5]="false";
		}
		this.rentalAvailability=Boolean.parseBoolean(bookInfo[5]);
		this.id=bookInfo[6];
		if(bookInfo[7]==null) {bookInfo[7]="���� ���ݿ� ������ ����";}
		this.location=bookInfo[7];
		
		if(bookInfo[8]==null) {bookInfo[8]="-1";}
		this.remainingPeriod=Integer.parseInt(bookInfo[8]);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuther() {
		return auther;
	}

	public void setAuther(String auther) {
		this.auther = auther;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean canRental() {
		return rentalAvailability;
	}
	public String getRentalAvailability() {
		if(rentalAvailability) {//true
			return "true";
		}else {
			return"false";
		}
	}
	public void setRentalAvailability(boolean rentalAvailability) {
		this.rentalAvailability = rentalAvailability;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getRemainingPeriod() {
		return remainingPeriod;
	}

	public void setRemainingPeriod(int remainingPeriod) {
		this.remainingPeriod = remainingPeriod;
	}

	public int getRegistrationNum() {
		return registrationNum;
	}

	public void setRegistrationNum(int registrationNum) {
		this.registrationNum = registrationNum;
	}

	public String toString() {
		StringBuilder sb=new StringBuilder("[");
		if(rentalAvailability) {
			sb.append("��Ϲ�ȣ: "+registrationNum+", ����: "+title+", �۰�: "+auther+", ���ǻ�: "
					+publisher+", �뿩���ɿ���: "+rentalAvailability+", �帣: "+genre+", ��ġ: "+location+"]");
		}
		else {
			sb.append("��Ϲ�ȣ: "+registrationNum+", ����: "+title+", �۰�: "+auther+", ���ǻ�: "
					+publisher+", �뿩���ɿ���: "+rentalAvailability+", ������ ���"+id+", ���� ��¥: "+remainingPeriod+"]");
		}
		return new String(sb);
	}
	

}
