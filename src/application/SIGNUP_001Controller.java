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
	// 아이디 중복 확인
	void CHECK_ID_EVENT(ActionEvent event) {

		boolean id_match = Pattern.matches("^[0-9a-z]*$", uId); // 아이디가 숫자, 영어 소문자로만 이루어지면 true, 아니면 false
		boolean result = LoginDAO.idCompare(uId);

		// 아이디 빈칸 체크
		if (uId == null || uId.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" ");
			alert.setContentText("아이디를 입력해주세요.");
			alert.showAndWait();

			check_id = false;
		}

		// 아이디 형식 체크
		else if (id_match == false) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" ");
			alert.setContentText("영어 대문자, 한글, 특수문자는 아이디로 사용할 수 없습니다.");
			alert.showAndWait();

			check_id = false;
		}

		// 중복되는 아이디라면
		else if (result == true) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" ");
			alert.setContentText("중복되는 아이디가 존재합니다.");
			alert.showAndWait();

			check_id = false;
		}
		
		// 중복되는 아이디가 아니라면
		else if (result == false) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(" ");
			alert.setContentText("사용 가능한 아이디 입니다.");
			alert.showAndWait();

			check_id = true; // 버튼 클릭 여부 확인용.
		}

	}

	@FXML
	// DB에 저장
	void SIGN_UP_END_EVENT(ActionEvent event) {
		LoginDAO.SignUp(uId, uPw, uName, uMail);
		
		// 가입성공 메세지
		Alert welcomealert = new Alert(AlertType.INFORMATION);
		welcomealert.setTitle("회원가입 완료");
		welcomealert.setContentText("가입을 축하드립니다!");
		welcomealert.showAndWait();

		// 회원가입 창 종료
		Stage stage = (Stage) SIGNUP_btn.getScene().getWindow();
		stage.close();

	}

}
