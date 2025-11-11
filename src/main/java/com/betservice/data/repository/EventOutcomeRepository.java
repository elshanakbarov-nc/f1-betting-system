package com.betservice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betservice.data.model.EventOutcome;

public interface EventOutcomeRepository extends JpaRepository<EventOutcome, String> {

}
