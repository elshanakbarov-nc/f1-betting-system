package com.betservice.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.betservice.data.model.Bet;
import com.betservice.data.model.User;
import com.betservice.data.repository.BetRepository;
import com.betservice.data.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BetService {
    private final UserRepository userRepository;
    private final BetRepository betRepository;

    public BetService(UserRepository userRepository, BetRepository betRepository) {
        this.userRepository = userRepository;
        this.betRepository = betRepository;
    }

    @Transactional
    public Bet placeBet(Long userId, String eventId, String driverId, BigDecimal amount, int odds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);

        Bet bet = new Bet();
        bet.setUserId(userId);
        bet.setEventId(eventId);
        bet.setDriverId(driverId);
        bet.setAmount(amount);
        bet.setOdds(odds);
        bet.setStatus(Bet.BetStatus.PENDING);

        return betRepository.save(bet);
    }
}
