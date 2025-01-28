package org.zan.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zan.demo.entity.User;
import org.zan.demo.entity.WaterGrid;

import java.util.Optional;
import java.util.UUID;

public interface WaterGridRepository extends JpaRepository<WaterGrid, UUID> {
    Optional<WaterGrid> findByUser(User user);
}
