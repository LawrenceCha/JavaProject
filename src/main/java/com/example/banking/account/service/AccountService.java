package com.example.banking.account.service;

import com.example.banking.account.entity.AccountEntity;
import com.example.banking.dao.AccountDao;
import com.example.banking.dao.UserInfoDao;
import com.example.banking.dto.Account;
import com.example.banking.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserInfoDao userInfoDao;
    private final AccountDao accountDao;

    public List<AccountEntity> getAccountInfo(String id) {
        List<Account> accountList =  accountDao.findByUserId(id);

        List<AccountEntity> accountEntities = new ArrayList<>();

        for(Account account : accountList) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setAccount(account.getAccount());
            accountEntity.setBranchName("신주쿠");
            accountEntity.setBrandCode(account.getBranchCode());
            accountEntity.setAmount(account.getAmount());
            accountEntities.add(accountEntity);
        }

        return accountEntities;
    }
}
