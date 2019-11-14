package TP;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUp_Controller implements Initializable {
	@FXML
	public VBox SignUpVbox;
	@FXML
	public TextField idField, passwordField, nameField, emailField, phonenumberField;
	@FXML
	public ChoiceBox<String> email_next;
	@FXML
	public TextArea addressField;
	@FXML
	public Button SignUpButton, mainbutton;

	@FXML
	public void mainbuttonAction() throws Exception {
		try {
			Stage primaryStage = (Stage) mainbutton.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/TP/Login_GUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("HelloBooks");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void signupAction() {
		//회원가입 내역 전부 작성 X
		if (idField.getText().length() == 0 || passwordField.getText().length() == 0
				|| nameField.getText().length() == 0 || emailField.getText().length() == 0
				|| phonenumberField.getText().length() == 0 || email_next.getValue() == null
				|| addressField.getText().length() == 0)
			new Alert(Alert.AlertType.WARNING, "빈칸을 전부 채워주세요.", ButtonType.CLOSE).show();
		//ID 중복의 경우
		else if(false) {
		}
		//회원가입 내역 완벽할때
		else {
			StringBuilder data = new StringBuilder();
			data.append(idField.getText() + " " + passwordField.getText() + " " + nameField.getText() + " "
					+ emailField.getText() + " " + phonenumberField.getText() + " " + email_next.getValue() + " "
					+ addressField.getText());

			//회원가입하면 로그인창으로 이동
			try {
				Stage primaryStage = (Stage) SignUpButton.getScene().getWindow();
				Parent search = FXMLLoader.load(getClass().getResource("/TP/Login_GUI.fxml"));
				Scene scene = new Scene(search);
				primaryStage.setTitle("HelloBooks");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> publicComboList = FXCollections.observableArrayList("naver.com", "gmail.com",
				"hanmail.net");
		email_next.setItems(publicComboList);
	}

}
