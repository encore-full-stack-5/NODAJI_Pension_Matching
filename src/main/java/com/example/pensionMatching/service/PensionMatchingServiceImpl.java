package com.example.pensionMatching.service;


import com.example.pensionMatching.api.ApiPayment;
import com.example.pensionMatching.domain.dto.request.KafkaUserDto;
import com.example.pensionMatching.domain.dto.request.PensionWinAndBonus;
import com.example.pensionMatching.domain.dto.response.TicketResult;
import com.example.pensionMatching.domain.entity.PensionBonusNum;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import com.example.pensionMatching.domain.repository.PurchasedTicketsRepository;
import com.example.pensionMatching.global.util.TokenInfo;
import com.example.pensionMatching.kafka.producer.KafkaProducer;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PensionMatchingServiceImpl implements PensionMatchingService, TicketService {

    private final ApiPayment apiPayment;
    private final PurchasedTicketsRepository purchasedTicketsRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public void insertTickets(List<PurchasedTickets> purchasedTickets) {
        purchasedTicketsRepository.saveAll(purchasedTickets);
    }

    @Override
    @Transactional
    public void matchingTicket(PensionWinAndBonus drawResult) {
        List<PurchasedTickets> purchasedTickets = purchasedTicketsRepository.findByRound(
            drawResult.pensionWinNum().getDrawRound());
        System.out.println(purchasedTickets);

        purchasedTickets.forEach((purchasedTicket) -> {
            Integer result = ticketWinMatching(drawResult, purchasedTicket);
            purchasedTicket.setResult(result);
            purchasedTicket.setDrawDate(drawResult.pensionWinNum().getDrawDate());
        });

        Set<String> userIdsWithPositiveResult = purchasedTickets.stream()
            .filter(purchasedTicket -> purchasedTicket.getResult() > 0)
            .map(PurchasedTickets::getUserId)
            .collect(Collectors.toSet());

        userIdsWithPositiveResult.forEach(userId -> {
            KafkaUserDto kafkaUserDto = new KafkaUserDto(userId,
                1, "연금복권", 1, drawResult.pensionWinNum().getDrawRound());
            kafkaProducer.send(kafkaUserDto, "email-topic");
        });

    }

    @Override
    public List<TicketResult> getAllTicket(TokenInfo tokenInfo) {
        return purchasedTicketsRepository.findByUserId(tokenInfo.userId());
    }

    @Override
    public List<TicketResult> getAllTicketByResult(TokenInfo tokenInfo, Integer result) {
        if (result > 0) {
            return purchasedTicketsRepository.findByUserIdAndResultGreaterThan(tokenInfo.userId(), 0);
        } else {
            return purchasedTicketsRepository.findByUserIdAndResultIs(tokenInfo.userId(), 0);
        }
    }


    @Override
    public Integer ticketWinMatching(PensionWinAndBonus drawResult,
        PurchasedTickets purchasedTicket) {
        Integer result = 0; // 미추첨
        purchasedTicket.setDrawDate(drawResult.pensionWinNum().getDrawDate());

        if (purchasedTicket.getSixth() == drawResult.pensionWinNum().getSixthNum()) {
            if (purchasedTicket.getFifth() == drawResult.pensionWinNum().getFifthNum()) {
                if (purchasedTicket.getFourth() == drawResult.pensionWinNum().getFourthNum()) {
                    if (purchasedTicket.getThird() == drawResult.pensionWinNum().getThirdNum()) {
                        if (purchasedTicket.getSecond() == drawResult.pensionWinNum()
                            .getSecondNum()) {
                            if (purchasedTicket.getFirst() == drawResult.pensionWinNum()
                                .getFirstNum()) {
                                if (purchasedTicket.getGroupNum() == drawResult.pensionWinNum()
                                    .getGroupNum()) {
                                    result = 1;
                                } else {
                                    result = 2;
                                }
                            } else {
                                result = 3;
                            }
                        } else {
                            result = 4;
                        }
                    } else {
                        result = 5;
                    }
                } else {
                    result = 6;
                }
            } else {
                result = 7;
            }
        } else {
            result = -1;
        }

        if (result == -1) {
            result = ticketBonusMatching(drawResult.pensionBonusNum(), purchasedTicket, result);
        }

        if (result > 0) {
            apiPayment.winResult(purchasedTicket.getUserId(), result, getPrize(result));
        }

        return result;
    }

    @Override
    public Integer ticketBonusMatching(PensionBonusNum pensionBonusNum,
        PurchasedTickets purchasedTicket, Integer result) {
        if (purchasedTicket.getSixth() == pensionBonusNum.getSixthNum() &&
            purchasedTicket.getFifth() == pensionBonusNum.getFifthNum() &&
            purchasedTicket.getFourth() == pensionBonusNum.getFourthNum() &&
            purchasedTicket.getThird() == pensionBonusNum.getThirdNum() &&
            purchasedTicket.getSecond() == pensionBonusNum.getSecondNum() &&
            purchasedTicket.getFirst() == pensionBonusNum.getFirstNum()
        ) {
            result = 8;
        }
        return result;
    }

    @Override
    public Long getPrize(Integer result) {
        if (result == 1)
            return 7_000_000L;
        else if (result == 2 || result == 3 || result == 8)
            return 1_000_0000L;
        else if (result == 4)
            return 100_000L;
        else if (result == 5)
            return 50_000L;
        else if (result == 6)
            return 5_000L;
        else if (result == 7)
            return 1_000L;
        return 0L;
    }

}
