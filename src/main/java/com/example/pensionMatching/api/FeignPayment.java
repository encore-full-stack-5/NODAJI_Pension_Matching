package com.example.pensionMatching.api;

import com.example.pensionMatching.domain.dto.request.WinRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "feignPayment", url = "34.46.237.231:30602")
public interface FeignPayment {

    @PostMapping("/api/v1/pension")
    void winResult(@RequestBody WinRequestDto winRequestDto);

}
