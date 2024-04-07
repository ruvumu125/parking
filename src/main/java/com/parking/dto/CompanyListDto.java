package com.parking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parking.model.Admin;
import com.parking.model.AdminTypeEnum;
import com.parking.model.Agent;
import com.parking.model.Company;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyListDto {

    private Long id;
    private String companyName;
    private String companyPhoneNumber;
    private String companyAddress;
    private String adminName;
    private Boolean isCompanyActive;

    @JsonIgnore
    private List<Agent> agents;
    @JsonIgnore
    private List<Agent> admins;

    public static CompanyListDto fromEntity(Company company) {
        if(company == null) {
            return null;
        }

        List<Admin> admins=company.getAdmins();
        String nono="";
        for (Admin admin : admins) {
            if (admin.getAdminTypeEnum().equals(AdminTypeEnum.MAIN_ADMIN)) {
                nono = admin.getUser().getUserFullName();
            }
        }

        return CompanyListDto.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .companyPhoneNumber(company.getCompanyPhoneNumber())
                .companyAddress(company.getCompanyAddress())
                .adminName(nono)
                .isCompanyActive(company.getIsCompanyActive())
                .build();
    }
}
