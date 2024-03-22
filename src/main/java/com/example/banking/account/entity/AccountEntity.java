package com.example.banking.account.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountEntity {

    private String account;
    private String branchName;
    private String brandCode;
    private long amount;

}

