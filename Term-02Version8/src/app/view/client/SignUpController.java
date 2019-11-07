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
	static Stage SignUpStage;// ȸ������ ������ �˾�â ����, ȸ������ â�� ���ιǷ�

	@FXML
	private Label label_title;// ���� (Sign up, ȸ������)
	@FXML
	private Label label_signUp_fail;// ȸ������ �����ϸ� �ߴ� label (status - failed - success)
	@FXML
	private Label label_signUp_fail_message;// ȸ������ ���� ����
	
	
	@FXML
	private TextField txt_signUp_username;// ȸ�� ���̵�
	@FXML
	private Label label_form_username;// ���̵� ����
	@FXML
	private TextField txt_signUp_password;// ȸ�� ��й�ȣ
	@FXML
	private Label label_form_password;// ��й�ȣ ����
	@FXML
	private TextField txt_signUp_confirm_password;// ��й�ȣ �ٸ��� ����
	@FXML
	private Label label_form_confirm_password;// ��й�ȣ ��Ȯ�� ����(?)
	@FXML
	private TextField txt_signUp_name;// ȸ�� �̸�
	@FXML
	private Label label_form_name;// �̸� ����
	@FXML
	private TextField txt_signUp_phone;// ȸ�� ��ȭ��ȣ
	@FXML
	private Label label_form_phone;// ��ȭ��ȣ ����

	@FXML
	private Button btn_back;
	@FXML
	private Button btn_signUp;// ȸ�����Կ��� �̸� ��ȭ��ȣ ���̵� ��� �Է��ϰ� ������ �� ������ ��ư
	

	

	@FXML
	private void initialize() {
		if(ClientGUIController.LANGUAGE.equals("�ѱ���")) {
			label_title.setText("ȸ������");
			txt_signUp_username.setPromptText("���̵�");
			label_form_username.setText("���̵� - ����� ���ڸ� �����մϴ�");
			txt_signUp_password.setPromptText("��й�ȣ");
			label_form_password.setText("��й�ȣ - Ư�����ڸ� ����� �� �ֽ��ϴ�.");
			txt_signUp_confirm_password.setPromptText("��й�ȣ ���Է�");
			label_form_confirm_password.setText("��й�ȣ Ȯ��");
			txt_signUp_name.setPromptText("�̸�");
			label_form_name.setText("�̸�- �ѱ۰� ��� �����մϴ�");
			txt_signUp_phone.setPromptText("��ȭ��ȣ");
			label_form_phone.setText("��ȭ��ȣ - (����: 010-OOOO-OOOO)");
			
			btn_signUp.setText("ȸ������");
			btn_back.setText("����");

			
		}else if(ClientGUIController.LANGUAGE.equals("English")) {
			
		}
	}
	

	public void signUp(ActionEvent event) {// ���� �Է¹޾��� �� signUp ��ư ������ ����
		try {
		if (txt_signUp_name.getText().length() 			==   0 		|| 		txt_signUp_phone.getText().length()		 ==		 0 // �������� �𸣰ڴµ� ==null�� �ϸ� �ȉ�, ���̴� 0������, null���� �ƴѵ�?
		|| txt_signUp_username.getText().length() 	==	 0		|| 		txt_signUp_password.getText().length()	 ==		 0) {// �ϳ��� null�� ������ ȸ������ ����
		throw new MyException("Fill in the all fields");
		
		} 
		else if(!txt_signUp_password.getText().equals(txt_signUp_confirm_password.getText())) {//�� ��й�ȣ�� �ٸ��ٸ�
			throw new MyException("Passwords does not match");
		}
		else {
			lc.SignUp(txt_signUp_name.getText(), txt_signUp_phone.getText(), txt_signUp_username.getText(), txt_signUp_password.getText()); // ȸ������ �����ϸ�
			this.SignUpStage=(Stage) btn_signUp.getScene().getWindow();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("PopForSignUpSuccess.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Notice");

			primaryStage.setScene(scene);
			primaryStage.show();
		}																	
		} catch (MyException e) {
			if(ClientGUIController.LANGUAGE.equals("�ѱ���")) {
				label_signUp_fail.setText("�α��� ����");
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

	
	public static void close() {// ȸ������ �Ϸ�ƴٴ� �˾�â
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
