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
	
	
	ObservableList<String> tf_rentalAvailable_list = FXCollections.observableArrayList("����","�Ұ���","���þ���");
	ObservableList<String> tf_gerne_list = FXCollections.observableArrayList("����","�Ҽ�","����","���þ���");
	private String rentalAvailability="Not selected";
	private String gerne="���þ���";
	
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
		cb_rental_ability.setValue("���þ���");
		rentalAvailability="���þ���";
		cb_rental_ability.setItems(tf_rentalAvailable_list);
		cb_gerne.setValue("���þ���");
		gerne="���þ���";
		cb_gerne.setItems(tf_gerne_list);
		
		new SearchBookReceiveThread(socket).start();
		
		cb_rental_ability.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { //���� ���� !!!
			 
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
            	rentalAvailability=tf_rentalAvailable_list.get(new_value.intValue()).toString();
            }
        }); 
		
		cb_gerne.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { //���� ���� !!!
			 
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
			ArrayList<String> ms=new ArrayList<>();//0-����, 1-�۰�, 2-���ǻ�, 3-�帣, 4-�뿩���ɼ�

				if(tf_title.getText().length()!=0) ms.add("Title-"+tf_title.getText());
				if(tf_auther.getText().length()!=0) ms.add("Auther-"+tf_auther.getText());
				if(tf_publisher.getText().length()!=0) ms.add("Publisher-"+tf_publisher.getText());
				if(!gerne.equals("���þ���")) ms.add("Gerne-"+gerne);//���߿� �ٲ�� ��
				if(!rentalAvailability.equals("���þ���")) {
					if(rentalAvailability.equals("����")) {
						ms.add("RentalAvailability-1");
					}else {
						ms.add("RentalAvailability-0");//�Ұ���
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

	        SearchBookReceiveThread(Socket socket){//������
	            this.socket = socket;
	        }
	        @Override
	        public void run() {//start �ϸ� �ڵ����� �����.
	        	BufferedReader br=null;
	            try {
	                br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
	                //socket�� ����� �Է½�Ʈ���� br�� ����. ���� �̷�����?
	                while(true) {  	
	                	
	                    String msg = br.readLine();
	                    if(msg.equals("null")) {
	                    	ta_result.appendText("ã���ô� ������ å�� �����ϴ�.");
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
