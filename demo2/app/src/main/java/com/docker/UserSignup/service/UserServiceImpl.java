package com.docker.UserSignup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import com.docker.UserSignup.model.User;
import com.docker.UserSignup.repository.UserRepository;
import com.docker.UserSignup.util.Rot13;

@Service("userService")
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}

	public boolean findByLogin(String userName, String password) {	
		User usr = userRepository.findByUserName(userName);

		final String passwd = Rot13.rot13(password);

		if(usr != null && usr.getPassword().equals(passwd)) {
			return true;
		} 
		
		return false;		
	}

	public boolean findByUserName(String userName) {
		User usr = userRepository.findByUserName(userName);
		
		if(usr != null) {
			return true;
		}
		
		return false;
	}

}
