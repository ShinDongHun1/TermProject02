package app.view.manager.membership;

import java.io.IOException;

import app.Controller;
import authentication.LogInContext;
import authentication.MyException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMemberController implements Controller{
	LogInContext lc = new LogInContext();


	@FXML
	private Button btn_back;

	@FXML
	private Label label_error_username;// 회원가입 실패 이유
	@FXML
	private Label label_error_password;// 회원가입 실패 이유
	@FXML
	private Label label_error_name;// 회원가입 실패 이유
	@FXML
	private Label label_error_phone;// 회원가입 실패 이유
	@FXML
	private Button btn_signUp;// 회원가입에서 이름 전화번호 아이디 비번 입력하고 가입할 때 누르는 버튼
	@FXML
	private TextField txt_signUp_name;// 회원 아이디, 회원가입에서 사용
	@FXML
	private TextField txt_signUp_phone;// 회원 전화번호, 회원가입에서 사용
	@FXML
	private TextField txt_signUp_userName;// 회원 전화번호, 회원가입에서 사용
	@FXML
	private TextField txt_signUp_password;// 회원 전화번호, 회원가입에서 사용

	@FXML
	private Button popUp_ok;


	public void signUp(ActionEvent event) {// 정보 입력받았을 때 signUp 버튼 누르면 실행
		label_error_username.setText("");
		label_error_password.setText("");
		label_error_name.setText("");
		label_error_phone.setText("");
		
	if(txt_signUp_userName.getText().length()==0 ||txt_signUp_password.getText().length()==0|| 
		txt_signUp_name.getText().length()==0	        || txt_signUp_phone.getText().length()==0 ) {
		if(txt_signUp_userName.getText().length()==0) {
			label_error_username.setText("Fill in the blank");
		}
		if(txt_signUp_password.getText().length()==0) {
			label_error_password.setText("Fill in the blank");
		}
		if(txt_signUp_name.getText().length()==0) {
			label_error_name.setText("Fill in the blank");
		}
		if(txt_signUp_phone.getText().length()==0) {
			label_error_phone.setText("Fill in the blank");
		}
	}
		else {
			try {
				lc.SignUp(txt_signUp_name.getText(), txt_signUp_phone.getText(), txt_signUp_userName.getText(), txt_signUp_password.getText()); // 회원가입 성공하면
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("PopForAddSuccess.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setTitle("Notice");
				primaryStage.setScene(scene);
				primaryStage.show();
				
			} catch (MyException e) {
				if(e.getMessage().contains("ID")) {
					label_error_username.setText(e.getMessage());
				}
				if(e.getMessage().contains("Password")) {
					label_error_password.setText(e.getMessage());
				}
				if(e.getMessage().contains("Name")) {
					label_error_name.setText(e.getMessage());
				}
				if(e.getMessage().contains("Phone")) {
					label_error_phone.setText(e.getMessage());
				}
			//e.printStackTrace();
		}	catch (IOException e) {
			e.printStackTrace();
		}																		

	} 
}

	
	public void closePopUp(ActionEvent event) {// 회원삭제 완료됐다는 팝업창

		Stage main = (Stage) popUp_ok.getScene().getWindow();
		main.close();

}

	@Override
	public void moveBackStage(ActionEvent event) {

		Stage main = (Stage) btn_back.getScene().getWindow();
		main.close();
		
	}

	
}
