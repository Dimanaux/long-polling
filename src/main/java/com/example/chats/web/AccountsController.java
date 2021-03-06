package com.example.chats.web;

import com.example.chats.accounts.Account;
import com.example.chats.accounts.AccountDetails;
import com.example.chats.accounts.Accounts;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AccountsController {
    private final Accounts accounts;

    public AccountsController(Accounts accounts) {
        this.accounts = accounts;
    }

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping(value = "/join", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        accounts.create(username, password);
        return "redirect:/login";
    }

    @GetMapping("/welcome")
    @ResponseBody
    public String welcome(@AuthenticationPrincipal Authentication authentication) {
        Account currentAccount = ((AccountDetails) authentication.getPrincipal()).getAccount();
        return "welcome, " + currentAccount.toMap();
    }

    @GetMapping("/accounts/{id}")
    @ResponseBody
    public ResponseEntity<?> accountInfo(@PathVariable("id") Long id) {
        Optional<Account> account = accounts.findById(id);
        return account
                .map(Account::toMap)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
