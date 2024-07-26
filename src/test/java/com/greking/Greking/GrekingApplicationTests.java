package com.greking.Greking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootTest
class GrekingApplicationTests {

	private String URL = "jdbc:postgresql://localhost:5432/Greking";
	private String USERNAME = "minjun"; //postgresql 계정
	private String PASSWORD; //비밀번호

	@Test
	public void ConnectionTest() throws Exception{
		Connection con = DriverManager.getConnection(URL,USERNAME, PASSWORD); //db 연결
		System.out.println(con); //연결 정보 출력
		Statement pre = con.createStatement();
		ResultSet rs = pre.executeQuery("select * from user_info");

		if (rs.next()) {
			System.out.println(rs);
			System.out.println(rs.getString("user_id"));
		}
	}

}
