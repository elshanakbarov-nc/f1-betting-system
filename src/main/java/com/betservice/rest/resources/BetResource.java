package com.betservice.rest.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betservice.data.model.Bet;
import com.betservice.rest.request.PlaceBetRequest;
import com.betservice.rest.response.PlaceBetResponse;
import com.betservice.service.BetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bets")
public class BetResource {
    private final BetService betService;
    @PostMapping
    public ResponseEntity<PlaceBetResponse> placeBet(@RequestBody PlaceBetRequest request) {
        try {
            Bet bet = betService.placeBet(request.userId(), request.eventId(), request.driverId(), request.amount(), 3);
            PlaceBetResponse response = new PlaceBetResponse(bet.getId(), "SUCCESS", "Bet placed");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new PlaceBetResponse(null, "FAILED", e.getMessage()));
        }
    }
}
