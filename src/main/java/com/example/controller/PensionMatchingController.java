package com.example.controller;


import com.example.service.PensionMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pension")
public class PensionMatchingController {

    private final PensionMatchingService pensionMatchingService;

    @PostMapping("/matching")
    public void matchingTicket(@RequestBody String userId){
        pensionMatchingService.matchingTicket(userId);
    }
}
