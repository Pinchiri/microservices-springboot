package com.rolando.accounts.service;

import com.rolando.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Create a new account based on the given customer information.
     *
     * @param customerDto CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);
}
