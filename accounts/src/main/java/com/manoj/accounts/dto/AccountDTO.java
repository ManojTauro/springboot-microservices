package com.manoj.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

//@Schema(
//        name = "Accounts",
//        description = "Schema to hold Account information"
//)
public record AccountDTO(
        @NotEmpty
        @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
        //    @Schema(
//            description = "Account Number of Eazy Bank account", example = "3454433243"
//    )
        Long accountNumber,

        @NotEmpty(message = "AccountType can not be a null or empty")
//        @Schema(
//                description = "Account type of Eazy Bank account", example = "Savings"
//        )
        String accountType,

        @NotEmpty(message = "BranchAddress can not be a null or empty")
//        @Schema(
//                description = "Eazy Bank branch address", example = "123 NewYork"
//        )
        String branchAddress
) {

}
