package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.UserDao;
import com.example.model.User;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	public User findById(Long id) {
		return userDao.findById(id);
	}
	
	public void save(User user) {
		userDao.save(user);
	}
	
	public void update(User user) {
		userDao.update(user);
	}
	
	public void delete(Long id) {
		userDao.delete(id);
	}
}
