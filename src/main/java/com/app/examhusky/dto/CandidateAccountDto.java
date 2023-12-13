package com.app.examhusky.dto;

import com.app.examhusky.model.Account;
import com.app.examhusky.model.Candidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateAccountDto {

    private Account account;

    private Candidate candidate;
}