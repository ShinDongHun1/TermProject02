package TP;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main_Controller implements Initializable {

	@FXML
	public Button mainbutton, logoutButton, changeInfoButton, searchButton, left, right;
	@FXML
	public TextField searchField;
	@FXML
	public ImageView adPicture, userPicture;
	@FXML
	public Label adExplain, userName, userId, lendOK;
	@FXML
	public ListView newBookField, bestSellerField, newAlertField;

	private Image[] ad = {

	};

	private Image[] user = {

	};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	public void goLeftAction() {
		// adPicture.setImage();
	}

	@FXML
	public void goRightAction() {

	}

	public void mainAction() throws Exception {
		try {
			Stage primaryStage = (Stage) mainbutton.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/TP/Main_GUI.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("HelloBooks/Main");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void serachAction() throws Exception {
		try {
			Stage primaryStage = (Stage) searchButton.getScene().getWindow();
			Parent search = FXMLLoader.load(getClass().getResource("/TP/Search_GUI.fxml"));
			Scene scene = new Scene(search);
			primaryStage.setTitle("HelloBooks/search");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void logoutAction() throws Exception {
		try {
			Stage primaryStage = (Stage) searchButton.getScene().getWindow();
			Parent search = FXMLLoader.load(getClass().getResource("/TP/Login_GUI.fxml"));
			Scene scene = new Scene(search);
			primaryStage.setTitle("HelloBooks");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void changeInfoAction() {

	}

}
