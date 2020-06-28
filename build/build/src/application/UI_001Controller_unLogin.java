package application;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//�ش� �ҽ����ϰ� UI_001Dumy������ ������ ��ɵ��� ���� �ӽ� UI�� ���߿� ���� ����
public class UI_001Controller_unLogin implements Initializable{
	@FXML private TextField searchWordField=new TextField(); //�˻��� �Է��ϴ� �ʵ�
	
	@FXML private Button searchBtn =new Button();
	
	@FXML private Button mainBtn=new Button();
	@FXML private Button nomalSearchBtn=new Button();
	@FXML private Button basketBtn=new Button();
	@FXML private Button freeDeliveryBtn=new Button();
	
	static String searchWord; //�˻��� �Է� �� �˻�� �����Ͽ� �ٸ� UI Controller���� ������ ��
	
	//'�˻�' ��ư �Է½� UI_002�� �ҷ����� ���� ���� �ڵ鷯
	public void handlesearchBtnAction(ActionEvent event) {
		try {
			searchWord=searchWordField.getText(); //searchWordField�� �Էµ� �˻�� static���� ����� �ʵ忡 �����Ͽ� �ٸ� Ŭ�������� ����ϰ� ��

			//Parent UI_002 = FXMLLoader.load(getClass().getResource("UI_002_unLogin.fxml"));
			Parent UI_002 = FXMLLoader.load(Class.forName("application.UI_002Controller_unLogin").getResource("UI_002_unLogin.fxml"));
			Scene scene=new Scene(UI_002);
			Stage primaryStage=(Stage)searchBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//------------------------���� ��ư���� ���� �ڵ鷯---------------------//
	//'����'��ư ���ý� UI_005�� �ҷ����� ���� ���� �ڵ鷯
	public void handleMainBtnBtnAction(ActionEvent event) {
		try {
			Parent UI_001 = FXMLLoader.load(Class.forName("application.UI_001Controller_unLogin").getResource("UI_001_unLogin.fxml"));
			Scene scene=new Scene(UI_001);
			Stage primaryStage=(Stage)freeDeliveryBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//'�Ϲݰ˻�'��ư ���ý� UI_002�� �ҷ����� ���� ���� �ڵ鷯
	public void handleNomalSearchBtnAction(ActionEvent event) {
		try {
			Parent UI_002 = FXMLLoader.load(Class.forName("application.UI_002Controller_unLogin").getResource("UI_002_unLogin.fxml"));
			Scene scene=new Scene(UI_002);
			Stage primaryStage=(Stage)freeDeliveryBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//'������'��ư ���ý� UI_005�� �ҷ����� ���� ���� �ڵ鷯
	public void handlefreeDeliveryBtnAction(ActionEvent event) {
		try {
			Parent UI_005 = FXMLLoader.load(Class.forName("application.UI_005Controller_unLogin").getResource("UI_005_unLogin.fxml"));
			Scene scene=new Scene(UI_005);
			Stage primaryStage=(Stage)freeDeliveryBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//'�� ���' ��ư ���� �� UI_004�� �ҷ����� ���� ���� �ڵ鷯
	public void handlebasketBtnAction(ActionEvent event) {
		try {
			Parent UI_004 = FXMLLoader.load(Class.forName("application.UI_004Controller_unLogin").getResource("UI_004_unLogin.fxml"));
			Scene scene=new Scene(UI_004);
			Stage primaryStage=(Stage)basketBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handlesearchBtnAction(event);
			}
		});
		//-----------������ ȭ�� �̵� ��ư��-------------//
		mainBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleMainBtnBtnAction(event);
			}
		});
		
		nomalSearchBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleNomalSearchBtnAction(event);
			}
		});
		
		freeDeliveryBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handlefreeDeliveryBtnAction(event);
			}
		});
		
		basketBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handlebasketBtnAction(event);
			}
		});
	}
}