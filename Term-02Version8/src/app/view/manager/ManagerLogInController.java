package app.view.manager;

import java.io.IOException;

import authentication.LogInContext;
import authentication.MyException;
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
import app.Controller;
import app.view.ClientGUIController;

public class ManagerLogInController implements Controller {
	private static final String [] manager_id= {"도서관"};
	private static final String [] manager_password= {"1234"};
	LogInContext lc = new LogInContext();

	@FXML
	private Button btn_back;

	
	@FXML
	private Button btn_logIn;// 로그인 버튼
	@FXML
	private TextField txt_logIn_managername;// 로그인 아이디,회원가입 할 때도 사용
	@FXML
	private PasswordField txt_logIn_password;// 로그인 비밀번호, 회원가입 할 때도 사용
	@FXML
	private Label label_logIn_status;// 로그인 성공 ,실패를 보여주는 Label
	@FXML
	private Label label_logIn_message;// 로그인 성공 ,실패를 보여주는 Label
	


	
	@FXML
	private void initialize() {
		if(ClientGUIController.LANGUAGE.equals("한국어")) {
			label_logIn_status.setText("로그인 상태");
			txt_logIn_managername.setPromptText("관리자 아이디");
			txt_logIn_password.setPromptText("비밀번호");
			btn_logIn.setText("로그인");
			btn_back.setText("이전 페이지로 되돌아갑니다.");
		}else if(ClientGUIController.LANGUAGE.equals("English")) {
			
		}
	}

	@FXML
	public void Login(ActionEvent event) {
		try {
			
			for(int i=0; i<manager_id.length; i++) {
				if( txt_logIn_managername.getText().equals(manager_id[i])) {//아이디가 맞고
					if(txt_logIn_password.getText().equals(manager_password[i])) {//비번도 같으면
						if(ClientGUIController.LANGUAGE.equals("한국어")) {
							label_logIn_status.setText("로그인 성공");
						}
						else if(ClientGUIController.LANGUAGE.equals("English")) {
						label_logIn_status.setText("LogIn Success");
						}
						
						Stage primaryStage = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("membership\\MemberManagerGUI.fxml"));
						Scene scene = new Scene(root);
						primaryStage.setTitle("Member Management");

						primaryStage.setScene(scene);
						primaryStage.show();
						
						Stage main = (Stage) btn_logIn.getScene().getWindow();
						main.close();
					}
					else {//비번 틀리면		
						throw new MyException("Passwords do not match");
					}
				}
			}
			//아이디가 없다면
			throw new MyException("ID does not exist");
			
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
