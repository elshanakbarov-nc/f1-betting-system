package com.betservice.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betservice.data.model.Bet;
import com.betservice.data.model.EventOutcome;
import com.betservice.data.model.User;
import com.betservice.data.repository.BetRepository;
import com.betservice.data.repository.EventOutcomeRepository;
import com.betservice.data.repository.UserRepository;
import com.betservice.messaging.model.EventType;
import com.betservice.messaging.model.kafka.KafkaMessage;
import com.betservice.messaging.producer.Producer;
import com.betservice.provider.ergast.F1EventProvider;
import com.betservice.rest.dto.EventDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService {

    private final Producer<KafkaMessage> producer;
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final EventOutcomeRepository eventOutcomeRepository;
    private final F1EventProvider eventProvider;

    public List<EventDTO> listEvents(Optional<String> sessionType, Optional<String> year, Optional<String> country) {
        return eventProvider.listEvents(sessionType, year, country);
    }

    public void processEventOutcome(String eventId, String winnerDriverId) {
        KafkaMessage message = new KafkaMessage(
                EventType.BET_PLACED,
                Instant.now(),
                eventId,
                winnerDriverId,
                UUID.randomUUID().toString()
        );
        producer.sendMessage(message);
    }

    @Transactional
    public void resolveEventOutcome(String eventId, String winnerDriverId) {
        EventOutcome outcome = new EventOutcome(eventId, winnerDriverId);
        eventOutcomeRepository.save(outcome);

        List<Bet> bets = betRepository.findByEventId(eventId);

        for (Bet bet : bets) {
            User user = userRepository.findById(bet.getUserId())
                    .orElseThrow(() -> new IllegalStateException("User not found for bet"));
            System.out.println("🎯 Evaluating Bet ID: " + bet.getId() + " for User ID: " + user.getId());
            if (bet.getDriverId().equals(winnerDriverId)) {
                System.out.println("🏆 Bet ID won: " + bet.getId());
                bet.setStatus(Bet.BetStatus.WON);
                BigDecimal prize = bet.getAmount().multiply(BigDecimal.valueOf(bet.getOdds()));
                user.setBalance(user.getBalance().add(prize));
                userRepository.save(user);
            } else {
                System.out.println("❌ Bet ID lost: " + bet.getId());
                bet.setStatus(Bet.BetStatus.LOST);
            }
            System.out.println("✅ Bet ID evaluated: " + bet.getId() + " Status: " + bet.getStatus());
            betRepository.save(bet);
        }
    }
}