package app.view.client;

import java.io.IOException;

import app.Controller;
import app.view.ClientGUIController;
import app.view.client.data.dataStore;
import app.view.client.funtion.ClientAppController;
import authentication.LogInContext;
import authentication.MyException;
import database.MemberLogManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ClientLogInController implements Controller {
	LogInContext lc = new LogInContext();

	@FXML
	private Button btn_back;

	
	@FXML
	private Button btn_clientLogIn_to_signUp;// 회원GUI-> LogIn for Client->회원가입 화면으로 이동 버튼
	@FXML
	private Button btn_logIn;// 로그인 버튼
	@FXML
	private TextField txt_logIn_userName;// 로그인 아이디,회원가입 할 때도 사용
	@FXML
	private PasswordField txt_logIn_password;// 로그인 비밀번호, 회원가입 할 때도 사용
	@FXML
	private Label label_logIn_status;// 로그인 성공 ,실패를 보여주는 Label
	@FXML
	private Label label_logIn_message;// 로그인 성공 ,실패를 이유를 보여주는 Label
	

	@FXML
	private void initialize() {
		if(ClientGUIController.LANGUAGE.equals("한국어")) {
			label_logIn_status.setText("로그인 상태");
			txt_logIn_userName.setPromptText("아이디");
			txt_logIn_password.setPromptText("비밀번호");
			btn_logIn.setText("로그인");
			btn_clientLogIn_to_signUp.setText("계정을 새로 만드시겠습니까?");
			btn_back.setText("이전 페이지로 되돌아갑니다.");
		}else if(ClientGUIController.LANGUAGE.equals("English")) {
			
		}
	}

	
	@FXML
	public void Login(ActionEvent event) {
		try {
			lc.logIn(txt_logIn_userName.getText(), txt_logIn_password.getText());
			if(ClientGUIController.LANGUAGE.equals("한국어")) {
				label_logIn_status.setText("로그인 성공");
			}
			else if(ClientGUIController.LANGUAGE.equals("English")) {
			label_logIn_status.setText("LogIn Success");
			}
			dataStore.client=MemberLogManager.getMemberInfoFromDB(txt_logIn_userName.getText());
			

			
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("funtion\\ClientApp.fxml"));
			Scene scene = new Scene(root);
			
			
			
			primaryStage.setTitle("Client App");
			
			primaryStage.setOnCloseRequest(ActionEvent->ClientAppController.closeConnect());
			
			scene.getStylesheets().add(getClass().getResource("funtion\\ClientAppCss.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Stage main = (Stage) btn_logIn.getScene().getWindow();
			main.close();
			
			
			
		} catch (MyException e) {
			if(ClientGUIController.LANGUAGE.equals("한국어")) {
				label_logIn_status.setText("로그인 실패");
				label_logIn_message.setText(e.changeKorean());
			}
			else if(ClientGUIController.LANGUAGE.equals("English")) {
				label_logIn_status.setText("LogIn Failed");
				label_logIn_message.setText(e.getMessage());
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		} // 다른창으로 넘어갈거

		
	}



	

	@FXML
	public void changeSignUpScene(ActionEvent event) {// 회원가입 창으로 넘어가기
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("SignUpGUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("SignUp");
			primaryStage.setScene(scene);
			primaryStage.show();

	
			Stage main = (Stage) btn_clientLogIn_to_signUp.getScene().getWindow();
			main.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 다른창으로 넘어갈거

	}


	@Override
	public void moveBackStage(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Client GUI");
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("..\\ClientGUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("..\\Css.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Stage main = (Stage)btn_back.getScene().getWindow();
			main.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		
	}
}
