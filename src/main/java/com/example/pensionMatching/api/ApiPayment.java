package com.example.pensionMatching.api;

import com.example.pensionMatching.domain.dto.request.WinRequestDto;
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
public class ApiPayment {

    public final FeignPayment feignPayment;
    public static List<Map<String, Object>> fail = new ArrayList<>();

    @Async
    public void winResult(String userId, Integer result, Long amount) {
        Integer month = leftMonths(result);
        // Long amount = getPrize(result);
        WinRequestDto winRequestDto = new WinRequestDto("연금복권", month, amount);
        try {
            feignPayment.winResult(userId, winRequestDto);
        } catch (Exception e) {
            Map<String , Object> map = new HashMap<>();
            map.put("win", winRequestDto);
            fail.add(map);
        }
    }

    // private Long getPrize(Integer result) {
    //     if(result == 1) return 7_000_000L;
    //     else if(result == 2 || result == 3 || result == 8) return 1_000_0000L;
    //     else if(result == 4) return 100_000L;
    //     else if(result == 5) return 50_000L;
    //     else if(result == 6) return 5_000L;
    //     else if(result == 7) return 1_000L;
    //     return 0L;
    // }

    private Integer leftMonths(Integer result) {
        if(result == 1) return 240;
        else if(result == 2 || result == 8) return 120;
        return 1;
    }
}
