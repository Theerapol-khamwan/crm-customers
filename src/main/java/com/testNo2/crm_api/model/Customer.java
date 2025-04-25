package com.testNo2.crm_api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.testNo2.crm_api.dto.CustomerSalesRankDTO;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SqlResultSetMapping(
	    name = "CustomerSalesRankMapping",
	    classes = @ConstructorResult(
	        targetClass = CustomerSalesRankDTO.class,
	        columns = {
	            @ColumnResult(name = "CustomerId", type = Long.class),
	            @ColumnResult(name = "SaleDate", type = Date.class), 
	            @ColumnResult(name = "MaxSaleAmount", type = BigDecimal.class),
	            @ColumnResult(name = "SalesRank", type = Integer.class)
	        }
	    )
	)
@Entity
@Table(name = "Customers", schema = "dbo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CustomerId")
	private Long customerId;

	@Column(name = "Firstname", nullable = false, length = 100)
	private String firstname;
	
	@Column(name = "Lastname", nullable = false, length = 100)
	private String lastname;
	
	@Column(name = "CustomerDate")
	private LocalDate customerDate;
	
	@Column(name = "IsVIP")
	private Boolean isVIP;
	
	@Column(name = "StatusCode", length = 50)
	private String statusCode;
	
	@CreationTimestamp
    @Column(name = "CreatedOn", updatable = false)
	private LocalDateTime createdOn;
	
	@UpdateTimestamp
    @Column(name = "ModifiedOn")
	private LocalDateTime modifiedOn;

}
