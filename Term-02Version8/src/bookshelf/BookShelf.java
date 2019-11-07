package bookshelf;

import book.Book;

public class BookShelf {
	private static final int NUM_OF_ROW=8;
	private static final int NUM_OF_COL=20;//�� �ٿ� ���� ������ å�� ����
	
	
	private Book [][] bookShelf=new Book[8][20];//�� ĭ�� 20�� ���� ����, �� 8�� ����
	private String gerne;//������ å�� �帣
	public BookShelf(String gerne) {
		this.gerne=gerne;
	}
	
	public void addBook(Book book) {
		//�� á����� ���� �� �������
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
