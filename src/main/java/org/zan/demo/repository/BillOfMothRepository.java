package org.zan.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.zan.demo.entity.BillOfMonth;
import org.zan.demo.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface BillOfMothRepository extends JpaRepository<BillOfMonth, UUID> {
    List<BillOfMonth> findAllByCustomer(Customer customer);
    List<BillOfMonth> findAllByCustomerAndStatus(Customer customer,String status);
    List<BillOfMonth> findAllByStatus(String status);
}
