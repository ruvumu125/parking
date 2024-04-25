package com.parking.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.parking.model.ParkingTicket;
import com.parking.model.ParkingTicketPaymentStatusEnum;
import com.parking.model.ParkingTicketStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingTicketDto {

    private Long id;
    private String parkingTicketNumber;
    private ParkingSpaceDto parkingSpace;
    private AgentDto agent;
    private CompanyDto company;
    private VehicleDto vehicle;
    private Instant entryTime;
    private Instant exitTime;
    private BigDecimal unitPrice;
    private BigDecimal fareAmount;
    private double duration;
    private ParkingTicketStatusEnum parkingTicketStatusEnum;
    private ParkingTicketPaymentStatusEnum parkingTicketPaymentStatusEnum;

    public static ParkingTicketDto fromEntity(ParkingTicket parkingTicket) {
        if(parkingTicket == null) {
            return null;
        }

        return ParkingTicketDto.builder()
                .id(parkingTicket.getId())
                .parkingTicketNumber(parkingTicket.getParkingTicketNumber())
                .parkingSpace(ParkingSpaceDto.fromEntity(parkingTicket.getParkingSpace()))
                .agent(AgentDto.fromEntity(parkingTicket.getAgent()))
                .company(CompanyDto.fromEntity(parkingTicket.getCompany()))
                .vehicle(VehicleDto.fromEntity(parkingTicket.getVehicle()))
                .entryTime(parkingTicket.getEntryTime())
                .exitTime(parkingTicket.getExitTime())
                .unitPrice(parkingTicket.getUnitPrice())
                .fareAmount(parkingTicket.getFareAmount())
                .duration(parkingTicket.getDuration())
                .parkingTicketPaymentStatusEnum(parkingTicket.getParkingTicketPaymentStatusEnum())
                .parkingTicketStatusEnum(parkingTicket.getParkingTicketStatusEnum())
                .build();
    }

    public static ParkingTicket toEntity(ParkingTicketDto parkingTicketDto) {
        if(parkingTicketDto == null) {
            return null;
        }

        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicket.setId(parkingTicketDto.getId());
        parkingTicket.setParkingTicketNumber(parkingTicketDto.getParkingTicketNumber());
        parkingTicket.setParkingSpace(ParkingSpaceDto.toEntity(parkingTicketDto.getParkingSpace()));
        parkingTicket.setAgent(AgentDto.toEntity(parkingTicketDto.getAgent()));
        parkingTicket.setCompany(CompanyDto.toEntity(parkingTicketDto.getCompany()));
        parkingTicket.setVehicle(VehicleDto.toEntity(parkingTicketDto.getVehicle()));
        parkingTicket.setEntryTime(parkingTicketDto.getEntryTime());
        parkingTicket.setExitTime(parkingTicketDto.getExitTime());
        parkingTicket.setUnitPrice(parkingTicketDto.getUnitPrice());
        parkingTicket.setDuration(parkingTicketDto.getDuration());
        parkingTicket.setParkingTicketPaymentStatusEnum(parkingTicketDto.getParkingTicketPaymentStatusEnum());
        parkingTicket.setParkingTicketStatusEnum(parkingTicketDto.getParkingTicketStatusEnum());

        return parkingTicket;
    }
}
