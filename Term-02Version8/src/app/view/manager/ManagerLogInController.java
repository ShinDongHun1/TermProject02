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
	private static final String [] manager_id= {"������"};
	private static final String [] manager_password= {"1234"};
	LogInContext lc = new LogInContext();

	@FXML
	private Button btn_back;

	
	@FXML
	private Button btn_logIn;// �α��� ��ư
	@FXML
	private TextField txt_logIn_managername;// �α��� ���̵�,ȸ������ �� ���� ���
	@FXML
	private PasswordField txt_logIn_password;// �α��� ��й�ȣ, ȸ������ �� ���� ���
	@FXML
	private Label label_logIn_status;// �α��� ���� ,���и� �����ִ� Label
	@FXML
	private Label label_logIn_message;// �α��� ���� ,���и� �����ִ� Label
	


	
	@FXML
	private void initialize() {
		if(ClientGUIController.LANGUAGE.equals("�ѱ���")) {
			label_logIn_status.setText("�α��� ����");
			txt_logIn_managername.setPromptText("������ ���̵�");
			txt_logIn_password.setPromptText("��й�ȣ");
			btn_logIn.setText("�α���");
			btn_back.setText("���� �������� �ǵ��ư��ϴ�.");
		}else if(ClientGUIController.LANGUAGE.equals("English")) {
			
		}
	}

	@FXML
	public void Login(ActionEvent event) {
		try {
			
			for(int i=0; i<manager_id.length; i++) {
				if( txt_logIn_managername.getText().equals(manager_id[i])) {//���̵� �°�
					if(txt_logIn_password.getText().equals(manager_password[i])) {//����� ������
						if(ClientGUIController.LANGUAGE.equals("�ѱ���")) {
							label_logIn_status.setText("�α��� ����");
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
					else {//��� Ʋ����		
						throw new MyException("Passwords do not match");
					}
				}
			}
			//���̵� ���ٸ�
			throw new MyException("ID does not exist");
			
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
