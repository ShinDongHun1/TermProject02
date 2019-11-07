package app.view.manager.membership;


import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import app.Controller;

public class MemberManagerController implements Controller{
	ObservableList<String> tf_management_list = FXCollections.observableArrayList("Member","Book");
	
	@FXML
	private ChoiceBox cb_management;//관리모드 선택
	
	@FXML
	private Button btn_back;
	
	@FXML
	private Button btn_move_memberlog;
	
	@FXML
	private Button btn_move_remove_member;
	@FXML
	private Button btn_move_add_member;
	

	@FXML
	private Button btn_move_find_member;
	

	
	@FXML
	private void initialize() {
		cb_management.setValue("Member");
		cb_management.setItems(tf_management_list);
		
		cb_management.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { //여기 공부 !!!
			 
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
              	
            	//새 창 열고
            	try {
            		if(tf_management_list.get(new_value.intValue()).equals("Book")) {
            	
            			Stage primaryStage = new Stage();
            			Parent root = FXMLLoader.load(getClass().getResource("..\\book\\BookManagerGUI.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setTitle("Book Management");

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
		
	public void moveAllMemberlog(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("MemberLog");
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("ListMemberLog.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void moveDeleteMember(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("DeleteMember");
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("DeleteMember.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void moveAddMember(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("AddMember");
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("AddMember.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void moveFindMember(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Find Member");
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("FindMemberGUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void moveBookManagement(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Book Management");
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("BookManagementGUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Stage main = (Stage) btn_back.getScene().getWindow();
			main.close();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@Override
	public void moveBackStage(ActionEvent event) {
		Stage main = (Stage) btn_back.getScene().getWindow();
		main.close();	
	}

}
