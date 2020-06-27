package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

//	-----------------------------------------ȸ������-------------------------------------------

	public static void SignUp(String uId, String uPw, String uName, String uMail) {
//		DB ����
		Connection conn = DBConnection.getMySQLConnection();

		try {
//			DB�� �Է°� ����			
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

//	--------------------------------------���̵� �ߺ� Ȯ��--------------------------------------

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
//						�Էµ� id�� ������ ���̵��� �ߺ��� �ִٸ�  true
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

//	------------------------------------------�α���--------------------------------------------

	public static boolean login(String uId, String uPw) {

		boolean LoginResult = false;
		Connection conn = DBConnection.getMySQLConnection();
		try {
//			������ �Էµ� id�� ���� id�� �˻�
			String sql = "select id,pw from member_tb where id = ? and pw = ?"; // mysql���� ���̺� ��ȸ
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setString(2, uPw);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				// DB �����Ϳ� �Է°� ��
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
