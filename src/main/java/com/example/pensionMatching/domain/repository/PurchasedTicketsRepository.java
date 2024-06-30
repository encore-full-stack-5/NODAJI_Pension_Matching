package com.example.pensionMatching.domain.repository;

import com.example.pensionMatching.domain.dto.response.TicketResult;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedTicketsRepository extends JpaRepository<PurchasedTickets, Long> {

    List<TicketResult> findByUserId(String userId);

    List<TicketResult> findByUserIdAndResultGreaterThan(String userId, Integer result);

    List<TicketResult> findByUserIdAndResultIs(String userId, Integer result);

    List<PurchasedTickets> findByRound(Integer drawRound);
}
