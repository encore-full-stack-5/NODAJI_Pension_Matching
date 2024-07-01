package com.example.pensionMatching.domain.dto.request;

public record WinRequestDto(
    String userId,
    Integer leftMonths,
    Long amount
) {

}
