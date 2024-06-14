package com.example.pensionMatching.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PENSION_WINNING_NUMS")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PensionWinNum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WINNING_NUM_ID")
    private Long id;

    @Column(name="DRAW_ROUND")
    private int drawRound;

    @Column(name = "FIRST_NUM")
    private int firstNum;

    @Column(name = "SECOND_NUM")
    private int secondNum;


    @Column(name = "THIRD_NUM")
    private int thirdNum;


    @Column(name = "FOURTH_NUM")
    private int fourthNum;


    @Column(name = "FIFTH_NUM")
    private int fifthNum;


    @Column(name = "SIXTH_NUM")
    private int sixthNum;


    @Column(name = "GROUP_NUM")
    private int groupNum;


    @Column(name = "DRAW_DATE")
    private LocalDate drawDate;

    @OneToOne(mappedBy = "pensionWinNum", cascade = CascadeType.ALL)
    private PensionBonusNum pensionBonusNum;

}