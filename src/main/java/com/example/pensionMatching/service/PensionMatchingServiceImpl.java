package com.example.pensionMatching.service;


import com.example.pensionMatching.api.ApiWinDraw;
import com.example.pensionMatching.api.FeignWinDraw;
import com.example.pensionMatching.domain.dto.request.MatchItem;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PensionMatchingServiceImpl implements PensionMatchingService{

    private final ApiWinDraw apiWinDraw;

    @Override
    public void matchingTicket(MatchItem matchItem) {
        List<PurchasedTickets> purchasedTickets =
            apiWinDraw.getPurchasedTickets(matchItem.round(), matchItem.userId());

        Object drawByRound = apiWinDraw.getDrawByRound(matchItem.round());
    }
}
