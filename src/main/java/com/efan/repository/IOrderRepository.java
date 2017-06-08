package com.efan.repository;

import com.efan.core.primary.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {

    Page<Order> findAllByPayKey(String payKey, Pageable pageable);
    Order findByOrderNumber(String orderNumber);
}