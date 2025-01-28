package org.zan.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "m_water_grid")
@Setter
@Getter
public class WaterGrid {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private OffsetDateTime cratedAt;

    private OffsetDateTime updatedAt;

    private BigDecimal totalCurrentUsage;

    private BigDecimal currentUsageThisMount;

    private Boolean status;

    @OneToOne(mappedBy = "waterGrid")
    private User user;

    @PrePersist
    private void setCreatedAt(){
        this.cratedAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    private void setUpdatedAt(){
        this.updatedAt = OffsetDateTime.now();
    }

}
