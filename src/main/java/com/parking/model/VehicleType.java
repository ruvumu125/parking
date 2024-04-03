package com.parking.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleType extends AbstractEntity {


	@Column(name = "vehiculetypename")
	private String vehiculeTypeName;

	@OneToMany(mappedBy = "vehicleType", cascade = CascadeType.ALL)
	private List<ParkingPrice> parkingPrices;
}
