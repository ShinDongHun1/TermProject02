package app.view.client.funtion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import app.view.client.data.dataStore;
import authentication.Member;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BookSearchController {
	private Member client;
	private Socket socket;
	
	
	ObservableList<String> tf_rentalAvailable_list = FXCollections.observableArrayList("가능","불가능","선택안함");
	ObservableList<String> tf_gerne_list = FXCollections.observableArrayList("과학","소설","수필","선택안함");
	private String rentalAvailability="Not selected";
	private String gerne="선택안함";
	
	@FXML
	private TextField tf_title;
	@FXML
	private TextField tf_auther;
	@FXML
	private TextField tf_publisher;
	
	@FXML
	private ChoiceBox cb_rental_ability;
	
	@FXML
	private ChoiceBox cb_gerne;
	
	@FXML
	private  Button btn_find;
	@FXML
	private TextArea ta_result;
	
	@FXML
	private void initialize() {
		client=dataStore.client;
		socket=dataStore.socket;
		cb_rental_ability.setValue("선택안함");
		rentalAvailability="선택안함";
		cb_rental_ability.setItems(tf_rentalAvailable_list);
		cb_gerne.setValue("선택안함");
		gerne="선택안함";
		cb_gerne.setItems(tf_gerne_list);
		
		new SearchBookReceiveThread(socket).start();
		
		cb_rental_ability.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { //여기 공부 !!!
			 
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
            	rentalAvailability=tf_rentalAvailable_list.get(new_value.intValue()).toString();
            }
        }); 
		
		cb_gerne.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { //여기 공부 !!!
			 
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
            	gerne=tf_gerne_list.get(new_value.intValue()).toString();
            }
        }); 
		
	}
	
	
	public void searchBook(ActionEvent event) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			ArrayList<String> ms=new ArrayList<>();//0-제목, 1-작가, 2-출판사, 3-장르, 4-대여가능성

				if(tf_title.getText().length()!=0) ms.add("Title-"+tf_title.getText());
				if(tf_auther.getText().length()!=0) ms.add("Auther-"+tf_auther.getText());
				if(tf_publisher.getText().length()!=0) ms.add("Publisher-"+tf_publisher.getText());
				if(!gerne.equals("선택안함")) ms.add("Gerne-"+gerne);//나중에 바꿔야 함
				if(!rentalAvailability.equals("선택안함")) {
					if(rentalAvailability.equals("가능")) {
						ms.add("RentalAvailability-1");
					}else {
						ms.add("RentalAvailability-0");//불가능
					}
				}
			
			
			String request="Search Book:";
			for(int i=0; i<ms.size(); i++) {
				if(i==0) {
					request+=ms.get(i);
				}else  {
					request+=":"+ms.get(i);
				}
				
			}
			request+="\r\n";
			System.out.println(request);
			
			ta_result.clear();
			pw.println(request);
			
			btn_find.setDisable(true);
			Thread.sleep(500);
			btn_find.setDisable(false);
		}catch(IOException e) {
			e.printStackTrace();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	
	  private class SearchBookReceiveThread extends Thread{
	        Socket socket = null;

	        SearchBookReceiveThread(Socket socket){//생성자
	            this.socket = socket;
	        }
	        @Override
	        public void run() {//start 하면 자동으로 실행됨.
	        	BufferedReader br=null;
	            try {
	                br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
	                //socket에 저장된 입력스트림이 br에 저장. 대충 이런느낌?
	                while(true) {  	
	                	
	                    String msg = br.readLine();
	                    if(msg.equals("null")) {
	                    	ta_result.appendText("찾으시는 정보의 책이 없습니다.");
	                    }else {
	                    	ta_result.appendText(msg);
	                    	ta_result.appendText("\r\n");
	                    }
	                }
	            }
	            catch (IOException e) {
	            	try {
						br.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                e.printStackTrace();
	            }finally {
	            	
	            }
	        }
	    }
	
}
