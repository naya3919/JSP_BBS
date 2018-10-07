package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//jsp에서 회원 데이터베이스 테이블에 접근할수있도록 DAO를 생성
	//DAO는 데이터베이스 접근객체의 약자
	//여기선 회원정보를 불러오거나 데이터베이스에 회원정보를 넣고자할때 사용
public class UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS?serverTimezone=UTC&useSSL=false";
			String dbID = "root";
			String dbPassword ="hongjo8";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT UserPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword))
					return 1; //로그인 성공
				else
					return 0; // 암호불일치
			}
			return -1; //아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 데이터베이스오류
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES(?, ?, ?, ? ,?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserGender());
			pstmt.setString(4, user.getUserEmail());
			pstmt.setString(5, user.getUserName());
			int tmp = pstmt.executeUpdate();
			System.out.println(tmp);
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스오류
	}

	
}
