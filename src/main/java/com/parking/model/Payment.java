package com.parking.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "parking_ticket_id",nullable = false)
    private ParkingTicket parkingTicket;

}
