package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientGUI extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Client GUI");
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("view\\ClientGUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("view\\Css.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	public static void main(String[] args) {
		launch(args);
	}

	
	
	public static void ShowManagerLogInScene() throws IOException {// 관리자 로그인 창으로 넘어가기
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(ClientGUI.class.getResource("view\\manager\\ManagerLogInGUI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("ManagerLogIn");
		scene.getStylesheets().add(ClientGUI.class.getResource("view\\Css.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void ShowClientLogInScene() throws IOException {// 고객 로그인 창으로 넘어가기
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(ClientGUI.class.getResource("view\\client\\ClientLogInGUI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("ClientLogIn");
		scene.getStylesheets().add(ClientGUI.class.getResource("view\\Css.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	


}
