package com.app.examhusky.dto;

import com.app.examhusky.model.Account;
import com.app.examhusky.model.Candidate;

public class CandidateAccountDto {

    private Account account;

    private Candidate candidate;

    public CandidateAccountDto() {
    }

    public CandidateAccountDto(Account account, Candidate candidate) {
        this.account = account;
        this.candidate = candidate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}