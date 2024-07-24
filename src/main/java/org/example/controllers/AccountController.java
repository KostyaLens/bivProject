package org.example.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.exception.NotEnoughFundsException;
import org.example.mappers.AccountMapper;
import org.example.mappers.UserMapper;
import org.example.services.AccountService;
import org.example.services.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController{

    private final AccountService accountService;

    private final UserService userService;

    private final UserMapper userMapper;

    private final AccountMapper accountMapper;

    @GetMapping("/getBalance/{id}")
    public long getBalance(@PathVariable long id){
        return userMapper.toDto(userService.getUserById(id).orElseThrow()).getAccountDto().getBalance();
    }

    @PutMapping("/upBalance/{id}")
    public void upBalance(@PathVariable long  id, @RequestParam @Min(value = 0, message = "Не возможно пополнить баланс отрицаьтельной суммой") long amount){
        accountService.upBalance(id, amount);
    }

    @PutMapping("/downBalance/{id}")
    public void downBalance(@PathVariable long id, @RequestParam @Min(value = 0, message = "Не возможно снять с баланса отрицательную сумму") long amount){
        accountService.downBalance(id, amount);
    }

    @PutMapping("/transfer")
    public void transfer(@RequestParam long fromUserId, @RequestParam long toUserId, @RequestParam @Min(value = 0, message = "Не возможно перевемсти на другой счёт отрицательную сумму") long amount) throws NotEnoughFundsException {
        accountService.downBalance(fromUserId, amount);
        accountService.upBalance(toUserId, amount);
    }
}
