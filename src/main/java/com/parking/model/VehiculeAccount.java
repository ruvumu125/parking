package com.parking.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicule_accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeAccount extends AbstractEntity {

    @Column(name = "accountnumber",updatable = false)
    private String accountNumber;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "vehicule_id",nullable = false)
    private Vehicle vehicle;

    @Column(name = "opendate",updatable = false)
    private Instant openDate;

    @Lob
    @Column(columnDefinition = "LONGBLOB",name = "qrcodeimage",updatable = false)
    private byte[] qrCodeImage;


//    @Column(columnDefinition = "bytea",name = "qrcodeimage",updatable = false)
//    private byte[] qrCodeImage;

    @Column(name = "qrcodestring",updatable = false)
    private String qrCodeString;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
}
