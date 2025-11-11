package com.betservice.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betservice.data.model.Bet;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findByUserId(Long userId);
    List<Bet> findByEventId(String eventId);
}

