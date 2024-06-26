package com.example.pensionMatching.api;

import com.example.pensionMatching.domain.dto.request.WinRequestDto;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "feignPayment", url = "192.168.0.10:8080")
public interface FeignPayment {

    @PostMapping("/api/v1/win/{userId}")
    void winResult(@PathVariable String userId, @RequestBody WinRequestDto winRequestDto);

}
