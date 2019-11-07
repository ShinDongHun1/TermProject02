package app.view.manager.membership;

import authentication.LogInContext;
import authentication.Member;
import authentication.MyException;
import database.MemberLogManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import app.Controller;

public class DeleteMemberController  implements Controller{
	
	LogInContext lc = new LogInContext();
	
	
	@FXML
	private TextField tf_username;
	@FXML
	private TextField tf_password;
	
	@FXML
	private Button btn_delete;
	@FXML
	private Button btn_back;
	@FXML
	private Button btn_show;
	
	@FXML
	private TextArea ta_member_info;
	@FXML
	private Label label_error_username;// 아이디 오류
	
	@FXML
	private Label label_error_password;//비번 오류
	
	@FXML
	private Button popUp_ok;
	
	public void deleteMember(ActionEvent event) {
		try {
			label_error_username.setText("");
			label_error_password.setText("");
			
			if(tf_username.getText().length()==0 || tf_password.getText().length()==0) {
				if(tf_username.getText().length()==0) {
					label_error_username.setText("Fill in the blank");
				}
				if(tf_password.getText().length()==0) {
					label_error_password.setText("Fill in the blank");
				}
			}
			
			else {
			lc.SignOut(tf_username.getText(), tf_password.getText());
		
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("PopForDeleteSuccess.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Notice");

			primaryStage.setScene(scene);
			primaryStage.show();
			
			}
		}catch(MyException e) {
			if(e.getMessage().contains("ID")) {//ID오류면
				label_error_password.setText("");
				label_error_username.setText(e.getMessage());
			}if(e.getMessage().contains("Password")) {//비번오류면
				label_error_username.setText("");
				label_error_password.setText(e.getMessage());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void showMemberInfo(ActionEvent event) {
		ta_member_info.clear();
		try {
			label_error_username.setText("");
			label_error_password.setText("");
			
			if(tf_username.getText().length()==0 || tf_password.getText().length()==0) {
				if(tf_username.getText().length()==0) {
					label_error_username.setText("Fill in the blank");
				}
				if(tf_password.getText().length()==0) {
					label_error_password.setText("Fill in the blank");
				}
			}
			else {
				Member find_mem=MemberLogManager.getMemberInfoFromDB(tf_username.getText());
				System.out.println(tf_username.getText());
				if(find_mem==null) {
					ta_member_info.setText("존재하는 회원이 없습니다.");
				}
				else if(!find_mem.getPassword().equals(tf_password.getText())) {
					throw new MyException("Passwords do not match");
				}
				else {
					ta_member_info.appendText("아이디: "+find_mem.getID()+"\r\n");
					ta_member_info.appendText("비밀번호: "+find_mem.getPassword()+"\r\n");
					ta_member_info.appendText("이름: "+find_mem.getName()+"\r\n");
					ta_member_info.appendText("전화번호: "+find_mem.getPhone()+"\r\n");
					ta_member_info.appendText("연체료: "+find_mem.getLateFee()+" 원\r\n");
					//ta_member_info.appendText("ID: "+find_mem);   나중에 책 정보
				}
			}
		}catch(MyException e) {
			if(e.getMessage().contains("ID")) {//ID오류면
				label_error_password.setText("");
				label_error_username.setText(e.getMessage());
			}if(e.getMessage().contains("Password")) {//비번오류면
				label_error_username.setText("");
				label_error_password.setText(e.getMessage());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closePopUp(ActionEvent event) {// 회원삭제 완료됐다는 팝업창
			Stage main = (Stage) popUp_ok.getScene().getWindow();
			main.close();

	}
	
	
	
	@Override
	public void moveBackStage(ActionEvent event) {

			Stage main = (Stage) btn_back.getScene().getWindow();
			main.close();
			
	}

}
