package com.appstore.api.dao;

import java.util.List;

import com.appstore.entity.User;

public interface UserDAO {
	public void saveUser(User User);
	public List<User> listUser();
	public User getUser(String userName);
	public User getUser(int userID);
}
