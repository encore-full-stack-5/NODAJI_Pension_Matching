package com.example.pensionMatching.api;

import com.example.pensionMatching.domain.entity.PensionWinNum;
import com.example.pensionMatching.domain.entity.PurchasedTickets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiWinDraw {

    public final FeignWinDraw feignWinDraw;
    public static List<Map<String, Object>> failTicketList = new ArrayList<>();
    public static List<Map<String, Object>> failDrawList = new ArrayList<>();

    @Async
    public List<PurchasedTickets> getPurchasedTickets(Integer round) {
        List<PurchasedTickets> pensionBuyingTickets = null;
        try {
            pensionBuyingTickets = feignWinDraw.getPensionBuyingTickets(round);
        } catch (Exception e) {
            Map<String , Object> map = new HashMap<>();
            map.put("round", round);
            failTicketList.add(map);
        }

        return pensionBuyingTickets;
    }

}
