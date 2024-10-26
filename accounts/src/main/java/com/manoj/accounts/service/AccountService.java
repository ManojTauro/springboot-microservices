package com.manoj.accounts.service;

import com.manoj.accounts.dto.CustomerDTO;

public interface AccountService {
    void create(CustomerDTO customerDTO);

    CustomerDTO fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDTO customerDTO);

    boolean deleteAccount(String mobileNumber);
}
