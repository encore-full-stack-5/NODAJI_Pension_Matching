package com.example.pensionMatching.domain.dto.request;

public record KafkaUserDto(
    String id,
    long point,
    String game,
    int rank
) {}