package com.example.service;


import com.example.api.FeignWinDraw;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PensionMatchingServiceImpl implements PensionMatchingService{

    private final FeignWinDraw feignWinDraw;

    @Override
    public void matchingTicket(String userId) {

    }
}
