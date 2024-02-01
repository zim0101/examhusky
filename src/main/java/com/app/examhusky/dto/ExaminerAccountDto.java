package com.app.examhusky.dto;

import com.app.examhusky.model.Account;
import com.app.examhusky.model.Examiner;

public class ExaminerAccountDto {

    private Account account;

    private Examiner examiner;

    public ExaminerAccountDto() {
    }

    public ExaminerAccountDto(Account account, Examiner examiner) {
        this.account = account;
        this.examiner = examiner;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Examiner getExaminer() {
        return examiner;
    }

    public void setExaminer(Examiner examiner) {
        this.examiner = examiner;
    }
}