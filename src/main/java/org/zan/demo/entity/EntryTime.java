package org.zan.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "m_entry_time")
@Getter
@Setter
public class EntryTime extends BaseEntity{
    private Timestamp startEffectiveDate;
    private Timestamp endEffectiveDate;
    private Integer hourIn;
    private Integer hourOut;
    private Integer minuteIn;
    private Integer minuteOut;
    private Boolean isActive = false;
}
