package com.example.pensionMatching.service;

import com.example.pensionMatching.domain.dto.request.PensionWinAndBonus;
import com.example.pensionMatching.domain.entity.PensionBonusNum;
import com.example.pensionMatching.domain.entity.PurchasedTickets;

public interface TicketService {
    Integer ticketWinMatching(PensionWinAndBonus drawResult, PurchasedTickets purchasedTicket);

    Integer ticketBonusMatching(PensionBonusNum pensionBonusNum, PurchasedTickets purchasedTicket, Integer result);

    Long getPrize(Integer result);
}
