package org.example.services;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }
    public void deleteById(long id){
        userRepository.deleteById(id);
    }
}
