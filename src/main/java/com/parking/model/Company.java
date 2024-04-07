package com.parking.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company extends AbstractEntity {

	@Column(name = "companyname")
	private String companyName;

	@Column(name = "companyphonenumber")
	private String companyPhoneNumber;

	@Column(name = "companyaddress")
	private String companyAddress;

	@Column(name = "iscompanyactive")
	private Boolean isCompanyActive;

	@OneToMany(mappedBy = "company")
	private List<Agent> agents;

	@OneToMany(mappedBy = "company")
	private List<Admin> admins;

	@OneToMany(mappedBy = "company")
	private List<ParkingSpace> parkingSpaces;

	@OneToMany(mappedBy = "company")
	private List<ParkingPrice> parkingPrices;
}
