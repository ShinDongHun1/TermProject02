package app.view.manager.book;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class BookManagerController {
	ObservableList<String> tf_management_list = FXCollections.observableArrayList("Member","Book");
	@FXML
	private ChoiceBox cb_management;//관리모드 선택
	
	@FXML
	private Button btn_back;
	
	@FXML
	private Button btn_move_book_log;
	
	@FXML
	private Button btn_move_remove_book;
	@FXML
	private Button btn_move_add_book;
	

	@FXML
	private Button btn_move_find_book;
	@FXML
	private void initialize() {
		cb_management.setValue("Book");
		cb_management.setItems(tf_management_list);
		
		cb_management.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { //여기 공부 !!!
			 
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
              	
            	//새 창 열고
            	try {
            		if(tf_management_list.get(new_value.intValue()).equals("Member")) {
            	
            			Stage primaryStage = new Stage();
            			Parent root = FXMLLoader.load(getClass().getResource("..\\membership\\MemberManagerGUI.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setTitle("Member Management");

					primaryStage.setScene(scene);
					primaryStage.show();
            		Stage main = (Stage) btn_back.getScene().getWindow();
            		main.close();
            		}
            	}catch(IOException e) {
            		e.printStackTrace();
            	}
            } 
        }); 
	}
}
