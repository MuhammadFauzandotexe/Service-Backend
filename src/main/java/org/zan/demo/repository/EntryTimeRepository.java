package org.zan.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zan.demo.entity.EntryTime;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public interface EntryTimeRepository extends JpaRepository<EntryTime, UUID> {

    @Query("SELECT e FROM EntryTime e WHERE" +
            " e.startEffectiveDate <= :currentTimestamp AND" +
            " e.endEffectiveDate >= :currentTimestamp AND"+
            " isActive=true ORDER BY e.startEffectiveDate DESC")
    Optional<EntryTime> findCurrentEffectiveDate(@Param("currentTimestamp") Timestamp currentTimestamp);
}
