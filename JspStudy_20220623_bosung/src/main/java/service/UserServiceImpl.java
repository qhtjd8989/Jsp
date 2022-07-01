package service;

import domain.dao.UserDao;
import domain.dao.UserDaoImpl;
import web.dto.SignupReqDto;

public class UserServiceImpl implements UserService {
	
	private final UserDao userDao;
	
	public UserServiceImpl() {
		userDao = new UserDaoImpl();
	}

	@Override
	public boolean createUser(SignupReqDto signupReqDto) throws Exception {
		return userDao.save(signupReqDto.toEntity()) > 0;
	}

	@Override
	public void getUser() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updateUser() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
