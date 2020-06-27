package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

//	-----------------------------------------회원가입-------------------------------------------

	public static void SignUp(String uId, String uPw, String uName, String uMail) {
//		DB 접속
		Connection conn = DBConnection.getMySQLConnection();

		try {
//			DB에 입력값 저장			
			String sql = "INSERT INTO member_tb(id, pw, name, email) VALUES(?,?,?,?)";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, uId);
			pstmt.setString(2, uPw);
			pstmt.setString(3, uName);
			pstmt.setString(4, uMail);
			pstmt.executeUpdate();

			DBConnection.close(conn);
			DBConnection.close(pstmt);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	--------------------------------------아이디 중복 확인--------------------------------------

	public static boolean idCompare(String id) {
		boolean result = false;
		Connection conn = DBConnection.getMySQLConnection();

		try {
			String sql = "select * from member_tb";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.next()) {
					do {
//						입력된 id와 서버의 아이디중 중복이 있다면  true
						if (rs.getString("id").equals(id)) {
							result = true;
							break;
						}
					} while (rs.next());
				}
			}

			DBConnection.close(conn);
			DBConnection.close(pstmt);
			DBConnection.close(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

//	------------------------------------------로그인--------------------------------------------

	public static boolean login(String uId, String uPw) {

		boolean LoginResult = false;
		Connection conn = DBConnection.getMySQLConnection();
		try {
//			서버에 입력된 id와 같은 id를 검색
			String sql = "select id,pw from member_tb where id = ? and pw = ?"; // mysql에서 테이블 조회
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setString(2, uPw);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				// DB 데이터와 입력값 비교
				if (rs.getString("id").equals(uId) && rs.getString("pw").equals(uPw)) {
					try {
						LoginResult = true;

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

			DBConnection.close(conn);
			DBConnection.close(pstmt);
			DBConnection.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return LoginResult;
	}

}
