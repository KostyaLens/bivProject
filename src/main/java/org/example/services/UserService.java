package org.example.services;

import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exception.NotFoundUserException;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    public void update(User user){
        userRepository.save(user);
    }


    public User getByUsername(String username) throws NotFoundUserException {
        return userRepository.findByUsername(username).orElseThrow(() ->  new NotFoundUserException("Пользователь не найден"));
    }
}
