package domain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.entity.User;
import domain.jdbc.DBConnectionMgr;

public class UserDaoImpl implements UserDao {
	
	private DBConnectionMgr pool;
	
	private String sql;
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDaoImpl() {
		pool = DBConnectionMgr.getInstance();
	}

	@Override
	public int save(User user) throws Exception {
		int result  = 0;
		
		sql = "INSERT INTO\r\n"
				+ "	user_mst\r\n"
				+ "VALUES(\r\n"
				+ "	0,\r\n"
				+ "	?,\r\n"
				+ "	?,\r\n"
				+ "	?,\r\n"
				+ "	?,\r\n"
				+ "	?,\r\n"
				+ "	?,\r\n"
				+ "	NOW(),\r\n"
				+ "	NOW()\r\n"
				+ ");";
		con = pool.getConnection(); // 연결
		try { // 연결된 후에 예외가 발생하면 프로그램이 꺼지기 때문에 예외처리를 걸어줌
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getName()); //비어있는 쿼리값을 완성시켜줌  ?는 1부터 시작 
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getUsername());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getRoles());
			pstmt.setString(6, user.getProvider());
			result = pstmt.executeUpdate(); // 쿼리실행   성공횟수 리턴
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt); // 연결을 끊어줌
		}
		
		return result;
	}

	@Override
	public User findUserByUsername(String username) throws Exception {
		return null;
	}

	@Override
	public int modify(int user_code) throws Exception {
		return 0;
	}

	@Override
	public int remove(int user_code) throws Exception {
		return 0;
	}
	
}
