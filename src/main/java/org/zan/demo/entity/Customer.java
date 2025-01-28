package org.zan.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "customer")
@Setter
@Getter
public class Customer extends BaseEntity{

    private String deviceId;

    private String email;

    private String address;

    @OneToOne(mappedBy = "customer")
    private User user;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<BillOfMonth> billOfMonths;
}
