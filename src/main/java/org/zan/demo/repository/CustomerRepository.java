package org.zan.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zan.demo.entity.Customer;
import org.zan.demo.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByUser(User user);
}
