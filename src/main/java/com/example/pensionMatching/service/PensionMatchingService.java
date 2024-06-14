package com.example.pensionMatching.service;

import com.example.pensionMatching.domain.dto.request.PensionWinAndBonus;

public interface PensionMatchingService {

    void matchingTicket(PensionWinAndBonus drawResult);
}
