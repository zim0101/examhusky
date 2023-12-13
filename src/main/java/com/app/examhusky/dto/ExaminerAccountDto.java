package com.app.examhusky.dto;

import com.app.examhusky.model.Account;
import com.app.examhusky.model.Examiner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminerAccountDto {

    private Account account;

    private Examiner examiner;
}