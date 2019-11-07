package app.view.client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import app.view.ClientGUIController;

public class PopUpForSignUpSuccessController {
	@FXML
	private Button popUp_ok;
	@FXML
	private Label popUp_message;
	
	@FXML
	private void initialize() {
		if(ClientGUIController.LANGUAGE.equals("�ѱ���")) {
			popUp_ok.setText("Ȯ��");
			popUp_message.setText("ȸ������ ����!!");
			
		}else if(ClientGUIController.LANGUAGE.equals("English")) {
			
		}

	}
	
	public void goToLogin(ActionEvent event) {// ȸ������ �Ϸ�ƴٴ� �˾�â

		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("ClientLogInGUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("LogIn");
			scene.getStylesheets().add(getClass().getResource("..\\Css.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			Stage main = (Stage) popUp_ok.getScene().getWindow();
			main.close();
			SignUpController.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
