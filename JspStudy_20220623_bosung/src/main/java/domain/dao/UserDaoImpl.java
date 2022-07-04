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
		User user = null;
		
		sql = "SELECT\r\n"
				+ "	um.user_code,\r\n"
				+ "	um.name,\r\n"
				+ "	um.email,\r\n"
				+ "	um.username,\r\n"
				+ "	um.password,\r\n"
				+ "	um.roles,\r\n"
				+ "	um.provider,\r\n"
				+ "	ud.user_profile_img,\r\n"
				+ "	ud.user_address,\r\n"
				+ "	ud.user_phone,\r\n"
				+ "	ud.user_gender\r\n"
				+ "FROM\r\n"
				+ "	user_mst um\r\n"
				+ "	LEFT OUTER JOIN user_dtl ud ON(ud.user_code = um.user_code)\r\n"
				+ "WHERE\r\n"
				+ "	um.username = ?;";
		
		con = pool.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = User.builder()
						.user_code(rs.getInt(1))
						.name(rs.getString(2))
						.email(rs.getString(3))
						.username(rs.getString(4))
						.password(rs.getString(5))
						.roles(rs.getString(6))
						.provider(rs.getString(7))
						.user_profile_img(rs.getString(8))
						.user_address(rs.getString(9))
						.user_phone(rs.getString(10))
						.user_gender(rs.getInt(11))
						.build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return user;
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
