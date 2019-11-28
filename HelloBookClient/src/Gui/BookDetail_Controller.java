package Gui;

import java.net.URL;
import java.util.ResourceBundle;

import Gui.model.DataModel;
import book.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BookDetail_Controller extends Base_Controller implements Initializable {

	@FXML
	public Button btn_Back, btn_Left, btn_WishList, btn_Borrow, btn_BuyNow, btn_Right;
	@FXML
	public Label lb_MainBookName, lb_MainBookAuthor, lb_RentalStatus, lb_Title, lb_Author, lb_Publisher, lb_BookCondition, lb_FullPrice, lb_SalePrice, lb_LendPrice, lb_Introduction; 
	
	private Book book;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.book=DataModel.book_for_detail;
	
		// Base start
		super.base();
		// Base end
		if(book==null) {
			lb_MainBookName.setText("책이 존재하지 않습니다.");
		}else {
			lb_MainBookName.setText(book.getTitle());
			lb_MainBookAuthor.setText(book.getAuther());
			lb_RentalStatus.setText(book.getRental_status()+"");
			lb_Title.setText(book.getTitle());
			lb_Author.setText(book.getAuther());
			lb_Publisher.setText(book.getPublisher());
			lb_BookCondition.setText(book.getBook_condition());
			lb_FullPrice.setText(book.getFull_price()+"");
			lb_SalePrice.setText(book.getSale_price()+"");
			lb_LendPrice.setText(book.getLend_price()+"");
			lb_Introduction.setText(book.getIntroduction());
		}	
	}

	@FXML
	public void backAction() { //전 화면으로 

	}

	@FXML
	public void putWishListAction() { //이 책을 wishlist에 넣기

	}

	@FXML
	public void borrowedNowAction() { //이 책을 바로 빌리기 책주인에게 알리기 -> 책 주인의 New Alert에 뜨기

	}

	@FXML
	public void buyNowAction() { //이 책을 바로 구매하기 -> 책 주인의 New Alert에 뜨기

	}
	@FXML
	public void goLeftAction() { // 왼쪽의 다른 책 보여주기
		
	}
	
	public void goRightAction() { // 오른쪽의 다른 책 보여주기
		
	}

}
