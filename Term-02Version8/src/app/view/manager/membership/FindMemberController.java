package app.view.manager.membership;

import authentication.Member;
import database.MemberLogManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import app.Controller;

public class FindMemberController implements Controller{
	@FXML
	private TextField tf_username;
	
	@FXML
	private Button btn_find;
	
	@FXML
	private Button btn_back;

	@FXML
	private TextArea ta_member_info;
	
	
	public void findMember(ActionEvent event) {
		ta_member_info.clear();
		
		Member find_mem=MemberLogManager.getMemberInfoFromDB(tf_username.getText());

		if(find_mem==null) {
			ta_member_info.setText("존재하는 회원이 없습니다.");
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
	
	
	@Override
	public void moveBackStage(ActionEvent event) {
		
	}

}
