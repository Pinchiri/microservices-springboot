package com.rolando.accounts.dto;

import com.rolando.accounts.constants.AccountsConstants;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDto {
    @NotEmpty(message = "Name can't be null or empty")
    @Size(min = 5, max = 30, message = "The name of the customer should be between 5 and 30 characters")
    private String name;

    @NotEmpty(message = "Email address can't be null or empty")
    @Email(message = "Email address should be in the correct format")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = AccountsConstants.NUMBER_PATTERN_MSG)
    private String mobileNumber;

    private AccountsDto accountsDto;
}
