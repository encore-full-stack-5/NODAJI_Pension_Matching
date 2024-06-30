package com.example.pensionMatching.controller;


import com.example.pensionMatching.domain.dto.request.PensionWinAndBonus;
import com.example.pensionMatching.domain.dto.response.TicketResult;
import com.example.pensionMatching.domain.entity.PensionWinNum;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import com.example.pensionMatching.global.util.TokenInfo;
import com.example.pensionMatching.service.PensionMatchingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*"
    ,methods = {
    RequestMethod.GET,
    RequestMethod.POST,
    RequestMethod.DELETE,
    RequestMethod.PUT,
    RequestMethod.OPTIONS}, allowedHeaders = "*")
@RequestMapping("/api/v1/pension")
public class PensionMatchingController {

    private final PensionMatchingService pensionMatchingService;

    //Todo: 추첨 서버에서 Feign 요청하기
    @PostMapping("/matching")
    public void matchingTicket(@RequestBody PensionWinAndBonus drawResult){
        pensionMatchingService.matchingTicket(drawResult);
    }

    // @PostMapping("/purchased")
    // public void purchasedTicket(@RequestBody List<PurchasedTickets> purchasedTickets){
    //     pensionMatchingService.insertTickets(purchasedTickets);
    // }

    @GetMapping
    public List<TicketResult> getAllTicket(
        @AuthenticationPrincipal TokenInfo tokenInfo,
        @RequestParam(value = "result", required = false) Integer result) {

        if (result != null) {
            return pensionMatchingService.getAllTicketByResult(tokenInfo, result);
        } else {
            return pensionMatchingService.getAllTicket(tokenInfo);
        }
    }

}
