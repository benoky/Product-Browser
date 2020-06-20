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

//�ش� �ҽ����ϰ� UI_001Dumy������ ������ ��ɵ��� ���� �ӽ� UI�� ���߿� ���� ����
public class UI_001Controller_Login implements Initializable {
	@FXML private Button searchBtn = new Button();
	@FXML private Button logout_btn = new Button();
	@FXML private TextField searchWordField = new TextField();

	static String searchWord; // �˻��� �Է� �� �˻�� �����Ͽ� �ٸ� UI Controller���� ������ ��

	@FXML private Button freeDeliveryBtn = new Button();

	// �α׾ƿ� ��ư Ŭ�� �� �ʱ�ȭ������
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
		
	// '�˻�' ��ư �Է½� UI_002�� �ҷ����� ���� ���� �ڵ鷯
	public void handleUI_002BtnAction(ActionEvent event) {
		try {
			searchWord = searchWordField.getText(); // searchWordField�� �Էµ� �˻�� static���� ����� �ʵ忡 �����Ͽ� �ٸ� Ŭ�������� ����ϰ� ��

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
		
		// �α׾ƿ� ��ư Ŭ�� �� �ڵ鷯 ȣ��
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
