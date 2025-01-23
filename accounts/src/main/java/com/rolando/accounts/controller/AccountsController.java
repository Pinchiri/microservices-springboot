package com.rolando.accounts.controller;

import com.rolando.accounts.constants.AccountsConstants;
import com.rolando.accounts.dto.CustomerDto;
import com.rolando.accounts.dto.ResponseDto;
import com.rolando.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    private IAccountsService iAccountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccount(customerDto);

        ResponseDto responseDto = new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = AccountsConstants.NUMBER_PATTERN_MSG)
            String mobileNumber
    ) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = AccountsConstants.NUMBER_PATTERN_MSG)
            String mobileNumber
    ) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(
                            AccountsConstants.STATUS_200,
                            AccountsConstants.MESSAGE_200
                    )
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(
                            AccountsConstants.STATUS_500,
                            AccountsConstants.MESSAGE_500
                    )
            );
        }
    }
}

