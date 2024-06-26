// package com.example.pensionMatching.kafka.producer;
//
// import com.example.pensionMatching.domain.dto.request.KafkaUserDto;
// import com.example.pensionMatching.kafka.dto.KafkaStatus;
// import lombok.RequiredArgsConstructor;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.stereotype.Component;
//
// @Component
// @RequiredArgsConstructor
// public class KafkaProducer {
//
//     private final KafkaTemplate<String, KafkaStatus<KafkaUserDto>> kafkaTemplate;
//
//     public void send(KafkaUserDto kafkaUserDto, String topic) {
//         KafkaStatus<KafkaUserDto> kafkaStatus = new KafkaStatus<>(kafkaUserDto, "result");
//         kafkaTemplate.send(topic, kafkaStatus);
//     }
// }
