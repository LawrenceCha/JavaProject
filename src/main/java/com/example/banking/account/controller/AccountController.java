package com.example.banking.account.controller;

import com.example.banking.account.entity.AccountEntity;
import com.example.banking.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/")
    public String account(Model model, @SessionAttribute(name = "id") String id) {

        List<AccountEntity> accountEntityList =  accountService.getAccountInfo(id);

        model.addAttribute("id", id);

        model.addAttribute("accountList", accountEntityList);

        return "function/main";

    }

}
