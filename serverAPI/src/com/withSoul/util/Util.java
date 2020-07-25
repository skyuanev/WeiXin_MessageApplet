package com.withSoul.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
	//jdbc:mysql://����д����������� IP ��ַ�Ͷ˿ں�/���ݿ���?useUnicode=true&characterEncoding=UTF-8
	private static final String URL = "";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String USER = "";// ���ݿ��û���
	private static final String PASS = "";// ���ݿ�����
	private static Connection conn = null;

	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(URL, USER, PASS);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
