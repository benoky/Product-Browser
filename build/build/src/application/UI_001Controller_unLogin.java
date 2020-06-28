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

//해당 소스파일과 UI_001Dumy파일은 나머지 기능들을 위한 임시 UI임 나중에 삭제 예정
public class UI_001Controller_unLogin implements Initializable{
	@FXML private TextField searchWordField=new TextField(); //검색어 입력하는 필드
	
	@FXML private Button searchBtn =new Button();
	
	@FXML private Button mainBtn=new Button();
	@FXML private Button nomalSearchBtn=new Button();
	@FXML private Button basketBtn=new Button();
	@FXML private Button freeDeliveryBtn=new Button();
	
	static String searchWord; //검색어 입력 시 검색어를 저장하여 다른 UI Controller에서 사용허게 함
	
	//'검색' 버튼 입력시 UI_002를 불러오기 위한 동작 핸들러
	public void handlesearchBtnAction(ActionEvent event) {
		try {
			searchWord=searchWordField.getText(); //searchWordField에 입력된 검색어를 static으로 선언된 필드에 저장하여 다른 클래스에서 사용하게 함

			//Parent UI_002 = FXMLLoader.load(getClass().getResource("UI_002_unLogin.fxml"));
			Parent UI_002 = FXMLLoader.load(Class.forName("application.UI_002Controller_unLogin").getResource("UI_002_unLogin.fxml"));
			Scene scene=new Scene(UI_002);
			Stage primaryStage=(Stage)searchBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//------------------------좌측 버튼들의 동작 핸들러---------------------//
	//'메인'버튼 선택시 UI_005를 불러오기 위한 동작 핸들러
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
	
	//'일반검색'버튼 선택시 UI_002를 불러오기 위한 동작 핸들러
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
	
	//'무료배송'버튼 선택시 UI_005를 불러오기 위한 동작 핸들러
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
	
	//'찜 목록' 버튼 선택 시 UI_004를 불러오기 위한 동작 핸들러
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
		//-----------좌측의 화면 이동 버튼들-------------//
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