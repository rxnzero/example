package com.example.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public class HibernateUserDao implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from User").getResultList();
	}

	public User findById(Long id) {
		return sessionFactory.getCurrentSession().find(User.class, id);
	}

	public void save(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	public void delete(Long id) {
		sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().getReference(User.class, id));
	}

}
