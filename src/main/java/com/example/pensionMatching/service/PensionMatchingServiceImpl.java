package com.example.pensionMatching.service;


import com.example.pensionMatching.api.ApiWinDraw;
import com.example.pensionMatching.domain.dto.request.PensionWinAndBonus;
import com.example.pensionMatching.domain.dto.response.TicketResult;
import com.example.pensionMatching.domain.entity.PensionBonusNum;
import com.example.pensionMatching.domain.entity.PensionWinNum;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import com.example.pensionMatching.domain.repository.PurchasedTicketsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PensionMatchingServiceImpl implements PensionMatchingService{

    private final ApiWinDraw apiWinDraw;
    private final PurchasedTicketsRepository purchasedTicketsRepository;

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
        int result = 0;
        for(PurchasedTickets purchasedTicket : purchasedTickets) {
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
            purchasedTicket.setResult(result);

            if(result == 0){
                ticketBonusMatching(drawResult.pensionBonusNum(), purchasedTicket);
            }

            purchasedTicketsRepository.save(purchasedTicket);
        }
    }

    private void ticketBonusMatching(PensionBonusNum pensionBonusNum,
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
        }
        purchasedTicket.setResult(result);
    }
}
