package com.greking.Greking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;

@SpringBootTest
class GrekingApplicationTests {

	private final String URL = "jdbc:postgresql://localhost:5432/Greking";
	private String USERNAME = "minjun"; //postgresql 계정
	private String PASSWORD; //비밀번호

	@Test
	public void ConnectionTest() throws Exception {
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD); // DB 연결
			System.out.println(con); // 연결 정보 출력
			Statement pre = con.createStatement();
			ResultSet rs = pre.executeQuery("SELECT * FROM user_entity");

			while (rs.next()) {
				System.out.println(rs.getString("user_id")); // user_id 컬럼 확인
			}
		} catch (SQLException e) {
			e.printStackTrace(); // SQLException 출력
		} finally {
			if (con != null) {
				con.close(); // 연결 종료
			}
		}
	}


}
