package com.example.pensionMatching.api;

import com.example.pensionMatching.domain.entity.PensionWinNum;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "feignWinDraw", url = "localhost:8081")
public interface FeignWinDraw {

    @GetMapping("/api/v1/pension/purchased")
    List<PurchasedTickets> getPensionBuyingTickets(@RequestParam Integer round);

    // @GetMapping("/api/v1/pension/draw/{round}")
    // PensionWinNum getDrawByRound(@PathVariable Integer round);
}
