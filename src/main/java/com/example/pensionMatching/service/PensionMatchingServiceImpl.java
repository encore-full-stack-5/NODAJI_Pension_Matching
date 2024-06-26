package com.example.pensionMatching.service;


import com.example.pensionMatching.api.ApiPayment;
import com.example.pensionMatching.api.ApiWinDraw;
import com.example.pensionMatching.domain.dto.request.KafkaUserDto;
import com.example.pensionMatching.domain.dto.request.PensionWinAndBonus;
import com.example.pensionMatching.domain.dto.response.TicketResult;
import com.example.pensionMatching.domain.entity.PensionBonusNum;
import com.example.pensionMatching.domain.entity.PensionWinNum;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import com.example.pensionMatching.domain.repository.PurchasedTicketsRepository;
import com.example.pensionMatching.kafka.producer.KafkaProducer;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PensionMatchingServiceImpl implements PensionMatchingService, TicketService{

    private final ApiWinDraw apiWinDraw;
    private final ApiPayment apiPayment;
    private final PurchasedTicketsRepository purchasedTicketsRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public void matchingTicket(PensionWinAndBonus drawResult) {
        List<PurchasedTickets> purchasedTickets =
            apiWinDraw.getPurchasedTickets(drawResult.pensionWinNum().getDrawRound());

        List<PurchasedTickets> ticketsList = ticketWinMatching(drawResult, purchasedTickets);

        purchasedTicketsRepository.saveAll(ticketsList);
    }

    @Override
    public List<TicketResult> getAllTicket(String userId) {
        return purchasedTicketsRepository.findByUserId(userId);
    }

    @Override
    public List<TicketResult> getAllTicketByResult(String userId, Integer result) {
        if(result > 0) {
            return purchasedTicketsRepository.findByUserIdAndResultGreaterThan(userId, 0);
        } else {
            return purchasedTicketsRepository.findByUserIdAndResultIs(userId, 0);
        }
    }

    @Override
    public List<PurchasedTickets> ticketWinMatching(PensionWinAndBonus drawResult, List<PurchasedTickets> purchasedTickets) {
        for(PurchasedTickets purchasedTicket : purchasedTickets) {
            Integer result = 0; // 미추첨
            purchasedTicket.setDrawDate(drawResult.pensionWinNum().getDrawDate());

            if(purchasedTicket.getSixth() == drawResult.pensionWinNum().getSixthNum()){
                if(purchasedTicket.getFifth() == drawResult.pensionWinNum().getFifthNum()){
                    if(purchasedTicket.getFourth() == drawResult.pensionWinNum().getFourthNum()){
                        if(purchasedTicket.getThird() == drawResult.pensionWinNum().getThirdNum()){
                            if(purchasedTicket.getSecond() == drawResult.pensionWinNum().getSecondNum()){
                                if(purchasedTicket.getFirst() == drawResult.pensionWinNum().getFirstNum()){
                                    if(purchasedTicket.getGroupNum() == drawResult.pensionWinNum().getGroupNum()){
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
            }else {
                result = -1;
            }

            if(result == -1){
                result = ticketBonusMatching(drawResult.pensionBonusNum(), purchasedTicket, result);
            }

            purchasedTicket.setResult(result);

            if(result > 0) {
                // KafkaUserDto kafkaUserDto = new KafkaUserDto(purchasedTicket.getUserId(),
                KafkaUserDto kafkaUserDto = new KafkaUserDto("11111111-1111-1111-1111-111111111111",
                    getPrize(result), "연금복권", result);
                kafkaProducer.send(kafkaUserDto, "email-topic");

                // apiPayment.winResult(purchasedTicket.getUserId(), result, getPrize(result));
                // apiPayment.winResult("11111111-1111-1111-1111-111111111111", result, getPrize(result));
            }
        }

        return purchasedTickets;
    }

    @Override
    public Integer ticketBonusMatching(PensionBonusNum pensionBonusNum,
        PurchasedTickets purchasedTicket, Integer result) {
        if(purchasedTicket.getSixth() == pensionBonusNum.getSixthNum() &&
            purchasedTicket.getFifth() == pensionBonusNum.getFifthNum() &&
            purchasedTicket.getFourth() == pensionBonusNum.getFourthNum() &&
            purchasedTicket.getThird() == pensionBonusNum.getThirdNum() &&
            purchasedTicket.getSecond() == pensionBonusNum.getSecondNum() &&
            purchasedTicket.getFirst() == pensionBonusNum.getFirstNum()
        ){
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
