package org.example.services;

import org.example.entity.*;
import org.example.exception.NotFoundBankException;
import org.example.exception.WrongPinCodeException;
import org.example.mappers.DebtorAndDepositorMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.exception.NotEnoughFundsException;
import org.example.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final BankAmenitiesService bankAmenitiesService;

    private final DebtorAndDepositorMapper debtorAndDepositorMapper;

    private final DebtorAndDepositorService debtorAndDepositorService;

    private final BankService bankService;

    private static final double ADDITIONAL_PERCENT = 0.05;

    @Transactional
    public Account createAccount(Account account, User user, Bank bank){
        account.setActive(true);
        account.setBank(bank);
        account.setUser(user);
        accountRepository.save(account);
        return account;
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountByUserAndBank(User user, Bank bank){
        return accountRepository.findByUserAndBank(user, bank);
    }

    @Transactional
    public void deposit(Account account, long amount) throws WrongPinCodeException {
        if (!account.isActive()){
            repayDebt(account, account.getBalance(), account.getPinCode());
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Transactional
    public void withdraw(Account account, String pinCode, long amount)
            throws NotEnoughFundsException, WrongPinCodeException {
        checkPinCode(account, pinCode);
        if (account.getBalance() < amount){
            throw new NotEnoughFundsException("Недостаточно средсв");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(Account sender, Account recipient, String pinCode, long amount)
            throws NotEnoughFundsException, WrongPinCodeException {
        withdraw(sender, pinCode, amount);
        deposit(recipient, amount);
    }

    @Transactional
    public void blockAccount(long idAccount){
        accountRepository.deleteById(idAccount);
    }

    @Transactional
    public void issuingCredit(Account account, String nameBank, int serviceNumber, long amount, String pinCode)
            throws WrongPinCodeException, NotFoundBankException {
        DebtorAndDepositor debtorAndDepositor = calculationOfServiceConditions(account, nameBank, serviceNumber, amount, pinCode, TypeBankAmenities.CREDIT);
        debtorAndDepositor.setType(TypeBankAmenities.CREDIT);
        debtorAndDepositorService.save(debtorAndDepositor);
        deposit(account, amount);
        Bank bank = bankService.getBankByName(nameBank);
        bank.setBudget(bank.getBudget() - amount);
        bankService.save(bank);
    }

    @Transactional
    public void makeDeposit(Account account, String nameBank, int serviceNumber, long amount, String pinCode)
            throws WrongPinCodeException, NotFoundBankException, NotEnoughFundsException {
        DebtorAndDepositor debtorAndDepositor = calculationOfServiceConditions(account, nameBank, serviceNumber, amount, pinCode, TypeBankAmenities.DEPOSIT);
        debtorAndDepositor.setType(TypeBankAmenities.DEPOSIT);
        debtorAndDepositorService.save(debtorAndDepositor);
        withdraw(account, pinCode, amount);
        Bank bank = bankService.getBankByName(nameBank);
        bank.setBudget(bank.getBudget() + amount);
        bankService.save(bank);
    }

    @Transactional
    public void repayDebt(Account account, long amount, String pinCode) throws WrongPinCodeException {
        checkPinCode(account, pinCode);
        DebtorAndDepositor debtorAndDepositor = debtorAndDepositorService.getDebtor(account);
        if (amount > debtorAndDepositor.getTotalDebt()){
            amount = debtorAndDepositor.getTotalDebt();
        }
        account.setBalance(account.getBalance() - amount);
        debtorAndDepositor.setTotalDebt(debtorAndDepositor.getTotalDebt() - amount);
        debtorAndDepositorService.save(debtorAndDepositor);
        if (debtorAndDepositor.getTotalDebt() == 0){
            debtorAndDepositorService.delete(debtorAndDepositor);
            account.setActive(true);
        }
        accountRepository.save(account);
        Bank bank = account.getBank();
        bank.setBudget(bank.getBudget() + amount);
        bankService.save(bank);
    }

    public void checkPinCode(Account account, String pinCode) throws WrongPinCodeException {
        if (!pinCode.equals(account.getPinCode())){
            throw new WrongPinCodeException("Неверный пин-код");
        }
    }

    private DebtorAndDepositor calculationOfServiceConditions(Account account, String nameBank, int serviceNumber, long amount,
                                                              String pinCode, TypeBankAmenities typeBankAmenities) throws WrongPinCodeException {
        checkPinCode(account, pinCode);
        BankAmenities bankAmenities = bankAmenitiesService.getBankAmenitiesByNameBankAndServiceNumber(nameBank, serviceNumber, typeBankAmenities);
        DebtorAndDepositor debtorAndDepositor = debtorAndDepositorMapper.toDebtor(account, bankAmenities);
        long debt = (long) (amount + amount * bankAmenities.getPercent() * 0.01);
        debtorAndDepositor.setTotalDebt(debt);
        return debtorAndDepositor;
    }

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void checkingDebtorOrDepositor() throws WrongPinCodeException {
        List<DebtorAndDepositor> debtorAndDepositorList = debtorAndDepositorService.getDebtorAndDepositorAfterDeadline(LocalDateTime.now());
        if (debtorAndDepositorList.size() > 0){
            for (DebtorAndDepositor debtorAndDepositor: debtorAndDepositorList){
                if (debtorAndDepositor.getType().equals(TypeBankAmenities.CREDIT)) {
                    accrualOfAdditionalInterest(debtorAndDepositor);
                }else {
                    returnOfDeposit(debtorAndDepositor);
                }
            }
        }
    }

    private void returnOfDeposit(DebtorAndDepositor debtorAndDepositor) {
        Account account = debtorAndDepositor.getAccount();
        account.setBalance(account.getBalance() + debtorAndDepositor.getTotalDebt());
        Bank bank = account.getBank();
        bank.setBudget(bank.getBudget() - debtorAndDepositor.getTotalDebt());
        accountRepository.save(account);
        debtorAndDepositorService.delete(debtorAndDepositor);
        bankService.save(bank);
    }

    private void accrualOfAdditionalInterest(DebtorAndDepositor debtorAndDepositor) throws WrongPinCodeException {
        Account account = debtorAndDepositor.getAccount();
        account.setActive(false);
        accountRepository.save(account);
        debtorAndDepositor.setTotalDebt((long) (debtorAndDepositor.getTotalDebt() + debtorAndDepositor.getTotalDebt() * ADDITIONAL_PERCENT));
        debtorAndDepositorService.save(debtorAndDepositor);
        repayDebt(account, account.getBalance(), account.getPinCode());
    }
}
