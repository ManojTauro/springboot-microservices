package com.manoj.accounts.service.impl;

import com.manoj.accounts.constants.AccountConstants;
import com.manoj.accounts.dto.AccountDTO;
import com.manoj.accounts.dto.CustomerDTO;
import com.manoj.accounts.exception.CustomerExistsException;
import com.manoj.accounts.exception.ResourceNotFoundException;
import com.manoj.accounts.mapper.AccountMapper;
import com.manoj.accounts.mapper.CustomerMapper;
import com.manoj.accounts.model.Account;
import com.manoj.accounts.model.Customer;
import com.manoj.accounts.repository.AccountRepository;
import com.manoj.accounts.repository.CustomerRepository;
import com.manoj.accounts.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void create(CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.mobileNumber());

        if (optionalCustomer.isPresent())
            throw new CustomerExistsException("Customer already registered with given mobile number " + customerDTO.mobileNumber());

        Customer customer = CustomerMapper.toCustomer(customerDTO, new Customer());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");

        Customer savedCustomer = customerRepository.save(customer);

        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");

        return newAccount;
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("%s not found with the given input data %s: '%s'", "Customer", "mobileNumber", mobileNumber)
                )
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("%s not found with the given input data %s: '%s'", "Account", "customerId", customer.getCustomerId().toString())
                )
        );

        AccountDTO accountDTO = AccountMapper.toDTO(account);

        return CustomerMapper.toDTO(customer, accountDTO);
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountDTO accountDto = customerDTO.accountDTO();

        if (accountDto != null) {
            Account accounts = accountRepository.findById(accountDto.accountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            String.format("%s not found with the given input data %s : '%s'", "Account", "AccountNumber", accountDto.accountNumber())
                    )
            );

            AccountMapper.toAccount(accountDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException(
                            String.format("%s not found with the given input data %s : '%s'", "Customer", "CustomerID", customerId)
                    )
            );

            CustomerMapper.toCustomer(customerDTO, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("%s not found with the given input data %s : '%s'", "Customer", "mobileNumber", mobileNumber)
                )
        );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }
}
