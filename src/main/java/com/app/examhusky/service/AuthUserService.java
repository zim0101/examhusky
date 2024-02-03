package com.app.examhusky.service;

import com.app.examhusky.model.Account;
import com.app.examhusky.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthUserService {

    private static final Logger log = LoggerFactory.getLogger(AuthUserService.class);

    private final AccountRepository accountRepository;

    public AuthUserService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Account currentAuthAccount() {
        return accountRepository.findByUsername(getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    public boolean authUserIsAdminGroup() {
        Authentication authentication = getAuthentication();
        Set<String> userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        List<String> roles = List.of("ROLE_ADMIN");

        log.info("matches: {}", userRoles.stream().anyMatch(roles::contains));
        return userRoles.stream().anyMatch(roles::contains);
    }

    public boolean authUserIsExaminerGroup() {
        Authentication authentication = getAuthentication();
        Set<String> userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        List<String> roles = List.of("ROLE_ADMIN, ROLE_EXAMINER");

        log.info("matches: {}", userRoles.stream().anyMatch(roles::contains));
        return userRoles.stream().anyMatch(roles::contains);
    }

    public boolean authUserIsCandidateGroup() {
        Authentication authentication = getAuthentication();
        Set<String> userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        List<String> roles = List.of("ROLE_CANDIDATE");

        log.info("matches: {}", userRoles.stream().anyMatch(roles::contains));
        return userRoles.stream().anyMatch(roles::contains);
    }
}

