package org.zan.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "t_bill_of_month")
@Getter
@Setter
public class BillOfMonth extends BaseEntity{

    private Integer year;

    private Integer month;

    private String status;

    private String bookingId;

    private String snapUrl;

    private BigDecimal amount;

    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
