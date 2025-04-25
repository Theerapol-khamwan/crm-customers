package com.testNo2.crm_api.dto;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSalesRankDTO {
	private Long customerId;
	private Date saleDate;
    private BigDecimal maxSale;
    private Integer saleRank;
    
}
