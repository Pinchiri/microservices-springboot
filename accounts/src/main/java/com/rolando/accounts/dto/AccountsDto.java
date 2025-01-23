package com.rolando.accounts.dto;

import com.rolando.accounts.constants.AccountsConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Account number can't be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = AccountsConstants.NUMBER_PATTERN_MSG)
    private Long accountNumber;

    @NotEmpty(message = "Account type can't be null or empty")
    private String accountType;

    @NotEmpty(message = "Branch address can't be null or empty")
    private String branchAddress;
}
