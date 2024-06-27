package com.example.pensionMatching.service;

import com.example.pensionMatching.domain.dto.request.PensionWinAndBonus;
import com.example.pensionMatching.domain.dto.response.TicketResult;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import java.util.List;

public interface PensionMatchingService {

    void matchingTicket(PensionWinAndBonus drawResult);

    List<TicketResult> getAllTicket(String userId);

    List<TicketResult> getAllTicketByResult(String userId, Integer result);

    void insertTickets(List<PurchasedTickets> purchasedTickets);
}
