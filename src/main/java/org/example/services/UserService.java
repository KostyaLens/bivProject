package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exception.NotFoundUserOrAccountException;
import org.example.exception.SortingException;
import org.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(User user){
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
    public Page<User> getAllUsers(int page, int count, List<String> sortingField, List<String> sortingDirection) throws SortingException {
        checkCorrectFieldSort(sortingField, sortingDirection);
        Sort sort = Sort.by(IntStream.range(0, sortingField.size()).mapToObj(i -> {
            String direction = sortingDirection.size() == 1 ? sortingDirection.get(0) : sortingDirection.get(i);
            return "ascending".equalsIgnoreCase(direction) ? Sort.Order.asc(sortingField.get(i)) :
                    Sort.Order.desc(sortingField.get(i));
        }).collect(Collectors.toList()));

        return userRepository.findAll(PageRequest.of(page, count, sort));
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) throws NotFoundUserOrAccountException {
        return userRepository.findByUsername(username).orElseThrow(() ->  new NotFoundUserOrAccountException("Пользователь не найден"));
    }

    private void checkCorrectFieldSort(List<String> sortingField, List<String> sortingDirection) throws SortingException {
        for (String s : sortingField) {
            if ("password".equals(s)) {
                throw new SortingException("данному полю сортировка невозможна");
            }
        }
        if ((sortingDirection.size() > 1) && (sortingDirection.size() != sortingField.size())){
            throw new SortingException("Не соотвествие параметров, не понятно в какую сторону сортировать");
        }
    }
}
