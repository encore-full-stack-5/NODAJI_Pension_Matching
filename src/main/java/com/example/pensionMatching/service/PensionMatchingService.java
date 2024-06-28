package com.example.pensionMatching.service;

import com.example.pensionMatching.domain.dto.request.PensionWinAndBonus;
import com.example.pensionMatching.domain.dto.response.TicketResult;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import com.example.pensionMatching.global.util.TokenInfo;
import java.util.List;

public interface PensionMatchingService {

    void matchingTicket(PensionWinAndBonus drawResult);

    List<TicketResult> getAllTicket(TokenInfo tokenInfo);

    List<TicketResult> getAllTicketByResult(TokenInfo tokenInfo, Integer result);

    void insertTickets(List<PurchasedTickets> purchasedTickets);
}
