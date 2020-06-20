package application;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//해당 소스파일과 UI_001Dumy파일은 나머지 기능들을 위한 임시 UI임 나중에 삭제 예정
public class UI_001Controller_Login implements Initializable {
	@FXML private Button searchBtn = new Button();
	@FXML private Button logout_btn = new Button();
	@FXML private TextField searchWordField = new TextField();

	static String searchWord; // 검색어 입력 시 검색어를 저장하여 다른 UI Controller에서 사용허게 함

	@FXML private Button freeDeliveryBtn = new Button();

	// 로그아웃 버튼 클릭 시 초기화면으로
	public void handleUI_001unLoginBtnAction(ActionEvent event) {
		try {
			Parent members = FXMLLoader.load(getClass().getResource("UI_001_unLogin.fxml"));
			Scene scene = new Scene(members);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	// '검색' 버튼 입력시 UI_002를 불러오기 위한 동작 핸들러
	public void handleUI_002BtnAction(ActionEvent event) {
		try {
			searchWord = searchWordField.getText(); // searchWordField에 입력된 검색어를 static으로 선언된 필드에 저장하여 다른 클래스에서 사용하게 함

			Parent UI_002 = FXMLLoader.load(getClass().getResource("UI_002_Login.fxml"));
			Scene scene = new Scene(UI_002);
			Stage primaryStage = (Stage) searchBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleUI_005BtnAction(ActionEvent event) {
		try {
			Parent UI_005 = FXMLLoader.load(getClass().getResource("UI_005_Login.fxml"));
			Scene scene = new Scene(UI_005);
			Stage primaryStage = (Stage) freeDeliveryBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_002BtnAction(event);
			}
		});
		
		// 로그아웃 버튼 클릭 시 핸들러 호출
		logout_btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_001unLoginBtnAction(event);
			}
		});

		freeDeliveryBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_005BtnAction(event);
			}
		});
	}
}
