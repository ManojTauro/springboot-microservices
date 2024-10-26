package com.manoj.accounts.mapper;

import com.manoj.accounts.dto.AccountDTO;
import com.manoj.accounts.model.Account;

public class AccountMapper {
    public static AccountDTO toDTO(Account account) {
       return new AccountDTO(
               account.getAccountNumber(),
               account.getAccountType(),
               account.getBranchAddress()
       );
    }

    public static void toAccount(AccountDTO accountDTO, Account account) {
        account.setAccountNumber(accountDTO.accountNumber());
        account.setAccountType(accountDTO.accountType());
        account.setBranchAddress(accountDTO.branchAddress());

    }
}
