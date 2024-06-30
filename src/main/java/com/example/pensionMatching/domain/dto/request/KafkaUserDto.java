package com.example.pensionMatching.domain.dto.request;

public record KafkaUserDto(
    String id,
    long point,
    String game,
    int rank,
    int round
) {
    @Override
    public String toString() {
        return "KafkaUserDto{" +
            "id='" + id + '\'' +
            ", point=" + point +
            ", game='" + game + '\'' +
            ", rank=" + rank +
            ", round=" + round +
            '}';
    }
}