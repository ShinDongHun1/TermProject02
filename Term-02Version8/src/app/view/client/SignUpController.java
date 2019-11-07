package app.view.client;

import java.io.IOException;

import app.Controller;
import app.view.ClientGUIController;
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

public class SignUpController implements Controller  {
	LogInContext lc = new LogInContext();
	static Stage SignUpStage;// 회원가입 성공시 팝업창 띄우고, 회원가입 창은 냅두므로

	@FXML
	private Label label_title;// 제목 (Sign up, 회원가입)
	@FXML
	private Label label_signUp_fail;// 회원가입 실패하면 뜨는 label (status - failed - success)
	@FXML
	private Label label_signUp_fail_message;// 회원가입 실패 이유
	
	
	@FXML
	private TextField txt_signUp_username;// 회원 아이디
	@FXML
	private Label label_form_username;// 아이디 형식
	@FXML
	private TextField txt_signUp_password;// 회원 비밀번호
	@FXML
	private Label label_form_password;// 비밀번호 형식
	@FXML
	private TextField txt_signUp_confirm_password;// 비밀번호 다르면 오류
	@FXML
	private Label label_form_confirm_password;// 비밀번호 재확인 형식(?)
	@FXML
	private TextField txt_signUp_name;// 회원 이름
	@FXML
	private Label label_form_name;// 이른 형식
	@FXML
	private TextField txt_signUp_phone;// 회원 전화번호
	@FXML
	private Label label_form_phone;// 전화번호 형식

	@FXML
	private Button btn_back;
	@FXML
	private Button btn_signUp;// 회원가입에서 이름 전화번호 아이디 비번 입력하고 가입할 때 누르는 버튼
	

	

	@FXML
	private void initialize() {
		if(ClientGUIController.LANGUAGE.equals("한국어")) {
			label_title.setText("회원가입");
			txt_signUp_username.setPromptText("아이디");
			label_form_username.setText("아이디 - 영어와 숫자만 가능합니다");
			txt_signUp_password.setPromptText("비밀번호");
			label_form_password.setText("비밀번호 - 특수문자를 사용할 수 있습니다.");
			txt_signUp_confirm_password.setPromptText("비밀번호 재입력");
			label_form_confirm_password.setText("비밀번호 확인");
			txt_signUp_name.setPromptText("이름");
			label_form_name.setText("이름- 한글과 영어만 가능합니다");
			txt_signUp_phone.setPromptText("전화번호");
			label_form_phone.setText("전화번호 - (형식: 010-OOOO-OOOO)");
			
			btn_signUp.setText("회원가입");
			btn_back.setText("이전");

			
		}else if(ClientGUIController.LANGUAGE.equals("English")) {
			
		}
	}
	

	public void signUp(ActionEvent event) {// 정보 입력받았을 때 signUp 버튼 누르면 실행
		try {
		if (txt_signUp_name.getText().length() 			==   0 		|| 		txt_signUp_phone.getText().length()		 ==		 0 // 왜인지는 모르겠는데 ==null로 하면 안됌, 길이는 0이지만, null값은 아닌듯?
		|| txt_signUp_username.getText().length() 	==	 0		|| 		txt_signUp_password.getText().length()	 ==		 0) {// 하나라도 null이 있으면 회원가입 실패
		throw new MyException("Fill in the all fields");
		
		} 
		else if(!txt_signUp_password.getText().equals(txt_signUp_confirm_password.getText())) {//두 비밀번호가 다르다면
			throw new MyException("Passwords does not match");
		}
		else {
			lc.SignUp(txt_signUp_name.getText(), txt_signUp_phone.getText(), txt_signUp_username.getText(), txt_signUp_password.getText()); // 회원가입 성공하면
			this.SignUpStage=(Stage) btn_signUp.getScene().getWindow();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("PopForSignUpSuccess.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Notice");

			primaryStage.setScene(scene);
			primaryStage.show();
		}																	
		} catch (MyException e) {
			if(ClientGUIController.LANGUAGE.equals("한국어")) {
				label_signUp_fail.setText("로그인 실패");
				label_signUp_fail_message.setText(e.changeKorean());
			}
			else if(ClientGUIController.LANGUAGE.equals("English")) {
				label_signUp_fail.setText("LogIn Failed");
				label_signUp_fail_message.setText(e.getMessage());
			}
		}	catch (IOException e) {
			e.printStackTrace();
		}	 
}

	
	public static void close() {// 회원가입 완료됐다는 팝업창
		SignUpStage.close();
		SignUpStage=null;
		
	}

	@Override
	public void moveBackStage(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("ClientLogInGUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("ClientLogIn");
			scene.getStylesheets().add(getClass().getResource("..\\Css.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();


			Stage main = (Stage) btn_back.getScene().getWindow();
			main.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
