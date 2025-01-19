package com.rolando.accounts.service.impl;

import com.rolando.accounts.constants.AccountsConstants;
import com.rolando.accounts.dto.AccountsDto;
import com.rolando.accounts.dto.CustomerDto;
import com.rolando.accounts.entity.Accounts;
import com.rolando.accounts.entity.Customer;
import com.rolando.accounts.exception.CustomerAlreadyExistsException;
import com.rolando.accounts.exception.ResourceNotFoundException;
import com.rolando.accounts.mapper.AccountsMapper;
import com.rolando.accounts.mapper.CustomerMapper;
import com.rolando.accounts.repository.AccountsRepository;
import com.rolando.accounts.repository.CustomerRepository;
import com.rolando.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number: " + customerDto.getMobileNumber());
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));

        return customerDto;
    }

    /**
     *
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if(accountsDto != null) {
           Accounts account = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                   () -> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber().toString())
           );
           AccountsMapper.mapToAccounts(accountsDto, account);
           account = accountsRepository.save(account);

           Long customerId = account.getCustomerId();
           Customer customer = customerRepository.findById(customerId).orElseThrow(
                   () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
           );
           CustomerMapper.mapToCustomer(customerDto, customer);
           customerRepository.save(customer);
           isUpdated = true;
        }
        return isUpdated;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = generateRandomAccountNumber();

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }

    private long generateRandomAccountNumber() {
        return 1000000000L + new Random().nextInt(900000000);
    }
}
