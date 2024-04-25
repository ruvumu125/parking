package com.parking.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking_tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingTicket extends AbstractEntity {

    @Column(name = "parkingticketnumber")
    private String parkingTicketNumber;

    @ManyToOne
    @JoinColumn(name = "parking_space_id")
    private ParkingSpace parkingSpace;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(name = "entry_time")
    private Instant entryTime;

    @Column(name = "exit_time",nullable = true)
    private Instant exitTime;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "fare_amount",nullable = true)
    private BigDecimal fareAmount;

    @Column(name = "duration",nullable = true)
    private double duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "parkingticketstatus")
    private ParkingTicketStatusEnum parkingTicketStatusEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "parkingticketpaymentstatus")
    private ParkingTicketPaymentStatusEnum parkingTicketPaymentStatusEnum;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "parkingTicket")
    private Payment payment;
}
