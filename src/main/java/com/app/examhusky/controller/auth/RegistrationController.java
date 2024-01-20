package com.app.examhusky.controller.auth;

import com.app.examhusky.model.Account;
import com.app.examhusky.service.AccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class RegistrationController {

    private final AccountService accountService;

    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public Account addAccountToModel() {
        return new Account();
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "auth/register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute Account account, BindingResult result) {
        if (result.hasErrors()) return "auth/register";

        if (accountService.accountExistWithEmail(account.getEmail())) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
            return "auth/register";
        }

        accountService.registerCandidateAccount(account);

        return "redirect:/login";
    }
}
