package com.parking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deposits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deposit extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
