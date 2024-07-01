package com.example.pensionMatching.domain.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PensionWinNum {

    private Long id;

    private int drawRound;

    private int firstNum;

    private int secondNum;

    private int thirdNum;

    private int fourthNum;

    private int fifthNum;

    private int sixthNum;

    private int groupNum;

    private LocalDate drawDate;
}