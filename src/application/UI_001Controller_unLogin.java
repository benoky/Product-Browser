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

//�ش� �ҽ����ϰ� UI_001Dumy������ ������ ��ɵ��� ���� �ӽ� UI�� ���߿� ���� ����
public class UI_001Controller_unLogin implements Initializable{
	// �α��� ���� ��ü
	@FXML private Button signup_btn;
	@FXML private Button login_btn;
	@FXML private TextField login_id;
	@FXML private PasswordField login_pw;
	
	@FXML private Button searchBtn =new Button();
	@FXML private TextField searchWordField=new TextField();
	
	static String searchWord; //�˻��� �Է� �� �˻�� �����Ͽ� �ٸ� UI Controller���� ������ ��
	
	@FXML private Button freeDeliveryBtn=new Button();
	
	//--------------------- ȸ������ â ---------------------
	public void handleUI_001SignUpBtnAction(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SIGNUP_001.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();

			stage.setTitle("ȸ������");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//--------------------- �α��� â ---------------------
	public void handleUI_001LoginBtnAction(ActionEvent event) {
		String uId = login_id.getText(); // ���̵� �ʵ忡 �Էµ� �� ��������
		String uPw = login_pw.getText(); // �н����� �ʵ忡 �Էµ� �� ��������
		
		boolean result = LoginDAO.login(uId, uPw);
		
		if (result == true) {
			try {
				// �α��� ���� �� ����ȭ�� �ε�
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
			alert.setTitle("�α��� ����");
			alert.setContentText("���̵� Ȥ�� �н����带 Ȯ�����ּ���.");
			alert.showAndWait();
		}
		
	}
	
	//'�˻�' ��ư �Է½� UI_002�� �ҷ����� ���� ���� �ڵ鷯
	public void handleUI_002BtnAction(ActionEvent event) {
		try {
			searchWord=searchWordField.getText(); //searchWordField�� �Էµ� �˻�� static���� ����� �ʵ忡 �����Ͽ� �ٸ� Ŭ�������� ����ϰ� ��

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