package com.rolando.accounts.service.impl;

import com.rolando.accounts.dto.CustomerDto;
import com.rolando.accounts.repository.AccountsRepository;
import com.rolando.accounts.repository.CustomerRepository;
import com.rolando.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * Create a new account based on the given customer information.
     *
     * @param customerDto CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

    }
}
