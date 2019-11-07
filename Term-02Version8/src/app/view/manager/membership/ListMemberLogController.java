package app.view.manager.membership;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import authentication.Member;
import database.MemberLogManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import app.Controller;

public class ListMemberLogController  implements Controller{
	@FXML
	private Button btn_back;
	@FXML
	private TextArea ta_memberLog;
	@FXML
	private Button btn_show;
	
	@FXML
	private Button btn_find_member;// 회원 찾기
	
	@FXML
	public void initialize() {

		List<Member> memList=MemberLogManager.getAllMemberFromDB();
		Iterator e=memList.iterator();
		while(e.hasNext()) {
			ta_memberLog.appendText(e.next().toString()+"\r\n");
		}
	}
	
	@Override
	public void moveBackStage(ActionEvent event) {
		// TODO Auto-generated method stub
		Stage main = (Stage) btn_back.getScene().getWindow();
		main.close();
	
	}
	
	public void updateMemberLog(ActionEvent event) {
		
		ta_memberLog.clear();
		List<Member> memList=MemberLogManager.getAllMemberFromDB();
		Iterator e=memList.iterator();
		while(e.hasNext()) {
			ta_memberLog.appendText(e.next().toString()+"\r\n");
		}
	}
	


}
