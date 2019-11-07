package bookshelf;

import book.Book;

public class BookShelf {
	private static final int NUM_OF_ROW=8;
	private static final int NUM_OF_COL=20;//한 줄에 보관 가능한 책의 개수
	
	
	private Book [][] bookShelf=new Book[8][20];//한 칸에 20권 보관 가능, 총 8줄 존재
	private String gerne;//보관할 책의 장르
	public BookShelf(String gerne) {
		this.gerne=gerne;
	}
	
	public void addBook(Book book) {
		//다 찼을경우 아직 안 만들었음
		for(int row=0; row<NUM_OF_ROW; row++) {
			for(int col=0; col<NUM_OF_COL; col++ ) {
				if(bookShelf[row][col]==null) {
					bookShelf[row][col]=book;
					return;
				}
			}
		}
	}


	public String getGerne() {
		return gerne;
	}

	public void setGerne(String gerne) {
		this.gerne = gerne;
	}
	
	
}
