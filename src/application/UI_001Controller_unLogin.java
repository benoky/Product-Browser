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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import server.LoginDAO;

//해당 소스파일과 UI_001Dumy파일은 나머지 기능들을 위한 임시 UI임 나중에 삭제 예정
public class UI_001Controller_unLogin implements Initializable{
	// 로그인 관련 객체
	@FXML private Button signup_btn;
	@FXML private Button login_btn;
	@FXML private TextField login_id;
	@FXML private PasswordField login_pw;
	
	@FXML private Button searchBtn =new Button();
	@FXML private TextField searchWordField=new TextField();
	
	static String searchWord; //검색어 입력 시 검색어를 저장하여 다른 UI Controller에서 사용허게 함
	
	@FXML private Button freeDeliveryBtn=new Button();
	
	//--------------------- 회원가입 창 ---------------------
	public void handleUI_001SignUpBtnAction(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SIGNUP_001.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();

			stage.setTitle("회원가입");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//--------------------- 로그인 창 ---------------------
	public void handleUI_001LoginBtnAction(ActionEvent event) {
		String uId = login_id.getText(); // 아이디 필드에 입력된 값 가져오기
		String uPw = login_pw.getText(); // 패스워드 필드에 입력된 값 가져오기
		
		boolean result = LoginDAO.login(uId, uPw);
		
		if (result == true) {
			try {
				// 로그인 성공 후 메인화면 로드
				Parent members = FXMLLoader.load(getClass().getResource("UI_001_Login.fxml"));
				Scene scene = new Scene(members);
				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setContentText("아이디 혹은 패스워드를 확인해주세요.");
			alert.showAndWait();
		}
		
	}
	
	//'검색' 버튼 입력시 UI_002를 불러오기 위한 동작 핸들러
	public void handleUI_002BtnAction(ActionEvent event) {
		try {
			searchWord=searchWordField.getText(); //searchWordField에 입력된 검색어를 static으로 선언된 필드에 저장하여 다른 클래스에서 사용하게 함

			Parent UI_002 = FXMLLoader.load(getClass().getResource("UI_002_unLogin.fxml"));
			Scene scene=new Scene(UI_002);
			Stage primaryStage=(Stage)searchBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void handleUI_005BtnAction(ActionEvent event) {
		try {
			Parent UI_005 = FXMLLoader.load(getClass().getResource("UI_005_unLogin.fxml"));
			Scene scene=new Scene(UI_005);
			Stage primaryStage=(Stage)freeDeliveryBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
	
		signup_btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_001SignUpBtnAction(event);
			}
		});
		
		login_btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_001LoginBtnAction(event);
			}
		});
		
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_002BtnAction(event);
			}
		});
		
		freeDeliveryBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_005BtnAction(event);
			}
		});	
	}
}