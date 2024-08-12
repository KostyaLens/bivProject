package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exception.NotFoundUserOrAccountException;
import org.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

    @Transactional
    public void deleteById(long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void update(User user){
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(int page, int count, String sortingField, String sortingDirection){
        switch (sortingDirection){
            case "descending":
                return userRepository.findAll(PageRequest.of(page, count, Sort.by(sortingField).descending()));
            default:
                return userRepository.findAll(PageRequest.of(page, count, Sort.by(sortingField).ascending()));
        }
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) throws NotFoundUserOrAccountException {
        return userRepository.findByUsername(username).orElseThrow(() ->  new NotFoundUserOrAccountException("Пользователь не найден"));
    }
}
