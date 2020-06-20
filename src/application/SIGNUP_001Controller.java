package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import server.LoginDAO;

public class SIGNUP_001Controller implements Initializable {

	@FXML private TextField signUp_id;
	@FXML private PasswordField signUp_pw;
	@FXML private TextField signUp_name;
	@FXML private TextField signUp_email;
	@FXML private Button checkID_btn;
	@FXML private Button SIGNUP_btn;

	boolean check_id = false;

	String uId = signUp_id.getText();
	String uPw = signUp_pw.getText();
	String uName = signUp_name.getText();
	String uMail = signUp_email.getText();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkID_btn.setOnAction(e -> CHECK_ID_EVENT(e));
		SIGNUP_btn.setOnAction(e -> SIGN_UP_END_EVENT(e));
	}

	@FXML
	// ���̵� �ߺ� Ȯ��
	void CHECK_ID_EVENT(ActionEvent event) {

		boolean id_match = Pattern.matches("^[0-9a-z]*$", uId); // ���̵� ����, ���� �ҹ��ڷθ� �̷������ true, �ƴϸ� false
		boolean result = LoginDAO.idCompare(uId);

		// ���̵� ��ĭ üũ
		if (uId == null || uId.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" ");
			alert.setContentText("���̵� �Է����ּ���.");
			alert.showAndWait();

			check_id = false;
		}

		// ���̵� ���� üũ
		else if (id_match == false) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" ");
			alert.setContentText("���� �빮��, �ѱ�, Ư�����ڴ� ���̵�� ����� �� �����ϴ�.");
			alert.showAndWait();

			check_id = false;
		}

		// �ߺ��Ǵ� ���̵���
		else if (result == true) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" ");
			alert.setContentText("�ߺ��Ǵ� ���̵� �����մϴ�.");
			alert.showAndWait();

			check_id = false;
		}
		
		// �ߺ��Ǵ� ���̵� �ƴ϶��
		else if (result == false) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(" ");
			alert.setContentText("��� ������ ���̵� �Դϴ�.");
			alert.showAndWait();

			check_id = true; // ��ư Ŭ�� ���� Ȯ�ο�.
		}

	}

	@FXML
	// DB�� ����
	void SIGN_UP_END_EVENT(ActionEvent event) {
		LoginDAO.SignUp(uId, uPw, uName, uMail);
		
		// ���Լ��� �޼���
		Alert welcomealert = new Alert(AlertType.INFORMATION);
		welcomealert.setTitle("ȸ������ �Ϸ�");
		welcomealert.setContentText("������ ���ϵ帳�ϴ�!");
		welcomealert.showAndWait();

		// ȸ������ â ����
		Stage stage = (Stage) SIGNUP_btn.getScene().getWindow();
		stage.close();

	}

}
