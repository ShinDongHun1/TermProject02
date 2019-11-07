package book;

public class Book {
	
	private 		int 				registrationNum; // 등록번호, 등록된 순으로 1씩 증가시킴.
	private 		String 			title;						//제목
	private		String 			auther;					//작가
	private 		String 			publisher;				//출판사
	private 		String 			genre;						//장르
	private		boolean 		rentalAvailability;//대여 여부, 가능하면 true, 이미 대여당한 상태라면 false.
	private 		String			id;							//책 빌려간 사람 아이디
	private 		String			location;					//위치
	private 		int  				remainingPeriod; // 남은 기간, 대여여부가 true일때는 존재 X
	

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
		if(bookInfo[7]==null) {bookInfo[7]="아직 선반에 놓이지 않음";}
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
			sb.append("등록번호: "+registrationNum+", 제목: "+title+", 작가: "+auther+", 출판사: "
					+publisher+", 대여가능여부: "+rentalAvailability+", 장르: "+genre+", 위치: "+location+"]");
		}
		else {
			sb.append("등록번호: "+registrationNum+", 제목: "+title+", 작가: "+auther+", 출판사: "
					+publisher+", 대여가능여부: "+rentalAvailability+", 빌려간 사람"+id+", 남은 날짜: "+remainingPeriod+"]");
		}
		return new String(sb);
	}
	

}
