package com.tianlb.myTest.service;

import com.tianlb.myTest.model.User;
import com.tianlb.myTest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User geyUserById(Long id) {
        return userRepository.findFirstById(id);
    }
}
