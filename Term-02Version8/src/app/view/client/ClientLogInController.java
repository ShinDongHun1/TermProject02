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
	private Button btn_clientLogIn_to_signUp;// ȸ��GUI-> LogIn for Client->ȸ������ ȭ������ �̵� ��ư
	@FXML
	private Button btn_logIn;// �α��� ��ư
	@FXML
	private TextField txt_logIn_userName;// �α��� ���̵�,ȸ������ �� ���� ���
	@FXML
	private PasswordField txt_logIn_password;// �α��� ��й�ȣ, ȸ������ �� ���� ���
	@FXML
	private Label label_logIn_status;// �α��� ���� ,���и� �����ִ� Label
	@FXML
	private Label label_logIn_message;// �α��� ���� ,���и� ������ �����ִ� Label
	

	@FXML
	private void initialize() {
		if(ClientGUIController.LANGUAGE.equals("�ѱ���")) {
			label_logIn_status.setText("�α��� ����");
			txt_logIn_userName.setPromptText("���̵�");
			txt_logIn_password.setPromptText("��й�ȣ");
			btn_logIn.setText("�α���");
			btn_clientLogIn_to_signUp.setText("������ ���� ����ðڽ��ϱ�?");
			btn_back.setText("���� �������� �ǵ��ư��ϴ�.");
		}else if(ClientGUIController.LANGUAGE.equals("English")) {
			
		}
	}

	
	@FXML
	public void Login(ActionEvent event) {
		try {
			lc.logIn(txt_logIn_userName.getText(), txt_logIn_password.getText());
			if(ClientGUIController.LANGUAGE.equals("�ѱ���")) {
				label_logIn_status.setText("�α��� ����");
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
			if(ClientGUIController.LANGUAGE.equals("�ѱ���")) {
				label_logIn_status.setText("�α��� ����");
				label_logIn_message.setText(e.changeKorean());
			}
			else if(ClientGUIController.LANGUAGE.equals("English")) {
				label_logIn_status.setText("LogIn Failed");
				label_logIn_message.setText(e.getMessage());
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		} // �ٸ�â���� �Ѿ��

		
	}



	

	@FXML
	public void changeSignUpScene(ActionEvent event) {// ȸ������ â���� �Ѿ��
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
		} // �ٸ�â���� �Ѿ��

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
