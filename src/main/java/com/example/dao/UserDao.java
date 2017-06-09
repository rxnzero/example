package com.example.dao;

import java.util.List;

import com.example.model.User;

public interface UserDao {
	public List<User> findAll();
	public User findById(Long id);
	public void save(User user);
	public void update(User user);
	public void delete(Long id);
}
