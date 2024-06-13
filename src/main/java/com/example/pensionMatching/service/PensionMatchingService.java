package com.example.pensionMatching.service;

import com.example.pensionMatching.domain.dto.request.MatchItem;

public interface PensionMatchingService {

    void matchingTicket(MatchItem matchItem);
}
