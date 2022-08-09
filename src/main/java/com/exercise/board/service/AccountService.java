package com.exercise.board.service;

import com.exercise.board.entity.Account;
import com.exercise.board.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Boolean Login(Account account)
    {
        return accountRepository.existsByIdAndPassword(account.getId(), account.getPassword());
    }

    public void Register(Account account)
    {
        accountRepository.save(account);
    }
}
