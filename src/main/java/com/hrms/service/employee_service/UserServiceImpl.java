package com.hrms.service.employee_service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hrms.entity.User;
import com.hrms.repository.employee_repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
}
