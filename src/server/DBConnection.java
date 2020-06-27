package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
//	MySQL�� �����ϴ� �޼ҵ�
	public static Connection getMySQLConnection() {

		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			?useUnicode=true&characterEncoding=UTF-8�� �������� ������ �ѱ��̸��"?"�� ����ȴ�.
			String url = "jdbc:mysql://localhost:3306/member_db?characterEncoding=UTF-8&serverTimezone=UTC"; // ���� URL
			String strUser = "root"; // ���� id
			String strPW = "2431"; // ���� �н�����
			conn = DriverManager.getConnection(url, strUser, strPW);
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� Ŭ������ �ε��� �� �����ϴ�.");
		} catch (SQLException e) {
			System.out.println("���� ������ �ùٸ��� �ʽ��ϴ�.");
		}
		return conn;
	}

//	�����ͺ��̽� �۾��� ����� ���� ������ �ݾ��ִ� �޼ҵ�
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
