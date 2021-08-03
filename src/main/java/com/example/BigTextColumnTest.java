package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BigTextColumnTest {
	public static Connection conn = null;
	public static PreparedStatement pstmt = null;

	/*
	 CREATE TABLE "BIG_DATA"
  	(
    	"IDX"        NUMBER(10,0),
    	"TITLE_DATA" VARCHAR2(4000 BYTE),
    	"SUMMARY_DATA" LONG,
    	"HTML_BODY" CLOB,
		CONSTRAINT PK_BIG_DATA PRIMARY KEY(IDX)
  	);

	CREATE SEQUENCE SEQ_BIG_DATA START WITH 1 INCREMENT BY 1 MAXVALUE 99999 CYCLE NOCACHE;
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// ������ �������� ���� DB Ŀ�ؼ��� Ȯ���Ҷ��� �ʿ��ϹǷ� ���������� �����Ͽ� ���.
		String DBMS_IP = "xxx.xxx.xxx.xxx";
		String DBMS_PORT = "1521";
		String DB_NAME = "xxx";
		String DB_USERID = "xxx";
		String DB_PASSWORD = "xxx";
		int timeOut = 1000;

		try {
			String JDBC_DRIVER = "jdbc:oracle:thin:@" + DBMS_IP + ":" + DBMS_PORT + ":" + DB_NAME; // ����̹� ����
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Connection ��ü, Statement ��ü ���� �����ÿ��� DB������ ���� SQL�� ó����ü ����
			if (DBConnect(JDBC_DRIVER, DB_USERID, DB_PASSWORD, "SUMMARY_DATA")) {
				pstmt.setQueryTimeout(timeOut); // Ÿ�Ӿƿ� ����
				StringBuffer sb = new StringBuffer();
				String tmpData = loadDocument("summary.txt");
				sb.append(tmpData);
				tmpData = sb.toString();
				System.out.println(tmpData.length());
				// ������ ���� �� ������ ���� ���ڵ�� ��ȯ
				System.out.println(runQuery(tmpData));

				DBClose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DBMS���� Ŀ�ؼ� Ȯ��
	 * 
	 * @param JDBC_DRIVER
	 * @param DB_USERID   ����� ���̵�
	 * @param DB_PASSWORD ����� ��й�ȣ
	 * @return ���� ���� ��ȯ
	 */
	public static boolean DBConnect(String JDBC_DRIVER, String DB_USERID, String DB_PASSWORD, String colName) {
		try {
			conn = DriverManager.getConnection(JDBC_DRIVER, DB_USERID, DB_PASSWORD);
			if (conn == null) // ���������� ��ü ������ �ȵǾ��� ��� ó��
				return false;
			pstmt = conn.prepareStatement("UPDATE BIG_DATA SET " + colName + "=?");
			if (pstmt == null) // ���������� ��ü ������ �ȵǾ��� ��� ó��
			{
				if (conn != null)
					conn.close();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * DB������ �����ϴ� �޼ҵ�
	 */
	public static void DBClose() {
		try {
			if (pstmt != null)
				pstmt.close();

			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���� ���� �� �����ü ����
	 * 
	 * @param sql
	 * @return
	 */
	public static int runQuery(String value) {
		int affecteRows = 0;
		try {
			pstmt.setString(1, value);
			affecteRows = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return affecteRows;
	}

	public static String loadDocument(String filepath) // �ؽ�Ʈ ������ ������ �о�ͼ� StringBuffer�� ���, ����ǥ���ĵ����� ���ڿ� ġȯ �� String
														// ��ü�� ��ȯ
	{
		InputStream is = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			File dataFile = new File(filepath);
			is = new FileInputStream(dataFile);
			try {
				// ������ ������ �� "EUC-KR"�� �����ϵ��� ����, ���ڼ��� �������� ������ �ѱ��� ������ ��찡 �߻��Ѵ�.
				br = new BufferedReader(new InputStreamReader(is, "EUC-KR"));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			char[] buffer = new char[2048];
			int read = -1;
			while ((read = br.read(buffer, 0, 1024)) != -1) {
				String tmp = new String(buffer);
				// System.out.println(tmp);
				sb.append(tmp.replaceAll("[\']", "\\'").replaceAll("[\"]", "\\\"")); // DB ���Խÿ� ������ ������ ó��
				// System.out.println(tmp.replaceAll("[\'\"]", "")); // �ƿ� ���ַ��� �Ѵٸ�
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// return sb.toString().replaceAll("<[^>]*>", ""); // HTML�±� �����ϱ�
		return sb.toString();
	}

}
