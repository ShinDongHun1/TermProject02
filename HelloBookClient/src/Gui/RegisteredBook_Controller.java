package Gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Gui.model.DataModel;
import book.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RegisteredBook_Controller extends Base_Controller implements Initializable {
	public Button btn_Back, btn_Remove, btn_Comfirm;
	@FXML
	public RadioButton Rbtn_canLend;
	@FXML
	public RadioButton Rbtn_lend;
	@FXML
	public TextField tf_who;

	public Label lb_RentalStatus, lb_Title, lb_Author, lb_Publisher, lb_BookCondition, lb_FullPrice, lb_SalePrice,
			lb_LendPrice, lb_Introduction;

	private Book book;
	@FXML
	private ToggleGroup status;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Base start
		super.base();
		// Base end
		
		this.book = DataModel.book_for_registered;
		
		if (book == null) {
			lb_Title.setText("책이 존재하지 않습니다.");
		} else {
			if (book.getRental_status()) {// 가능하다면
				Rbtn_canLend.setSelected(true);
				Rbtn_lend.setDisable(true);
				tf_who.setDisable(true);
			} else {//빌려준상대
				Rbtn_lend.setSelected(true);
				Rbtn_canLend.setDisable(true);
				//누구에게는 아직 생각중
				
			}
			lb_Title.setText(book.getTitle());
			lb_Author.setText(book.getAuther());
			lb_Title.setText(book.getTitle());
			lb_Author.setText(book.getAuther());
			lb_Publisher.setText(book.getPublisher());
			lb_BookCondition.setText(book.getBook_condition());
			lb_FullPrice.setText(book.getFull_price() + "");
			lb_SalePrice.setText(book.getSale_price() + "");
			lb_LendPrice.setText(book.getLend_price() + "");
			lb_Introduction.setText(book.getIntroduction());
		}
	}

	public void backAction() {
		try {
			Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/Gui/Main_GUI.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("HelloBooks/Main");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeAction() {
		Alert alert = new Alert(Alert.AlertType.WARNING, "이 책을 정말로 삭제하시겠습니까?", ButtonType.YES, ButtonType.CANCEL);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			// 등록된 책을 삭제
		}
	}

	public void comfirmAction() {// 정보 수정된 상태로 저장 (만약 책을 빌리거나 구매한다는 alert가 뜨면, 그거에 맞춰서 수정해줌,)
	}
}
