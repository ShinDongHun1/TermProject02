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
	ObservableList<String> tf_language_list = FXCollections.observableArrayList("English","�ѱ���");
	
	@FXML
	private Button btn_end;

	@FXML
	private Button btn_clientGUI_to_client_logIn;// ȸ��GUI-> �α��� ȭ������ �̵� ��ư
	
	@FXML
	private Label lb_title1;
	@FXML
	private Label lb_title2;
	@FXML
	private Label lb_language;
	
	@FXML
	private Button btn_clientGUI_to_manager_logIn;// ȸ��GUI-> �α��� ȭ������ �̵� ��ư

	@FXML
	private ChoiceBox cb_language;//����

	
	@FXML
	private void initialize() {
		
		LANGUAGE="English";
		cb_language.setValue("English");
		cb_language.setItems(tf_language_list);
		
		cb_language.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { //���� ���� !!!
			 
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
        
            	if(tf_language_list.get(new_value.intValue()).equals("�ѱ���")) {
            		LANGUAGE="�ѱ���";
            		 // set the text for the label to the selected item 

            		btn_clientGUI_to_client_logIn.setText("������ �α���");
            		btn_clientGUI_to_manager_logIn.setText("�����ڷ� �α���");
            		btn_end.setText("����");
            		lb_title1.setText("������");
            		lb_title2.setText("�̿��� ��");
            		lb_language.setText("���");
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
		
		//cb_language.getSelectionModel().getSelectedItem().toString() ��������
	}
	


	public void changeManagerScene(ActionEvent event) {// ������ �α��� â���� �Ѿ��
		try {
			cg.ShowManagerLogInScene();
	
			Stage main = (Stage) btn_clientGUI_to_manager_logIn.getScene().getWindow();
			main.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void changeLogInScene(ActionEvent event) {// �� �α��� â���� �Ѿ��
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
	public void moveBackStage(ActionEvent event) {// ���⼭ ���� stage�� ����=> ClientGUI������ �����ų����
		// TODO Auto-generated method stub
	 	Stage stage=(Stage) btn_end.getScene().getWindow();
	 	stage.close();
		
	}




}
