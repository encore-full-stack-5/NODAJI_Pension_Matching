package com.example.pensionMatching.domain.dto.request;

public record WinRequestDto(
    String type,
    Integer leftMonths,
    Long amount
) {

}
