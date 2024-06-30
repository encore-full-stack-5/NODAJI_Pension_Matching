package com.example.pensionMatching.domain.dto.response;

import com.example.pensionMatching.domain.entity.PurchasedTickets;
import java.time.LocalDate;

// 구입일자, 회차, 번호, 당첨결과, 추첨일
public record TicketResult(
    LocalDate createAt,
    Integer round,
    Integer groupNum,
    Integer first,
    Integer second,
    Integer third,
    Integer fourth,
    Integer fifth,
    Integer sixth,
    Integer result,
    LocalDate drawDate
) {
    public PurchasedTickets toEntity(){
        return PurchasedTickets.builder()
            .createAt(createAt)
            .round(round)
            .groupNum(groupNum)
            .first(first)
            .second(second)
            .third(third)
            .fourth(fourth)
            .fifth(fifth)
            .sixth(sixth)
            .result(result)
            .drawDate(drawDate)
            .build();
    }
}
