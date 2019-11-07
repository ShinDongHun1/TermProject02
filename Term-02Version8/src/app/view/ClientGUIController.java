package app.view;

import java.io.IOException;

import app.ClientGUI;
import app.Controller;
import authentication.LogInContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class ClientGUIController implements Controller {
	public static String LANGUAGE;
	private ClientGUI cg;
	LogInContext lc = new LogInContext();
	ObservableList<String> tf_language_list = FXCollections.observableArrayList("English","한국어");
	
	@FXML
	private Button btn_end;

	@FXML
	private Button btn_clientGUI_to_client_logIn;// 회원GUI-> 로그인 화면으로 이동 버튼
	
	@FXML
	private Label lb_title1;
	@FXML
	private Label lb_title2;
	@FXML
	private Label lb_language;
	
	@FXML
	private Button btn_clientGUI_to_manager_logIn;// 회원GUI-> 로그인 화면으로 이동 버튼

	@FXML
	private ChoiceBox cb_language;//언어선택

	
	@FXML
	private void initialize() {
		
		LANGUAGE="English";
		cb_language.setValue("English");
		cb_language.setItems(tf_language_list);
		
		cb_language.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { //여기 공부 !!!
			 
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
        
            	if(tf_language_list.get(new_value.intValue()).equals("한국어")) {
            		LANGUAGE="한국어";
            		 // set the text for the label to the selected item 

            		btn_clientGUI_to_client_logIn.setText("고객으로 로그인");
            		btn_clientGUI_to_manager_logIn.setText("관리자로 로그인");
            		btn_end.setText("종료");
            		lb_title1.setText("도서관");
            		lb_title2.setText("이용자 앱");
            		lb_language.setText("언어");
            	}else if(tf_language_list.get(new_value.intValue()).equals("English")) {
            		LANGUAGE="English";
            		btn_clientGUI_to_client_logIn.setText("LogIn For Client");
            		btn_clientGUI_to_manager_logIn.setText("LogIn For Manager");
            		btn_end.setText("Close");
            		lb_title1.setText("Library");
            		lb_title2.setText("Client App");
            		lb_language.setText("Language");
            	}
            } 
        }); 
		
		//cb_language.getSelectionModel().getSelectedItem().toString() 가져오기
	}
	


	public void changeManagerScene(ActionEvent event) {// 관리자 로그인 창으로 넘어가기
		try {
			cg.ShowManagerLogInScene();
	
			Stage main = (Stage) btn_clientGUI_to_manager_logIn.getScene().getWindow();
			main.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeLogInScene(ActionEvent event) {// 고객 로그인 창으로 넘어가기
		try {
			cg.ShowClientLogInScene();

			Stage main = (Stage) btn_clientGUI_to_client_logIn.getScene().getWindow();
			main.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void moveBackStage(ActionEvent event) {// 여기서 이전 stage는 없음=> ClientGUI에서만 종료시킬거임
		// TODO Auto-generated method stub
	 	Stage stage=(Stage) btn_end.getScene().getWindow();
	 	stage.close();
		
	}




}
