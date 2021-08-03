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

		// 다음의 정보들은 최초 DB 커넥션을 확립할때만 필요하므로 지역변수로 선언하여 사용.
		String DBMS_IP = "xxx.xxx.xxx.xxx";
		String DBMS_PORT = "1521";
		String DB_NAME = "xxx";
		String DB_USERID = "xxx";
		String DB_PASSWORD = "xxx";
		int timeOut = 1000;

		try {
			String JDBC_DRIVER = "jdbc:oracle:thin:@" + DBMS_IP + ":" + DBMS_PORT + ":" + DB_NAME; // 드라이버 설정
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Connection 객체, Statement 객체 생성 성공시에만 DB종류에 따른 SQL문 처리객체 생성
			if (DBConnect(JDBC_DRIVER, DB_USERID, DB_PASSWORD, "SUMMARY_DATA")) {
				pstmt.setQueryTimeout(timeOut); // 타임아웃 설정
				StringBuffer sb = new StringBuffer();
				String tmpData = loadDocument("summary.txt");
				sb.append(tmpData);
				tmpData = sb.toString();
				System.out.println(tmpData.length());
				// 쿼리문 실행 후 영향을 받은 레코드수 반환
				System.out.println(runQuery(tmpData));

				DBClose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DBMS와의 커넥션 확립
	 * 
	 * @param JDBC_DRIVER
	 * @param DB_USERID   사용자 아이디
	 * @param DB_PASSWORD 사용자 비밀번호
	 * @return 성공 여부 반환
	 */
	public static boolean DBConnect(String JDBC_DRIVER, String DB_USERID, String DB_PASSWORD, String colName) {
		try {
			conn = DriverManager.getConnection(JDBC_DRIVER, DB_USERID, DB_PASSWORD);
			if (conn == null) // 정상적으로 객체 생성이 안되었을 경우 처리
				return false;
			pstmt = conn.prepareStatement("UPDATE BIG_DATA SET " + colName + "=?");
			if (pstmt == null) // 정상적으로 객체 생성이 안되었을 경우 처리
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
	 * DB접속을 종료하는 메소드
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
	 * 쿼리 실행 후 결과객체 생성
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

	public static String loadDocument(String filepath) // 텍스트 파일의 내용을 읽어와서 StringBuffer에 담고, 정규표현식등으로 문자열 치환 후 String
														// 객체로 반환
	{
		InputStream is = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			File dataFile = new File(filepath);
			is = new FileInputStream(dataFile);
			try {
				// 파일을 오픈할 때 "EUC-KR"로 오픈하도록 설정, 문자셋을 지정하지 않으면 한글이 깨지는 경우가 발생한다.
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
				sb.append(tmp.replaceAll("[\']", "\\'").replaceAll("[\"]", "\\\"")); // DB 삽입시에 문제가 없도록 처리
				// System.out.println(tmp.replaceAll("[\'\"]", "")); // 아예 없애려고 한다면
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
		// return sb.toString().replaceAll("<[^>]*>", ""); // HTML태그 제거하기
		return sb.toString();
	}

}
