package com.example.pensionMatching.domain.repository;

import com.example.pensionMatching.domain.entity.PurchasedTickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedTicketsRepository extends JpaRepository<PurchasedTickets, Long> {

}
