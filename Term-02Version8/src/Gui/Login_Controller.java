package TP;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login_Controller implements Initializable {
	@FXML
	public TextField idField;
	@FXML
	public PasswordField passwordField;
	@FXML
	public Button loginButton, signUpButton;
	@FXML
	public VBox MainVbox;

	@FXML
	public void loginAction() throws Exception {
		//데이터 베이스에 저장되어 있으면
		try {
			Stage primaryStage = (Stage) loginButton.getScene().getWindow();
			Parent login = FXMLLoader.load(getClass().getResource("/TP/Main_GUI.fxml"));
			Scene scene = new Scene(login);
			primaryStage.setTitle("HelloBooks/Main");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void signUpAction() throws Exception {
		try {
			Stage primaryStage = (Stage) signUpButton.getScene().getWindow();
			Parent login = FXMLLoader.load(getClass().getResource("/TP/SignUp_GUI.fxml"));
			Scene scene = new Scene(login);
			primaryStage.setTitle("HelloBooks/signup");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
}