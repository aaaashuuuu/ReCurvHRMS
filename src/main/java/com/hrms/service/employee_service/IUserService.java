package com.hrms.service.employee_service;

import java.util.List;

import com.hrms.entity.User;

public interface IUserService {
	public List<User> getAllUsers();
	public User getUserById(Integer id);
	public User saveUser(User user);
	public void deleteUserById(Integer id);
}
