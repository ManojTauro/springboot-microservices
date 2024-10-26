package com.manoj.accounts.mapper;

import com.manoj.accounts.dto.AccountDTO;
import com.manoj.accounts.dto.CustomerDTO;
import com.manoj.accounts.model.Customer;

public class CustomerMapper {
    public static CustomerDTO toDTO(Customer customer, AccountDTO accountDTO) {
        return new CustomerDTO(
                customer.getName(),
                customer.getEmail(),
                customer.getMobileNumber(),
                accountDTO
        );
    }

    public static Customer toCustomer(CustomerDTO customerDto, Customer customer) {
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        customer.setMobileNumber(customerDto.mobileNumber());

        return customer;
    }

}
