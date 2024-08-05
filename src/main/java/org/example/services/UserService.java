package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exception.NotFoundUserOrAccountException;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
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

    public Page<User> getAllUsers(int page, int count, String sortingField, String sortingDirection){
        if (sortingDirection.equals("descending")){
            return userRepository.findAll(PageRequest.of(page, count, Sort.by(sortingField).descending()));
        }
        return userRepository.findAll(PageRequest.of(page, count, Sort.by(sortingField).ascending()));
    }
    public User getByUsername(String username) throws NotFoundUserOrAccountException {
        return userRepository.findByUsername(username).orElseThrow(() ->  new NotFoundUserOrAccountException("Пользователь не найден"));
    }
}
