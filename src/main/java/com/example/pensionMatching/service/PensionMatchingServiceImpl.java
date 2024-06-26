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

public class PensionMatchingServiceImpl implements PensionMatchingService{

    private final ApiWinDraw apiWinDraw;
    private final ApiPayment apiPayment;
    private final PurchasedTicketsRepository purchasedTicketsRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public void matchingTicket(PensionWinAndBonus drawResult) {
        List<PurchasedTickets> purchasedTickets =
            apiWinDraw.getPurchasedTickets(drawResult.pensionWinNum().getDrawRound());

        ticketWinMatchingAndSave(drawResult, purchasedTickets);
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

    private void ticketWinMatchingAndSave(PensionWinAndBonus drawResult, List<PurchasedTickets> purchasedTickets) {
        for(PurchasedTickets purchasedTicket : purchasedTickets) {
            int result = 0;
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
            }

            if(result == 0){
                result = ticketBonusMatching(drawResult.pensionBonusNum(), purchasedTicket);
            }

            PurchasedTickets ticket = PurchasedTickets.builder()
                .round(purchasedTicket.getRound())
                .userId(purchasedTicket.getUserId())
                .groupNum(purchasedTicket.getGroupNum())
                .first(purchasedTicket.getFirst())
                .second(purchasedTicket.getSecond())
                .third(purchasedTicket.getThird())
                .fourth(purchasedTicket.getFourth())
                .fifth(purchasedTicket.getFifth())
                .sixth(purchasedTicket.getSixth())
                .createAt(LocalDate.now())
                .result(result)
                .drawDate(drawResult.pensionWinNum().getDrawDate())
                .build();

            purchasedTicketsRepository.save(ticket);

            if(result != 0) {
                // KafkaUserDto kafkaUserDto = new KafkaUserDto(ticket.getUserId(),
                KafkaUserDto kafkaUserDto = new KafkaUserDto("11111111-1111-1111-1111-111111111111",
                    getPrize(result), "연금복권", result);
                kafkaProducer.send(kafkaUserDto, "email-topic");

                // apiPayment.winResult(ticket.getUserId(), result, getPrize(result));
                // apiPayment.winResult("11111111-1111-1111-1111-111111111111", result, getPrize(result));
            }
        }
    }

    private Integer ticketBonusMatching(PensionBonusNum pensionBonusNum,
        PurchasedTickets purchasedTicket) {
        int result = 0;
        if(purchasedTicket.getSixth() == pensionBonusNum.getSixthNum() &&
            purchasedTicket.getFifth() == pensionBonusNum.getFifthNum() &&
            purchasedTicket.getFourth() == pensionBonusNum.getFourthNum() &&
            purchasedTicket.getThird() == pensionBonusNum.getThirdNum() &&
            purchasedTicket.getSecond() == pensionBonusNum.getSecondNum() &&
            purchasedTicket.getFirst() == pensionBonusNum.getFirstNum()
        ){
            result = 8;
            return result;
        }
        return result;
    }

    private Long getPrize(Integer result) {
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
