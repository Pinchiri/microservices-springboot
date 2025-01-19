package com.rolando.accounts.service;

import com.rolando.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Create a new account based on the given customer information.
     *
     * @param customerDto CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on given mobileNumber
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);
}
