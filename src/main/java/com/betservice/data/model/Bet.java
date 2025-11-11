package com.betservice.data.model;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String eventId;

    @Column(nullable = false)
    private String driverId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private int odds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BetStatus status;

    public enum BetStatus {
        PENDING, WON, LOST
    }
}
